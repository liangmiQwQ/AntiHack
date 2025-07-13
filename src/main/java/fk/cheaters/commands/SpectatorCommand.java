package fk.cheaters.commands;

import fk.cheaters.lib.ConfigLib;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.GameMode;

public class SpectatorCommand {
  public static void register() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      dispatcher.register(CommandManager.literal("spectator")
          .requires(source -> ConfigLib.isHop())
          .executes(context -> execute(context.getSource()))
      );
    });
  }

  private static int execute(ServerCommandSource source) {
    if (source.getPlayer() != null && source.getPlayer().getGameMode() != GameMode.SPECTATOR) {
      source.getPlayer().changeGameMode(GameMode.SPECTATOR);
    } else {
      source.getPlayer().changeGameMode(GameMode.SURVIVAL);
    }
    return 1;
  }
}
