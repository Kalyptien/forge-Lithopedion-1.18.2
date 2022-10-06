package com.kalyptien.lithopedion.block;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.block.custom.*;
import com.kalyptien.lithopedion.item.ModCreativeModeTab;
import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lithopedion.MOD_ID);

    // ===================================================================================================================== BLOCKS
    public static final RegistryObject<Block> JADE_BLOCK = registerBlock( "jade_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> JADE_ORE = registerBlock( "jade_ore", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> DEEPSLATE_JADE_ORE = registerBlock( "deepslate_jade_ore", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    // ===================================================================================================================== LITHOPEDION
    public static final RegistryObject<Block> LITHOPEDION_CLAY = registerBlock("lithopedion_clay",
            () -> new LithopedionClayBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> LITHOPEDION_TERRACOTTA = registerBlock("lithopedion_terracotta",
            () -> new LithopedionClayBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> LITHOPEDION_SOLIDER = registerBlock("lithopedion_soldier",
            () -> new LithopedionSoldierBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> LITHOPEDION_JADE = registerBlock("lithopedion_jade",
            () -> new LithopedionJadeBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    // ===================================================================================================================== DECORATIONS
    public static final RegistryObject<Block> POT_CLAY = registerBlock("pot_clay",
            () -> new PotClayBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> TALL_POT_CLAY = registerBlock("tall_pot_clay",
            () -> new TallPotClayBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> SMALL_POT_CLAY = registerBlock("small_pot_clay",
            () -> new SmallPotClayBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> POT_TERRACOTTA = registerBlock("pot_terracotta",
            () -> new PotClayBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> TALL_POT_TERRACOTTA = registerBlock("tall_pot_terracotta",
            () -> new TallPotClayBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> SMALL_POT_TERRACOTTA = registerBlock("small_pot_terracotta",
            () -> new SmallPotClayBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    // ===================================================================================================================== CRAFT
    public static final RegistryObject<Block> EARTH_FURNACE = registerBlock("earth_furnace",
            () -> new EarthFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> POTTERY_WHEEL = registerBlock("pottery_wheel",
            () -> new PotteryWheelBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
