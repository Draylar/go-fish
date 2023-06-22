package draylar.gofish.loot.moon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;

import java.util.*;

public class FullMoonCondition implements LootCondition {

    public FullMoonCondition() {

    }

    @Override
    public LootConditionType getType() {
        return GoFishLoot.FULL_MOON;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);

        if(entity != null) {
            return entity.getWorld().isNight() &&
                    entity.getWorld().getDimension().getMoonPhase(entity.getWorld().getLunarTime()) == 0;
        }

        return false;
    }

    public static LootCondition.Builder builder() {
        return FullMoonCondition::new;
    }

    public static class Serializer implements JsonSerializer<FullMoonCondition> {

        @Override
        public void toJson(JsonObject jsonObject, FullMoonCondition condition, JsonSerializationContext jsonSerializationContext) {

        }

        @Override
        public FullMoonCondition fromJson(JsonObject obj, JsonDeserializationContext context) {
            return new FullMoonCondition();
        }
    }
}