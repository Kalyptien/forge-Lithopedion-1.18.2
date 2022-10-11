package com.kalyptien.lithopedion.entity;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Lithopedion.MOD_ID);

    public static final RegistryObject<EntityType<ChildrenEntity>> COE =
            ENTITY_TYPES.register("coe",
                    () -> EntityType.Builder.of(ChildrenEntity::new, MobCategory.CREATURE)
                            .sized(0.1f, 0.3f)
                            .build(new ResourceLocation(Lithopedion.MOD_ID, "coe").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
