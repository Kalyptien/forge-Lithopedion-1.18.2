package com.kalyptien.lithopedion.item;

import com.kalyptien.lithopedion.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;

public class ModFoods {
    public static final FoodProperties PETRIFIED_CARROT = (new FoodProperties.Builder()).nutrition( Foods.CARROT.getNutrition() + 2 ).saturationMod(Foods.CARROT.getSaturationModifier() * 1.5f)
            .effect(() -> new MobEffectInstance(ModEffects.THOOTRATTLE.get(), 400, 0), 1.0F)
            .build();

    public static final FoodProperties PETRIFIED_POTATO = (new FoodProperties.Builder()).nutrition( Foods.POTATO.getNutrition() + 2 ).saturationMod(Foods.POTATO.getSaturationModifier() * 1.5f)
            .effect(() -> new MobEffectInstance(ModEffects.THOOTRATTLE.get(), 400, 0), 1.0F)
            .build();

    public static final FoodProperties PETRIFIED_BREAD = (new FoodProperties.Builder()).nutrition( Foods.BREAD.getNutrition() + 3 ).saturationMod(Foods.BREAD.getSaturationModifier() * 1.5f)
            .effect(() -> new MobEffectInstance(ModEffects.THOOTRATTLE.get(), 600, 0), 1.0F)
            .build();
}
