package com.kalyptien.lithopedion.item;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.item.custom.PotteryStaffItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lithopedion.MOD_ID);

    public static final RegistryObject<Item> JADE = ITEMS.register("jade", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static final RegistryObject<Item> NAGA_HEARTH = ITEMS.register("naga_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> SOLDIER_HEARTH = ITEMS.register("soldier_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> CLAY_HEARTH = ITEMS.register("clay_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static final RegistryObject<Item> PARCHMENT_LITHOPEDION = ITEMS.register("parchment_lithopedion", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PARCHMENT_POT = ITEMS.register("parchment_pot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PARCHMENT_MASK = ITEMS.register("parchment_mask", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static final RegistryObject<Item> POTTERY_STAFF_FAST = ITEMS.register("pottery_staff_fast", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> POTTERY_STAFF_MEDIUM = ITEMS.register("pottery_staff_medium", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> POTTERY_STAFF_SLOW = ITEMS.register("pottery_staff_slow", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
