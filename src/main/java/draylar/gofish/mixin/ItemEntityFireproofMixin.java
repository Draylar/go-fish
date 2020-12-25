package draylar.gofish.mixin;

import draylar.gofish.api.FireproofEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityFireproofMixin extends Entity implements FireproofEntity {

    @Unique
    private static final TrackedData<Boolean> GF_FIRE_IMMUNE = DataTracker.registerData(ItemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ItemEntityFireproofMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("RETURN"))
    private void registerFireImmuneTracker(CallbackInfo ci) {
        dataTracker.startTracking(GF_FIRE_IMMUNE, false);
    }

    @Inject(
            method = "isFireImmune",
            at = @At("RETURN"),
            cancellable = true)
    private void isLavaFishingLoot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || dataTracker.get(GF_FIRE_IMMUNE));
    }

    // todo: inject into super instead
    @Override
    public boolean isOnFire() {
        if(gf_isFireproof()) {
            return false;
        }

        return super.isOnFire();
    }

    @Override
    public boolean gf_isFireproof() {
        return dataTracker.get(GF_FIRE_IMMUNE);
    }

    @Override
    public void gf_setFireproof(boolean value) {
        dataTracker.set(GF_FIRE_IMMUNE, value);
    }
}
