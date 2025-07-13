package fk.cheaters.commands;

import fk.cheaters.lib.ConfigLib;
import fk.cheaters.lib.DeopLib;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class GetOpCommand {
  public static void register() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      dispatcher.register(CommandManager.literal("spectator")
          .requires(source -> ConfigLib.isHop() && !source.hasPermissionLevel(2))
          .executes(context -> execute(context.getSource()))
      );
    });
  }

  private static int execute(ServerCommandSource source) {
    if (source.getPlayer() != null && !DeopLib.isInCooldown(source.getPlayer().getGameProfile())) {
      source.sendFeedback(() -> Text.literal("你现在有1分钟的op权限"), true);
      source.getServer().getPlayerManager().addToOperators(source.getPlayer().getGameProfile());
      DeopLib.enthrone(source.getPlayer().getGameProfile());
    }

    return 1;
  }
}
