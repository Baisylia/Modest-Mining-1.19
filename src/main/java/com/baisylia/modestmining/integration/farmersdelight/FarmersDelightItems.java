package com.baisylia.modestmining.integration.farmersdelight;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.ModTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class FarmersDelightItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, ModestMining.MOD_ID);

    public static final DeferredHolder<Item, Item> BRONZE_KNIFE = ITEMS.register("bronze_knife",
            () -> new KnifeItem(ModTiers.BRONZE, new Item.Properties().attributes(KnifeItem.createAttributes(ModTiers.BRONZE, 0.5F, -2.0F))));

    public static final DeferredHolder<Item, Item> STEEL_KNIFE = ITEMS.register("steel_knife",
            () -> new KnifeItem(ModTiers.STEEL, new Item.Properties().attributes(KnifeItem.createAttributes(ModTiers.STEEL, 0.5F, -2.0F))));

    public static final DeferredHolder<Item, Item> PRISMARITE_KNIFE = ITEMS.register("prismarite_knife",
            () -> new KnifeItem(ModTiers.PRISMARITE, new Item.Properties().attributes(KnifeItem.createAttributes(ModTiers.PRISMARITE, 0.5F, -2.0F))));

    public static final DeferredHolder<Item, Item> VALKYRIUM_KNIFE = ITEMS.register("valkyrium_knife",
            () -> new KnifeItem(ModTiers.VALKYRIUM, new Item.Properties().attributes(KnifeItem.createAttributes(ModTiers.VALKYRIUM, 0.5F, -2.0F))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
