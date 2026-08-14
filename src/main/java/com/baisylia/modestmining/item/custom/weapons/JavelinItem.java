package com.baisylia.modestmining.item.custom.weapons;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class JavelinItem extends Item {
    private final Tier tier;
    private final float throwDamage;

    public JavelinItem(Tier tier, float attackDamage, float attackSpeed, float reach, Properties properties) {
        super(properties.durability(tier.getUses()).attributes(createAttributes(tier, attackDamage, attackSpeed, reach)));
        this.tier = tier;
        this.throwDamage = (attackDamage + tier.getAttackDamageBonus()) * 1.5F;
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

    public float getThrowDamage() {
        return this.throwDamage;
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
    public InteractionResultHolder<ItemStack> use(net.minecraft.world.level.Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
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
    public void releaseUsing(ItemStack stack, net.minecraft.world.level.Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10) {
                if (!level.isClientSide()) {
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                    ThrownJavelinEntity javelin = new ThrownJavelinEntity(level, player, stack);
                    javelin.setBaseDamage(this.throwDamage);
                    if (player.fallDistance > 1.0F || player.isSprinting()) {
                        javelin.setCritArrow(true);
                    }
                    javelin.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        javelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    level.addFreshEntity(javelin);
                    level.playSound(null, javelin.getX(), javelin.getY(), javelin.getZ(), SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(stack);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.LOYALTY)) {
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
        return super.isPrimaryItemFor(stack, enchantment);
    }
}