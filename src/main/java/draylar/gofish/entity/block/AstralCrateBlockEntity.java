package draylar.gofish.entity.block;

import draylar.gofish.registry.GoFishEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.BlockPos;

public class AstralCrateBlockEntity extends EndPortalBlockEntity {

    public AstralCrateBlockEntity(BlockPos pos, BlockState state) {
        super(GoFishEntities.ASTRAL_CRATE, pos, state);
    }
}
