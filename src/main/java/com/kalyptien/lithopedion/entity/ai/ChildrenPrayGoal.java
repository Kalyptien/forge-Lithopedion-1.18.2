package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.block.custom.SanctuaryAutelBlock;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class ChildrenPrayGoal  extends Goal {

    private ChildrenEntity children = null;

    private Optional<BlockPos> autel = Optional.empty();

    private int prayTimer = 0;
    private int cooldown = 0;

    private final Predicate<BlockState> VALID_AUTEL_BLOCKS = (p_28074_) -> {
        if(this.children != null && this.children.getSanctuary() != null) {
            if (p_28074_.is(this.children.getSanctuary())) {
                return true;
            }
        }
        return false;
    };

    private final Predicate<BlockState> NEAREST_AUTEL_BLOCKS = (p_28074_) -> {
        if(this.children != null) {
            if (p_28074_.is(ModBlocks.FUNGUS_AUTEL.get())) {
                return true;
            }
        }
        return false;
    };

    public ChildrenPrayGoal(ChildrenEntity pMob) {
        this.children = pMob;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (!this.children.isSitting()) {
            return true;
        } else if(children.isConnect()){
            this.autel = this.children.findNearestBlock(this.VALID_AUTEL_BLOCKS, LithopedionUtil.sanctuary_autel_zone * 2);
            return autel.isPresent();
        } else {
            if (this.children.getRandom().nextInt(1200) == 0) {
                this.autel = this.children.findNearestBlock(this.NEAREST_AUTEL_BLOCKS, LithopedionUtil.sanctuary_autel_zone * 3);
                return autel.isPresent();
            }
            else return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.prayTimer >= 0;
    }

    @Override
    public void start() {
        this.prayTimer = 500 + this.children.getRandom().nextInt(600);
        super.start();
    }

    @Override
    public void stop() {
        this.cooldown = this.adjustedTickDelay(500);
        if(prayTimer <= 0){
            if(!this.children.isConnect() && this.autel.isPresent()){
                Level level = this.children.level;
                BlockState blockstate = level.getBlockState(this.autel.get());
                Block block = blockstate.getBlock();
                if(block instanceof SanctuaryAutelBlock){
                    ((SanctuaryAutelBlock) block).connect(this.children);
                }
            }
            if(Math.random() >= 0.5){
                this.children.setRessource(this.children.getRessource() + 1);
            }
        }
        this.children.setPraying(false);
        super.stop();
    }

    public void tick() {
        Level level = this.children.level;
        if(this.autel.isPresent()){
            int i = Mth.floor(this.children.getX() - this.autel.get().getX());
            int j = Mth.floor(this.children.getY() + this.autel.get().getY());
            int k = Mth.floor(this.children.getZ() - this.autel.get().getZ());

            Vec3 vec3 = new Vec3((double)this.children.getBlockX() + 0.5D, (double)j + 0.5D, (double)this.children.getBlockZ() + 0.5D);
            Vec3 vec31 = new Vec3((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
            BlockHitResult blockhitresult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.children));
            boolean flag = blockhitresult.getBlockPos().equals(this.autel);
            if (flag) {
                --this.prayTimer;
                this.children.setPraying(true);
            }
        }
    }
}
