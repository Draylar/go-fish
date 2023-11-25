package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.enchantment.DeepfryEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class GoFishEnchantments {

    public static final Enchantment DEEPFRY = register("deepfry", new DeepfryEnchantment());

    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, GoFish.id(name), enchantment);
    }

    public static void init() {
        // NO-OP
    }

    private GoFishEnchantments() {
        // NO-OP
    }
}
