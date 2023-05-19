package draylar.gofish.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrateItem extends BlockItem {

    private final Identifier loot;

    public CrateItem(Block block, Settings settings) {
        super(block, settings);
        loot = new Identifier("minecraft", "chests/pillager_outpost");
    }

    public CrateItem(Block block, Settings settings, Identifier loot) {
        super(block, settings);
        this.loot = loot;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if(player != null && player.isSneaking()) {
            return ActionResult.FAIL;
        }

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // only open crate if user is sneaking
        if(user.isSneaking()) {
            if(!world.isClient) {
                // drop loot
                getDrops((ServerWorld) world, loot, user.getPos()).forEach(stack -> {
                    ItemScatterer.spawn(world, user.getX(), user.getY(), user.getZ(), stack);
                });
            }

            // remove 1x from inventory for non-creative players
            if(!user.isCreative()) {
                user.getStackInHand(hand).decrement(1);
            }

            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    /**
     * Retrieves the loot table drops for the given Identifier.
     * If no loot table exists at the given Identifier, an empty list is returned.
     * @param identifier loot table Identifier
     * @return list of drops generated from the loot table
     */
    private List<ItemStack> getDrops(ServerWorld world, Identifier identifier, Vec3d pos) {
        List<ItemStack> output = new ArrayList<>();

        if (world != null && !world.isClient) {
            // set up loot objects
            LootTable supplier = Objects.requireNonNull(world.getServer()).getLootManager().getTable(identifier);
            LootContext.Builder builder =
                    new LootContext.Builder(world)
                            .random(world.random)
                            .parameter(LootContextParameters.ORIGIN, pos);

            // build & add loot to output
            List<ItemStack> stacks = supplier.generateLoot(builder.build(LootContextTypes.CHEST));
            output.addAll(stacks);
        }

        return output;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("gofish.crate_tooltip").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
    }
}
