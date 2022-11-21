package com.kalyptien.lithopedion.block.custom;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.core.BlockPos.withinManhattan;

public class SanctuaryAutelBlock extends SanctuaryBlock {

    private ArrayList<SanctuaryBlock> blocks = new ArrayList<>();
    private ArrayList<ChildrenEntity> childrens = new ArrayList<>();
    private ArrayList<SoldierEntity> soldiers = new ArrayList<>();

    private int soulLimiter = 5;
    private int soulCount = 0;
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
    public void connect(ChildrenEntity children){
        this.childrens.add(children);
        children.setSanctuary(this);
    }

    @Override
    public void connect(SoldierEntity soldier){
        this.soldiers.add(soldier);
        if (this.blocks.size() / (4 * soldiers.size()) > 1) {
            updateSoulLimiter();
            soldier.setSanctuaryVariant(Svariant);
        }
    }

    @Override
    public void connect(SanctuaryBlock block){
        this.blocks.add(block);
        block.setSanctuary(this);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for(BlockPos blockpos : withinManhattan(pPos, area, area, area)) {
            Block block = pLevel.getBlockState(blockpos).getBlock();
            if (this.isValidSanctuaryBlock(block)) {
                SanctuaryBlock Sblock = (SanctuaryBlock) block;
                if (this.canConnectToAutel(Sblock)){
                    this.connect(Sblock);
                }
            }
        }
        this.blocks.remove(this);
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
