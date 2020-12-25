package draylar.gofish.item;

import draylar.gofish.api.FishingBonus;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;

import java.util.List;

public class SoulLureItem extends Item implements FishingBonus {

    public static final String SOUL_SAND_VALLEY = "minecraft:soul_sand_valley";

    public SoulLureItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getLuckOfTheSea() {
        return 1;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText(String.format("gofish.lure.tooltip_%d", 1)).formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText(String.format("gofish.lots.tooltip_%d", 2), 1, " in Soul Sand Valley").formatted(Formatting.GRAY));
        // todo: translatable
    }

    @Override
    public boolean shouldApply(World world, PlayerEntity player) {
        Identifier id = world.getRegistryManager().get(Registry.BIOME_KEY).getId(world.getBiome(player.getBlockPos()));

        if(id != null) {
            // todo: make this a constant
            return id.toString().equals(SOUL_SAND_VALLEY);
        }

        return false;
    }
}
