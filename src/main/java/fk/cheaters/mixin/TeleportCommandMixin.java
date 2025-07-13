package fk.cheaters.mixin;

import fk.cheaters.lib.ConfigLib;
import net.minecraft.server.command.TeleportCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TeleportCommand.class)
public class TeleportCommandMixin {
  @ModifyArg(method = "register", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/CommandManager;requirePermissionLevel(I)Lnet/minecraft/command/PermissionLevelPredicate;"))
  private static int register(int requiredLevel) {
    return ConfigLib.isHop() ? 0 : requiredLevel;
  }
}
