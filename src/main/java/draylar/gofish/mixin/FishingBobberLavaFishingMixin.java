package draylar.gofish.mixin;

import draylar.gofish.item.ExtendedFishingRodItem;
import draylar.gofish.registry.GoFishParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberLavaFishingMixin extends Entity {

    @Shadow
    public abstract PlayerEntity getPlayerOwner();

    @Shadow
    public abstract void remove(Entity.RemovalReason reason);

    private FishingBobberLavaFishingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // this mixin is used to determine whether a bobber is actually bobbing for fish
    @ModifyVariable(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 0), index = 2)
    private float bobberInLava(float value) {
        BlockPos blockPos = this.getBlockPos();
        FluidState fluidState = this.getWorld().getFluidState(blockPos);

        if (fluidState.isIn(FluidTags.LAVA)) {
            // Fishing rod doesn't set active hand, and can be used in either, so we check both
            ItemStack mainHandStack = getPlayerOwner().getMainHandStack();
            ItemStack offHandStack = getPlayerOwner().getOffHandStack();

            // Player is holding extended fishing rod, check if it can be in lava.
            // Otherwise, fallback to default behavior.
            Hand rodHand = Hand.MAIN_HAND;
            boolean canFishInLava = false;

            if (mainHandStack.getItem() instanceof ExtendedFishingRodItem) {
                ExtendedFishingRodItem usedRod = (ExtendedFishingRodItem) mainHandStack.getItem();

                if (usedRod.canFishInLava()) {
                    canFishInLava = true;
                }
            } else if (offHandStack.getItem() instanceof ExtendedFishingRodItem) {
                ExtendedFishingRodItem usedRod = (ExtendedFishingRodItem) offHandStack.getItem();

                if (usedRod.canFishInLava()) {
                    canFishInLava = true;
                }
            }

            if (canFishInLava) {
                value = fluidState.getHeight(this.getWorld(), blockPos);
            } else {
                if (!getPlayerOwner().isCreative()) {
                    getPlayerOwner().getStackInHand(rodHand).damage(5, getPlayerOwner(), player -> player.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }

                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 5, 0, 1, 0, 0);
                }

                getPlayerOwner().playSound(SoundEvents.ENTITY_GENERIC_BURN, .5f, 1f);
                remove(RemovalReason.KILLED);
            }
        }

        return value;
    }

    // Original check is used to determine whether the bobber should free-fall.
    // Bobbers shouldn't free-fall through liquid anyways, so we return true for all liquids.
    // note that the original method call is inversed so we also inverse ours
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 1))
    private boolean fallOutsideLiquid(FluidState fluid, TagKey<Fluid> tag) {
        return !fluid.isEmpty();
    }

    @Inject(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void fishingLavaParticles(BlockPos pos, CallbackInfo ci, ServerWorld serverWorld, int i, BlockPos blockPos, float f, float g, float h, double d, double e, double j,
            BlockState blockState) {
        if (blockState.isOf(Blocks.LAVA)) {
            if (this.random.nextFloat() < 0.15f) {
                serverWorld.spawnParticles(ParticleTypes.LAVA, d, e - (double) 0.1f, j, 1, g, 0.1, h, 0.0);
            }
            float dZ = f * 0.04F;
            float dX = h * 0.04F;
            serverWorld.spawnParticles(GoFishParticles.LAVA_FISHING, d, e, j, 0, dX, 0.01D, (-dZ), 1.0D);
            serverWorld.spawnParticles(GoFishParticles.LAVA_FISHING, d, e, j, 0, (-dX), 0.01D, dZ, 1.0D);
        }
    }

    @Inject(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void fishSecondaryLavaParticles(BlockPos pos, CallbackInfo ci, ServerWorld serverWorld, int i, BlockPos blockPos, float f, float g, float h, double d, double e, double j,
            BlockState blockState) {
        if (blockState.isOf(Blocks.LAVA)) {
            serverWorld.spawnParticles(ParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
        }
    }

    @Redirect(method = "Lnet/minecraft/entity/projectile/FishingBobberEntity;getPositionType(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/projectile/FishingBobberEntity$PositionType;", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean isInValidLiquid(FluidState fluidState, TagKey<Fluid> tag) {
        return !fluidState.isEmpty();
    }
}
