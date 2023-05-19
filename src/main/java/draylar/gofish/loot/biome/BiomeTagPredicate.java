package draylar.gofish.loot.biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.biome.Biome;

public class BiomeTagPredicate {

    public static final BiomeTagPredicate EMPTY = new BiomeTagPredicate(Collections.emptyList());
    private static final String VALID_KEY = "valid";
    private final List<TagKey<Biome>> valid;

    public BiomeTagPredicate(List<TagKey<Biome>> valid) { this.valid = valid; }

    public BiomeTagPredicate(Builder builder) {
        this.valid = builder.valid;
    }

    public static Builder builder() {
        return new Builder();
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
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();

        for(TagKey<Biome> tag : valid) {
            arr.add(tag.id().toString());
        }

        obj.add(VALID_KEY, arr);
        return obj;
    }

    public static BiomeTagPredicate fromJson(JsonElement element) {
        JsonObject obj = JsonHelper.asObject(element, VALID_KEY);
        JsonArray arr = obj.getAsJsonArray(VALID_KEY);

        List<String> sArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            sArr.add(arr.get(i).getAsString());
        }

        return BiomeTagPredicate.builder().setValidByString(sArr).build();
    }

    public static class Builder {

        private List<TagKey<Biome>> valid = new ArrayList<>();

        private Builder() {

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
            return new BiomeTagPredicate(this);
        }
    }
}
