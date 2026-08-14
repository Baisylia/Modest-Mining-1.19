package com.baisylia.modestmining.mixin;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie {

    @Unique
    private static final List<RegistryObject<Item>> MODESTMINING$SPAWN_JAVELINS = List.of(
            ModItems.WOODEN_JAVELIN, ModItems.STONE_JAVELIN, ModItems.GOLDEN_JAVELIN, ModItems.IRON_JAVELIN
    );
    @Unique
    private static final float MODESTMINING$JAVELIN_SPAWN_CHANCE = 0.06F;

    protected DrownedMixin(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void modestmining$populateDrownedJavelin(RandomSource pRandom, DifficultyInstance pDifficulty, CallbackInfo ci) {
        if (ModConfig.SPEC.isLoaded() && !ModConfig.DROWNED_SPAWN_WITH_JAVELINS.get()) {
            return;
        }
        if (this.getMainHandItem().isEmpty() && pRandom.nextFloat() < MODESTMINING$JAVELIN_SPAWN_CHANCE) {
            Item javelin = MODESTMINING$SPAWN_JAVELINS.get(pRandom.nextInt(MODESTMINING$SPAWN_JAVELINS.size())).get();
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(javelin));
        }
    }
}
