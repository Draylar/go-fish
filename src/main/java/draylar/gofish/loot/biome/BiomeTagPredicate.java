package draylar.gofish.loot.biome;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BiomeTagPredicate(List<TagKey<Biome>> valid) {

    public static final Codec<BiomeTagPredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            TagKey.unprefixedCodec(RegistryKeys.BIOME).listOf().fieldOf("valid").forGetter(BiomeTagPredicate::valid)
                    )
                    .apply(instance, BiomeTagPredicate::new)
    );
    public static final BiomeTagPredicate EMPTY = new BiomeTagPredicate(Collections.emptyList());

    public static BiomeTagPredicate create(List<TagKey<Biome>> valid) {
        return new BiomeTagPredicate(valid);
    }

    public List<TagKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(RegistryEntry<Biome> biome) {

        for(TagKey<Biome> tag : valid) {
            if(biome.isIn(tag)) {
                return true;
            }
        }

        return false;
    }

    public JsonElement toJson() {
        return Util.getResult(CODEC.encodeStart(JsonOps.INSTANCE, this), IllegalStateException::new);
    }

    public static BiomeTagPredicate fromJson(@Nullable JsonElement json) {
        return Util.getResult(CODEC.parse(JsonOps.INSTANCE, json), JsonParseException::new);
    }

    public static class Builder {

        private List<TagKey<Biome>> valid = new ArrayList<>();

        public static Builder create() {
            return new BiomeTagPredicate.Builder();
        }

        public Builder setValid(List<TagKey<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder setValidByString(List<String> valid) {
            List<TagKey<Biome>> tagKeys = new ArrayList<>();
            for (String str : valid) {
                tagKeys.add(TagKey.of(RegistryKeys.BIOME, new Identifier(str)));
            }
            return setValid(tagKeys);
        }

        public Builder add(String tag) {
            if(!tag.isEmpty()) {
                this.valid.add(TagKey.of(RegistryKeys.BIOME, new Identifier(tag)));
            }

            return this;
        }

        public Builder of(BiomeTagPredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomeTagPredicate build() {
            return new BiomeTagPredicate(this.valid);
        }
    }
}
