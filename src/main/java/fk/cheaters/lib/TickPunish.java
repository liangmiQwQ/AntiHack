package fk.cheaters.lib;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class TickPunish {
  private static int ticks = -1;
  private static ServerPlayerEntity player = null;

  public static void onTick(MinecraftServer server) {
    if (ticks >= 0) {
      ticks++;
    }

    if (ticks % 20 == 0) {
      player.damage(player.getWorld(), player.getDamageSources().outOfWorld(), 3.3f);
      player.networkHandler.sendPacket(new PlaySoundS2CPacket(
          Registries.SOUND_EVENT.getEntry(SoundEvents.ENTITY_WITHER_SPAWN),
          SoundCategory.HOSTILE,                // 声音分类
          player.getX(), player.getY(), player.getZ(),
          1.0f, 1.0f, player.getWorld().random.nextLong()
      ));
      player.addStatusEffect(new StatusEffectInstance(
          StatusEffects.SLOWNESS,
          1000000,
          254
      ));

      player.addStatusEffect(new StatusEffectInstance(
          StatusEffects.MINING_FATIGUE,
          1000000,
          254
      ));
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
