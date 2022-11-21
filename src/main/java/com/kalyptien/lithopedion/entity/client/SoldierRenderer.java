package com.kalyptien.lithopedion.entity.client;

import com.google.common.collect.Maps;
import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.entity.client.layer.SoldierHeadLayer;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.kalyptien.lithopedion.variant.SoldierVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class SoldierRenderer extends GeoEntityRenderer<SoldierEntity>{

    public static final Map<SoldierVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SoldierVariant.class), (p_114874_) -> {
                for (int i = 0; i < SoldierVariant.values().length; i++){
                    p_114874_.put(SoldierVariant.byId(i),
                            new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/soe/" + SoldierVariant.byId(i).toString().toLowerCase() +".png"));
                }
            });

    public SoldierRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SoldierModel<SoldierEntity>());
        this.addLayer(new SoldierHeadLayer(this));
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(SoldierEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderType getRenderType(SoldierEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
