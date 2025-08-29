package net.mattias.capybara.core.event;

import net.mattias.capybara.Capybara;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final TagKey<Item> CAPYBARA_COSMETICS =
            ItemTags.create(new ResourceLocation(Capybara.MOD_ID, "capybara_cosmetics"));


    public static final TagKey<Item> CAPYBARA_ARMOR =
            ItemTags.create(new ResourceLocation(Capybara.MOD_ID, "capybara_armor"));
}