package draylar.gofish.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import draylar.gofish.impl.GoFishLootTables;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.List;

public class FishCommand {

    public static void register() {
        LiteralCommandNode<ServerCommandSource> root = CommandManager.literal("fish").requires(source -> source.hasPermissionLevel(2)).executes(context -> {
            fish(context, 1);
            return 1;
        }).then(CommandManager.argument("count", IntegerArgumentType.integer(1, 1000)).executes(context -> {
            fish(context, IntegerArgumentType.getInteger(context, "count"));
            return 1;
        })).build();

        CommandRegistrationCallback.EVENT.register((dispatcher, access, dedicated) -> {
            dispatcher.getRoot().addChild(root);
        });
    }

    private static void fish(CommandContext<ServerCommandSource> context, int times) throws CommandSyntaxException {
        ServerCommandSource serverCommandSource = context.getSource();
        ServerPlayerEntity player = context.getSource().getPlayer();
        ServerWorld world = context.getSource().getWorld();

        LootContextParameterSet lootContext = new LootContextParameterSet.Builder(serverCommandSource.getWorld()).add(LootContextParameters.ORIGIN, player.getPos())
                .add(LootContextParameters.TOOL, player.getStackInHand(player.getActiveHand())).addOptional(LootContextParameters.THIS_ENTITY, serverCommandSource.getEntity())
                .build(LootContextTypes.FISHING);

        LootTable table;
        if (world.getDimension().ultrawarm()) {
            table = world.getServer().getLootManager().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!world.getDimension().bedWorks()) {
            table = world.getServer().getLootManager().getLootTable(GoFishLootTables.END_FISHING);
        } else {
            table = world.getServer().getLootManager().getLootTable(LootTables.FISHING_GAMEPLAY);
        }

        for (int z = 0; z < times; z++) {
            List<ItemStack> list = table.generateLoot(lootContext);
            list.forEach(player::giveItemStack);
        }
    }
}
