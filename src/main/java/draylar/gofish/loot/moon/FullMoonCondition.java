package draylar.gofish.loot.moon;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;

import java.util.Set;

public record FullMoonCondition() implements LootCondition {

    public static final FullMoonCondition INSTANCE = new FullMoonCondition();
    public static final Codec<FullMoonCondition> CODEC = Codec.unit(INSTANCE);

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
        return () -> INSTANCE;
    }
}