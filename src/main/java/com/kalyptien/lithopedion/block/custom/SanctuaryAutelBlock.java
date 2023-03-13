package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.item.ModItems;
import com.kalyptien.lithopedion.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.core.BlockPos.withinManhattan;

public class SanctuaryAutelBlock extends SanctuaryBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 24, 16);

    public SanctuaryAutelBlock(Properties properties, int area, SanctuaryVariant Svariant){
        super(properties);
        this.area = area;
        this.Svariant = Svariant;
        this.SBvariant = SanctuaryBlockVariant.SANCTUARY;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Override
    public void connect(ChildrenEntity children){
        this.childrens.add(children);
    }

    @Override
    public void connect(SoldierEntity soldier){
        this.soldiers.add(soldier);
        if (this.blocks.size() / (4 * soldiers.size()) > 1) {
            updateSoulLimiter();
        }
    }

    @Override
    public void connect(SanctuaryBlock block){
        this.blocks.add(block);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for(BlockPos blockpos : withinManhattan(pPos, LithopedionUtil.sanctuary_autel_zone, LithopedionUtil.sanctuary_autel_zone, LithopedionUtil.sanctuary_autel_zone)) {
            Block block = pLevel.getBlockState(blockpos).getBlock();
            if (this.isValidSanctuaryBlock(block)) {
                SanctuaryBlock Sblock = (SanctuaryBlock) block;
                if (this.canConnectToAutel(Sblock)){
                    this.connect(Sblock);
                }
            }
        }
        this.blocks.remove(this);

        for(int x = pPos.getX() - area; x < pPos.getX() + area + 1; x++){
            for(int z = pPos.getZ() - area; z < pPos.getZ() + area + 1; z++){
                pLevel.setBlock(new BlockPos(x,pPos.getY() - 1,z), Blocks.RED_TERRACOTTA.defaultBlockState(), 0);
            }
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        this.soulCooldown--;
        if(soulCooldown <= 0){
            this.soulCooldown = 24000 - (24000 * (this.childrens.size() / 100));
            if(soulCount < soulLimiter){
                soulCount++;
            }
        }
    }
    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        this.spawnDestroyParticles(pLevel, pPlayer, pPos, pState);
        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);

        for (SanctuaryBlock block : this.blocks){
            block.unsetSanctuary();
        }

        for (ChildrenEntity children : this.childrens){
            children.setSanctuary(null);
        }

        for (SoldierEntity soldier : this.soldiers){
            soldier.setSanctuaryVariant(SanctuaryVariant.NONE);
            soldier.setSanctuary(null);
        }

        for(int x = pPos.getX() - area; x < pPos.getX() + area + 1; x++){
            for(int z = pPos.getZ() - area; z < pPos.getZ() + area + 1; z++){
                pLevel.setBlock(new BlockPos(x,pPos.getY() - 1,z), Blocks.GRASS_BLOCK.defaultBlockState(), 0);
            }
        }
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
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND && this.soulCount > 0) {
            pPlayer.setItemInHand(pHand, new ItemStack(ModItems.FUNGUS_ELEMENT.get(), this.soulCount));
            this.soulCount = 0;
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public ArrayList<SanctuaryBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<SanctuaryBlock> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<ChildrenEntity> getChildrens() {
        return childrens;
    }

    public void setChildrens(ArrayList<ChildrenEntity> childrens) {
        this.childrens = childrens;
    }

    public ArrayList<SoldierEntity> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<SoldierEntity> soldiers) {
        this.soldiers = soldiers;
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
