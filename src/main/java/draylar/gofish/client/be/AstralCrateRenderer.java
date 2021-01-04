package draylar.gofish.client.be;

import com.google.common.collect.ImmutableList;
import draylar.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class AstralCrateRenderer extends BlockEntityRenderer<AstralCrateBlockEntity> {

    private static final Random RANDOM = new Random(31100L);
    private static final List<RenderLayer> field_21732 = IntStream.range(0, 16).mapToObj((i) -> {
        return RenderLayer.getEndPortal(i + 1);
    }).collect(ImmutableList.toImmutableList());

    public AstralCrateRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AstralCrateBlockEntity astralCrate, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        RANDOM.setSeed(31100L);
        double distanceToCamera = astralCrate.getPos().getSquaredDistance(this.dispatcher.camera.getPos(), true);
        int k = this.getTextureZoom(distanceToCamera);
        float g = this.method_3594();
        Matrix4f matrixModel = matrixStack.peek().getModel();
        this.drawBox(astralCrate, matrixModel, vertexConsumerProvider.getBuffer(field_21732.get(0)));

        for(int l = 1; l < k; ++l) {
            this.drawBox(astralCrate, matrixModel, vertexConsumerProvider.getBuffer(field_21732.get(l)));
        }
    }

    private void drawBox(AstralCrateBlockEntity astralCrate, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float height = 0.99f;

        float r = .2f;
        float g = .2f;
        float b = .2f;

        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.00F, 0.99F, 0.01F, 1.0F, 0.99F, 1.0F, 1.0F, 1.0F, r, g, b, Direction.SOUTH);
        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.99F, 0.0F, 0.01F, 0.0F, 0.0F, 0.0F, r, g, b, Direction.NORTH);
        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.99F, 0.99F, 0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b, Direction.EAST);
        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.01F, 0.01F, 0.01F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b, Direction.WEST);
        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.01F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, r, g, b, Direction.DOWN);
        this.drawQuad(astralCrate, matrix4f, vertexConsumer, 0.01F, 0.99F, height, height, 1.0F, 1.0F, 0.0F, 0.0F, r, g, b, Direction.UP);
    }

    private void drawQuad(AstralCrateBlockEntity astralCrate, Matrix4f matrix4f, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z, float k, float l, float m, float r, float g, float b, Direction direction) {
        vertexConsumer.vertex(matrix4f, x1, y1, z).color(r, g, b, 1.0F).next();
        vertexConsumer.vertex(matrix4f, x2, y1, k).color(r, g, b, 1.0F).next();
        vertexConsumer.vertex(matrix4f, x2, y2, l).color(r, g, b, 1.0F).next();
        vertexConsumer.vertex(matrix4f, x1, y2, m).color(r, g, b, 1.0F).next();
    }

    protected int getTextureZoom(double distance) {
        return 5;
    }

    protected float method_3594() {
        return 0.75F;
    }
}
