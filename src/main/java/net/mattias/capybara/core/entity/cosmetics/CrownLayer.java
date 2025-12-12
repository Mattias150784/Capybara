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

public class CrownLayer extends RenderLayer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {

    private static final ResourceLocation CROWN_TEXTURE =
            new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaracosmetics/crown.png");

    private static final float PX = 1.0F / 16.0F;

    private final CrownModel<CapybaraEntity> crownModel;

    public CrownLayer(RenderLayerParent<CapybaraEntity, CapybaraModel<CapybaraEntity>> parent,
                      CrownModel<CapybaraEntity> crownModel) {
        super(parent);
        this.crownModel = crownModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       CapybaraEntity capy, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!capy.hasCrown() || capy.isInvisible()) return;

        poseStack.pushPose();
        CapybaraModel<CapybaraEntity> model = this.getParentModel();
        model.root().translateAndRotate(poseStack);
        model.getBody().translateAndRotate(poseStack);
        model.getHead().translateAndRotate(poseStack);

        poseStack.translate(0.0F, -2.0F * PX, 0.0F);

        this.crownModel.renderToBuffer(
                poseStack,
                buffer.getBuffer(this.crownModel.renderType(CROWN_TEXTURE)),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F
        );

        poseStack.popPose();
    }
}