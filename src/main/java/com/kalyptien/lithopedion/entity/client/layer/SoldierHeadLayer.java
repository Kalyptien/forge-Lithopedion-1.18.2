package com.kalyptien.lithopedion.entity.client.layer;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class SoldierHeadLayer extends GeoLayerRenderer<SoldierEntity> {


    private static final ResourceLocation MODEL = new ResourceLocation(Lithopedion.MOD_ID, "geo/soe.geo.json");
    public SoldierHeadLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, SoldierEntity soldier, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

            RenderType eyesTexture = RenderType.entityTranslucent(new ResourceLocation(Lithopedion.MOD_ID, "textures/entity/soe/layer/" + soldier.getVariant().toString().toLowerCase() + ".png"));

            matrixStackIn.pushPose();

            this.getRenderer().render(this.getEntityModel().getModel(MODEL), soldier, partialTicks, eyesTexture, matrixStackIn, bufferIn, bufferIn.getBuffer(eyesTexture), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

            matrixStackIn.popPose();
    }
}