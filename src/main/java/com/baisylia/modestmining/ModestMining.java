package com.baisylia.modestmining;

import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.config.ModConditions;
import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.item.ModItems;
import com.baisylia.modestmining.recipe.ModRecipeCategories;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ForgeScreen;
import com.baisylia.modestmining.screen.MillstoneScreen;
import com.baisylia.modestmining.screen.ModMenuTypes;
import com.baisylia.modestmining.sounds.ModSounds;
import com.baisylia.modestmining.world.feature.ModConfiguredFeatures;
import com.baisylia.modestmining.world.feature.ModFeatures;
import com.baisylia.modestmining.world.feature.ModPlacedFeatures;
import com.baisylia.modestmining.world.feature.ModPlacementModifiers;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.Item;
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

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

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

        ModAttributes.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModConfiguredFeatures.register(eventBus);
        ModPlacedFeatures.register(eventBus);
        ModPlacementModifiers.register(eventBus);
        ModFeatures.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEntityTypes.register(eventBus);
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

    private static void registerConditionalResourcePack(AddPackFindersEvent event, MutableComponent name, String folder, Supplier<Boolean> condition) {
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

    private static void registerFeaturePack(AddPackFindersEvent event, String displayName, String folder, String featureKey) {
        registerConditionalResourcePack(event, Component.literal(displayName), folder, () -> ModConfig.isFeatureEnabled(featureKey, false));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerBuiltinResourcePack(event, Component.literal("Modest Mining Materials"), "modestmining_materials");

            registerFeaturePack(event, "Modest Mining: Copper Bow Override","copper_bow_textures","bow_uses_copper");
            registerFeaturePack(event, "Modest Mining: Copper Repeater Override","copper_repeater_textures","repeater_uses_copper");
            registerFeaturePack(event, "Modest Mining: Copper Tripwire Hook ","copper_tripwire_hook_textures","tripwire_hook_uses_copper");
            registerFeaturePack(event, "Modest Mining: Aluminium Forge Override", "aluminium_forge_textures", "forge_uses_aluminium");
            registerFeaturePack(event, "Modest Mining: Aluminium Bucket Override","aluminium_bucket_textures","bucket_uses_aluminium");
            registerFeaturePack(event, "Modest Mining: Aluminium Fishing Rod Override","aluminium_fishing_rod_textures","fishing_rod_uses_aluminium");
            registerFeaturePack(event, "Modest Mining: Aluminium Cauldron Override","aluminium_cauldron_textures","cauldron_uses_aluminium");
            registerFeaturePack(event, "Modest Mining: Lead Hopper Override","lead_hopper_textures","hopper_uses_lead");
            registerFeaturePack(event, "Modest Mining: Lead Minecart Override","lead_minecart_textures","minecart_uses_lead");
            registerFeaturePack(event, "Modest Mining: Gold Brewing Stand Override","gold_brewing_stand_textures","brewing_stand_uses_gold");
            registerFeaturePack(event, "Modest Mining: Gold Comparator Override","gold_comparator_textures","comparator_uses_gold");
            registerFeaturePack(event, "Modest Mining: Gold Detector Rail Override","gold_detector_rail_textures","detector_rail_uses_gold");
            registerFeaturePack(event, "Modest Mining: Silver Activator Rail Override","silver_activator_rail_textures","activator_rail_uses_silver");
            registerFeaturePack(event, "Modest Mining: Silver Dropper Override","silver_dropper_textures","dropper_uses_silver");
            registerFeaturePack(event, "Modest Mining: Silver Note Block Override","silver_note_block_textures","note_block_uses_silver");
            registerFeaturePack(event, "Modest Mining: Bronze Piston Override","bronze_piston_textures","piston_uses_bronze");
            registerFeaturePack(event, "Modest Mining: Bronze Smoker Override","bronze_smoker_textures","smoker_uses_bronze");
            registerFeaturePack(event, "Modest Mining: Bronze Crossbow Override","bronze_crossbow_textures","crossbow_uses_bronze");
            registerFeaturePack(event, "Modest Mining: Bronze Shield Override","bronze_shield_textures","shield_uses_bronze");
            registerFeaturePack(event, "Modest Mining: Steel Anvil Override","steel_anvil_textures","anvil_uses_steel");
            registerFeaturePack(event, "Modest Mining: Steel Blast Furnace Override","steel_blast_furnace_textures","blast_furnace_uses_steel");
            registerFeaturePack(event, "Modest Mining: Steel Stonecutter Override","steel_stonecutter_textures","stonecutter_uses_steel");
            registerFeaturePack(event, "Modest Mining: Steel Flint and Steel Override","steel_flint_and_steel_textures","flint_and_steel_uses_steel");
            registerFeaturePack(event, "Modest Mining: Rose Gold Daylight Detector Override","rose_gold_daylight_detector_textures","daylight_detector_uses_rose_gold");
            registerFeaturePack(event, "Modest Mining: Rose Gold Observer Override","rose_gold_observer_textures","observer_uses_rose_gold");
            registerFeaturePack(event, "Modest Mining: Electrum Lamp Override","electrum_lamp_textures","lamp_uses_electrum");
            registerFeaturePack(event, "Modest Mining: Electrum Powered Rail Override","electrum_powered_rail_textures","powered_rail_uses_electrum");
            registerFeaturePack(event, "Modest Mining: Electrum Dispenser Override","electrum_dispenser_textures","dispenser_uses_electrum");
            registerFeaturePack(event, "Modest Mining: Electrum Jukebox Override","electrum_jukebox_textures","jukebox_uses_electrum");
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
