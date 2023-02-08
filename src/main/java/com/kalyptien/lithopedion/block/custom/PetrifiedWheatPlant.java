package com.kalyptien.lithopedion.block.custom;

import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PetrifiedWheatPlant extends CropBlock {


    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public PetrifiedWheatPlant(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.PETRIFIED_SEEDS.get();
    }

}
