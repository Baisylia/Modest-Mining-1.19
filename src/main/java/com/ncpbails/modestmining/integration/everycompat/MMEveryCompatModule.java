package com.ncpbails.modestmining.integration.everycompat;

import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class MMEveryCompatModule extends SimpleModule {
    public final SimpleEntrySet<WoodType, Block>
            pillar,
            boards, boardSlab, boardStairs, boardWall,
            tiles, tileSlab, tileStairs, tileWall,
            carved, carvedSlab, carvedStairs, carvedWall,
    chiseled_planks;
    public MMEveryCompatModule(String modId) {
        super(modId, "mm");

        // pillar
        pillar = SimpleEntrySet.builder(WoodType.class, "pillar",
                () -> getModBlock("oak_pillar"), () -> WoodTypeRegistry.OAK_TYPE,
                w -> new RotatedPillarBlock(Utils.copyPropertySafe(w.log)){
                    @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                    @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_pillar_side"))
            .addTexture(modRes("block/oak_pillar_top"))
            .build();
        this.addEntry(pillar);

        //boards
        boards = SimpleEntrySet.builder(WoodType.class, "boards",
                    () -> getModBlock("oak_boards"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new Block(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_boards"))
            .build();
        this.addEntry(boards);

        boardSlab = SimpleEntrySet.builder(WoodType.class, "board_slab",
                    () -> getModBlock("oak_board_slab"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new SlabBlock(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WOODEN_SLABS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_boards"))
            .build();
        this.addEntry(boardSlab);

        boardStairs = SimpleEntrySet.builder(WoodType.class, "board_stairs",
                    () -> getModBlock("oak_board_stairs"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new StairBlock(w.planks.defaultBlockState(), Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WOODEN_STAIRS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_boards"))
            .build();
        this.addEntry(boardStairs);

        boardWall = SimpleEntrySet.builder(WoodType.class, "board_wall",
                    () -> getModBlock("oak_board_wall"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new WallBlock(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WALLS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_boards"))
            .build();
        this.addEntry(boardWall);

        //tiles

        tiles = SimpleEntrySet.builder(WoodType.class, "tiles",
                    () -> getModBlock("oak_tiles"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new Block(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WOODEN_SLABS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_tiles"))
            .build();
        this.addEntry(tiles);

        tileSlab = SimpleEntrySet.builder(WoodType.class, "tile_slab",
                () -> getModBlock("oak_tile_slab"), () -> WoodTypeRegistry.OAK_TYPE,
                w -> new SlabBlock(Utils.copyPropertySafe(w.planks)){
                    @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                    @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WOODEN_SLABS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_tiles"))
            .build();
        this.addEntry(tileSlab);

        tileStairs = SimpleEntrySet.builder(WoodType.class, "tile_stairs",
                    () -> getModBlock("oak_tile_stairs"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new StairBlock(w.planks.defaultBlockState(), Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WOODEN_STAIRS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_tiles"))
            .build();
        this.addEntry(tileStairs);

        tileWall = SimpleEntrySet.builder(WoodType.class, "tile_wall",
                    () -> getModBlock("oak_tile_wall"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new WallBlock(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .addTag(BlockTags.WALLS, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/oak_tiles"))
            .build();
        this.addEntry(tileWall);

        //carved

        carved = SimpleEntrySet.builder(WoodType.class, "planks", "carved",
                        () -> getModBlock("carved_oak_planks"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new Block(Utils.copyPropertySafe(w.planks)){
                            @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                            @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                            @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                        })
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.WOODEN_SLABS, Registry.BLOCK_REGISTRY)
                .defaultRecipe()
                .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
                .addTexture(modRes("block/carved_oak_planks"))
                .build();
        this.addEntry(carved);

        carvedSlab = SimpleEntrySet.builder(WoodType.class, "carved_plank_slab",
                        () -> getModBlock("oak_carved_plank_slab"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new SlabBlock(Utils.copyPropertySafe(w.planks)){
                            @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                            @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                            @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                        })
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.WOODEN_SLABS, Registry.BLOCK_REGISTRY)
                .defaultRecipe()
                .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
                .addTexture(modRes("block/carved_oak_planks"))
                .build();
        this.addEntry(carvedSlab);

        carvedStairs = SimpleEntrySet.builder(WoodType.class, "carved_plank_stairs",
                        () -> getModBlock("oak_carved_plank_stairs"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new StairBlock(w.planks.defaultBlockState(), Utils.copyPropertySafe(w.planks)){
                            @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                            @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                            @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                        })
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.WOODEN_STAIRS, Registry.BLOCK_REGISTRY)
                .defaultRecipe()
                .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
                .addTexture(modRes("block/carved_oak_planks"))
                .build();
        this.addEntry(carvedStairs);

        carvedWall = SimpleEntrySet.builder(WoodType.class, "carved_plank_wall",
                        () -> getModBlock("oak_carved_plank_wall"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new WallBlock(Utils.copyPropertySafe(w.planks)){
                            @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                            @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                            @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                        })
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .addTag(BlockTags.WALLS, Registry.BLOCK_REGISTRY)
                .defaultRecipe()
                .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
                .addTexture(modRes("block/carved_oak_planks"))
                .build();
        this.addEntry(carvedWall);

        //chiseled
        chiseled_planks = SimpleEntrySet.builder(WoodType.class, "planks","chiseled",
                    () -> getModBlock("chiseled_oak_planks"), () -> WoodTypeRegistry.OAK_TYPE,
                    w -> new Block(Utils.copyPropertySafe(w.planks)){
                        @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; }
                        @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 60; }
                        @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 30; }
                    })
            .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
            .defaultRecipe()
            .createPaletteFromOak((p) -> p.remove(p.getDarkest()))
            .addTexture(modRes("block/chiseled_oak_planks"))
            .build();
        this.addEntry(chiseled_planks);
    }

    @Override
    public boolean isEntryAlreadyRegistered(String blockId, BlockType blockType, Registry<?> registry) {

        if (blockType instanceof WoodType wt) {
            if (blockId.contains("boards") && !isVanillaWood(blockId)) return false;
        }

        return super.isEntryAlreadyRegistered(blockId, blockType, registry);
    }

    private boolean isVanillaWood(String blockId) {
        return blockId.equals("oak_boards") || blockId.equals("spruce_boards") || blockId.equals("crimson_boards") || blockId.equals("warped_boards") || blockId.equals("birch_boards") || blockId.equals("jungle_boards") || blockId.equals("mangrove_boards") || blockId.equals("acacia_boards") || blockId.equals("dark_oak_boards") || blockId.equals("bamboo_boards");
    }
}
