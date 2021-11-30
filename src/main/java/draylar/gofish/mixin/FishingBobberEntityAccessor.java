package draylar.gofish.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingBobberEntity.class)
public interface FishingBobberEntityAccessor {
    @Accessor
    int getLuckOfTheSeaLevel();

    @Mutable
    @Accessor
    void setLuckOfTheSeaLevel(int luckOfTheSeaLevel);

    @Accessor
    int getLureLevel();

    @Mutable
    @Accessor
    void setLureLevel(int lureLevel);
}
