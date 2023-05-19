package draylar.gofish.registry;

import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public class GoFishTags {
    public static final TagKey<Biome> ICY = TagKey.of(RegistryKeys.BIOME, new Identifier("gofish", "icy_biomes"));
    public static final TagKey<Biome> PLAINS = TagKey.of(RegistryKeys.BIOME, new Identifier("gofish", "plains_biomes"));
    public static final TagKey<Biome> SWAMP = TagKey.of(RegistryKeys.BIOME, new Identifier("gofish", "swamp_biomes"));
}
