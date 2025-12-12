package net.mattias.capybara;

import com.mojang.logging.LogUtils;
import net.mattias.capybara.core.block.ModBlocks;
import net.mattias.capybara.core.entity.ModEntities;
import net.mattias.capybara.core.entity.client.CapybaraRenderer;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.mattias.capybara.core.gui.ModMenuTypes;
import net.mattias.capybara.core.gui.inventory.CapybaraInventoryScreen;
import net.mattias.capybara.core.item.ModCreativeModTabs;
import net.mattias.capybara.core.item.ModItems;
import net.mattias.capybara.core.loot.ModLootModifiers;
import net.mattias.capybara.core.sound.ModSounds;
import net.mattias.capybara.core.worldgen.ModEntitySpawnPlacements;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Random;

@Mod(Capybara.MOD_ID)
public class Capybara {
    public static final String MOD_ID = "capybara";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Capybara() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModSounds.register(modEventBus);


        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }


    public static boolean canSpawnHere(LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (!(world instanceof Level level)) {
            return false;
        }

        if (level.getDayTime() >= 12000) {
            return false;
        }

        BlockState blockState = world.getBlockState(pos.below());
        if (blockState.is(Blocks.WATER)) {
            return false;
        }

        return true;
    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CAPYBARA.get(), CapybaraRenderer::new);

            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.CAPYBARA_INVENTORY.get(), CapybaraInventoryScreen::new);
            });
        }
    }
}