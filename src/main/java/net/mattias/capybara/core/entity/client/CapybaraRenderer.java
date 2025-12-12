package net.mattias.capybara.core.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.cosmetics.*;
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
                0.8F);

        this.addLayer(new ArmorLayer(this));

        TopHatModel<CapybaraEntity> hatModel =
                new TopHatModel<>(ctx.bakeLayer(ModModelLayers.TOP_HAT_LAYER));

        ChefHatModel<CapybaraEntity> chefHatModel =
                new ChefHatModel<>(ctx.bakeLayer(ModModelLayers.CHEF_HAT_LAYER));

        WitchHatModel<CapybaraEntity> witchHatModel =
                new WitchHatModel<>(ctx.bakeLayer(ModModelLayers.WITCH_HAT_LAYER));

        CrownModel<CapybaraEntity> crownModel =
                new CrownModel<>(ctx.bakeLayer(ModModelLayers.CROWN_LAYER));

        PirateHatModel<CapybaraEntity> pirateHatModel =
                new PirateHatModel<>(ctx.bakeLayer(ModModelLayers.PIRATE_HAT_LAYER));

        PlungerHeadModel<CapybaraEntity> plungerHeadModel =
                new PlungerHeadModel<>(ctx.bakeLayer(ModModelLayers.PLUNGER_HEAD_LAYER));

        this.addLayer(new TopHatLayer(this, hatModel));
        this.addLayer(new ChefHatLayer(this, chefHatModel));
        this.addLayer(new WitchHatLayer(this, witchHatModel));
        this.addLayer(new CrownLayer(this, crownModel));
        this.addLayer(new PirateHatLayer(this, pirateHatModel));
        this.addLayer(new PlungerHeadLayer(this, plungerHeadModel));
    }

    @Override
    public ResourceLocation getTextureLocation(CapybaraEntity capy) {
        int variant = Math.max(1, capy.getFurColor());

        if (capy.isSheared()) {
            return new ResourceLocation(
                    Capybara.MOD_ID,
                    "textures/entity/capybarasheared/sheared_capybara_" + variant + ".png");
        }
        return new ResourceLocation(
                Capybara.MOD_ID,
                "textures/entity/capybarafurcolor/capybara_color_" + variant + ".png");
    }

    @Override
    public void render(CapybaraEntity entity, float entityYaw, float partialTicks,
                       PoseStack pose, MultiBufferSource buf, int light) {
        if (entity.isBaby()) pose.scale(0.5F, 0.5F, 0.5F);
        super.render(entity, entityYaw, partialTicks, pose, buf, light);
    }
}