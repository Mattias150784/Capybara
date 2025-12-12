package net.mattias.capybara.core.entity.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.client.CapybaraModel;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class WitchHatLayer extends RenderLayer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {

    private static final ResourceLocation WITCH_HAT_TEXTURE =
            new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaracosmetics/witch_hat.png");

    private static final float PX = 1.0F / 16.0F;

    private final WitchHatModel<CapybaraEntity> hatModel;

    public WitchHatLayer(RenderLayerParent<CapybaraEntity, CapybaraModel<CapybaraEntity>> parent,
                         WitchHatModel<CapybaraEntity> hatModel) {
        super(parent);
        this.hatModel = hatModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       CapybaraEntity capy, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!capy.hasWitchHat() || capy.isInvisible()) return;

        poseStack.pushPose();
        CapybaraModel<CapybaraEntity> model = this.getParentModel();
        model.root().translateAndRotate(poseStack);
        model.getBody().translateAndRotate(poseStack);
        model.getHead().translateAndRotate(poseStack);

        poseStack.translate(0.0F, -2.0F * PX, 0.0F);

        this.hatModel.renderToBuffer(
                poseStack,
                buffer.getBuffer(this.hatModel.renderType(WITCH_HAT_TEXTURE)),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F
        );

        poseStack.popPose();
    }
}