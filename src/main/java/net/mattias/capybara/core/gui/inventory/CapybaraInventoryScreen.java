package net.mattias.capybara.core.gui.inventory;

import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapybaraInventoryScreen extends AbstractContainerScreen<CapybaraInventoryMenu> {
    private static final ResourceLocation CAPYBARA_INVENTORY_LOCATION =
            new ResourceLocation("capybara:textures/gui/capybara_inventory.png");

    private final CapybaraEntity capybara;
    private float xMouse;
    private float yMouse;

    public CapybaraInventoryScreen(CapybaraInventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.capybara = menu.capybara;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        graphics.blit(CAPYBARA_INVENTORY_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.getSlot(0).getItem().isEmpty()) {
            graphics.blit(CAPYBARA_INVENTORY_LOCATION, x + 7, y + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        if (this.menu.getSlot(1).getItem().isEmpty()) {
            graphics.blit(CAPYBARA_INVENTORY_LOCATION, x + 7, y + 35, 0, this.imageHeight + 54, 18, 18);
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(
                graphics,
                x + 51,
                y + 60,
                17,
                (float)(x + 51) - this.xMouse,
                (float)(y + 75 - 50) - this.yMouse,
                this.capybara
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}