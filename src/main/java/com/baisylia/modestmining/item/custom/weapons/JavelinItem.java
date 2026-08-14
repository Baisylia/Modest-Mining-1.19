package com.baisylia.modestmining.item.custom.weapons;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import com.baisylia.modestmining.item.ModTiers;
import com.baisylia.modestmining.sounds.ModSounds;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class JavelinItem extends Item implements Vanishable {

    private static final UUID ATTACK_RANGE_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");

    private final Tier tier;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final float attackDamage;

    public JavelinItem(Tier tier, float attackDamage, float attackSpeed, float reach, Properties properties) {
        super(properties);
        this.tier = tier;
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ATTACK_RANGE_MODIFIER, "Weapon modifier", reach, AttributeModifier.Operation.ADDITION));

        this.defaultModifiers = builder.build();
    }

    public Tier getTier() {
        return this.tier;
    }

    public float getAttackDamage() {
        return 1.0F + this.attackDamage;
    }

    public float getAttackDamage(ItemStack stack) {
        if (stack.isEmpty()) {
            return this.getAttackDamage();
        }
        Multimap<Attribute, AttributeModifier> modifiers = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        Collection<AttributeModifier> damageModifiers = modifiers.get(Attributes.ATTACK_DAMAGE);
        if (damageModifiers.isEmpty()) {
            return this.getAttackDamage();
        }
        double base = 1.0D;
        double addValue = 0.0D;
        double multBase = 0.0D;
        double multTotal = 1.0D;

        for (AttributeModifier modifier : damageModifiers) {
            switch (modifier.getOperation()) {
                case ADDITION -> addValue += modifier.getAmount();
                case MULTIPLY_BASE -> multBase += modifier.getAmount();
                case MULTIPLY_TOTAL -> multTotal *= (1.0D + modifier.getAmount());
            }
        }
        double total = (base + addValue) * (1.0D + multBase) * multTotal;
        return (float) total;
    }

    public float getThrowDamage(ItemStack stack) {
        double multiplier = ModConfig.SPEC.isLoaded() ? ModConfig.JAVELIN_RANGED_DAMAGE_MULTIPLIER.get() : 1.0D;
        return (float) (this.getAttackDamage(stack) * multiplier);
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return HumanoidModel.ArmPose.THROW_SPEAR;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }
        });
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.isAutoSpinAttack()) {
            return InteractionResultHolder.pass(itemstack);
        } else if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !pPlayer.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double) pState.getDestroySpeed(pLevel, pPos) != 0.0D) {
            pStack.hurtAndBreak(2, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                int riptideLevel = EnchantmentHelper.getRiptide(pStack);
                if (riptideLevel <= 0 || player.isInWaterOrRain()) {
                    if (!pLevel.isClientSide) {
                        pStack.hurtAndBreak(1, player, (p_43388_) -> {
                            p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
                        });
                        if (riptideLevel == 0) {
                            ThrownJavelinEntity javelin = new ThrownJavelinEntity(pLevel, player, pStack);
                            javelin.setBaseDamage(this.getThrowDamage(pStack));
                            if ((player.fallDistance > 0.0F && !player.isOnGround()) || player.isSprinting()) {
                                javelin.setCritArrow(true);
                            }
                            javelin.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                            if (player.getAbilities().instabuild) {
                                javelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }
                            pLevel.addFreshEntity(javelin);
                            SoundEvent throwSound = (this.tier == Tiers.WOOD || this.tier == Tiers.STONE)
                                    ? ModSounds.JAVELIN_THROW_CRUDE.get()
                                    : ModSounds.JAVELIN_THROW.get();
                            pLevel.playSound(null, javelin, throwSound, SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!player.getAbilities().instabuild) {
                                player.getInventory().removeItem(pStack);
                            }
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (riptideLevel > 0) {
                        float f7 = player.getYRot();
                        float f = player.getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                        float f5 = 3.0F * ((1.0F + (float) riptideLevel) / 4.0F);
                        f1 *= f5 / f4;
                        f2 *= f5 / f4;
                        f3 *= f5 / f4;
                        player.push(f1, f2, f3);
                        player.startAutoSpinAttack(20);
                        if (player.isOnGround()) {
                            float f6 = 1.1999999F;
                            player.move(MoverType.SELF, new Vec3(0.0D, f6, 0.0D));
                        }

                        SoundEvent soundevent;
                        if (riptideLevel >= 3) {
                            soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
                        } else if (riptideLevel == 2) {
                            soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
                        } else {
                            soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
                        }

                        pLevel.playSound(null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.LOYALTY) {
            return true;
        }
        if (enchantment == Enchantments.RIPTIDE && this.tier == ModTiers.PRISMARITE) {
            return true;
        }
        if (enchantment == Enchantments.CHANNELING && this.tier == ModTiers.VALKYRIUM) {
            return true;
        }
        if (enchantment.category == EnchantmentCategory.TRIDENT) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}