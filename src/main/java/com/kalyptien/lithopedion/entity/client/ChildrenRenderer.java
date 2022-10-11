package com.kalyptien.lithopedion.entity.client;

import com.google.common.collect.Maps;
import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.variant.ChildrenVariant;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ChildrenRenderer extends GeoEntityRenderer<ChildrenEntity> {

    public static final Map<ChildrenVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ChildrenVariant.class), (p_114874_) -> {
                p_114874_.put(ChildrenVariant.DEFAULT,
                        new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/coe/stone.png"));
                p_114874_.put(ChildrenVariant.DIORITE,
                        new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/coe/diorite.png"));
                p_114874_.put(ChildrenVariant.ANDESITE,
                        new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/coe/andesite.png"));
                p_114874_.put(ChildrenVariant.GRANITE,
                        new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/coe/granite.png"));
            });

    public ChildrenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChildrenModel());
        this.shadowRadius = 0.1f;
    }

    @Override
    public ResourceLocation getTextureLocation(ChildrenEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderType getRenderType(ChildrenEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
