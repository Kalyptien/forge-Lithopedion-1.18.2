package com.kalyptien.lithopedion.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab LITHOPEDION_TAB = new CreativeModeTab("lithopediontab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.JADE.get());
        }
    };
}
