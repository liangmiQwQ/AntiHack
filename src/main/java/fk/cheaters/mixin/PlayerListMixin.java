package fk.cheaters.mixin;

import fk.cheaters.AntiHack;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListS2CPacket.Entry.class)
public class PlayerListMixin {
  @Mutable
  @Shadow
  @Final
  private boolean listed;

  @Inject(method = "<init>(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
      at = @At("RETURN"))
  private void modifyListedStatus(ServerPlayerEntity player, CallbackInfo ci) {
    if (shouldHidePlayer(player)) {
      this.listed = false;
    }
  }

  @Unique
  private boolean shouldHidePlayer(ServerPlayerEntity player) {
    return AntiHack.invisiblePlayers.contains(player.getUuid());
  }
}
