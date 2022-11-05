package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.entity.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.entity.variant.SanctuaryVariant;
import net.minecraft.world.level.block.Block;

public abstract class SanctuaryBlock extends Block {

    private SanctuaryBlock sanctuary;
    public SanctuaryVariant Svariant;
    public int area;
    public boolean isActive = false;
    public SanctuaryBlockVariant SBvariant;

    public SanctuaryBlock(Properties pProperties) {
        super(pProperties);
    }

    public boolean connect(ChildrenEntity children){
        return true;
    }

    public boolean connect(SoldierEntity soldier){
        return true;
    }

    public boolean connect(SanctuaryBlock block){
        return true;
    }

    public SanctuaryVariant getSvariant() {
        return Svariant;
    }

    public void setSvariant(SanctuaryVariant svariant) {
        Svariant = svariant;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SanctuaryBlock getSanctuary() {
        return sanctuary;
    }

    public void setSanctuary(SanctuaryBlock sanctuary) {
        this.sanctuary = sanctuary;
    }
}
