package draylar.gofish.client.item;

import draylar.gofish.entity.block.AstralCrateBlockEntity;
import draylar.gofish.registry.GoFishBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class AstralCrateItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final AstralCrateBlockEntity astralCrate = new AstralCrateBlockEntity(BlockPos.ORIGIN, GoFishBlocks.ASTRAL_CRATE.getDefaultState());

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getBlockEntityRenderDispatcher().renderEntity(astralCrate, matrices, vertexConsumers, light, overlay);
        client.getBlockRenderManager().renderBlock(GoFishBlocks.ASTRAL_CRATE.getDefaultState(), BlockPos.ORIGIN, client.world, matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), true,
                client.world.getRandom());
    }
}
