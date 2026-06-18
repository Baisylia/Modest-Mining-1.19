package com.baisylia.modestmining;

import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.config.ModConditions;
import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.effect.ModEffects;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.entity.client.ClamRenderer;
import com.baisylia.modestmining.item.ModItems;
import com.baisylia.modestmining.recipe.ModRecipeCategories;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ForgeScreen;
import com.baisylia.modestmining.screen.MillstoneScreen;
import com.baisylia.modestmining.screen.ModMenuTypes;
import com.baisylia.modestmining.sounds.ModSounds;
import com.baisylia.modestmining.world.feature.ModConfiguredFeatures;
import com.baisylia.modestmining.world.feature.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.IOException;
import java.util.List;

@Mod(ModestMining.MOD_ID)
public class ModestMining {
    public static final String MOD_ID = "modestmining";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RecipeBookType FORGING_RECIPE_BOOK_TYPE = RecipeBookType.create("FORGING");
    public static final RecipeBookType MILLING_RECIPE_BOOK_TYPE = RecipeBookType.create("MILLING");

    public ModestMining() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addPackFinders);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC, "modestmining-common.toml");
        ModConditions.register(eventBus);

        ModEffects.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModConfiguredFeatures.register(eventBus);
        ModPlacedFeatures.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEntityTypes.register(eventBus);
        GeckoLib.initialize();
        ModSounds.SOUND_EVENTS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void registerBuiltinResourcePack(AddPackFindersEvent event, MutableComponent name, String folder) {
        event.addRepositorySource((consumer, constructor) -> {
            ResourceLocation res = new ResourceLocation(ModestMining.MOD_ID, folder);
            IModFile file = ModList.get().getModFileById(ModestMining.MOD_ID).getFile();
            try (PathPackResources pack = new PathPackResources(
                    res.toString(),
                    file.findResource("resourcepacks/" + folder))) {

                consumer.accept(constructor.create(
                        res.toString(),
                        name,
                        false,
                        () -> pack,
                        pack.getMetadataSection(PackMetadataSection.SERIALIZER),
                        Pack.Position.BOTTOM,
                        PackSource.BUILT_IN,
                        false));

            } catch (IOException e) {
                if (!DatagenModLoader.isRunningDataGen())
                    e.printStackTrace();
            }
        });
    }

    private static void registerConditionalResourcePack(AddPackFindersEvent event, MutableComponent name, String folder, java.util.function.Supplier<Boolean> condition) {
        event.addRepositorySource((consumer, constructor) -> {
            if (condition.get()) {
                ResourceLocation res = new ResourceLocation(ModestMining.MOD_ID, folder);
                IModFile file = ModList.get().getModFileById(ModestMining.MOD_ID).getFile();
                try (PathPackResources pack = new PathPackResources(
                        res.toString(),
                        file.findResource("resourcepacks/" + folder))) {

                    consumer.accept(constructor.create(
                            res.toString(),
                            name,
                            true,
                            () -> pack,
                            pack.getMetadataSection(PackMetadataSection.SERIALIZER),
                            Pack.Position.TOP,
                            PackSource.BUILT_IN,
                            true));

                } catch (IOException e) {
                    if (!DatagenModLoader.isRunningDataGen())
                        e.printStackTrace();
                }
            }
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        SpawnPlacements.register(ModEntityTypes.CLAM.get(),
                SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                WaterAnimal::checkMobSpawnRules);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerBuiltinResourcePack(event, Component.literal("Modest Mining Materials"), "modestmining_materials");

            registerConditionalResourcePack(event,
                    Component.literal("Modest Mining: Aluminium Forge Override"),
                    "aluminium_forge_textures",
                    () -> ModConfig.isFeatureEnabled("forge_uses_aluminium", false)
            );

            // registerConditionalResourcePack(event,
            //         Component.literal("Modest Mining: Steel Rails Override"),
            //         "steel_rails_textures",
            //         () -> ModConfig.isFeatureEnabledEarly("rails_use_steel", false)
            // );
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
            ModRecipeCategories.init(event);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.CLAM.get(), ClamRenderer::new);
            MenuScreens.register(ModMenuTypes.FORGE_MENU.get(), ForgeScreen::new);
            MenuScreens.register(ModMenuTypes.MILLSTONE_MENU.get(), MillstoneScreen::new);
            event.enqueueWork(() -> {
                List<RegistryObject<Item>> javelins = List.of(
                        ModItems.WOODEN_JAVELIN, ModItems.STONE_JAVELIN, ModItems.GOLDEN_JAVELIN,
                        ModItems.IRON_JAVELIN, ModItems.DIAMOND_JAVELIN, ModItems.NETHERITE_JAVELIN,
                        ModItems.PRISMARITE_JAVELIN, ModItems.VALKYRIUM_JAVELIN
                );

                for (RegistryObject<Item> javelin : javelins) {
                    ItemProperties.register(javelin.get(), new ResourceLocation("throwing"),
                            (stack, level, entity, seed) ->
                                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
                    );
                }
            });
        }
    }
}
