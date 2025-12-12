package net.mattias.capybara.core.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mattias.capybara.core.entity.animations.CapybaraAnimations;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CapybaraModel<T extends CapybaraEntity> extends HierarchicalModel<T> {
	private final ModelPart Capybara;
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart Legs;
	private final ModelPart backLegs;
	private final ModelPart backlegLeft;
	private final ModelPart backlegRight;
	private final ModelPart frontLegs;
	private final ModelPart frontlegRight;
	private final ModelPart frontlegLeft;
	private final ModelPart bone;
	private final ModelPart bone2;

	public CapybaraModel(ModelPart root) {
		this.Capybara = root.getChild("Capybara");
		this.Body = this.Capybara.getChild("Body");
		this.Head = this.Body.getChild("Head");
		this.Legs = this.Body.getChild("Legs");
		this.backLegs = this.Legs.getChild("backLegs");
		this.backlegLeft = this.backLegs.getChild("backlegLeft");
		this.backlegRight = this.backLegs.getChild("backlegRight");
		this.frontLegs = this.Legs.getChild("frontLegs");
		this.frontlegRight = this.frontLegs.getChild("frontlegRight");
		this.frontlegLeft = this.frontLegs.getChild("frontlegLeft");
		this.bone = this.Body.getChild("bone");
		this.bone2 = this.Body.getChild("bone2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Capybara = partdefinition.addOrReplaceChild("Capybara", CubeListBuilder.create(), PartPose.offset(5.0F, 20.0F, -3.0F));

		PartDefinition Body = Capybara.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(-5.0F, -8.5F, 2.5F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(1, 30).addBox(-2.6631F, -3.5F, -8.8333F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(17, 48).addBox(-2.6631F, -5.5F, 1.1667F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(23, 48).addBox(1.3369F, -5.5F, 1.1667F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3369F, -6.0F, -1.8922F));

		PartDefinition Legs = Body.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(3.0F, 10.5F, 0.5F));

		PartDefinition backLegs = Legs.addOrReplaceChild("backLegs", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 10.0F));

		PartDefinition backlegLeft = backLegs.addOrReplaceChild("backlegLeft", CubeListBuilder.create().texOffs(35, 46).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 1.0F));

		PartDefinition backlegRight = backLegs.addOrReplaceChild("backlegRight", CubeListBuilder.create().texOffs(1, 48).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -2.0F, 1.0F));

		PartDefinition frontLegs = Legs.addOrReplaceChild("frontLegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition frontlegRight = frontLegs.addOrReplaceChild("frontlegRight", CubeListBuilder.create().texOffs(35, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition frontlegLeft = frontLegs.addOrReplaceChild("frontlegLeft", CubeListBuilder.create().texOffs(35, 30).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -2.0F, 0.0F));

		PartDefinition bone = Body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(1, 0).addBox(-6.0F, -4.5F, -3.5F, 12.0F, 13.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(0, 87).addBox(-5.0F, -3.5F, -2.5F, 10.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(30, 38).addBox(6.0F, 8.5F, -3.5F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(36, 62).addBox(-6.0F, 8.49F, -3.49F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(35, 60).addBox(-6.0F, 8.5F, 13.51F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(30, 40).addBox(-6.0F, 8.5F, -3.5F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = Body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(71, 118).addBox(-5.0F, -1.5F, -2.5F, 10.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(112, 117).addBox(-8.0F, -1.0F, -3.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(37, 117).addBox(5.0F, -1.0F, -3.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 9.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(CapybaraEntity entity, float limbSwing, float limbSwingAmount,
						  float ageInTicks, float netHeadYaw, float headPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isBaby()) {
			float headScale = 1.45F;

			this.Head.xScale = headScale;
			this.Head.yScale = headScale;
			this.Head.zScale = headScale;
		} else {
			this.Head.xScale = 1.0F;
			this.Head.yScale = 1.0F;
			this.Head.zScale = 1.0F;
		}

		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		if (entity.isSitting()) {
			this.animate(entity.idleAnimationState, CapybaraAnimations.Sit, ageInTicks, 1f);
		} else {
			this.animateWalk(CapybaraAnimations.Walk, limbSwing, limbSwingAmount, 2f, 2.5f);
			this.animate(entity.idleAnimationState, CapybaraAnimations.Idle, ageInTicks, 1f);
		}
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Capybara.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Capybara;
	}

	public ModelPart getHead() {
		return this.Head;
	}
	public ModelPart getBody() {
		return this.Body;
	}
}
