package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.api.SoundInstance;
import draylar.gofish.item.ExtendedFishingRodItem;
import draylar.gofish.item.LureItem;
import draylar.gofish.item.SoulLureItem;
import draylar.gofish.item.TooltippedItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

public class GoFishItems {

    public static final Item BLAZE_ROD = register("blaze_rod", new ExtendedFishingRodItem.Builder()
            .durability(125)
            .autosmelt()
            .lavaProof(true)
            .build());

    public static final Item SKELETAL_ROD = register("skeletal_rod", new ExtendedFishingRodItem.Builder()
            .durability(75)
            .withCastSound(new SoundInstance(SoundEvents.ENTITY_SKELETON_STEP, 1.0F, SoundInstance.DEFAULT_PITCH))
            .withRetrieveSound(new SoundInstance(SoundEvents.ENTITY_SKELETON_STEP, 0.5F, SoundInstance.DEFAULT_PITCH))
            .lavaProof(true)
            .build());

    public static final Item DIAMOND_REINFORCED_ROD = register("diamond_reinforced_rod", new ExtendedFishingRodItem.Builder()
            .durability(300)
            .color(Formatting.AQUA)
            .lavaProof(true)
            .build());

    public static final Item EYE_OF_FISHING = register("ender_rod", new ExtendedFishingRodItem.Builder()
            .durability(250)
            .color(Formatting.LIGHT_PURPLE)
            .build());

    public static final Item FROSTED_ROD = register("frosted_rod", new ExtendedFishingRodItem.Builder()
            .durability(150)
            .build());

    public static final Item SLIME_ROD = register("slime_rod", new ExtendedFishingRodItem.Builder()
            .durability(150)
            .color(Formatting.GREEN)
            .withCastSound(new SoundInstance(SoundEvents.ENTITY_SLIME_JUMP, 0.8F, SoundInstance.DEFAULT_PITCH))
            .withRetrieveSound(new SoundInstance(SoundEvents.ENTITY_SLIME_JUMP, 0.5F, SoundInstance.DEFAULT_PITCH))
            .build());

    public static final Item SOUL_ROD = register("soul_rod", new ExtendedFishingRodItem.Builder()
            .durability(250)
            .color(Formatting.LIGHT_PURPLE)
            .baseExperienceGain(5)
            .lavaProof(true)
            .build());

    public static final Item MATRIX_ROD = register("matrix_rod", new ExtendedFishingRodItem.Builder()
            .durability(200)
            .color(Formatting.LIGHT_PURPLE)
            .build());

    public static final Item CELESTIAL_ROD = register("celestial_rod", new ExtendedFishingRodItem.Builder()
            .durability(150)
            .color(Formatting.LIGHT_PURPLE)
            .tooltipLines(1)
            .nightLuck()
            .build());

