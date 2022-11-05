package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class SoldierFloatGoal extends FloatGoal {

    private final SoldierEntity soldier;

    public SoldierFloatGoal(SoldierEntity pMob) {
        super(pMob);
        this.soldier = pMob;
    }

    @Override
    public void start() {
        this.soldier.setMoving(true);
    }

    @Override
    public void tick() {
        if (this.soldier.getRandom().nextFloat() < 0.8F) {
            this.soldier.getJumpControl().jump();
        }
    }

    @Override
    public void stop() {
        this.soldier.setMoving(false);
    }
}
