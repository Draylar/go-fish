package draylar.gofish.block;

import draylar.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class AstralCrateBlock extends Block implements BlockEntityProvider {

    public AstralCrateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AstralCrateBlockEntity(pos, state);
    }
}
