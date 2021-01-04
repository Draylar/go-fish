package draylar.gofish;

import draylar.gofish.command.FishCommand;
import draylar.gofish.config.GoFishConfig;
import draylar.gofish.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoFish implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(GoFishItems.GOLDEN_FISH));
    public static final GoFishConfig CONFIG = AutoConfig.register(GoFishConfig.class, GsonConfigSerializer::new).getConfig();
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
    }

    public static Identifier id(String name) {
        return new Identifier("gofish", name);
    }
}
