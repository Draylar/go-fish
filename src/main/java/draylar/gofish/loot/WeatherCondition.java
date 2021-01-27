package draylar.gofish.loot;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import draylar.gofish.loot.biome.BiomeCategoryPredicate;
import draylar.gofish.loot.biome.BiomeLootCondition;
import draylar.gofish.loot.biome.BiomePredicate;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;
import net.minecraft.world.World;

import java.util.Set;

public class WeatherCondition implements LootCondition {

    protected boolean raining = false;
    protected boolean thundering = false;
    protected boolean snowing = false;

    public WeatherCondition() {

    }

    public WeatherCondition(boolean raining, boolean thundering, boolean snowing) {
        this.raining = raining;
        this.thundering = thundering;
        this.snowing = snowing;
    }

    @Override
    public LootConditionType getType() {
        return GoFishLoot.WEATHER;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);

        if(entity != null) {
            World world = entity.world;

            // If raining is required and the world is not raining, return false.
            if (raining && !world.isRaining()) {
                return false;
            }

            // If thundering is required and the world is not raining, return false.
            if (thundering && !world.isThundering()) {
                return false;
            }

            // same check for snowing
            if (snowing) {
                // >= .15 = no snow
                if(world.getBiome(entity.getBlockPos()).getTemperature() >= .15) {
                    return false;
                }

                return world.isRaining();
            }

            // Both conditions match, return true.
            return true;
        }

        return false;
    }

    public static LootCondition.Builder builder(boolean raining, boolean thundering, boolean snowing) {
        return () -> new WeatherCondition(raining, thundering, snowing);
    }

    public static class Serializer implements JsonSerializer<WeatherCondition> {

        @Override
        public void toJson(JsonObject jsonObject, WeatherCondition condition, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("raining", condition.raining);
            jsonObject.addProperty("thundering", condition.thundering);
            jsonObject.addProperty("snowing", condition.snowing);
        }

        @Override
        public WeatherCondition fromJson(JsonObject obj, JsonDeserializationContext context) {
            return new WeatherCondition(
                    obj.has("raining") && obj.get("raining").getAsBoolean(),
                    obj.has("thundering") && obj.get("thundering").getAsBoolean(),
                    obj.has("snowing") && obj.get("snowing").getAsBoolean());
        }
    }
}