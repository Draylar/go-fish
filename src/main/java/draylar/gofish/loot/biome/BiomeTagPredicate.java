package draylar.gofish.loot.biome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.tag.Tag;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomeTagPredicate {

    public static final BiomeTagPredicate EMPTY = new BiomeTagPredicate(Collections.emptyList());
    private static final String VALID_KEY = "valid";
    private final List<Tag<Biome>> valid;

    public BiomeTagPredicate(List<Tag<Biome>> valid) { this.valid = valid; }

    public BiomeTagPredicate(Builder builder) {
        this.valid = builder.valid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Tag<Biome>> getValid() {
        return valid;
    }

    public boolean test(Biome biome) {

        for(Tag<Biome> tag : valid) {
            if(tag.values().contains(biome)) {
                return true;
            }
        }

        return false;
    }

    public JsonElement toJson() {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();

        for(Tag<Biome> tag : valid) {
//            arr.add(tag.);
        }

        obj.add(VALID_KEY, arr);
        return obj;
    }

    public static BiomeTagPredicate fromJson(JsonElement element) {
        JsonObject obj = JsonHelper.asObject(element, VALID_KEY);
        JsonArray arr = obj.getAsJsonArray(VALID_KEY);

        List<Tag<Biome>> sArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
//            sArr.add(Tag.Builder.create().read(arr.get(i)));
        }

        return new BiomeTagPredicate(sArr);
    }

    public static class Builder {

        private List<Tag<Biome>> valid = new ArrayList<>();

        private Builder() {

        }

        public Builder setValid(List<Tag<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder add(Tag<Biome> tag) {
            if(tag != null) {
                this.valid.add(tag);
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
