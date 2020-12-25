package draylar.gofish.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomeCategoryPredicate {

    public static final BiomeCategoryPredicate EMPTY = new BiomeCategoryPredicate(Collections.emptyList());
    private static final String VALID_KEY = "valid";
    private final List<String> valid;

    public BiomeCategoryPredicate(List<String> valid) {
        this.valid = valid;
    }

    public BiomeCategoryPredicate(Builder builder) {
        this.valid = builder.valid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getValid() {
        return valid;
    }

    public boolean test(Biome.Category category) {
        String low = category.getName();

        for(String s : valid) {
            if(s.equals(low)) {
                return true;
            }
        }

        return false;
    }

    public JsonElement toJson() {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();

        for(String s : valid) {
            arr.add(s);
        }

        obj.add(VALID_KEY, arr);
        return obj;
    }

    public static BiomeCategoryPredicate fromJson(JsonElement element) {
        JsonObject obj = JsonHelper.asObject(element, VALID_KEY);
        JsonArray arr = obj.getAsJsonArray(VALID_KEY);

        List<String> sArr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            sArr.add(arr.get(i).getAsString());
        }

        return new BiomeCategoryPredicate(sArr);
    }

    public static class Builder {

        private List<String> valid = new ArrayList<>();

        private Builder() {

        }

        public Builder setValid(List<String> valid) {
            this.valid = valid;
            return this;
        }

        public Builder add(String category) {
            if(!category.isEmpty()) {
                this.valid.add(category);
            }

            return this;
        }

        public Builder of(BiomeCategoryPredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomeCategoryPredicate build() {
            return new BiomeCategoryPredicate(this);
        }
    }
}
