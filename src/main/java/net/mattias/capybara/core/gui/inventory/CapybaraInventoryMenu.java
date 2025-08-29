package net.mattias.capybara.core.gui.inventory;

import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.mattias.capybara.core.event.ModTags;
import net.mattias.capybara.core.gui.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CapybaraInventoryMenu extends AbstractContainerMenu {
    private final SimpleContainer capybaraContainer;
    public final CapybaraEntity capybara;

    public CapybaraInventoryMenu(int containerId, Inventory playerInventory, CapybaraEntity capybara) {
        super(ModMenuTypes.CAPYBARA_INVENTORY.get(), containerId);
        this.capybara = capybara;
        this.capybaraContainer = capybara.getInventory();
        this.capybaraContainer.startOpen(playerInventory.player);

        // Cosmetic Slot
        this.addSlot(new Slot(capybaraContainer, 0, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.CAPYBARA_COSMETICS);
            }
            @Override
            public int getMaxStackSize() { return 1; }
        });

        // Armor Slot
        this.addSlot(new Slot(capybaraContainer, 1, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.CAPYBARA_ARMOR);
            }
            @Override
            public int getMaxStackSize() {
                return 1; }
        });


        this.addSlot(new Slot(capybaraContainer, 2, 8, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9,
                        8 + j * 18, 102 + i * 18 - 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            int containerSize = this.capybaraContainer.getContainerSize();

            if (index < containerSize) {
                if (!this.moveItemStackTo(stack, containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.capybaraContainer.stillValid(player)
                && this.capybara.isAlive()
                && this.capybara.distanceTo(player) < 8.0F;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.capybaraContainer.stopOpen(player);
    }
}