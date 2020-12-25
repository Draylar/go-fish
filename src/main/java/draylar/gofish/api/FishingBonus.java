package draylar.gofish.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface FishingBonus {

    default int getLure() {
        return 0;
    }

    default int getLuckOfTheSea() {
        return 0;
    }

    default int getBaseExperience() {
        return 0;
    }

    default boolean providesAutosmelt() {
        return false;
    }

    default boolean shouldApply(World world, PlayerEntity player) {
        return true;
    }
}
