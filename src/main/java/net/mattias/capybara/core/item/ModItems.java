package net.mattias.capybara.core.item;


import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.ModEntities;
//import net.mattias.capybara.core.item.custom.WitchHatItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Capybara.MOD_ID);

    public static final RegistryObject<Item> CAPYBARA_SPANW_EGG = ITEMS.register("capybara_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CAPYBARA, 0x6e4c27, 0x9e7242, new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_IRON_ARMOR = ITEMS.register("capybara_iron_armor",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CAPYBARA_GOLD_ARMOR = ITEMS.register("capybara_gold_armor",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CAPYBARA_DIAMOND_ARMOR = ITEMS.register("capybara_diamond_armor",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CAPYBARA_TOP_HAT = ITEMS.register("capybara_top_hat",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_CHEF_HAT = ITEMS.register("capybara_chef_hat",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_WITCH_HAT = ITEMS.register("capybara_witch_hat",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_CROWN = ITEMS.register("capybara_crown",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_PIRATE_HAT = ITEMS.register("capybara_pirate_hat",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPYBARA_PLUNGER_HEAD = ITEMS.register("capybara_plunger_head",
            () -> new Item(new Item.Properties()));

//    public static final RegistryObject<Item> CAPYBARA_SUPERMAN_CAPE = ITEMS.register("capybara_superman_cape",
//            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}