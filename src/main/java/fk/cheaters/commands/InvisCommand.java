package fk.cheaters.commands;

import com.mojang.brigadier.Command;
import fk.cheaters.AntiHack;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class InvisCommand {
  public static void register() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      dispatcher.register(CommandManager.literal("invis")
          .requires(source -> source.hasPermissionLevel(2)) // 需要OP等级2
          .executes(context -> execute(context.getSource()))
      );
    });
  }

  private static int execute(ServerCommandSource source) {
    if (source.getPlayer() != null) {
      ServerPlayerEntity player = source.getPlayer();
      if (AntiHack.invisiblePlayers.remove(player.getUuid())) {
        source.sendFeedback(() -> Text.literal("恢复到正常状态"), true);
      } else {
        AntiHack.invisiblePlayers.add(player.getUuid());
        source.sendFeedback(() -> Text.literal("进入隐身状态"), true);
      }
    } else {
      source.sendFeedback(() -> Text.literal("服务器内部发生错误"), true);
      return 0;
    }

    return Command.SINGLE_SUCCESS;
  }
}
