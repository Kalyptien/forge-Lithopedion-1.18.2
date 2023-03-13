package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Predicate;

public class SoldierFloatGoal extends FloatGoal {

    private SoldierEntity soldier = null;

    private final double soldierWatchDMultiplier = 0.5;

    private final Predicate<BlockState> VALID_STATUS_BLOCKS = (p_28074_) -> {
        if(this.soldier != null && this.soldier.getSanctuary() != null) {
            if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.FUNGUS) {
                return true;
            }
            /*else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.FIRE) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.EARTH) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.WATER) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.AIR) {
                return true;
            }*/
        }
        return false;
    };

    private final Predicate<BlockState> VALID_STONE_BLOCKS = (p_28074_) -> {
        if(this.soldier != null && this.soldier.getSanctuary() != null) {
            if (p_28074_.is(ModBlocks.FUNGUS_STONE.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.FUNGUS) {
                return true;
            }
            /*else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.FIRE) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.EARTH) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.WATER) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.soldier.getSanctuary().getSvariant() == SanctuaryVariant.AIR) {
                return true;
            }*/
        }
        return false;
    };

    private final Predicate<BlockState> VALID_AUTEL_BLOCKS = (p_28074_) -> {
        if(this.soldier != null && this.soldier.getSanctuary() != null) {
            if (p_28074_.is(this.soldier.getSanctuary())) {
                return true;
            }
        }
        return false;
    };

    public SoldierFloatGoal(SoldierEntity pMob) {
        super(pMob);
        this.soldier = pMob;
    }

    @Override
    public boolean canUse() {
        if(soldier.isConnect()){

            Optional<BlockPos> autel = this.soldier.findNearestBlock(this.VALID_AUTEL_BLOCKS, (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier));
            Optional<BlockPos> status = this.soldier.findNearestBlock(this.VALID_STATUS_BLOCKS, (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier));
            Optional<BlockPos> stone = this.soldier.findNearestBlock(this.VALID_STONE_BLOCKS, (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier));

            MoveControl movecontrol = this.soldier.getMoveControl();

            double X = movecontrol.getWantedX();
            double Y = movecontrol.getWantedY();
            double Z = movecontrol.getWantedZ();

            if(autel.isPresent() &&
                    autel.get().getX() - (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier) < X && X < autel.get().getX() + (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier) &&
                    autel.get().getY() - (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier) < Y && Y < autel.get().getY() + (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier) &&
                    autel.get().getZ() - (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier) < Z && Z < autel.get().getZ() + (LithopedionUtil.sanctuary_autel_zone * this.soldierWatchDMultiplier)){
                return super.canUse();
            }
            else if (status.isPresent() &&
                    status.get().getX() - (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier) < X && X < status.get().getX() + (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier) &&
                    status.get().getY() - (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier) < Y && Y < status.get().getY() + (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier) &&
                    status.get().getZ() - (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier) < Z && Z < status.get().getZ() + (LithopedionUtil.sanctuary_status_zone * this.soldierWatchDMultiplier)){
                return super.canUse();
            }
            else if (stone.isPresent() &&
                    stone.get().getX() - (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier) < X && X < stone.get().getX() + (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier) &&
                    stone.get().getY() - (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier) < Y && Y < stone.get().getY() + (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier) &&
                    stone.get().getZ() - (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier) < Z && Z < stone.get().getZ() + (LithopedionUtil.sanctuary_stone_zone * this.soldierWatchDMultiplier)){
                return super.canUse();
            }
            else{
                return false;
            }
        }
        else{
            if(this.soldier.isPraying() || this.soldier.isSitting()){
                return false;
            }
            else return super.canUse();
        }
    }

    @Override
    public void start() {
        this.soldier.setMoving(true);
    }

    @Override
    public void stop() {
        this.soldier.setMoving(false);
    }
}
