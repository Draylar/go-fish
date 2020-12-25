package draylar.gofish.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomePredicate {

    public static final BiomePredicate EMPTY = new BiomePredicate(Collections.emptyList());
    private static final String VALID_KEY = "valid";
    private final List<String> valid;

    public BiomePredicate(List<String> valid) {
        this.valid = valid;
    }

    private BiomePredicate(Builder builder) {
        this.valid = builder.valid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getValid() {
        return valid;
    }

    public boolean test(World world, Biome biome) {
        Identifier id = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);

        if(id != null) {
            String low = id.toString();

            for (String s : valid) {
                if (new Identifier(s).toString().equals(low)) {
                    return true;
                }
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

        private List<String> valid;

        private Builder() {

        }

        public Builder setValid(List<String> valid) {
            this.valid = valid;
            return this;
        }

        public Builder add(String biome) {
            if(!biome.isEmpty()) {
                valid.add(biome);
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

