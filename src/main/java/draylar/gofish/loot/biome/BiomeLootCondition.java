package draylar.gofish.loot.biome;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class BiomeLootCondition implements LootCondition {

    protected final BiomeCategoryPredicate category;
    protected final BiomePredicate biome;

    public BiomeLootCondition(BiomeCategoryPredicate category, BiomePredicate biome) {
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
            RegistryEntry<Biome> fisherBiome = lootContext.getWorld().getBiome(new BlockPos(origin));

            // Category predicate is null, check exact biome
            if (category == null || category.getValid().isEmpty()) {
                if (biome != null && !biome.getValid().isEmpty()) {
                    return biome.test(lootContext.getWorld(), fisherBiome);
                }
            }

            // Category predicate is not null, check it
            else if (!category.getValid().isEmpty()) {
                return category.test(Biome.getCategory(fisherBiome));
            }
        }

        return false;
    }

    public static LootCondition.Builder builder(RegistryKey<Biome>... biomes) {
        return builder(Collections.emptyList(), Arrays.asList(biomes));
    }

    public static LootCondition.Builder builder(Biome.Category... categories) {
        return builder(Arrays.asList(categories), Collections.emptyList());
    }

    public static LootCondition.Builder builder(List<Biome.Category> categories, List<RegistryKey<Biome>> biomes) {
        List<String> stringCats = new ArrayList<>();
        List<String> stringBiomes = new ArrayList<>();

        categories.forEach(category -> stringCats.add(category.getName()));
        biomes.forEach(biome -> stringBiomes.add(biome.getValue().toString()));

        return builder(BiomeCategoryPredicate.builder().setValid(stringCats), BiomePredicate.builder().setValid(stringBiomes));
    }

    public static LootCondition.Builder builder(String category, String biome) {
        return builder(BiomeCategoryPredicate.builder().add(category));
    }

    public static LootCondition.Builder builder(BiomeCategoryPredicate.Builder categoryBuilder) {
        return builder(categoryBuilder, BiomePredicate.builder());
    }

    public static LootCondition.Builder builder(BiomePredicate.Builder biomeBuilder) {
        return builder(BiomeCategoryPredicate.builder(), biomeBuilder);
    }

    public static LootCondition.Builder builder(BiomeCategoryPredicate.Builder categoryBuilder, BiomePredicate.Builder biomeBuilder) {
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
            BiomeCategoryPredicate categoryPredicate;
            BiomePredicate biomePredicate;

            if (obj.has("category")) {
                categoryPredicate = BiomeCategoryPredicate.fromJson(obj.get("category"));
            } else {
                categoryPredicate = BiomeCategoryPredicate.EMPTY;
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