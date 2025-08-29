package net.mattias.capybara.core.worldgen;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
        public static final ResourceKey<BiomeModifier> ADD_CAPYBARA_RIVER = registerKey("add_capybara_river");
        public static final ResourceKey<BiomeModifier> ADD_CAPYBARA_SWAMP = registerKey("add_capybara_swamp");
        public static final ResourceKey<BiomeModifier> ADD_CAPYBARA_MANGROVE = registerKey("add_capybara_mangrove");

        public static void bootstrap(BootstapContext<BiomeModifier> context) {
            var biomes = context.lookup(Registries.BIOME);

//            // River Biome
//            context.register(ADD_CAPYBARA_RIVER, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
//                    HolderSet.direct(biomes.getOrThrow(Biomes.RIVER)),
//                    List.of(new MobSpawnSettings.SpawnerData(
//                            ModEntities.CAPYBARA.get(),
//                            10,
//                            1,
//                            3
//                    ))
//            ));

            // Swamp Biome
            context.register(ADD_CAPYBARA_SWAMP, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                    HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP)),
                    List.of(new MobSpawnSettings.SpawnerData(
                            ModEntities.CAPYBARA.get(),
                            5,
                            1,
                            3
                    ))
            ));

            // Mangrove Biome
            context.register(ADD_CAPYBARA_MANGROVE, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                    HolderSet.direct(biomes.getOrThrow(Biomes.MANGROVE_SWAMP)),
                    List.of(new MobSpawnSettings.SpawnerData(
                            ModEntities.CAPYBARA.get(),
                            5,
                            1,
                            3
                    ))
            ));
        }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Capybara.MOD_ID, name));
    }
}