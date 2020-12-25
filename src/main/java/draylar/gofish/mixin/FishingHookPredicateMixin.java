package draylar.gofish.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.FishingHookPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHookPredicate.class)
public class FishingHookPredicateMixin {

    /*
     * In recent Minecraft versions, fishing was changed so that rare loot could only be obtained in large bodies of water (to combat fish farms).
     * The downside to this is that Go Fish's /fish command can not give crates or rare loot, because this conditional returns false.
     * We assume this check only matters when the player is fishing in survival, and always pass the check when the player is creative.
     * In other words, we fall back to old treasure fishing behavior when the player is in creative mode.
     */
    @Inject(
            method = "test",
            at = @At("HEAD"),
            cancellable = true)
    private void overrideCreativePredicate(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;

            if (player.isCreative() && player.hasPermissionLevel(2)) {
                cir.setReturnValue(true);
            }
        }
    }
}