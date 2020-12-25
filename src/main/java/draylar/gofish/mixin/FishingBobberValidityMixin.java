package draylar.gofish.mixin;

import draylar.gofish.item.ExtendedFishingRodItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberValidityMixin extends Entity {

    @Shadow public abstract PlayerEntity getPlayerOwner();

    private FishingBobberValidityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * Patches {@link FishingBobberEntity#removeIfInvalid(PlayerEntity)} to work for all items of the type {@link FishingRodItem}.
     *
     * @param playerEntity  owner of this {@link FishingBobberEntity}
     * @param cir  mixin callback info
     */
    @Inject(
            method = "removeIfInvalid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeIfInvalid(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = playerEntity.getMainHandStack();
        ItemStack offHandStack = playerEntity.getOffHandStack();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof ExtendedFishingRodItem;
        boolean offHandHasRod = offHandStack.getItem() instanceof ExtendedFishingRodItem;

        if (!playerEntity.removed && playerEntity.isAlive() && (mainHandHasRod || offHandHasRod) && this.squaredDistanceTo(playerEntity) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }
}
