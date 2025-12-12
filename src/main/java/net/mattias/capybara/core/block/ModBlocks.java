package net.mattias.capybara.core.block;

import net.mattias.capybara.Capybara;
import net.mattias.capybara.core.block.custom.FurBlock;
import net.mattias.capybara.core.block.custom.FurCarpetBlock;
import net.mattias.capybara.core.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Capybara.MOD_ID);

    public static final RegistryObject<Block> CAPYBARA_FUR_BLOCK = registerBlock("capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> BLACK_CAPYBARA_FUR_BLOCK = registerBlock("black_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> CREAM_CAPYBARA_FUR_BLOCK = registerBlock("cream_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> DARK_BROWN_CAPYBARA_FUR_BLOCK = registerBlock("dark_brown_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> GOLD_CAPYBARA_FUR_BLOCK = registerBlock("gold_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> LIGHT_BROWN_CAPYBARA_FUR_BLOCK = registerBlock("light_brown_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> GRAY_CAPYBARA_FUR_BLOCK = registerBlock("gray_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> LIGHT_GRAY_CAPYBARA_FUR_BLOCK = registerBlock("light_gray_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> RED_CAPYBARA_FUR_BLOCK = registerBlock("red_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> WHITE_CAPYBARA_FUR_BLOCK = registerBlock("white_capybara_fur_block",
            () -> new FurBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

    public static final RegistryObject<Block> CAPYBARA_FUR_CARPET = registerBlock("capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> BLACK_CAPYBARA_FUR_CARPET = registerBlock("black_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> CREAM_CAPYBARA_FUR_CARPET = registerBlock("cream_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> DARK_BROWN_CAPYBARA_FUR_CARPET = registerBlock("dark_brown_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> GOLD_CAPYBARA_FUR_CARPET = registerBlock("gold_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> LIGHT_BROWN_CAPYBARA_FUR_CARPET = registerBlock("light_brown_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> GRAY_CAPYBARA_FUR_CARPET = registerBlock("gray_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> LIGHT_GRAY_CAPYBARA_FUR_CARPET = registerBlock("light_gray_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> RED_CAPYBARA_FUR_CARPET = registerBlock("red_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    public static final RegistryObject<Block> WHITE_CAPYBARA_FUR_CARPET = registerBlock("white_capybara_fur_carpet",
            () -> new FurCarpetBlock(Block.Properties.copy(Blocks.WHITE_CARPET)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}