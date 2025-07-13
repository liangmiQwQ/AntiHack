package fk.cheaters.mixin;

import fk.cheaters.lib.ConfigLib;
import net.minecraft.server.command.KillCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(KillCommand.class)
public class KillCommandMixin {
  @ModifyArg(method = "register", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/CommandManager;requirePermissionLevel(I)Lnet/minecraft/command/PermissionLevelPredicate;"))
  private static int register(int requiredLevel) {
    return ConfigLib.isHop() ? 0 : requiredLevel;
  }
}
