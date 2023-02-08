package com.kalyptien.lithopedion.item;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.entity.ModEntityTypes;
import com.kalyptien.lithopedion.item.custom.DebugStaffItem;
import com.kalyptien.lithopedion.item.custom.PotteryStaffItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lithopedion.MOD_ID);

    // MISC
    public static final RegistryObject<Item> JADE = ITEMS.register("jade", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> FUNGUS_JADE = ITEMS.register("fungus_jade", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    // ELEMENT
    public static final RegistryObject<Item> FUNGUS_ELEMENT = ITEMS.register("fungus_element", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> WATER_ELEMENT = ITEMS.register("water_element", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> FIRE_ELEMENT = ITEMS.register("fire_element", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> EARTH_ELEMENT = ITEMS.register("earth_element", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> AIR_ELEMENT = ITEMS.register("air_element", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    // FOOD
    public static final RegistryObject<Item> PETRIFIED_SEEDS = ITEMS.register("petrified_seeds",
            () -> new ItemNameBlockItem(ModBlocks.PETRIFIED_WHEAT_PLANT.get(),
                    new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PETRIFIED_WHEAT = ITEMS.register("petrified_wheat", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PETRIFIED_POTATO = ITEMS.register("petrified_potato",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB).food(ModFoods.PETRIFIED_POTATO)));
    //public static final RegistryObject<Item> WATERMELON_SHELL
    public static final RegistryObject<Item> PETRIFIED_CARROT = ITEMS.register("petrified_carrot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB).food(ModFoods.PETRIFIED_CARROT)));
    public static final RegistryObject<Item> PETRIFIED_BREAD = ITEMS.register("petrified_bread",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB).food(ModFoods.PETRIFIED_BREAD)));
    //HEARTH
    public static final RegistryObject<Item> NAGA_HEARTH = ITEMS.register("naga_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> SOLDIER_HEARTH = ITEMS.register("soldier_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> CLAY_HEARTH = ITEMS.register("clay_hearth", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    // PARCHMENT
    public static final RegistryObject<Item> PARCHMENT_LITHOPEDION = ITEMS.register("parchment_lithopedion", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PARCHMENT_POT = ITEMS.register("parchment_pot", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> PARCHMENT_MASK = ITEMS.register("parchment_mask", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    //STAFF
    public static final RegistryObject<Item> POTTERY_STAFF_FAST = ITEMS.register("pottery_staff_fast", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> POTTERY_STAFF_MEDIUM = ITEMS.register("pottery_staff_medium", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static final RegistryObject<Item> POTTERY_STAFF_SLOW = ITEMS.register("pottery_staff_slow", () -> new PotteryStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static final RegistryObject<Item> DEBUG_STAFF = ITEMS.register("debug_staff", () -> new DebugStaffItem(new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    // SPAWN
    public static final RegistryObject<Item> COE_SPAWN_EGG = ITEMS.register("coe_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.COE,0x636363, 0x9c9c9c,
                    new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));

    public static final RegistryObject<Item> SOE_SPAWN_EGG = ITEMS.register("soe_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.SOE,0x636363, 0x947540,
                    new Item.Properties().tab(ModCreativeModeTab.LITHOPEDION_TAB)));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
