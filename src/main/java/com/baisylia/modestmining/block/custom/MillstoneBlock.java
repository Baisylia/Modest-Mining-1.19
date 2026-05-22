package com.baisylia.modestmining.block.custom;

import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.block.entity.custom.MillstoneBlockEntity;
import com.baisylia.modestmining.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;


public class MillstoneBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty BASE = BooleanProperty.create("base");

    public MillstoneBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(BASE, true));
    }

    /* FACING */

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(LIT, pContext.getLevel().hasNeighborSignal(pContext.getClickedPos()))
                .setValue(BASE, true);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {

            boolean powered = level.hasNeighborSignal(pos);

            if (state.getValue(LIT) != powered) {
                level.setBlock(pos, state.setValue(LIT, powered), 3);
            }
        }
    }

    /*@Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
        }
    }*/

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if (state.getValue(LIT)) {
            double x = (double)pos.getX() + (double)0.5F;
            double y = pos.getY();
            double z = (double)pos.getZ() + (double)0.5F;
            if (randomSource.nextInt(10) == 0) {
                level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, ModSounds.FORGE_CRACKLE.get(), SoundSource.BLOCKS, 0.5F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.6F, false);
            }
            double angle = randomSource.nextDouble() * Math.PI * 2D;
            double radius = 0.45D + randomSource.nextDouble() * 0.18D;
            double px = x + Math.cos(angle) * radius;
            double py = y + 0.72D + randomSource.nextDouble() * 0.22D;
            double pz = z + Math.sin(angle) * radius;
            double motionX = Math.cos(angle) * 0.04D;
            double motionY = 0.02D + randomSource.nextDouble() * 0.025D;
            double motionZ = Math.sin(angle) * 0.04D;

            level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                    px, py, pz, motionX, motionY, motionZ
            );
            if (level.getBlockEntity(pos) instanceof MillstoneBlockEntity millstone) {
                if (!millstone.getItem(0).isEmpty()) {
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, millstone.getItem(0)),
                            px, py, pz, motionX, motionY + 0.015D, motionZ);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, BASE);
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MillstoneBlockEntity millstoneBlockEntity) {
                millstoneBlockEntity.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof MillstoneBlockEntity millstoneBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), millstoneBlockEntity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MillstoneBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MILLSTONE_BLOCK_ENTITY.get(),
                MillstoneBlockEntity::tick);
    }

    private static final VoxelShape SHAPE = Shapes.join(Block.box(2, 0, 2, 14, 6, 14),
            Shapes.join(Block.box(2, 6, 2, 14, 12, 14),
                    Block.box(7, 12, 7, 9, 16, 9), BooleanOp.OR), BooleanOp.OR
    );
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}