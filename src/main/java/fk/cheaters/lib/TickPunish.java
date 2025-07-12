package fk.cheaters.lib;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class TickPunish {
  private static int ticks = -1;
  private static ServerPlayerEntity player = null;

  public static void onTick(MinecraftServer server) {
    if (ticks >= 0) {
      ticks++;
    }

    if (ticks % 20 == 0) {
      player.damage(player.getWorld(), player.getDamageSources().outOfWorld(), 3.3f);
    }

    if (ticks == 60) {
      player.setExperienceLevel(0);
    }

    if (ticks == 100) {
      player.getInventory().clear();
    }

    if (ticks == 120) {
      player.damage(player.getWorld(), player.getDamageSources().magic(), Float.MAX_VALUE);
      player.kill(player.getWorld());
      ticks = -1;
      TickPunish.player = null;
    }
  }

  public static void punish(ServerPlayerEntity player) {
    ticks = 0;
    TickPunish.player = player;
  }
}
