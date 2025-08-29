package net.mattias.capybara.core.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LegsLayer extends RenderLayer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    private final CapybaraModel<CapybaraEntity> model;

    public LegsLayer(RenderLayerParent<CapybaraEntity, CapybaraModel<CapybaraEntity>> parent) {
        super(parent);
        this.model = parent.getModel();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       CapybaraEntity capybara, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (capybara.isInvisible()) return;

        int variant = Math.max(1, capybara.getLegsVariant());
        ResourceLocation texture = new ResourceLocation(
                Capybara.MOD_ID,
                "textures/entity/capybaralegcolor/capybara_legs_" + variant + ".png"
        );

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.model.renderToBuffer(poseStack, buffer, packedLight,
                LivingEntityRenderer.getOverlayCoords(capybara, 0.0F),
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}