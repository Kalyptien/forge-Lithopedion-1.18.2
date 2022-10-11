package com.kalyptien.lithopedion;

import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.block.entity.ModBlockEntities;
import com.kalyptien.lithopedion.entity.ModEntityTypes;
import com.kalyptien.lithopedion.entity.client.ChildrenRenderer;
import com.kalyptien.lithopedion.item.ModItems;
import com.kalyptien.lithopedion.recipe.ModRecipes;
import com.kalyptien.lithopedion.screen.ModMenuTypes;
import com.kalyptien.lithopedion.screen.PotteryWheelScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Lithopedion.MOD_ID)
public class Lithopedion
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "lithopedion";

    public Lithopedion()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModRecipes.register(eventBus);
        ModMenuTypes.register(eventBus);

        ModEntityTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LITHOPEDION_CLAY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LITHOPEDION_SOLIDER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LITHOPEDION_JADE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_CLAY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TALL_POT_CLAY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_POT_CLAY.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EARTH_FURNACE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTERY_WHEEL.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.POTTERY_WHEEL_MENU.get(), PotteryWheelScreen::new);

        EntityRenderers.register(ModEntityTypes.COE.get(), ChildrenRenderer::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
