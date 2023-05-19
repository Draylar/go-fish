package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.particle.CustomDefaultParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class GoFishParticles {

    public static final DefaultParticleType LAVA_FISHING = register("lava_fishing", false);

    private static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, GoFish.id(name), new CustomDefaultParticleType(alwaysShow));
    }

    public static void init() {

    }

    private GoFishParticles() {

    }
}
