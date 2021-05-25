package draylar.gofish.mixin;

import draylar.gofish.api.ExperienceBobber;
import draylar.gofish.api.FireproofEntity;
import draylar.gofish.api.FishingBonus;
import draylar.gofish.api.SmeltingBobber;
import draylar.gofish.item.ExtendedFishingRodItem;
import draylar.gofish.registry.GoFishEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(FishingRodItem.class)
public class FishingRodPropertyMixin {

    /**
     * @author Draylar
     * @reason Go Fish extensively modifies the vanilla Fishing Lure with additional properties.
     */
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack heldStack = user.getStackInHand(hand);
        Random random = world.random;

        if (user.fishHook != null) {
            // Retrieve fishing bobber and damage Fishing Rod
            if (!world.isClient) {
                int damage = user.fishHook.use(heldStack);
                heldStack.damage(damage, user, player -> player.sendToolBreakStatus(hand));
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        } else {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

            // Summon new fishing bobber
            if (!world.isClient) {
                boolean smeltBuff = false;
                int bonusLure = 0;
                int bonusLuck = 0;
                int bonusExperience = 0;

                // Find buffing items in player inventory
                List<FishingBonus> found = new ArrayList<>();
                for (ItemStack stack : user.inventory.main) {
                    Item item = stack.getItem();

                    if (item instanceof FishingBonus) {
                        FishingBonus bonus = (FishingBonus) item;

                        if (!found.contains(bonus)) {
                            if(bonus.shouldApply(world, user)) {
                                found.add(bonus);
                                smeltBuff = bonus.providesAutosmelt() || smeltBuff;
                                bonusLure += bonus.getLure();
                                bonusLuck += bonus.getLuckOfTheSea();
                                bonusExperience += bonus.getBaseExperience();
                            }
                        }
                    }
                }

                // Check if this rod autosmelts
                boolean hasDeepfryEnchantment = EnchantmentHelper.getLevel(GoFishEnchantments.DEEPFRY, heldStack) != 0;
                boolean rodAutosmelts = heldStack.getItem() instanceof ExtendedFishingRodItem && ((ExtendedFishingRodItem) heldStack.getItem()).autosmelts();
                boolean smelts = hasDeepfryEnchantment || rodAutosmelts || smeltBuff;

                // Calculate lure and luck
                int lure = EnchantmentHelper.getLure(heldStack) + bonusLuck + bonusLure;
                int lots = EnchantmentHelper.getLuckOfTheSea(heldStack) + bonusLuck + bonusLuck;

                // Summon bobber with stats
                FishingBobberEntity bobber = new FishingBobberEntity(user, world, lots, lure);
                world.spawnEntity(bobber);
                ((FireproofEntity) bobber).gf_setFireproof(false);
                ((SmeltingBobber) bobber).gf_setSmelts(smelts);
                ((ExperienceBobber) bobber).gf_setBaseExperience(1 + bonusExperience);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(Items.FISHING_ROD));
        }

        return TypedActionResult.success(heldStack, world.isClient());
    }
}
