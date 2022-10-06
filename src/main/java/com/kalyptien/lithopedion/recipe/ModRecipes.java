package com.kalyptien.lithopedion.recipe;

import com.kalyptien.lithopedion.Lithopedion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Lithopedion.MOD_ID);

    public static final RegistryObject<RecipeSerializer<PotteryWheelRecipe>> POTTERY_WHEEL_SERIALIZER =
            SERIALIZERS.register("pottery_wheel", () -> PotteryWheelRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
