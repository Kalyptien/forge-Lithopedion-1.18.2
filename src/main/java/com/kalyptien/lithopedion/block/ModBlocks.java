package com.kalyptien.lithopedion.block;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.LithopedionUtil;
import com.kalyptien.lithopedion.block.custom.*;
import com.kalyptien.lithopedion.variant.SanctuaryVariant;
import com.kalyptien.lithopedion.item.ModCreativeModeTab;
import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lithopedion.MOD_ID);

    // ORE
    public static final RegistryObject<Block> JADE_BLOCK = registerBlock( "jade_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> JADE_ORE = registerBlock( "jade_ore", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> DEEPSLATE_JADE_ORE = registerBlock( "deepslate_jade_ore", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(9f)
            .requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    //WOOD
    public static final RegistryObject<Block> PETRIFIED_PLANKS = registerBlock("petrified_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_LOG = registerBlock("petrified_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_WOOD = registerBlock("petrified_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG = registerBlock("stripped_petrified_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
            ModCreativeModeTab.LITHOPEDION_TAB);

    public static final RegistryObject<Block> PETRIFIED_BUTTON = registerBlock("petrified_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops().noCollission()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE = registerBlock("petrified_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_DOOR = registerBlock("petrified_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(5f).requiresCorrectToolForDrops().noOcclusion()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_TRAPDOOR = registerBlock("petrified_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(5f).requiresCorrectToolForDrops().noOcclusion()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_STAIRS = registerBlock("petrified_stairs",
            () -> new StairBlock(() -> ModBlocks.PETRIFIED_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_SLAB = registerBlock("petrified_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_FENCE = registerBlock("petrified_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_FENCE_GATE = registerBlock("petrified_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> PETRIFIED_WALL = registerBlock("petrified_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.LITHOPEDION_TAB);
    // PLANTS
    public static final RegistryObject<Block> PETRIFIED_WHEAT_PLANT = registerBlockWithoutBlockItem("petrified_wheat_plant",
            () -> new PetrifiedWheatPlant(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    // LITHOPEDION
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
    // POTS
    public static final RegistryObject<Block> POT_CLAY = registerBlock("pot_clay",
            () -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> TALL_POT_CLAY = registerBlock("tall_pot_clay",
            () -> new TallPotBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> SMALL_POT_CLAY = registerBlock("small_pot_clay",
            () -> new SmallPotBlock(BlockBehaviour.Properties.copy(Blocks.CLAY).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> POT_TERRACOTTA = registerBlock("pot_terracotta",
            () -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> TALL_POT_TERRACOTTA = registerBlock("tall_pot_terracotta",
            () -> new TallPotBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> SMALL_POT_TERRACOTTA = registerBlock("small_pot_terracotta",
            () -> new SmallPotBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    // SANCTUARY
    public static final RegistryObject<Block> FUNGUS_AUTEL = registerBlock("fungus_autel",
            () -> new SanctuaryAutelBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WOOD).noOcclusion(), LithopedionUtil.sanctuary_autel_zone, SanctuaryVariant.FUNGUS),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> FUNGUS_STONE = registerBlock("fungus_stone",
            () -> new SanctuaryStoneBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion(), LithopedionUtil.sanctuary_stone_zone, SanctuaryVariant.FUNGUS),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> FUNGUS_STATUS = registerBlock("fungus_status",
            () -> new SanctuaryStatusBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).noOcclusion(), LithopedionUtil.sanctuary_status_zone, SanctuaryVariant.FUNGUS),
            ModCreativeModeTab.LITHOPEDION_TAB);

    // CRAFT
    public static final RegistryObject<Block> EARTH_FURNACE = registerBlock("earth_furnace",
            () -> new EarthFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);
    public static final RegistryObject<Block> POTTERY_WHEEL = registerBlock("pottery_wheel",
            () -> new PotteryWheelBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE).noOcclusion()),
            ModCreativeModeTab.LITHOPEDION_TAB);

    // =====================================================================================================================

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
