package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.loot.WeatherCondition;
import draylar.gofish.loot.biome.BiomeLootCondition;
import draylar.gofish.loot.moon.FullMoonCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.util.JsonSerializer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class GoFishLoot {

    public static final LootConditionType MATCH_BIOME = register("match_biome", new BiomeLootCondition.Serializer());
    public static final LootConditionType FULL_MOON = register("full_moon", new FullMoonCondition.Serializer());
    public static final LootConditionType WEATHER = register("weather", new WeatherCondition.Serializer());

    private static LootConditionType register(String id, JsonSerializer<? extends LootCondition> serializer) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, GoFish.id(id), new LootConditionType(serializer));
    }

    public static void init() {

    }

    private GoFishLoot() {

    }
}
