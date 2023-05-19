package draylar.gofish.mixin;

import draylar.gofish.api.ExperienceBobber;
import draylar.gofish.api.FireproofEntity;
import draylar.gofish.api.FishingBonus;
import draylar.gofish.api.SmeltingBobber;
import draylar.gofish.item.ExtendedFishingRodItem;
import draylar.gofish.registry.GoFishEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(FishingRodItem.class)
public class FishingRodPropertyMixin {

    @Unique private PlayerEntity player;
    @Unique private ItemStack heldStack;

    @Inject(method = "use", at = @At("HEAD"))
    private void storeContext(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        this.heldStack = user.getStackInHand(hand);
        this.player = user;
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private boolean modifyBobber(World world, Entity entity) {
        if(entity instanceof FishingBobberEntity bobber) {
            modifyBobber(world, bobber);
        }

        return world.spawnEntity(entity);
    }

    @Unique
    private void modifyBobber(World world, FishingBobberEntity bobber) {
        boolean smeltBuff = false;
        int bonusLure = 0;
        int bonusLuck = 0;
        int bonusExperience = 0;

        // Find buffing items in player inventory
        List<FishingBonus> found = new ArrayList<>();
        for (ItemStack stack : player.getInventory().main) {
            Item item = stack.getItem();

            if (item instanceof FishingBonus bonus) {
                if (!found.contains(bonus)) {
                    if(bonus.shouldApply(world, player)) {
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

        // Modify bobber statistics
        ((FireproofEntity) bobber).gf_setFireproof(false);
        ((SmeltingBobber) bobber).gf_setSmelts(smelts);
        ((ExperienceBobber) bobber).gf_setBaseExperience(1 + bonusExperience);
        FishingBobberEntityAccessor accessor = (FishingBobberEntityAccessor) bobber;
        accessor.setLureLevel(Math.min((accessor.getLureLevel() + bonusLure),5));
        accessor.setLuckOfTheSeaLevel(accessor.getLuckOfTheSeaLevel() + bonusLuck);
    }
}
