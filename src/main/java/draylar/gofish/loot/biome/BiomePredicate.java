package draylar.gofish.loot.biome;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BiomePredicate(List<RegistryKey<Biome>> valid) {

    public static final Codec<BiomePredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            RegistryKey.createCodec(RegistryKeys.BIOME).listOf().fieldOf("valid").forGetter(BiomePredicate::valid)
                    )
                    .apply(instance, BiomePredicate::new)
    );
    public static final BiomePredicate EMPTY = new BiomePredicate(Collections.emptyList());

    public static BiomePredicate create(List<RegistryKey<Biome>> valid) {
        return new BiomePredicate(valid);
    }

    public List<RegistryKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(RegistryEntry<Biome> biome) {
        for (RegistryKey<Biome> key : valid) {
            if (biome.matchesKey(key)) {
                return true;
            }
        }
        return false;
    }

    public JsonElement toJson() {
        return Util.getResult(CODEC.encodeStart(JsonOps.INSTANCE, this), IllegalStateException::new);
    }

    public static BiomePredicate fromJson(JsonElement json) {
        return Util.getResult(CODEC.parse(JsonOps.INSTANCE, json), JsonParseException::new);
    }

    public static class Builder {

        private List<RegistryKey<Biome>> valid;

        public static Builder create() {
            return new BiomePredicate.Builder();
        }

        public Builder setValid(List<RegistryKey<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder setValidFromString(List<String> valid) {
            List<RegistryKey<Biome>> rKeys = new ArrayList<>();
            for (String str : valid) {
                if (!valid.isEmpty()) {
                    rKeys.add(RegistryKey.of(RegistryKeys.BIOME, new Identifier(str)));
                }
            }

            return setValid(rKeys);
        }

        public Builder add(RegistryKey<Biome> biome) {
            valid.add(biome);
            return this;
        }

        public Builder add(String biome) {
            if(!biome.isEmpty()) {
                valid.add(RegistryKey.of(RegistryKeys.BIOME, new Identifier(biome)));
            }

            return this;
        }

        public Builder of(BiomePredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomePredicate build() {
            return new BiomePredicate(this.valid);
        }
    }
}

