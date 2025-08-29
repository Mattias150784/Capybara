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

public class ArmorLayer extends RenderLayer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    private final CapybaraModel<CapybaraEntity> model;

    public ArmorLayer(RenderLayerParent<CapybaraEntity, CapybaraModel<CapybaraEntity>> parent) {
        super(parent);
        this.model = parent.getModel();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       CapybaraEntity capybara, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (capybara.isInvisible()) return;
        int variant = capybara.getArmorVariant();
        if (variant == 0) return;

        ResourceLocation texture;
        if (variant == 1) {
            texture = new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaraarmor/capybara_iron_armor.png");
        } else if (variant == 2) {
            texture = new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaraarmor/capybara_gold_armor.png");
        } else if (variant == 3) {
            texture = new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaraarmor/capybara_diamond_armor.png");
        } else {
            texture = new ResourceLocation(Capybara.MOD_ID, "textures/entity/capybaraarmor/capybara_iron_armor.png");
        }

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.model.renderToBuffer(poseStack, buffer, packedLight,
                LivingEntityRenderer.getOverlayCoords(capybara, 0.0F),
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}