package draylar.gofish.api;

import net.minecraft.sound.SoundEvent;

import java.util.Random;

public class SoundInstance {

    public static final FloatGetter DEFAULT_PITCH = random ->  0.4F / (random.nextFloat() * 0.4F + 0.8F);
    private final SoundEvent sound;
    private final FloatGetter volume;
    private final FloatGetter pitch;

    public SoundInstance(SoundEvent sound, float volume, FloatGetter pitch) {
        this.sound = sound;
        this.volume = random -> volume;
        this.pitch = pitch;
    }

    public SoundInstance(SoundEvent sound, FloatGetter volume, FloatGetter pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public float getVolume(Random random) {
        return volume.get(random);
    }

    public float getPitch(Random random) {
        return pitch.get(random);
    }

    @FunctionalInterface
    public interface FloatGetter {
        float get(Random random);
    }
}
