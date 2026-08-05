package com.baisylia.modestmining;

import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.client.ClientConfigSetup;
import com.baisylia.modestmining.config.ModConditions;
import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.item.ModArmourMaterials;
import com.baisylia.modestmining.item.ModItems;
import com.baisylia.modestmining.recipe.ModRecipeCategories;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ForgeScreen;
import com.baisylia.modestmining.screen.MillstoneScreen;
import com.baisylia.modestmining.screen.ModMenuTypes;
import com.baisylia.modestmining.sounds.ModSounds;
import com.baisylia.modestmining.world.feature.ModFeatures;
import com.baisylia.modestmining.world.feature.ModPlacementModifiers;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

import java.util.List;

@Mod(ModestMining.MOD_ID)
public class ModestMining {
    public static final String MOD_ID = "modestmining";
    public static final Logger LOGGER = LogUtils.getLogger();

    // TODO: make custom recipe book types here?
    public static final RecipeBookType FORGING_RECIPE_BOOK_TYPE = RecipeBookType.FURNACE;
    public static final RecipeBookType MILLING_RECIPE_BOOK_TYPE = RecipeBookType.BLAST_FURNACE;

    public ModestMining(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(Type.COMMON, ModConfig.SPEC, "modestmining-common.toml");
        ModConditions.register(modEventBus);

        ModAttributes.register(modEventBus);
        ModArmourMaterials.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModPlacementModifiers.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
        }

        NeoForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
            ModRecipeCategories.init(event);
        }

        @SubscribeEvent
        public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.FORGE_MENU.get(), ForgeScreen::new);
            event.register(ModMenuTypes.MILLSTONE_MENU.get(), MillstoneScreen::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                List<DeferredHolder<Item, Item>> javelins = List.of(
                        ModItems.WOODEN_JAVELIN, ModItems.STONE_JAVELIN, ModItems.GOLDEN_JAVELIN,
                        ModItems.IRON_JAVELIN, ModItems.DIAMOND_JAVELIN, ModItems.NETHERITE_JAVELIN,
                        ModItems.PRISMARITE_JAVELIN, ModItems.VALKYRIUM_JAVELIN
                );

                for (DeferredHolder<Item, Item> javelin : javelins) {
                    ItemProperties.register(javelin.get(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "throwing"),
                            (stack, level, entity, seed) ->
                                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
                    );
                }
            });
        }
    }
}
