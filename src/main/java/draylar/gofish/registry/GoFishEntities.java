package draylar.gofish.registry;

import draylar.gofish.GoFish;
import draylar.gofish.entity.block.AstralCrateBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class GoFishEntities {

    public static final BlockEntityType<AstralCrateBlockEntity> ASTRAL_CRATE = register(
            "astral_crate",
            FabricBlockEntityTypeBuilder.create(AstralCrateBlockEntity::new, GoFishBlocks.ASTRAL_CRATE).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entity) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, GoFish.id(name), entity);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, GoFish.id(name), entity);
    }

    public static void init() {
        // NO-OP
    }

    private GoFishEntities() {
        // NO-OP
    }
}
