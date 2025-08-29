package net.mattias.capybara.core.event;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.client.CapybaraModel;
import net.mattias.capybara.core.entity.client.ModModelLayers;
//import net.mattias.capybara.core.entity.cosmetics.SupermanCapeModel;
import net.mattias.capybara.core.entity.cosmetics.TopHatLayer;
import net.mattias.capybara.core.entity.cosmetics.TopHatModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Capybara.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CAPYBARA_LAYER, CapybaraModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TOP_HAT_LAYER, TopHatModel::createBodyLayer);
//        event.registerLayerDefinition(ModModelLayers.SUPERMAN_CAPE_LAYER, SupermanCapeModel::createBodyLayer);

    }
}