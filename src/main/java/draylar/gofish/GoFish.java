package draylar.gofish;

import draylar.gofish.command.FishCommand;
import draylar.gofish.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoFish implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroup.builder(id("group"))
        .icon(() -> new ItemStack(GoFishItems.GOLDEN_FISH))
        .build();
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        GoFishBlocks.init();
        GoFishItems.init();
        GoFishEnchantments.init();
        GoFishLoot.init();
        GoFishLootHandler.init();
        GoFishParticles.init();
        GoFishEntities.init();

        FishCommand.register();

        FuelRegistry.INSTANCE.add(GoFishItems.OAKFISH, 300); // same time as coal
        FuelRegistry.INSTANCE.add(GoFishItems.CHARFISH, 1600); // same time as coal

        registerBrewingRecipes();
    }

    public static Identifier id(String name) {
        return new Identifier("gofish", name);
    }

    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, GoFishItems.CLOUDY_CRAB, Potions.SLOW_FALLING);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, GoFishItems.CHARFISH, Potions.WEAKNESS);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, GoFishItems.RAINY_BASS, Potions.WATER_BREATHING);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, GoFishItems.MAGMA_COD, Potions.FIRE_RESISTANCE);
    }
}
