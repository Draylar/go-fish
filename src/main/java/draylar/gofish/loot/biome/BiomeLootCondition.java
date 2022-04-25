package draylar.gofish.loot.biome;

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
import net.minecraft.tag.TagKey;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class BiomeLootCondition implements LootCondition {

//    protected final BiomeTagPredicate category;
//    protected final BiomePredicate biome;
    protected final TagKey<Biome> biomeTag;

    public BiomeLootCondition(TagKey<Biome> biomeTag) {
        this.biomeTag = biomeTag;
//        this.category = category;
//        this.biome = biome;
    }

    @Override
    public LootConditionType getType() {
        return GoFishLoot.MATCH_BIOME;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Vec3d origin = lootContext.get(LootContextParameters.ORIGIN);
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);

        if(origin != null) {

            // TODO: Figure out why nether biome tags are busted
            return lootContext.getWorld().getDimension().bedWorks() &&
                    lootContext.getWorld().getBiome(new BlockPos(origin)).isIn(biomeTag);

        }

        return false;
    }

    public static LootCondition.Builder builder (TagKey<Biome> biome) {
        return () -> new BiomeLootCondition(biome);
    }

    public static class Serializer implements JsonSerializer<BiomeLootCondition> {

        @Override
        public void toJson(JsonObject json, BiomeLootCondition object, JsonSerializationContext context) {
            json.add("biomeTag", context.serialize(object.biomeTag));
        }

        @Override
        public BiomeLootCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            return new BiomeLootCondition(context.deserialize(json.get("biomeTag"), TagKey.class));
        }
    }
}