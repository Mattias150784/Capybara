package net.mattias.capybara.core.worldgen;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntitySpawnPlacements {
    public static void register() {
        SpawnPlacements.register(
                ModEntities.CAPYBARA.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        Capybara.canSpawnHere(world, reason, pos, random)
        );
    }
}