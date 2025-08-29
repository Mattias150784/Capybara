package net.mattias.capybara.core.datagen;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.item.ModItems;
import net.mattias.capybara.core.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Capybara.MOD_ID);
    }

    @Override
    protected void start() {
        
        // Top Hat Armor
        
        add("top_hat_from_jungle_temple", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_TOP_HAT.get()));

        add("top_hat_from_desert_pyramid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_TOP_HAT.get()));

        add("top_hat_from_woodland_mansion", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_TOP_HAT.get()));

        add("top_hat_from_simple_dungeon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_TOP_HAT.get()));

        add("top_hat_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build() // 20% chance
        }, ModItems.CAPYBARA_TOP_HAT.get()));

        // Iron Capybara Armor
        
        add("capybara_iron_armor_from_jungle_temple", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_IRON_ARMOR.get()));

        add("capybara_iron_armor_from_desert_pyramid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_IRON_ARMOR.get()));

        add("capybara_iron_armor_from_woodland_mansion", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_IRON_ARMOR.get()));

        add("capybara_iron_armor_from_simple_dungeon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_IRON_ARMOR.get()));

        add("capybara_iron_armor_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_IRON_ARMOR.get()));

        // Gold Capybara Armor
        
        add("capybara_gold_armor_from_jungle_temple", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_GOLD_ARMOR.get()));

        add("capybara_gold_armor_from_desert_pyramid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_GOLD_ARMOR.get()));

        add("capybara_gold_armor_from_woodland_mansion", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_GOLD_ARMOR.get()));

        add("capybara_gold_armor_from_simple_dungeon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_GOLD_ARMOR.get()));

        add("capybara_gold_armor_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_GOLD_ARMOR.get()));
        
        // Diamond Capybara Armor

        add("capybara_diamond_armor_from_jungle_temple", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_DIAMOND_ARMOR.get()));

        add("capybara_diamond_armor_from_desert_pyramid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_DIAMOND_ARMOR.get()));

        add("capybara_diamond_armor_from_woodland_mansion", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_DIAMOND_ARMOR.get()));

        add("capybara_diamond_armor_from_simple_dungeon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build() // 40% chance
        }, ModItems.CAPYBARA_DIAMOND_ARMOR.get()));

        add("capybara_diamond_armor_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build() // 30% chance
        }, ModItems.CAPYBARA_DIAMOND_ARMOR.get()));
        
    }
}