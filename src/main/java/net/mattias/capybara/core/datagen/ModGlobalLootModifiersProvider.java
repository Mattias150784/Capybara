package net.mattias.capybara.core.datagen;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.item.ModItems;
import net.mattias.capybara.core.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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

        CosmeticLoot("capybara_top_hat", ModItems.CAPYBARA_TOP_HAT.get());
        CosmeticLoot("capybara_chef_hat", ModItems.CAPYBARA_CHEF_HAT.get());
        CosmeticLoot("capybara_witch_hat", ModItems.CAPYBARA_WITCH_HAT.get());
        CosmeticLoot("capybara_crown", ModItems.CAPYBARA_CROWN.get());
        CosmeticLoot("capybara_pirate_hat", ModItems.CAPYBARA_PIRATE_HAT.get());
        CosmeticLoot("capybara_plunger_head", ModItems.CAPYBARA_PLUNGER_HEAD.get());
        CosmeticLoot("capybara_santa_hat", ModItems.CAPYBARA_SANTA_HAT.get());
        CosmeticLoot("capybara_elf_hat", ModItems.CAPYBARA_ELF_HAT.get());


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
    private void CosmeticLoot(String nameBase, Item item) {

        add(nameBase + "_from_jungle_temple", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build()
        }, item));

        add(nameBase + "_from_desert_pyramid", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build()
        }, item));

        add(nameBase + "_from_woodland_mansion", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.30f).build()
        }, item));

        add(nameBase + "_from_simple_dungeon", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.40f).build()
        }, item));
    }
}