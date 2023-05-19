package draylar.gofish.client.be;

import draylar.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class AstralCrateRenderer extends EndPortalBlockEntityRenderer<AstralCrateBlockEntity> {

    public AstralCrateRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(AstralCrateBlockEntity astralCrate, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        this.renderSides(astralCrate, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider.getBuffer(this.getLayer()));
    }

    private void renderSides(AstralCrateBlockEntity entity, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float r = .2f;
        float g = .2f;
        float b = .2f;

        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F, 0.99F, 0.01F, 1.0F, 0.99F, 1.0F, 1.0F, 1.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.99F, 0.0F, 0.01F, 0.0F, 0.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.99F, 0.99F, 0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.01F, 0.01F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.01F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.99F, 0.99F, 1.0F, 1.0F, 0.0F, 0.0F, r, g, b);
    }

    private void renderSide(AstralCrateBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, float r, float g, float b) {
        vertices.vertex(model, x1, y1, z1).color(r, g, b, 1.0F).next();
        vertices.vertex(model, x2, y1, z2).color(r, g, b, 1.0F).next();
        vertices.vertex(model, x2, y2, z3).color(r, g, b, 1.0F).next();
        vertices.vertex(model, x1, y2, z4).color(r, g, b, 1.0F).next();
    }
}
