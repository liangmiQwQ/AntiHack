package fk.cheaters.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fk.cheaters.AntiHack;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class InvisCommand {
  public static void register(){
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      dispatcher.register(CommandManager.literal("/invis")
          .requires(source -> source.hasPermissionLevel(2)) // 需要OP等级2
          .executes(context -> execute(context.getSource()))
      );
    });
  }

  private static int execute(ServerCommandSource source){
    source.sendFeedback(() -> Text.literal(""), true);
    return Command.SINGLE_SUCCESS;
  }
}
