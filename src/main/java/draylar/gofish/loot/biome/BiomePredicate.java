package draylar.gofish.loot.biome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomePredicate {

    public static final BiomePredicate EMPTY = new BiomePredicate(Collections.emptyList());
    private static final String VALID_KEY = "valid";
    private final List<RegistryKey<Biome>> valid;

    public BiomePredicate(List<String> valid) {
        this.valid = builder().setValidFromString(valid).valid;
    }

    private BiomePredicate(Builder builder) {
        this.valid = builder.valid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<RegistryKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(World world, RegistryEntry<Biome> biome) {
        for (RegistryKey<Biome> key : valid) {
            if (biome.matchesKey(key)) {
                return true;
            }
        }
        return false;
    }

    public JsonElement toJson() {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();

        for(RegistryKey<Biome> rKey : valid) {
            arr.add(rKey.getValue().toString());
        }

        obj.add(VALID_KEY, arr);
        return obj;
    }

    public static BiomePredicate fromJson(JsonElement element) {
        JsonObject obj = JsonHelper.asObject(element, VALID_KEY);
        JsonArray arr = obj.getAsJsonArray(VALID_KEY);

        List<String> sArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            sArr.add(arr.get(i).getAsString());
        }

        return new BiomePredicate(sArr);
    }


    public static class Builder {

        private List<RegistryKey<Biome>> valid;

        private Builder() {

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
            return new BiomePredicate(this);
        }
    }
}

