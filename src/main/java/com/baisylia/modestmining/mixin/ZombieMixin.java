package com.baisylia.modestmining.mixin;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.ai.JavelinAttackGoal;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster {

    protected ZombieMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void modestmining$addJavelinGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new JavelinAttackGoal(this, 1.0D, 40, 10.0F));
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void modestmining$populateJavelinEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (ModConfig.SPEC.isLoaded() && !ModConfig.ZOMBIES_SPAWN_WITH_JAVELINS.get()) {
            return;
        }
        ItemStack held = this.getMainHandItem();
        if ((held.is(Items.IRON_SWORD) || held.is(Items.IRON_SHOVEL)) && random.nextInt(3) == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.IRON_JAVELIN.get()));
        }
    }
}
