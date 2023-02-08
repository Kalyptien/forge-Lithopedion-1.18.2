package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.block.custom.SanctuaryAutelBlock;
import com.kalyptien.lithopedion.block.custom.SanctuaryStatusBlock;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class ChildrenPrayGoal  extends MoveToBlockGoal {
    private ChildrenEntity soldier = null;
    private int prayTimer = 0;
    private int cooldown = 0;

    public ChildrenPrayGoal(ChildrenEntity pMob, double speed, int length) {
        super(pMob, speed, length, 6);
        this.soldier = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (!this.soldier.isSitting()) {
            if (this.soldier.getRandom().nextInt(1000) == 0) {
                return this.findNearestBlock();
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.prayTimer < 0) { //  && this.soldier.getRandom().nextInt(250) == 0
            return false;
        } else if (this.soldier.isPassenger()) {
            return false;
        } else {
            return super.canContinueToUse();
        }
    }

    @Override
    public void start() {
        this.prayTimer = 1000 + this.soldier.getRandom().nextInt(1000);
        super.start();
    }

    @Override
    public void stop() {
        this.cooldown = this.adjustedTickDelay(2000);
        if(!this.soldier.isConnect()){
            Level level = this.soldier.level;
            BlockState blockstate = level.getBlockState(this.blockPos);
            Block block = blockstate.getBlock();
            if(block instanceof SanctuaryAutelBlock){
                ((SanctuaryAutelBlock) block).connect(this.soldier);
                this.soldier.setSanctuary(((SanctuaryAutelBlock) block));
            }
        }
        this.soldier.setPraying(false);
        super.stop();
    }

    public void tick() {
        super.tick();
        this.soldier.getLookControl().setLookAt(this.blockPos.getX() + 0.5D, this.blockPos.getY() + 0.5D, this.blockPos.getZ() + 0.5D, (float) (this.soldier.getMaxHeadYRot() + 20), (float) this.soldier.getMaxHeadXRot());

        if (this.isReachedTarget() && this.soldier.getNavigation().isDone() && this.isInterruptable()) {
            this.soldier.setPraying(true);
            --this.prayTimer;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        if(soldier.isConnect()){
            return (pLevel.isEmptyBlock(pPos.above()) && pLevel.isEmptyBlock(pPos.above().above()) && pLevel.getBlockState(pPos).getBlock() == soldier.getSanctuary());
        }
        else{
            return (pLevel.isEmptyBlock(pPos.above()) && pLevel.isEmptyBlock(pPos.above().above()) && pLevel.getBlockState(pPos).getBlock() instanceof SanctuaryAutelBlock);
        }
    }

    @Override
    public double acceptedDistance() {
        return super.acceptedDistance() * 3.5;
    }
}
