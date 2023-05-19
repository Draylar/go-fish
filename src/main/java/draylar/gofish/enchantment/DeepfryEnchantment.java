package draylar.gofish.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

/*
 * Original idea is from The-Myth-Of-Legends at (https://www.reddit.com/r/minecraftsuggestions/comments/ai9hr8/new_fishing_rod_enchantment_deepfry/).
 * "Only 1 level. Upon fishing out either Cod or Salmon, it will be cooked."
 */
public class DeepfryEnchantment extends Enchantment {

    public DeepfryEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.FISHING_ROD, new EquipmentSlot[] {
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND
        });
    }

    @Override
    public int getMinPower(int level) {
        return 15;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
