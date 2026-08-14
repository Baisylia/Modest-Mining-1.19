package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.ai.JavelinAttackGoal;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class DrownedJavelinEvents {

    private static final List<DeferredHolder<Item, Item>> SPAWN_JAVELINS = List.of(
            ModItems.WOODEN_JAVELIN, ModItems.STONE_JAVELIN, ModItems.GOLDEN_JAVELIN, ModItems.IRON_JAVELIN
    );
    private static final float JAVELIN_SPAWN_CHANCE = 0.06F;
    private static final int ATTACK_INTERVAL = 40;
    private static final float ATTACK_RADIUS = 10.0F;

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) {
            return;
        }

        if (event.getEntity() instanceof Drowned drowned) {
            addJavelinGoalIfMissing(drowned);

            if (ModConfig.DROWNED_SPAWN_WITH_JAVELINS.get()
                    && drowned.getMainHandItem().isEmpty()
                    && drowned.getRandom().nextFloat() < JAVELIN_SPAWN_CHANCE) {
                Item javelin = SPAWN_JAVELINS.get(drowned.getRandom().nextInt(SPAWN_JAVELINS.size())).get();
                drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(javelin));
            }
        }
    }

    private static void addJavelinGoalIfMissing(Mob mob) {
        boolean alreadyHasGoal = mob.goalSelector.getAvailableGoals().stream()
                .anyMatch(wrappedGoal -> wrappedGoal.getGoal() instanceof JavelinAttackGoal);
        if (!alreadyHasGoal) {
            mob.goalSelector.addGoal(2, new JavelinAttackGoal(mob, 1.0D, ATTACK_INTERVAL, ATTACK_RADIUS));
        }
    }
}
