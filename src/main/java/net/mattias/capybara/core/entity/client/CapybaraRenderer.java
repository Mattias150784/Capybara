package net.mattias.capybara.core.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mattias.capybara.Capybara;
//import net.mattias.capybara.core.entity.cosmetics.SupermanCapeLayer;
//import net.mattias.capybara.core.entity.cosmetics.SupermanCapeModel;
import net.mattias.capybara.core.entity.cosmetics.TopHatLayer;
import net.mattias.capybara.core.entity.cosmetics.TopHatModel;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CapybaraRenderer
        extends MobRenderer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {

    public CapybaraRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new CapybaraModel<>(ctx.bakeLayer(ModModelLayers.CAPYBARA_LAYER)),
                1.0F);

        this.addLayer(new SnoutLayer(this));
        this.addLayer(new EarsLayer(this));
        this.addLayer(new LegsLayer(this));
        // this.addLayer(new PatternLayer(this)); // Commented out to disable Pattern Layer
        this.addLayer(new ArmorLayer(this));

        TopHatModel<CapybaraEntity> hatModel =
                new TopHatModel<>(ctx.bakeLayer(ModModelLayers.TOP_HAT_LAYER));

        this.addLayer(new TopHatLayer(this, hatModel));

//        SupermanCapeModel<CapybaraEntity> supermanCapeModel =
//                new SupermanCapeModel<>(ctx.bakeLayer(ModModelLayers.SUPERMAN_CAPE_LAYER));
//
//        this.addLayer(new SupermanCapeLayer(this, supermanCapeModel));
    }

    @Override
    public ResourceLocation getTextureLocation(CapybaraEntity capy) {
        int furColor = Math.max(1, capy.getFurColor());

        return new ResourceLocation(
                Capybara.MOD_ID,
                "textures/entity/capybarafurcolor/capybara_color_" + furColor + ".png");
    }

    @Override
    public void render(CapybaraEntity entity, float entityYaw, float partialTicks,
                       PoseStack pose, MultiBufferSource buf, int light) {
        if (entity.isBaby()) pose.scale(0.5F, 0.5F, 0.5F);
        super.render(entity, entityYaw, partialTicks, pose, buf, light);
    }
}