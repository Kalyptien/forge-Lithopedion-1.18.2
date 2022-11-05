package com.kalyptien.lithopedion.entity.ai;

import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class ChildrenFloatGoal extends FloatGoal {

    private final ChildrenEntity children;

    public ChildrenFloatGoal(ChildrenEntity pMob) {
        super(pMob);
        this.children = pMob;
    }

    @Override
    public void start() {
        this.children.setMoving(true);
    }

    @Override
    public void tick() {
        if (this.children.getRandom().nextFloat() < 0.8F) {
            this.children.getJumpControl().jump();
        }
    }

    @Override
    public void stop() {
        this.children.setMoving(false);
    }

}
