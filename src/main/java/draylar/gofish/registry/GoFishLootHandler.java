package draylar.gofish.registry;

import draylar.gofish.loot.WeatherCondition;
import draylar.gofish.loot.biome.MatchBiomeLootCondition;
import draylar.gofish.loot.moon.FullMoonCondition;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;

public class GoFishLootHandler {

    private static boolean hasModifiedFishPool = false;

    public static void init() {
        registerFishHandler();
    }

    private static void registerFishHandler() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(LootTables.FISHING_FISH_GAMEPLAY.equals(id) && source.isBuiltin() && !hasModifiedFishPool) {
                // Only modify first pool, which SHOULD be the fish pool
                hasModifiedFishPool = true;

                tableBuilder.modifyPools(lpb -> {
                    // The default fish loot table has a total weight of 100.
                    // An entry with a weight of 10 represents a 10% chance to get that fish compared to the standard 4, but the percentage goes down as more custom fish are added.
                    // In most situations, only 1-2 fish are added per biome or area, so the chance for that fish is still ~5-10%.

                    // Cold Fish in Icy biomes
                    lpb.with(ItemEntry.builder(GoFishItems.ICICLE_FISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.ICY)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.SNOWBALL_FISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.ICY)).build());

                    // Swamp
                    lpb.with(ItemEntry.builder(GoFishItems.SLIMEFISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.SWAMP)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.LILYFISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.SWAMP)).build());

                    // Ocean
                    lpb.with(ItemEntry.builder(GoFishItems.SEAWEED_EEL).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.OCEAN)).build());

                    // Mesa
                    lpb.with(ItemEntry.builder(GoFishItems.TERRAFISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.MESA)).build());

                    // General Plains
                    lpb.with(ItemEntry.builder(GoFishItems.CARROT_CARP).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.PLAINS)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.OAKFISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.PLAINS)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.CARROT_CARP).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.FOREST)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.OAKFISH).weight(10).conditionally(MatchBiomeLootCondition.builder(ConventionalBiomeTags.FOREST)).build());

                    // Misc
                    lpb.with(ItemEntry.builder(GoFishItems.LUNARFISH).weight(50).conditionally(FullMoonCondition.builder()).build());
                    lpb.with(ItemEntry.builder(GoFishItems.GALAXY_STARFISH).weight(25).conditionally(FullMoonCondition.builder()).build());
                    lpb.with(ItemEntry.builder(GoFishItems.STARRY_SALMON).weight(50).conditionally(FullMoonCondition.builder()).build());
                    lpb.with(ItemEntry.builder(GoFishItems.NEBULA_SWORDFISH).weight(25).conditionally(FullMoonCondition.builder()).build());

                    // weather
                    lpb.with(ItemEntry.builder(GoFishItems.RAINY_BASS).weight(100).conditionally(WeatherCondition.builder(true, false, false)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.THUNDERING_BASS).weight(50).conditionally(WeatherCondition.builder(false, true, false)).build());
                    lpb.with(ItemEntry.builder(GoFishItems.CLOUDY_CRAB).weight(50).conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.createY(NumberRange.DoubleRange.atLeast(150)))).build());
                    lpb.with(ItemEntry.builder(GoFishItems.BLIZZARD_BASS).weight(100).conditionally(WeatherCondition.builder(false, false, true)).build());
                });
            }
        });
    }
}
