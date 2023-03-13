package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.variant.SanctuaryBlockVariant;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class SanctuaryBlock extends BaseEntityBlock {

    private SanctuaryAutelBlock sanctuary;
    public SanctuaryVariant Svariant;
    public int area;

    public static final BooleanProperty ACTIVED = BooleanProperty.create("actived");
    public SanctuaryBlockVariant SBvariant;

    public SanctuaryBlock(Properties pProperties) {
        super(pProperties);
    }

    public void connect(ChildrenEntity children){
        if(this.sanctuary != null){
            this.sanctuary.connect(children);
        }
    }

    public void connect(SoldierEntity soldier){
        if(this.sanctuary != null){
            this.sanctuary.connect(soldier);
        }
    }

    public void connect(SanctuaryBlock block){
        if(this.sanctuary != null){
            this.sanctuary.connect(block);
        }
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

    public boolean isValidSanctuaryBlock(Block block){
        return block instanceof SanctuaryBlock;
    }

    public boolean canConnectToAutel(SanctuaryBlock block){
        return block.getSanctuary() == null && block.getSvariant() == this.getSvariant();
    }

    public boolean isConnectToAutel(SanctuaryBlock block){
        return block.getSanctuary() != null && block.getSvariant() == this.getSvariant();
    }

    public SanctuaryAutelBlock getSanctuary() {
        return sanctuary;
    }

    public void setSanctuary(SanctuaryAutelBlock sanctuary) {
        this.sanctuary = sanctuary;
    }

    public void unsetSanctuary() {
        this.sanctuary = null;
    }
}
