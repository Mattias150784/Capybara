package net.mattias.capybara.core.item;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Capybara.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CAPYBARA_TAB = CREATIVE_MODE_TABS.register("capybara_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CAPYBARA_SPANW_EGG.get()))
                    .title(Component.translatable("creativetab.capybara_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CAPYBARA_SPANW_EGG.get());
                        pOutput.accept(ModItems.CAPYBARA_IRON_ARMOR.get());
                        pOutput.accept(ModItems.CAPYBARA_GOLD_ARMOR.get());
                        pOutput.accept(ModItems.CAPYBARA_DIAMOND_ARMOR.get());
                        pOutput.accept(ModItems.CAPYBARA_TOP_HAT.get());
                        pOutput.accept(ModItems.CAPYBARA_CHEF_HAT.get());
                        pOutput.accept(ModItems.CAPYBARA_WITCH_HAT.get());
                        pOutput.accept(ModItems.CAPYBARA_CROWN.get());
                        pOutput.accept(ModItems.CAPYBARA_PIRATE_HAT.get());
                        pOutput.accept(ModItems.CAPYBARA_PLUNGER_HEAD.get());

//                        pOutput.accept(ModItems.CAPYBARA_SUPERMAN_CAPE.get());

                        pOutput.accept(ModBlocks.CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.BLACK_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.CREAM_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.DARK_BROWN_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.GOLD_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.GRAY_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.LIGHT_BROWN_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.LIGHT_GRAY_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.RED_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.WHITE_CAPYBARA_FUR_BLOCK.get());
                        pOutput.accept(ModBlocks.CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.BLACK_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.CREAM_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.DARK_BROWN_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.GOLD_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.GRAY_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.LIGHT_BROWN_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.LIGHT_GRAY_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.RED_CAPYBARA_FUR_CARPET.get());
                        pOutput.accept(ModBlocks.WHITE_CAPYBARA_FUR_CARPET.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}