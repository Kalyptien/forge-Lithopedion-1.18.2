package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.core.BlockPos.withinManhattan;

public class SanctuaryStatusBlock extends SanctuaryBlock {

    public static final IntegerProperty JADE_PROCESS = IntegerProperty.create("jade_process", 0, 2);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    public SanctuaryStatusBlock(Properties properties, int area, SanctuaryVariant Svariant){
        super(properties);
        this.area = area;
        this.Svariant = Svariant;
        this.SBvariant = SanctuaryBlockVariant.STATUS;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for(BlockPos blockpos : withinManhattan(pPos, area, area, area)) {
            Block block = pLevel.getBlockState(blockpos).getBlock();
            if (this.isValidSanctuaryBlock(block)) {
                SanctuaryBlock Sblock = (SanctuaryBlock) block;
                if (this.isConnectToAutel(Sblock)){
                    Sblock.connect(this);
                }
                else if(Sblock instanceof SanctuaryAutelBlock){
                    Sblock.connect(this);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {

            int currentState = pState.getValue(JADE_PROCESS);
            Item item = pPlayer.getItemInHand(pHand).getItem();

            if(currentState == 0 && item == ModItems.JADE.get()){
                pLevel.setBlock(pPos, pState.setValue(JADE_PROCESS, 1), 3);
                pPlayer.setItemInHand(pHand, new ItemStack(Items.AIR, 0));
            }
            else if(currentState == 1 && item == Items.AIR){
                pLevel.setBlock(pPos, pState.setValue(JADE_PROCESS, 0), 3);
                pPlayer.setItemInHand(pHand, new ItemStack(ModItems.JADE.get(), 1));
            }
            else if(currentState == 2 && item == Items.AIR){
                pLevel.setBlock(pPos, pState.setValue(JADE_PROCESS, 0), 3);
                pPlayer.setItemInHand(pHand, new ItemStack(ModItems.FUNGUS_JADE.get(), 1));
                // Changer en fonction du type de sanctuaire
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
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
        pBuilder.add(JADE_PROCESS);
    }
}
