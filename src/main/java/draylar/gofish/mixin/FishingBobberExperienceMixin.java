package draylar.gofish.mixin;

import draylar.gofish.api.ExperienceBobber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * This mixin is responsible for allowing Fishing Bobbers to have a customizable amount of base experience gain per catch.
 * For usage, cast a {@link FishingBobberEntity} to {@link ExperienceBobber}, and manipulate the base experience through the setter provided.
 */
@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberExperienceMixin extends Entity implements ExperienceBobber {

    @Shadow public abstract PlayerEntity getPlayerOwner();

    @Unique
    private int gf_baseExperience = 1;

    private FishingBobberExperienceMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public int gf_getBaseExperience() {
        return gf_baseExperience;
    }

    @Override
    public void gf_setBaseExperience(int experience) {
        this.gf_baseExperience = experience;
    }

    @Redirect(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", ordinal = 1)
    )
    private boolean modifyExperience(World world, Entity entity) {
        ServerPlayerEntity player = (ServerPlayerEntity) getPlayerOwner();
        return player.world.spawnEntity(new ExperienceOrbEntity(player.world, player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, this.random.nextInt(6) + gf_baseExperience));
    }
}
