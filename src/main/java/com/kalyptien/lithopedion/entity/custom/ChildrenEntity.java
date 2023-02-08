package com.kalyptien.lithopedion.entity.custom;

import com.kalyptien.lithopedion.block.custom.SanctuaryAutelBlock;
import com.kalyptien.lithopedion.entity.ai.ChildrenFloatGoal;
import com.kalyptien.lithopedion.entity.ai.ChildrenPrayGoal;
import com.kalyptien.lithopedion.entity.ai.ChildrenSitGoal;
import com.kalyptien.lithopedion.variant.ChildrenHarvestVariant;
import com.kalyptien.lithopedion.variant.ChildrenVariant;
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

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class ChildrenEntity extends Animal implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    private static final EntityDataAccessor<Integer> TYPE_VARIANT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RESSOURCE_AMOUNT = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PRAYING = SynchedEntityData.defineId(ChildrenEntity.class, EntityDataSerializers.BOOLEAN);

    private static final UUID SPEED_MODIFIER_SITTING_UUID = UUID.fromString("2EF64346-9E56-44E9-9574-1BF9FD6443CF");
    private static final AttributeModifier SPEED_MODIFIER_SITTING = new AttributeModifier(SPEED_MODIFIER_SITTING_UUID, "Sitting speed reduction", -0.75D, AttributeModifier.Operation.MULTIPLY_BASE);
    private SanctuaryAutelBlock sanctuary = null;
    private ChildrenHarvestVariant harvest_type = ChildrenHarvestVariant.NONE;

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
        this.goalSelector.addGoal(0, new ChildrenFloatGoal(this));
        this.goalSelector.addGoal(1, new ChildrenSitGoal(this));
        this.goalSelector.addGoal(2, new ChildrenPrayGoal(this,1.0D, 24));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.walk", true));
            return PlayState.CONTINUE;
        }
        else if(this.isSitting()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.sit", false));
            return PlayState.CONTINUE;
        }
        else if(this.isPraying()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.pray", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.coe.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    /* DATA */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE_VARIANT, 0);
        this.entityData.define(RESSOURCE_AMOUNT, 0);
        this.entityData.define(SITTING, false);
        this.entityData.define(MOVING, false);
        this.entityData.define(PRAYING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("Ressource", this.getRessource());
        tag.putBoolean("Sitting", this.isSitting());
        tag.putBoolean("Moving", this.isMoving());
        tag.putBoolean("Praying", this.isPraying());

    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(TYPE_VARIANT, tag.getInt("Variant"));
        this.setRessource(tag.getInt("Ressource"));
        this.setSitting(tag.getBoolean("Sitting"));
        this.setMoving(tag.getBoolean("Moving"));
        this.setPraying(tag.getBoolean("Praying"));
    }

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

    public boolean isMoving() {
        return this.entityData.get(MOVING);
    }
    public void setMoving(boolean moving) {
        this.entityData.set(MOVING, moving);
    }

    public boolean isPraying(){
        return this.entityData.get(PRAYING);
    }
    public void setPraying(boolean praying) {
        this.entityData.set(PRAYING, praying);
    }
    public boolean isConnect(){
        return this.sanctuary != null;
    }
    public int getRessource(){return this.entityData.get(RESSOURCE_AMOUNT);}
    public ChildrenHarvestVariant getHarvestType(){ return this.harvest_type;}
    public SanctuaryAutelBlock getSanctuary() {
        return sanctuary;
    }

    public void setRessource(int amount){this.entityData.set(RESSOURCE_AMOUNT, amount);}
    public void setHarvestType(int id){ this.harvest_type = ChildrenHarvestVariant.byId(id);}
    public void setSanctuary(SanctuaryAutelBlock sanctuary) {
        this.sanctuary = sanctuary;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

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

    /* OTHER */

    public Optional<BlockPos> findNearestBlock(Predicate<BlockState> pPredicate, double pDistance) {
        BlockPos blockpos = this.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int i = 0; (double)i <= pDistance; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < pDistance; ++j) {
                for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                        if (blockpos.closerThan(blockpos$mutableblockpos, pDistance) && pPredicate.test(this.level.getBlockState(blockpos$mutableblockpos))) {
                            return Optional.of(blockpos$mutableblockpos);
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }
}