    // fish
    public static final Item ENDER_EEL = register("ender_eel", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item ICICLE_FISH = register("icicle_fish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 0, 0), 1).build())));
    public static final Item LILYFISH = register("lilyfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build())));
    public static final Item MATRIX_FISH = register("matrix_fish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build())));
    public static final Item SEAWEED = register("seaweed", new Item(new Item.Settings()));
    public static final Item BAKED_SEAWEED = register("baked_seaweed", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build())));
    public static final Item SEAWEED_EEL = register("seaweed_eel", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).build())));
    public static final Item SLIMEFISH = register("slimefish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 5, 0), 1).build())));
    public static final Item SNOWBALL_FISH = register("snowball_fish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).build())));
    public static final Item TERRAFISH = register("terrafish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build())));
    public static final Item CARROT_CARP = register("carrot_carp", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).build())));
    public static final Item BAKED_CARROT_CARP = register("baked_carrot_carp", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).build())));
    public static final Item OAKFISH = register("oakfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).build())));
    public static final Item CHARFISH = register("charfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 5, 0), 1).build())));

    // nether
    public static final Item SPIKERFISH = register("spikerfish", new Item(new Item.Settings().fireproof()));
    public static final Item BLACKSTONE_TROUT = register("blackstone_trout", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.75f).build()).fireproof()));
    public static final Item GRILLED_BLACKSTONE_TROUT = register("grilled_blackstone_trout", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).build()).fireproof(), 2));
    public static final Item GRILLED_BLACKSTONE_DELUXE = register("grilled_blackstone_deluxe", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(11).saturationModifier(0.8f).build()).fireproof(), 3));
    public static final Item BONEFISH = register("bonefish", new Item(new Item.Settings().fireproof()));
    public static final Item GILDED_BLACKSTONE_CARP = register("gilded_blackstone_carp", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build()).fireproof()));
    public static final Item SMOKEY_SALMON = register("smokey_salmon", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 15, 0), 1).build()).fireproof()));
    public static final Item SOUL_SALMON = register("soul_salmon", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.75f).build()).fireproof()));
    public static final Item MAGMA_COD = register("magma_cod", new Item(new Item.Settings().food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20 * 15, 0), 1).hunger(6).saturationModifier(0.5f).build()).fireproof()));
    public static final Item BASALT_BASS = register("basalt_bass", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).build()).fireproof()));
    public static final Item OBSIDIAN_HALIBUT = register("obsidian_halibut", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).build()).fireproof()));

    // end
    public static final Item ENDFISH = register("endfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item BAKED_ENDFISH = register("baked_endfish", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8f).build()), 2));
    public static final Item ENDFISH_AND_CHORUS = register("endfish_and_chorus", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(1f).build()), 2));
    public static final Item CHORUS_COD = register("chorus_cod", new ChorusFruitItem(new Item.Settings().food(new FoodComponent.Builder().hunger(6).build()).rarity(Rarity.EPIC)));
    public static final Item DRAGONFISH = register("dragonfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(8).build()).rarity(Rarity.EPIC)));
    public static final Item OMEGA_FLOATER = register("omega_floater", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).build()).rarity(Rarity.EPIC)));
    public static final Item PORTAL_PUFFER = register("portal_puffer", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).build()).rarity(Rarity.EPIC)));

    // Full Moon fish
    public static final Item LUNARFISH = register("lunarfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item GALAXY_STARFISH = register("galaxy_starfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item STARRY_SALMON = register("starry_salmon", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item NEBULA_SWORDFISH = register("nebula_swordfish", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).build())));
    public static final Item AQUATIC_ASTRAL_STEW = register("aquatic_astral_stew", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(9).saturationModifier(0.75f).build()), 3));

    // weather
    public static final Item RAINY_BASS = register("rainy_bass", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build())));
    public static final Item STEAMED_BASS = register("steamed_bass", new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.25f).build()), 2));
    public static final Item CLOUDY_CRAB = register("cloudy_crab", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.75f).build())));
    public static final Item THUNDERING_BASS = register("thundering_bass", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.75f).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 15 * 20), 1).build())));
    public static final Item SMOKED_CLOUDY_CRAB = register("smoked_cloudy_crab", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(7).saturationModifier(0.25f).build())));
    public static final Item BLIZZARD_BASS = register("blizzard_bass", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build())));

    // accessories
    public static final Item GOLDEN_FISH = register("golden_fish", new LureItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), 1));
    public static final Item SIMPLE_LURE = register("simple_lure", new LureItem(new Item.Settings().maxCount(1), 1));
    public static final Item SOUL_LURE = register("soul_lure", new SoulLureItem(new Item.Settings().maxCount(1)));

    public static <T extends Item> T register(String name, T item) {
        Registry.register(Registries.ITEM, GoFish.id(name), item);
        ItemGroupEvents.modifyEntriesEvent(GoFish.GROUP).register(entries -> entries.add(item));
        return item;
    }

    public static void init() {
        // NO-OP
    }
}
