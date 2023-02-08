package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class SoldierSitGoal extends Goal {
    private final SoldierEntity soldier;
    private int sitTimer;
    private int cooldown;

    public SoldierSitGoal(SoldierEntity soldierIn) {
        this.soldier = soldierIn;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (this.soldier.isSitting()) {
            return true;
        } else if (this.soldier.isPassenger() || !this.soldier.isOnGround() || this.soldier.isInWater() || this.soldier.isPraying() || this.soldier.isMoving()) {
            return false;
        } else if (this.soldier.xxa != 0.0F || this.soldier.yya != 0.0F || this.soldier.zza != 0.0F) {
            return false;
        } else {
            if (this.soldier.level.isNight()) {
                return this.soldier.getRandom().nextInt(150) == 0;
            }
            else return this.soldier.getRandom().nextInt(1200) == 0;
        }
    }

    @Override
    public void start() {
        this.sitTimer = 1000 + this.soldier.getRandom().nextInt(1000);
        this.soldier.setSitting(true);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.soldier.isSitting() || this.soldier.isPassenger()) {
            return false;
        } else if (this.soldier.isInWater()) {
            return false;
        } else if (this.sitTimer <= 0) {
            return false;
        } else return this.soldier.xxa == 0.0F && this.soldier.yya == 0.0F && this.soldier.zza == 0.0F;
    }

    @Override
    public void stop() {
        this.cooldown = this.adjustedTickDelay(500);
        this.soldier.setSitting(false);
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
