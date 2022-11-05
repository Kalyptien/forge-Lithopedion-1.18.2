package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.entity.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.entity.variant.SanctuaryVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import static net.minecraft.core.BlockPos.withinManhattan;

public class SanctuaryAutelBlock extends SanctuaryBlock {

    private ArrayList<SanctuaryBlock> blocks;

    private ArrayList<ChildrenEntity> childrens = new ArrayList<>();

    private ArrayList<SoldierEntity> soldiers = new ArrayList<>();

    private int soulLimiter = 5;

    private int soulCooldown = 24000;

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    public SanctuaryAutelBlock(Properties properties, int area, SanctuaryVariant Svariant){
        super(properties);
        this.area = area;
        this.Svariant = Svariant;
        this.SBvariant = SanctuaryBlockVariant.SANCTUARY;
    }

    @Override
    public boolean connect(ChildrenEntity children){
        this.childrens.add(children);
        return true;
    }

    @Override
    public boolean connect(SoldierEntity soldier){
        if(this.soldiers.size() != 0){
            if(this.blocks.size() / (4 * soldiers.size()) > 1){
                this.soldiers.add(soldier);
                updateSoulLimiter();
                soldier.setSanctuaryVariant(Svariant);
                // Change head texture
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return true;
        }
    }

    @Override
    public boolean connect(SanctuaryBlock block){
        this.blocks.add(block);
        block.setActive(true);
        block.setSanctuary(this);
        // Change texture of the blocks with the variant + type of block
        return true;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for(BlockPos blockpos : withinManhattan(pPos, area, area, area)) {
            Block block = pLevel.getBlockState(blockpos).getBlock();
            if (isValidSanctuaryBlock(block)) {
                SanctuaryBlock Sblock = (SanctuaryBlock) block;
                if (canConnectToAutel(Sblock)){
                    Sblock.connect(Sblock);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {

    }

    public boolean isValidSanctuaryBlock(Block block){
        return block instanceof SanctuaryBlock;
    }

    public boolean canConnectToAutel(SanctuaryBlock block){
        return block.getSanctuary() == null && block.getSvariant() == this.getSvariant();
    }

    public void updateSoulLimiter(){
        if(this.soldiers.size() != 0){
            this.soulLimiter = this.soldiers.size() * 5;
        }
        else{

            this.soulLimiter = 5;
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
