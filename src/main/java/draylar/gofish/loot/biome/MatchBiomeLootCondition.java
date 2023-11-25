package draylar.gofish.loot.biome;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import draylar.gofish.registry.GoFishLoot;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.*;

public record MatchBiomeLootCondition(Optional<BiomeTagPredicate> category, Optional<BiomePredicate> biome) implements LootCondition {

    public static final Codec<MatchBiomeLootCondition> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.createStrictOptionalFieldCodec(BiomeTagPredicate.CODEC, "category").forGetter(MatchBiomeLootCondition::category),
                            Codecs.createStrictOptionalFieldCodec(BiomePredicate.CODEC, "biome").forGetter(MatchBiomeLootCondition::biome)
                    )
                    .apply(instance, MatchBiomeLootCondition::new)
    );

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
            if (category.isEmpty() || category.get().getValid().isEmpty()) {
                if (biome.isPresent() && !biome.get().getValid().isEmpty()) {
                    return biome.get().test(fisherBiome);
                }
            }

            // Category predicate is not null, check it
            else {
                return category.get().test(fisherBiome);
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

        return builder(BiomeTagPredicate.Builder.create().setValidByString(stringCats), BiomePredicate.Builder.create().setValidFromString(stringBiomes));
    }

    public static LootCondition.Builder builder(String category, String biome) {
        return builder(BiomeTagPredicate.Builder.create().add(category), BiomePredicate.Builder.create().add(biome));
    }

    public static LootCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder) {
        return builder(categoryBuilder, BiomePredicate.Builder.create());
    }

    public static LootCondition.Builder builder(BiomePredicate.Builder biomeBuilder) {
        return builder(BiomeTagPredicate.Builder.create(), biomeBuilder);
    }

    public static LootCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder, BiomePredicate.Builder biomeBuilder) {
        return () -> new MatchBiomeLootCondition(Optional.of(categoryBuilder.build()), Optional.of(biomeBuilder.build()));
    }
}
