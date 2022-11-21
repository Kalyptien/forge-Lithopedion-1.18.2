package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ChildrenSitGoal extends Goal {
    private final ChildrenEntity children;
    private int sitTimer;
    private int cooldown;

    public ChildrenSitGoal(ChildrenEntity pMob) {
        this.children = pMob;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (this.children.isSitting()) {
            return true;
        } else if (this.children.isPassenger() || !this.children.isOnGround() || this.children.isInWater()) {
            return false;
        } else if (this.children.xxa != 0.0F || this.children.yya != 0.0F || this.children.zza != 0.0F) {
            return false;
        } else {
            if (this.children.level.isNight()) {
                return this.children.getRandom().nextInt(150) == 0;
            }
            else return this.children.getRandom().nextInt(1200) == 0;
        }
    }

    @Override
    public void start() {
        this.sitTimer = 500 + this.children.getRandom().nextInt(600);
        this.children.setSitting(true);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.children.isSitting() || this.children.isPassenger()) {
            return false;
        } else if (this.children.isInWater()) {
            return false;
        } else if (this.sitTimer <= 0) {
            return false;
        } else return this.children.xxa == 0.0F && this.children.yya == 0.0F && this.children.zza == 0.0F;
    }

    @Override
    public void stop() {
        this.cooldown = this.adjustedTickDelay(100);
        this.children.setSitting(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        --this.sitTimer;
    }
}
