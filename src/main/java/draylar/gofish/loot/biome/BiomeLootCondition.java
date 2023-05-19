package draylar.gofish.loot.biome;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class BiomeLootCondition implements LootCondition {

    protected final BiomeTagPredicate category;
    protected final BiomePredicate biome;

    public BiomeLootCondition(BiomeTagPredicate category, BiomePredicate biome) {
        this.category = category;
        this.biome = biome;
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

        if(origin != null) {
            RegistryEntry<Biome> fisherBiome = lootContext.getWorld().getBiome(new BlockPos((int) Math.floor(origin.x), (int) Math.floor(origin.y), (int) Math.floor(origin.z)));

            // Category predicate is null, check exact biome
            if (category == null || category.getValid().isEmpty()) {
                if (biome != null && !biome.getValid().isEmpty()) {
                    return biome.test(lootContext.getWorld(), fisherBiome);
                }
            }

            // Category predicate is not null, check it
            else if (!category.getValid().isEmpty()) {
                return category.test(fisherBiome);
            }
        }

        return false;
    }

    public static LootCondition.Builder builder(RegistryKey<Biome>... biomes) {
        return builder(Collections.emptyList(), List.of(biomes));
    }

    public static LootCondition.Builder builder(TagKey<Biome>... categories) {
        return builder(Arrays.asList(categories), Collections.emptyList());
    }

    public static LootCondition.Builder builder(List<TagKey<Biome>> categories, List<RegistryKey<Biome>> biomes) {
        List<String> stringCats = new ArrayList<>();
        List<String> stringBiomes = new ArrayList<>();

        categories.forEach(category -> stringCats.add(category.id().toString()));
        biomes.forEach(biome -> stringBiomes.add(biome.getValue().toString()));

        return builder(BiomeTagPredicate.builder().setValidByString(stringCats), BiomePredicate.builder().setValidFromString(stringBiomes));
    }

    public static LootCondition.Builder builder(String category, String biome) {
        return builder(BiomeTagPredicate.builder().add(category), BiomePredicate.builder().add(biome));
    }

    public static LootCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder) {
        return builder(categoryBuilder, BiomePredicate.builder());
    }

    public static LootCondition.Builder builder(BiomePredicate.Builder biomeBuilder) {
        return builder(BiomeTagPredicate.builder(), biomeBuilder);
    }

    public static LootCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder, BiomePredicate.Builder biomeBuilder) {
        return () -> new BiomeLootCondition(categoryBuilder.build(), biomeBuilder.build());
    }

    public static class Serializer implements JsonSerializer<BiomeLootCondition> {

        @Override
        public void toJson(JsonObject jsonObject, BiomeLootCondition condition, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("category", condition.category.toJson());
            jsonObject.add("biome", condition.biome.toJson());
        }

        @Override
        public BiomeLootCondition fromJson(JsonObject obj, JsonDeserializationContext context) {
            BiomeTagPredicate categoryPredicate;
            BiomePredicate biomePredicate;

            if (obj.has("category")) {
                categoryPredicate = BiomeTagPredicate.fromJson(obj.get("category"));
            } else {
                categoryPredicate = BiomeTagPredicate.EMPTY;
            }

            if (obj.has("biome")) {
                biomePredicate = BiomePredicate.fromJson(obj.get("biome"));
            } else {
                biomePredicate = BiomePredicate.EMPTY;
            }

            return new BiomeLootCondition(categoryPredicate, biomePredicate);
        }
    }
}
