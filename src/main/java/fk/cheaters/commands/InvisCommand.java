package fk.cheaters.commands;

import com.mojang.brigadier.Command;
import fk.cheaters.AntiHack;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.EnumSet;

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
    if (source.getPlayer() != null && source.getPlayer().getServer() != null) {
      ServerPlayerEntity player = source.getPlayer();
      MinecraftServer server = player.getServer();

      if (AntiHack.invisiblePlayers.remove(player.getUuid())) {
        source.sendFeedback(() -> Text.literal("恢复到正常状态"), true);
      } else {
        AntiHack.invisiblePlayers.add(player.getUuid());
        source.sendFeedback(() -> Text.literal("进入隐身状态"), true);
      }

      server.getPlayerManager().sendToAll(new PlayerListS2CPacket(
          EnumSet.of(PlayerListS2CPacket.Action.UPDATE_LISTED),
          server.getPlayerManager().players
      ));
    } else {
      source.sendFeedback(() -> Text.literal("服务器内部发生错误"), true);
      return 0;
    }

    return Command.SINGLE_SUCCESS;
  }
}
