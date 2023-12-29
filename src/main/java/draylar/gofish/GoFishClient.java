package draylar.gofish;

import draylar.gofish.client.be.AstralCrateRenderer;
import draylar.gofish.client.item.AstralCrateItemRenderer;
import draylar.gofish.registry.GoFishBlocks;
import draylar.gofish.registry.GoFishEntities;
import draylar.gofish.registry.GoFishItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoFishClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GoFishBlocks.ASTRAL_CRATE, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(GoFishEntities.ASTRAL_CRATE, AstralCrateRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(GoFishBlocks.ASTRAL_CRATE.asItem(), new AstralCrateItemRenderer());

        registerFishingRodPredicates(GoFishItems.BLAZE_ROD);
        registerFishingRodPredicates(GoFishItems.CELESTIAL_ROD);
        registerFishingRodPredicates(GoFishItems.FROSTED_ROD);
        registerFishingRodPredicates(GoFishItems.SOUL_ROD);
        registerFishingRodPredicates(GoFishItems.MATRIX_ROD);
        registerFishingRodPredicates(GoFishItems.SLIME_ROD);
        registerFishingRodPredicates(GoFishItems.DIAMOND_REINFORCED_ROD);
        registerFishingRodPredicates(GoFishItems.SKELETAL_ROD);
        registerFishingRodPredicates(GoFishItems.EYE_OF_FISHING);
    }

    public void registerFishingRodPredicates(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("cast"), (itemStack, clientWorld, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                boolean bl = livingEntity.getMainHandStack() == itemStack;
                boolean bl2 = livingEntity.getOffHandStack() == itemStack;
                if (livingEntity.getMainHandStack().getItem() instanceof FishingRodItem) {
                    bl2 = false;
                }

                return (bl || bl2) && livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).fishHook != null ? 1.0F : 0.0F;
            }
        });
    }
}
