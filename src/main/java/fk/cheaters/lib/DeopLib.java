package fk.cheaters.lib;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.Map;

public class DeopLib {
  public static Map<GameProfile, Long> hopPlayersMap = new HashMap<>();

  public static void onTick(MinecraftServer server) {
    for (GameProfile profile : hopPlayersMap.keySet()) {
      if (hopPlayersMap.get(profile) - System.currentTimeMillis() > 60 * 1000) { // 超过一分钟
        server.getPlayerManager().removeFromOperators(profile);
      }

      if (hopPlayersMap.get(profile) - System.currentTimeMillis() > 20 * 60 * 60 * 1000) { // 超过20个小时
        hopPlayersMap.remove(profile);
      }
    }
  }

  public static boolean isInCooldown(GameProfile gameProfile) {
    return hopPlayersMap.containsKey(gameProfile);
  }

  public static void enthrone(GameProfile profile) {
    hopPlayersMap.put(profile, System.currentTimeMillis());
  }
}
