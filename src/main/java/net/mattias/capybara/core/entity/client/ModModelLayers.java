package net.mattias.capybara.core.entity.client;


import net.mattias.capybara.Capybara;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {


    public static final ModelLayerLocation CAPYBARA_LAYER = new ModelLayerLocation(
            new ResourceLocation(Capybara.MOD_ID, "capybara_layer"), "main");

    public static final ModelLayerLocation TOP_HAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Capybara.MOD_ID, "top_hat"), "main");

//    public static final ModelLayerLocation SUPERMAN_CAPE_LAYER = new ModelLayerLocation(
//            new ResourceLocation(Capybara.MOD_ID, "superman_cape"), "main");
}