package com.baisylia.modestmining.item.custom.weapons;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import com.baisylia.modestmining.item.ModTiers;
import com.baisylia.modestmining.sounds.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class JavelinItem extends Item {
    private final Tier tier;
    private final float attackDamage;

    public JavelinItem(Tier tier, float attackDamage, float attackSpeed, float reach, Properties properties) {
        super(properties.durability(tier.getUses()).attributes(createAttributes(tier, attackDamage, attackSpeed, reach)));
        this.tier = tier;
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();
    }

    private static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed, float reach) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "javelin_reach"), reach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public Tier getTier() {
        return this.tier;
    }

    public float getAttackDamage() {
        return 1.0F + this.attackDamage;
    }

    public float getThrowDamage() {
        double multiplier = ModConfig.SPEC.isLoaded() ? ModConfig.JAVELIN_RANGED_DAMAGE_MULTIPLIER.get() : 1.0D;
        return (float) (this.getAttackDamage() * multiplier);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return this.tier.getRepairIngredient().test(repairCandidate) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isAutoSpinAttack()) {
            return InteractionResultHolder.pass(itemstack);
        } else if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0F && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10) {
                float riptideStrength = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
                if (!(riptideStrength > 0.0F) || player.isInWaterOrRain()) {
                    Holder<SoundEvent> soundHolder;
                    if (riptideStrength > 0.0F) {
                        soundHolder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
                                .orElse(SoundEvents.TRIDENT_RIPTIDE_1);
                    } else if (this.tier == Tiers.WOOD || this.tier == Tiers.STONE || this.tier == ModTiers.FLINT) {
                        soundHolder = ModSounds.JAVELIN_THROW_CRUDE;
                    } else {
                        soundHolder = ModSounds.JAVELIN_THROW;
                    }
                    if (!level.isClientSide()) {
                        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                        if (riptideStrength == 0.0F) {
                            ThrownJavelinEntity javelin = new ThrownJavelinEntity(level, player, stack);
                            javelin.setBaseDamage(this.getThrowDamage());
                            if ((player.fallDistance > 0.0F && !player.onGround()) || player.isSprinting()) {
                                javelin.setCritArrow(true);
                            }
                            javelin.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                            if (player.getAbilities().instabuild) {
                                javelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }
                            level.addFreshEntity(javelin);
                            level.playSound(null, javelin.getX(), javelin.getY(), javelin.getZ(), soundHolder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!player.getAbilities().instabuild) {
                                player.getInventory().removeItem(stack);
                            }
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (riptideStrength > 0.0F) {
                        float f7 = player.getYRot();
                        float f = player.getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                        f1 *= riptideStrength / f4;
                        f2 *= riptideStrength / f4;
                        f3 *= riptideStrength / f4;
                        player.push(f1, f2, f3);
                        player.startAutoSpinAttack(20, 8.0F, stack);
                        if (player.onGround()) {
                            float f6 = 1.1999999F;
                            player.move(MoverType.SELF, new Vec3(0.0D, f6, 0.0D));
                        }

                        level.playSound(null, player, soundHolder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.LOYALTY)) {
            return true;
        }
        if (enchantment.is(Enchantments.RIPTIDE) && this.tier == ModTiers.PRISMARITE) {
            return true;
        }
        if (enchantment.is(Enchantments.CHANNELING) && this.tier == ModTiers.VALKYRIUM) {
            return true;
        }
        if (enchantment.is(Enchantments.IMPALING) || enchantment.is(Enchantments.CHANNELING) || enchantment.is(Enchantments.RIPTIDE)) {
            return false;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.LOYALTY)) {
            return true;
        }
        if (enchantment.is(Enchantments.RIPTIDE) && this.tier == ModTiers.PRISMARITE) {
            return true;
        }
        if (enchantment.is(Enchantments.CHANNELING) && this.tier == ModTiers.VALKYRIUM) {
            return true;
        }
        if (enchantment.is(Enchantments.IMPALING) || enchantment.is(Enchantments.CHANNELING) || enchantment.is(Enchantments.RIPTIDE)) {
            return false;
        }
        return super.isPrimaryItemFor(stack, enchantment);
    }
}