package net.mattias.capybara.core.gui;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.mattias.capybara.core.gui.inventory.CapybaraInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Capybara.MOD_ID);

    public static final RegistryObject<MenuType<CapybaraInventoryMenu>> CAPYBARA_INVENTORY =
            MENUS.register("capybara_inventory", () ->
                    IForgeMenuType.create((windowId, inv, data) -> {
                        int entityId = data.readVarInt();
                        CapybaraEntity capybara = (CapybaraEntity) inv.player.level().getEntity(entityId);
                        return new CapybaraInventoryMenu(windowId, inv, capybara);
                    }));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}