package com.kalyptien.lithopedion.effect;

import com.kalyptien.lithopedion.Lithopedion;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Lithopedion.MOD_ID);

    public static final RegistryObject<MobEffect> THOOTRATTLE = MOB_EFFECTS.register("thoot_rattle",
            () -> new ThootRattleEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> FRACTURE = MOB_EFFECTS.register("fracture",
            () -> new FractureEffect(MobEffectCategory.HARMFUL, 3124687));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
