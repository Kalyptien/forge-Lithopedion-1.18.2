package com.kalyptien.lithopedion.block.entity;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.block.entity.custom.PotteryWheelBlockEntity;
import com.kalyptien.lithopedion.block.entity.custom.SanctuaryAutelBLockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Lithopedion.MOD_ID);

    public static final RegistryObject<BlockEntityType<PotteryWheelBlockEntity>> POTTERY_WHEEL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("pottery_wheel_block_entity",
                    ()-> BlockEntityType.Builder.of(PotteryWheelBlockEntity::new, ModBlocks.POTTERY_WHEEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<PotteryWheelBlockEntity>> SANCTUARY_AUTEL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sanctuary_autel_block_entity",
                    ()-> BlockEntityType.Builder.of(SanctuaryAutelBLockEntity::new, ModBlocks.FUNGUS_AUTEL.get()).build(null));
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
