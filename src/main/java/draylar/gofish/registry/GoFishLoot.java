package draylar.gofish.registry;

import com.mojang.serialization.Codec;
import draylar.gofish.GoFish;
import draylar.gofish.loot.WeatherCondition;
import draylar.gofish.loot.biome.MatchBiomeLootCondition;
import draylar.gofish.loot.moon.FullMoonCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class GoFishLoot {

    public static final LootConditionType MATCH_BIOME = register("match_biome", MatchBiomeLootCondition.CODEC);
    public static final LootConditionType FULL_MOON = register("full_moon", FullMoonCondition.CODEC);
    public static final LootConditionType WEATHER = register("weather", WeatherCondition.CODEC);

    private static LootConditionType register(String id, Codec<? extends LootCondition> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, GoFish.id(id), new LootConditionType(codec));
    }

    public static void init() {

    }

    private GoFishLoot() {

    }
}
