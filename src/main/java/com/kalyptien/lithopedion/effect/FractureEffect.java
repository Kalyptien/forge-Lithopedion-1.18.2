package com.kalyptien.lithopedion.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FractureEffect extends MobEffect {

    public FractureEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            if((pAmplifier == 0)  && (pLivingEntity.getSpeed()  >= 1.5) ){
                pLivingEntity.hurt(DamageSource.MAGIC, 1.0F);
                pLivingEntity.teleportTo(pLivingEntity.getX() + pLivingEntity.getDeltaMovement().x, pLivingEntity.getY(), pLivingEntity.getZ() + pLivingEntity.getDeltaMovement().z);
            }
            else if((pAmplifier == 1)  && (pLivingEntity.getSpeed() >= 1.0) ){
                if((pLivingEntity.getDeltaMovement().x >= 1.5) ){
                    pLivingEntity.hurt(DamageSource.MAGIC, 2.0F);
                    pLivingEntity.teleportTo(pLivingEntity.getX() + pLivingEntity.getDeltaMovement().x, pLivingEntity.getY(), pLivingEntity.getZ() + pLivingEntity.getDeltaMovement().z);
                }
                else{
                    pLivingEntity.hurt(DamageSource.MAGIC, 1.0F);
                }
            }
            else if((pAmplifier >= 2)  && (!pLivingEntity.isCrouching()) ){
                if((pLivingEntity.getSpeed()  >= 5.0) ){
                    pLivingEntity.hurt(DamageSource.MAGIC, 3.0F);
                    pLivingEntity.teleportTo(pLivingEntity.getX() + pLivingEntity.getDeltaMovement().x, pLivingEntity.getY(), pLivingEntity.getZ() + pLivingEntity.getDeltaMovement().z);
                }
                else if((pLivingEntity.getSpeed()  >= 4.0) ){
                    pLivingEntity.hurt(DamageSource.MAGIC, 2.0F);
                    pLivingEntity.teleportTo(pLivingEntity.getX() + pLivingEntity.getDeltaMovement().x, pLivingEntity.getY(), pLivingEntity.getZ() + pLivingEntity.getDeltaMovement().z);
                }
                else {
                    pLivingEntity.hurt(DamageSource.MAGIC, 1.0F);
                }
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
