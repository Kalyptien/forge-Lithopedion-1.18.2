package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Predicate;

public class ChildrenFloatGoal extends FloatGoal {

    private ChildrenEntity children = null;

    private final Predicate<BlockState> VALID_STATUS_BLOCKS = (p_28074_) -> {
        if(this.children != null && this.children.getSanctuary() != null) {
            if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.FUNGUS) {
                return true;
            }
            /*else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.FIRE) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.EARTH) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.WATER) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.AIR) {
                return true;
            }*/
        }
        return false;
    };

    private final Predicate<BlockState> VALID_STONE_BLOCKS = (p_28074_) -> {
        if(this.children != null && this.children.getSanctuary() != null) {
            if (p_28074_.is(ModBlocks.FUNGUS_STONE.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.FUNGUS) {
                return true;
            }
            /*else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.FIRE) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.EARTH) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.WATER) {
                return true;
            }
            else if (p_28074_.is(ModBlocks.FUNGUS_STATUS.get()) && this.children.getSanctuary().getSvariant() == SanctuaryVariant.AIR) {
                return true;
            }*/
        }
        return false;
    };

    private final Predicate<BlockState> VALID_AUTEL_BLOCKS = (p_28074_) -> {
        if(this.children != null && this.children.getSanctuary() != null) {
            if (p_28074_.is(this.children.getSanctuary())) {
                return true;
            }
        }
        return false;
    };

    public ChildrenFloatGoal(ChildrenEntity pMob) {
        super(pMob);
        this.children = pMob;
    }

    public boolean canUse() {
        if(children.isConnect()){

            Optional<BlockPos> autel = this.children.findNearestBlock(this.VALID_AUTEL_BLOCKS, LithopedionUtil.sanctuary_autel_zone);
            Optional<BlockPos> status = this.children.findNearestBlock(this.VALID_STATUS_BLOCKS, LithopedionUtil.sanctuary_status_zone);
            Optional<BlockPos> stone = this.children.findNearestBlock(this.VALID_STONE_BLOCKS, LithopedionUtil.sanctuary_stone_zone);

            MoveControl movecontrol = this.children.getMoveControl();

            double X = movecontrol.getWantedX();
            double Y = movecontrol.getWantedY();
            double Z = movecontrol.getWantedZ();

             if(autel.isPresent() &&
                     autel.get().getX() - LithopedionUtil.sanctuary_autel_zone < X && X < autel.get().getX() + LithopedionUtil.sanctuary_autel_zone &&
                     autel.get().getY() - LithopedionUtil.sanctuary_autel_zone < Y && Y < autel.get().getY() + LithopedionUtil.sanctuary_autel_zone &&
                     autel.get().getZ() - LithopedionUtil.sanctuary_autel_zone < Z && Z < autel.get().getZ() + LithopedionUtil.sanctuary_autel_zone){
                 return super.canUse();
             }
             else if (status.isPresent() &&
                     status.get().getX() - LithopedionUtil.sanctuary_status_zone < X && X < status.get().getX() + LithopedionUtil.sanctuary_status_zone &&
                     status.get().getY() - LithopedionUtil.sanctuary_status_zone < Y && Y < status.get().getY() + LithopedionUtil.sanctuary_status_zone &&
                     status.get().getZ() - LithopedionUtil.sanctuary_status_zone < Z && Z < status.get().getZ() + LithopedionUtil.sanctuary_status_zone){
                 return super.canUse();
             }
             else if (stone.isPresent() &&
                     stone.get().getX() - LithopedionUtil.sanctuary_stone_zone < X && X < stone.get().getX() + LithopedionUtil.sanctuary_stone_zone &&
                     stone.get().getY() - LithopedionUtil.sanctuary_stone_zone < Y && Y < stone.get().getY() + LithopedionUtil.sanctuary_stone_zone &&
                     stone.get().getZ() - LithopedionUtil.sanctuary_stone_zone < Z && Z < stone.get().getZ() + LithopedionUtil.sanctuary_stone_zone){
                 return super.canUse();
             }
             else{
                 movecontrol.setWantedPosition(children.getX(),children.getY(),children.getZ(),0);
                 return false;
             }
        }
        else{
            return super.canUse();
        }
    }

}
