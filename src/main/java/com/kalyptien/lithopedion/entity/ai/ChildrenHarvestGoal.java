package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.variant.ChildrenHarvestVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;

public class ChildrenHarvestGoal extends MoveToBlockGoal {

    private final ChildrenEntity children;

    private BlockPos targetPos = BlockPos.ZERO;
    private int harvestTime;

    public ChildrenHarvestGoal(ChildrenEntity childrenIn, double speed, int length, int yMax) {
        super(childrenIn, speed, length, yMax);
        this.children = childrenIn;
    }

    @Override
    public boolean canUse() {
        if (!this.children.isSitting()){
            return false;
        } else if (this.children.isPassenger()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.harvestTime > 160) {
            return false;
        } else if (this.children.isPassenger()) {
            return false;
        } else {
            return super.canContinueToUse();
        }
    }

    @Override
    public void start() {
        super.start();
        this.harvestTime = 0;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).isCollisionShapeFullBlock(worldIn, pos)) {
            for (int i = 1; i < 7; ++i) {
                BlockPos blockpos = pos.above(i);
                if (i > 2 && worldIn.getBlockState(blockpos).getBlock() == Blocks.GRASS) {
                    this.targetPos = blockpos;
                    this.children.setHarvestType(ChildrenHarvestVariant.FABRIC.getId());
                    return true;
                } else if (i > 2 && worldIn.getBlockState(blockpos).getBlock() == Blocks.OAK_WOOD) {
                    this.targetPos = blockpos;
                    this.children.setHarvestType(ChildrenHarvestVariant.WOOD.getId());
                    return true;
                } else if (i > 2 && worldIn.getBlockState(blockpos).getBlock() == Blocks.STONE) {
                    this.targetPos = blockpos;
                    this.children.setHarvestType(ChildrenHarvestVariant.STONE.getId());
                    return true;
                } else if (worldIn.getBlockState(blockpos).isCollisionShapeFullBlock(worldIn, pos.above(i))) {
                    return false;
                }
            }
        }

        return false;
    }
}
