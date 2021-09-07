package draylar.gofish.registry;

import draylar.gofish.loot.WeatherCondition;
import draylar.gofish.loot.biome.BiomeLootCondition;
import draylar.gofish.loot.moon.FullMoonCondition;
import draylar.gofish.mixin.LocationPredicateBuilderAccessor;
import draylar.gofish.mixin.LootTableBuilderAccessor;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class GoFishLootHandler {

    public static final Identifier FISH_LOOT_TABLE = new Identifier("gameplay/fishing/fish");

    public static void init() {
        registerFishHandler();
    }

    private static void registerFishHandler() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if(identifier.equals(FISH_LOOT_TABLE)) {
                // Get first pool, which SHOULD be the fish pool
                LootPool lootPool = ((LootTableBuilderAccessor) fabricLootSupplierBuilder).getPools().get(0);

                FabricLootPoolBuilder lpb = FabricLootPoolBuilder.of(lootPool);

                // The default fish loot table has a total weight of 100.
                // An entry with a weight of 10 represents a 10% chance to get that fish compared to the standard 4, but the percentage goes down as more custom fish are added.
                // In most situations, only 1-2 fish are added per biome or area, so the chance for that fish is still ~5-10%.

                // Cold Fish in Icy biomes
                lpb.with(ItemEntry.builder(GoFishItems.ICICLE_FISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.ICY)));
                lpb.with(ItemEntry.builder(GoFishItems.SNOWBALL_FISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.ICY)));

                // Swamp
                lpb.with(ItemEntry.builder(GoFishItems.SLIMEFISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.SWAMP)));
                lpb.with(ItemEntry.builder(GoFishItems.LILYFISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.SWAMP)));

                // Ocean
                lpb.with(ItemEntry.builder(GoFishItems.SEAWEED_EEL).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.OCEAN)));

                // Mesa
                lpb.with(ItemEntry.builder(GoFishItems.TERRAFISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.MESA)));

                // General Plains
                lpb.with(ItemEntry.builder(GoFishItems.CARROT_CARP).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.PLAINS)));
                lpb.with(ItemEntry.builder(GoFishItems.OAKFISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.PLAINS)));
                lpb.with(ItemEntry.builder(GoFishItems.CARROT_CARP).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.FOREST)));
                lpb.with(ItemEntry.builder(GoFishItems.OAKFISH).weight(10).conditionally(BiomeLootCondition.builder(Biome.Category.FOREST)));

                // Misc
                lpb.with(ItemEntry.builder(GoFishItems.LUNARFISH).weight(50).conditionally(FullMoonCondition.builder()));
                lpb.with(ItemEntry.builder(GoFishItems.GALAXY_STARFISH).weight(25).conditionally(FullMoonCondition.builder()));
                lpb.with(ItemEntry.builder(GoFishItems.STARRY_SALMON).weight(50).conditionally(FullMoonCondition.builder()));
                lpb.with(ItemEntry.builder(GoFishItems.NEBULA_SWORDFISH).weight(25).conditionally(FullMoonCondition.builder()));

                // weather
                lpb.with(ItemEntry.builder(GoFishItems.RAINY_BASS).weight(100).conditionally(WeatherCondition.builder(true, false, false)));
                lpb.with(ItemEntry.builder(GoFishItems.THUNDERING_BASS).weight(50).conditionally(WeatherCondition.builder(false, true, false)));
                LocationPredicate.Builder biome = new LocationPredicate.Builder();
                ((LocationPredicateBuilderAccessor) biome).setY(NumberRange.FloatRange.atLeast(150));
                lpb.with(ItemEntry.builder(GoFishItems.CLOUDY_CRAB).weight(50).conditionally(LocationCheckLootCondition.builder(biome)));
                lpb.with(ItemEntry.builder(GoFishItems.BLIZZARD_BASS).weight(100).conditionally(WeatherCondition.builder(false, false, true)));

                ((LootTableBuilderAccessor) fabricLootSupplierBuilder).getPools().set(0, lpb.build());
            }
        });
    }
}
