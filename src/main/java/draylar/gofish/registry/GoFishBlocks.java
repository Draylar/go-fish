package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.block.AstralCrateBlock;
import draylar.gofish.block.CrateBlock;
import draylar.gofish.item.CrateItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class GoFishBlocks {

    // The Wooden crate is padded with junk and cobwebs, but will often contain minimal resources, and a rare special item drop.
    //   Junk: Cobwebs, String, Kelp, Sticks, Planks, Seaweed
    //   Resources: coal, iron ore, iron nuggets, gold nuggets, flint, gold ingots
    //   Food: carrots, wheat, potatoes, beetroots
    //   Special: enchanting bottle, low-level enchanted book, emerald, bucket / bucket with fish, more materials
    //   Weapons: damaged crossbows, arrows, bows, stone tools
    //   Fish: all types of vanilla fish
    public static Block WOODEN_CRATE = registerCrate("wooden_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)), new Item.Settings().maxCount(8), GoFish.id("gameplay/fishing/wooden_crate"));

    // The Iron Crate provides less junk, a chance for iron tools, and better rare loot.
    //   Junk: Oak Planks, sticks, Oak Logs, String, Seaweed, Kelp, Bones
    //   Resources: coal, iron ore, iron nuggets, gold nuggets, gold ingots, lapis, redstone
    //   Food: carrots, wheat, potatoes, beetroots, cooked potatoes
    //   Special: mid-level enchanted book, emerald, more materials
    //   Weapons: damaged crossbows, arrows, bows, stone tools
    //   Fish: all types of vanilla fish
    public static Block IRON_CRATE = registerCrate("iron_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)), new FabricItemSettings().maxCount(8), GoFish.id("gameplay/fishing/iron_crate"));

    // The Gold Crate is a rare crate that drops gold items and materials.
    public static Block GOLDEN_CRATE = registerCrate("golden_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK)), new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON), GoFish.id("gameplay/fishing/golden_crate"));

    // The Diamond Crate provides good materials
    public static Block DIAMOND_CRATE = registerCrate("diamond_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)), new Item.Settings().maxCount(8).rarity(Rarity.RARE), GoFish.id("gameplay/fishing/diamond_crate"));
    public static Block FROSTED_CRATE = registerCrate("frosted_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)), new Item.Settings().maxCount(8).rarity(Rarity.RARE), GoFish.id("gameplay/fishing/frosted_crate"));
    public static Block SLIMEY_CRATE = registerCrate("slimey_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK)), new Item.Settings().maxCount(8), GoFish.id("gameplay/fishing/slimey_crate"));
    public static Block SUPPLY_CRATE = registerCrate("supply_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)), new Item.Settings().maxCount(8), GoFish.id("gameplay/fishing/supply_crate"));
    public static Block FIERY_CRATE = registerCrate("fiery_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS)), new Item.Settings().fireproof().maxCount(8), GoFish.id("gameplay/fishing/fiery_crate"));
    public static Block SOUL_CRATE = registerCrate("soul_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.STONE)), new Item.Settings().fireproof().maxCount(8).rarity(Rarity.RARE), GoFish.id("gameplay/fishing/soul_crate"));
    public static Block GILDED_BLACKSTONE_CRATE = registerCrate("gilded_blackstone_crate", new CrateBlock(FabricBlockSettings.copyOf(Blocks.GILDED_BLACKSTONE)), new Item.Settings().fireproof().maxCount(8).rarity(Rarity.UNCOMMON), GoFish.id("gameplay/fishing/gilded_blackstone_crate"));
    public static Block ASTRAL_CRATE = registerCrate("astral_crate", new AstralCrateBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).nonOpaque()), new Item.Settings().fireproof().maxCount(8).rarity(Rarity.EPIC), GoFish.id("gameplay/fishing/astral_crate"));
    public static Block END_CRATE = registerCrate("end_crate", new AstralCrateBlock(FabricBlockSettings.copyOf(Blocks.END_STONE)), new Item.Settings().fireproof().maxCount(8).rarity(Rarity.EPIC), GoFish.id("gameplay/fishing/end_crate"));

    public static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, GoFish.id(name), block);
    }

    public static <T extends Block> T registerCrate(String name, T block, Item.Settings settings) {
        T registeredBlock = Registry.register(Registries.BLOCK, GoFish.id(name), block);
        Registry.register(Registries.ITEM, GoFish.id(name), new CrateItem(block, settings));
        ItemGroupEvents.modifyEntriesEvent(GoFish.GROUP).register(entries -> entries.add(registeredBlock));
        return registeredBlock;
    }

    public static <T extends Block> T registerCrate(String name, T block, Item.Settings settings, Identifier id) {
        T registeredBlock = Registry.register(Registries.BLOCK, GoFish.id(name), block);
        Registry.register(Registries.ITEM, GoFish.id(name), new CrateItem(block, settings, id));
        ItemGroupEvents.modifyEntriesEvent(GoFish.GROUP).register(entries -> entries.add(registeredBlock));
        return registeredBlock;
    }

    public static <T extends Block> T register(String name, T block, Item.Settings settings) {
        T registeredBlock = Registry.register(Registries.BLOCK, GoFish.id(name), block);
        Registry.register(Registries.ITEM, GoFish.id(name), new BlockItem(block, settings));
        return registeredBlock;
    }

    public static void init() {
        // NO-OP
    }
}
