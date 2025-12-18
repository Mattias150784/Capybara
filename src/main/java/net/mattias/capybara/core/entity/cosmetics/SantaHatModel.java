package net.mattias.capybara.core.entity.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SantaHatModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart bb_main;

	public SantaHatModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(12, 25).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(16, 3).addBox(-2.0F, -7.0F, -2.5F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(0.0F, -9.0F, -0.5F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -5.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0F, 0F, 0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bb_main;
	}
}