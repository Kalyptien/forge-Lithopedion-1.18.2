package com.kalyptien.lithopedion.entity.client;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChildrenModel extends AnimatedGeoModel<ChildrenEntity> {
    @Override
    public ResourceLocation getModelLocation(ChildrenEntity object) {
        return new ResourceLocation(Lithopedion.MOD_ID, "geo/coe.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ChildrenEntity object) {
        return ChildrenRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ChildrenEntity animatable) {
        return new ResourceLocation(Lithopedion.MOD_ID, "animations/coe.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(ChildrenEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
