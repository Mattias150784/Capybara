package net.mattias.capybara.core.entity.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ChefHatModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bone;

    public  ChefHatModel(ModelPart root){
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer () {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.8F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim (Entity entity,float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                           float headPitch){

    }

    @Override
    public void renderToBuffer (PoseStack poseStack, VertexConsumer vertexConsumer,int packedLight, int packedOverlay,
                                float red, float green, float blue, float alpha){
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    @Override
    public ModelPart root() {
        return this.bone;
    }
}
