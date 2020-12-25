package draylar.gofish.mixin;

import draylar.gofish.item.ExtendedFishingRodItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {

    private PlayerEntity gofish_owner;

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void storeContext(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        gofish_owner = fishingBobberEntity.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHandSwingProgress(F)F"),
            index = 12
    )
    private int mod(int i) {
        ItemStack itemStack = gofish_owner.getMainHandStack();

        // previous condition was hit
        if (itemStack.getItem() != Items.FISHING_ROD) {
            if(itemStack.getItem() instanceof ExtendedFishingRodItem) {
                // undo change
                return -i;
            }
        }

        return i;
    }
}
