package draylar.gofish.registry;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class GoFishTags {
    public static final TagKey<Biome> ICY = TagKey.of(Registry.BIOME_KEY, new Identifier("gofish", "icy_biomes"));
    public static final TagKey<Biome> PLAINS = TagKey.of(Registry.BIOME_KEY, new Identifier("gofish", "plains_biomes"));
    public static final TagKey<Biome> SWAMP = TagKey.of(Registry.BIOME_KEY, new Identifier("gofish", "swamp_biomes"));
}
