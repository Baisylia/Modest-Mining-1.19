package com.baisylia.modestmining.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ClamBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<ClamBlock> CODEC = simpleCodec(ClamBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<ClamPart> PART = EnumProperty.create("part", ClamPart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_NORTH_FRONT = Block.box(0, 0, 2, 16, 6, 16);
    private static final VoxelShape SHAPE_NORTH_BACK_L = Shapes.or(Block.box(0, 0, 0, 16, 6, 10), Block.box(8, 0, 10, 16, 6, 16));
    private static final VoxelShape SHAPE_NORTH_BACK_R = Shapes.or(Block.box(0, 0, 0, 16, 6, 10), Block.box(0, 0, 10, 8, 6, 16));

    private static final VoxelShape SHAPE_SOUTH_FRONT = Block.box(0, 0, 0, 16, 6, 14);
    private static final VoxelShape SHAPE_SOUTH_BACK_L = Shapes.or(Block.box(0, 0, 6, 16, 6, 16), Block.box(0, 0, 0, 8, 6, 6));
    private static final VoxelShape SHAPE_SOUTH_BACK_R = Shapes.or(Block.box(0, 0, 6, 16, 6, 16), Block.box(8, 0, 0, 16, 6, 6));

    private static final VoxelShape SHAPE_EAST_FRONT = Block.box(0, 0, 0, 14, 6, 16);
    private static final VoxelShape SHAPE_EAST_BACK_L = Shapes.or(Block.box(6, 0, 0, 16, 6, 16), Block.box(0, 0, 8, 6, 6, 16));
    private static final VoxelShape SHAPE_EAST_BACK_R = Shapes.or(Block.box(6, 0, 0, 16, 6, 16), Block.box(0, 0, 0, 6, 6, 8));

    private static final VoxelShape SHAPE_WEST_FRONT = Block.box(2, 0, 0, 16, 6, 16);
    private static final VoxelShape SHAPE_WEST_BACK_L = Shapes.or(Block.box(0, 0, 0, 10, 6, 16), Block.box(10, 0, 0, 16, 6, 8));
    private static final VoxelShape SHAPE_WEST_BACK_R = Shapes.or(Block.box(0, 0, 0, 10, 6, 16), Block.box(10, 0, 8, 16, 6, 16));

    public ClamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, ClamPart.TOP_LEFT)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public static BlockPos getMainPos(BlockPos pos, ClamPart part, Direction facing) {
        return switch (part) {
            case TOP_LEFT -> pos;
            case TOP_RIGHT -> pos.relative(facing.getCounterClockWise());
            case BOTTOM_LEFT -> pos.relative(facing);
            case BOTTOM_RIGHT -> pos.relative(facing.getCounterClockWise()).relative(facing);
        };
    }

    public static BlockPos getPosForPart(BlockPos mainPos, ClamPart targetPart, Direction facing) {
        return switch (targetPart) {
            case TOP_LEFT -> mainPos;
            case TOP_RIGHT -> mainPos.relative(facing.getClockWise());
            case BOTTOM_LEFT -> mainPos.relative(facing.getOpposite());
            case BOTTOM_RIGHT -> mainPos.relative(facing.getClockWise()).relative(facing.getOpposite());
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockPos posTL = context.getClickedPos();
        Level level = context.getLevel();

        for (ClamPart part : ClamPart.values()) {
            BlockPos p = getPosForPart(posTL, part, facing);
            if (p.getY() < level.getMinBuildHeight() || p.getY() >= level.getMaxBuildHeight()) {
                return null;
            }
            BlockState stateAt = level.getBlockState(p);
            if (!stateAt.canBeReplaced(context)) {
                return null;
            }
            if (!level.getBlockState(p.below()).isFaceSturdy(level, p.below(), Direction.UP)) {
                return null;
            }
        }

        FluidState fluidstate = level.getFluidState(posTL);
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(PART, ClamPart.TOP_LEFT)
                .setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            Direction facing = state.getValue(FACING);
            for (ClamPart part : ClamPart.values()) {
                if (part == ClamPart.TOP_LEFT) continue;
                BlockPos targetPos = getPosForPart(pos, part, facing);
                FluidState fluidState = level.getFluidState(targetPos);
                level.setBlock(targetPos, this.defaultBlockState()
                        .setValue(FACING, facing)
                        .setValue(PART, part)
                        .setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8), 3);
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            ClamPart part = state.getValue(PART);
            Direction facing = state.getValue(FACING);
            BlockPos mainPos = getMainPos(pos, part, facing);

            for (ClamPart p : ClamPart.values()) {
                BlockPos partPos = getPosForPart(mainPos, p, facing);
                if (!partPos.equals(pos) && !partPos.equals(mainPos)) {
                    BlockState targetState = level.getBlockState(partPos);
                    if (targetState.is(this)) {
                        boolean isWater = targetState.getValue(WATERLOGGED);
                        level.setBlock(partPos, isWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                    }
                }
            }

            if (!pos.equals(mainPos)) {
                BlockState mainState = level.getBlockState(mainPos);
                if (mainState.is(this)) {
                    if (!player.isCreative()) {
                        level.destroyBlock(mainPos, true, player);
                    } else {
                        boolean isWater = mainState.getValue(WATERLOGGED);
                        level.setBlock(mainPos, isWater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                    }
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        ClamPart part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        BlockPos mainPos = getMainPos(pos, part, facing);

        for (ClamPart p : ClamPart.values()) {
            BlockPos partPos = getPosForPart(mainPos, p, facing);
            BlockPos belowPos = partPos.below();
            if (level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (!state.canSurvive(level, currentPos)) {
            return state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        }

        ClamPart part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        BlockPos mainPos = getMainPos(currentPos, part, facing);

        for (ClamPart p : ClamPart.values()) {
            if (p == part) continue;
            BlockPos targetPos = getPosForPart(mainPos, p, facing);
            BlockState targetState = level.getBlockState(targetPos);
            if (!targetState.is(this) || targetState.getValue(FACING) != facing || targetState.getValue(PART) != p) {
                return state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        ClamPart part = state.getValue(PART);

        return switch (facing) {
            case NORTH -> switch (part) {
                case TOP_LEFT, TOP_RIGHT -> SHAPE_NORTH_FRONT;
                case BOTTOM_LEFT -> SHAPE_NORTH_BACK_L;
                case BOTTOM_RIGHT -> SHAPE_NORTH_BACK_R;
            };
            case SOUTH -> switch (part) {
                case TOP_LEFT, TOP_RIGHT -> SHAPE_SOUTH_FRONT;
                case BOTTOM_LEFT -> SHAPE_SOUTH_BACK_L;
                case BOTTOM_RIGHT -> SHAPE_SOUTH_BACK_R;
            };
            case EAST -> switch (part) {
                case TOP_LEFT, TOP_RIGHT -> SHAPE_EAST_FRONT;
                case BOTTOM_LEFT -> SHAPE_EAST_BACK_L;
                case BOTTOM_RIGHT -> SHAPE_EAST_BACK_R;
            };
            case WEST -> switch (part) {
                case TOP_LEFT, TOP_RIGHT -> SHAPE_WEST_FRONT;
                case BOTTOM_LEFT -> SHAPE_WEST_BACK_L;
                case BOTTOM_RIGHT -> SHAPE_WEST_BACK_R;
            };
            default -> SHAPE_NORTH_FRONT;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, WATERLOGGED);
    }

    public enum ClamPart implements StringRepresentable {
        TOP_LEFT("top_left"),
        TOP_RIGHT("top_right"),
        BOTTOM_LEFT("bottom_left"),
        BOTTOM_RIGHT("bottom_right");

        private final String name;

        ClamPart(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
