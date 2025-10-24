package com.ncpbails.modestmining;

import com.mojang.logging.LogUtils;
import com.ncpbails.modestmining.block.ModBlocks;
import com.ncpbails.modestmining.block.entity.ModBlockEntities;
import com.ncpbails.modestmining.effect.ModEffects;
import com.ncpbails.modestmining.entity.ModEntityTypes;
import com.ncpbails.modestmining.entity.client.ClamRenderer;
import com.ncpbails.modestmining.integration.everycompat.EveryCompatIntegration;
import com.ncpbails.modestmining.item.ModItems;
import com.ncpbails.modestmining.recipe.ModRecipes;
import com.ncpbails.modestmining.screen.ForgeScreen;
import com.ncpbails.modestmining.screen.ModMenuTypes;
import com.ncpbails.modestmining.sounds.ModSounds;
import com.ncpbails.modestmining.world.feature.ModConfiguredFeatures;
import com.ncpbails.modestmining.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModestMining.MOD_ID)
public class ModestMining
{
    public static final String MOD_ID = "modestmining";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModestMining()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addPackFinders);

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

        EveryCompatIntegration.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SpawnPlacements.register(ModEntityTypes.CLAM.get(),
                SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                WaterAnimal::checkMobSpawnRules);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    public void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerBuiltinResourcePack(event, Component.literal("Modest Mining Materials"), "modestmining_materials");
        }
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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.CLAM.get(), ClamRenderer::new);
            MenuScreens.register(ModMenuTypes.FORGE_MENU.get(), ForgeScreen::new);
        }
    }
}
