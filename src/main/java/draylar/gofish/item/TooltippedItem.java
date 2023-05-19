package draylar.gofish.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class TooltippedItem extends Item {

    private final int lines;

    public TooltippedItem(Settings settings, int lines) {
        super(settings);
        this.lines = lines;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if(lines > 0) {
            for (int i = 1; i <= lines; i++) {
                tooltip.add(Text.translatable(String.format("%s.tooltip_%d", getTranslationKey(), i)).formatted(Formatting.GRAY));
            }
        }
    }
}
