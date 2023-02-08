package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.minecraft.core.BlockPos.withinManhattan;

public class SanctuaryStoneBlock extends SanctuaryBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 17, 16);

    public SanctuaryStoneBlock(Properties properties, int area, SanctuaryVariant Svariant){
        super(properties);
        this.area = area;
        this.Svariant = Svariant;
        this.SBvariant = SanctuaryBlockVariant.STONE;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for(BlockPos blockpos : withinManhattan(pPos, area, area, area)) {
            Block block = pLevel.getBlockState(blockpos).getBlock();
            if (this.isValidSanctuaryBlock(block)) {
                SanctuaryBlock Sblock = (SanctuaryBlock) block;
                if(Sblock instanceof SanctuaryAutelBlock){
                    Sblock.connect(this);
                    this.setSanctuary((SanctuaryAutelBlock) Sblock);
                    break;
                }
                else if (this.isConnectToAutel(Sblock)){
                    Sblock.connect(this);
                    this.setSanctuary(Sblock.getSanctuary());
                    break;
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /* FACING */

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
