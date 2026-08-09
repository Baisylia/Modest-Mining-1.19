package com.baisylia.modestmining.integration.farmersdelight;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.ModTiers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class FarmersDelightItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModestMining.MOD_ID);

    public static final RegistryObject<Item> BRONZE_KNIFE = ITEMS.register("bronze_knife",
            () -> new KnifeItem(ModTiers.BRONZE, 0.5F, -2.0F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STEEL_KNIFE = ITEMS.register("steel_knife",
            () -> new KnifeItem(ModTiers.STEEL, 0.5F, -2.0F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_KNIFE = ITEMS.register("prismarite_knife",
            () -> new KnifeItem(ModTiers.PRISMARITE, 0.5F, -2.0F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> VALKYRIUM_KNIFE = ITEMS.register("valkyrium_knife",
            () -> new KnifeItem(ModTiers.VALKYRIUM, 0.5F, -2.0F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
