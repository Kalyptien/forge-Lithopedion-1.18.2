package com.kalyptien.lithopedion.entity.custom;

import com.kalyptien.lithopedion.entity.ai.ChildrenSitGoal;
import com.kalyptien.lithopedion.entity.harvest.ChildrenHarvest;
import com.kalyptien.lithopedion.entity.variant.ChildrenVariant;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class ChildrenEntity extends Animal implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    private static final EntityDataAccessor<Integer> TYPE_VARIANT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TYPE_HARVEST = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FABRIC_AMOUNT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WOOD_AMOUNT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STONE_AMOUNT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.BOOLEAN);


    private static final UUID SPEED_MODIFIER_SITTING_UUID = UUID.fromString("2EF64346-9E56-44E9-9574-1BF9FD6443CF");
    private static final AttributeModifier SPEED_MODIFIER_SITTING = new AttributeModifier(SPEED_MODIFIER_SITTING_UUID, "Sitting speed reduction", -0.75D, AttributeModifier.Operation.MULTIPLY_BASE);


    public ChildrenEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ChildrenSitGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            System.out.println("someone walk here"); // dont work
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.walk", true));
            return PlayState.CONTINUE;
        }
        else if(this.isSitting()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.sit", false));
            return PlayState.CONTINUE;
        }
        /*else if(this.getHarvestType() != 0){
            if (this.getHarvestType() == ChildrenHarvest.FABRIC.getId()){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.harvest_fabric", true));
            } else if (this.getHarvestType() == ChildrenHarvest.WOOD.getId()){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.harvest_wood", true));
            } else if (this.getHarvestType() == ChildrenHarvest.STONE.getId()){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.harvest_stone", true));
            }

            return PlayState.CONTINUE;
        }*/

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE_VARIANT, 0);
        this.entityData.define(FABRIC_AMOUNT, 0);
        this.entityData.define(WOOD_AMOUNT, 0);
        this.entityData.define(STONE_AMOUNT, 0);
        this.entityData.define(TYPE_HARVEST, 0);
        this.entityData.define(SITTING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("Fabric", this.getTypeVariant());
        tag.putInt("Wood", this.getTypeVariant());
        tag.putInt("Stone", this.getTypeVariant());
        tag.putInt("HarvestType", this.getTypeVariant());
        tag.putBoolean("Sitting", this.isSitting());

    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(TYPE_VARIANT, tag.getInt("Variant"));

        this.setSitting(tag.getBoolean("Sitting"));

        this.entityData.set(FABRIC_AMOUNT, tag.getInt("Fabric"));
        this.entityData.set(WOOD_AMOUNT, tag.getInt("Wood"));
        this.entityData.set(STONE_AMOUNT, tag.getInt("Stone"));

        this.entityData.set(TYPE_HARVEST, tag.getInt("HarvestType"));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    /* GOAL */

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        AttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (modifiableattributeinstance.getModifier(SPEED_MODIFIER_SITTING_UUID) != null) {
            modifiableattributeinstance.removeModifier(SPEED_MODIFIER_SITTING);
        }

        if (sitting) {
            modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_SITTING);
        }
    }
    public int getFabric(){return this.entityData.get(FABRIC_AMOUNT);}
    public int getWood(){return this.entityData.get(WOOD_AMOUNT);}
    public int getStone(){return this.entityData.get(STONE_AMOUNT);}

    public void setFabric(int amount){this.entityData.set(FABRIC_AMOUNT, amount);}
    public void setWood(int amount){this.entityData.set(WOOD_AMOUNT, amount);}
    public void setStone(int amount){this.entityData.set(STONE_AMOUNT, amount);}

    public int getHarvestType(){ return this.entityData.get(TYPE_HARVEST);}

    public void setHarvestType(int id){ this.entityData.set(TYPE_HARVEST, id);}

    /* SOUND */
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.STONE_FALL, 0.15F, 1.0F);
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STONE_STEP;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.STONE_HIT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    /* VARIANTS */

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_,
                                        MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_,
                                        @Nullable CompoundTag p_146750_) {
        ChildrenVariant variant = Util.getRandom(ChildrenVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    public ChildrenVariant getVariant() {
        return ChildrenVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(TYPE_VARIANT);
    }

    private void setVariant(ChildrenVariant variant) {
        this.entityData.set(TYPE_VARIANT, variant.getId() & 255);
    }
}
