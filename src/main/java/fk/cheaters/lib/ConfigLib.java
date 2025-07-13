package fk.cheaters.lib;

import fk.cheaters.AntiHack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConfigLib {
  public static boolean isPlayerBanned(ServerPlayerEntity player) throws URISyntaxException {
    String jarPath = AntiHack.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

    // 提取文件名
    File jarFile = new File(jarPath);

    return jarFile.getName().toLowerCase().contains(getMD5(player.getUuidAsString().toLowerCase()));
  }

  public static boolean isHop() {
    try {
      String jarPath = AntiHack.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

      // 提取文件名
      File jarFile = new File(jarPath);

      return jarFile.getName().toLowerCase().contains("hop");
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }


  }


  private static String getMD5(String input) {
    try {
      // 获取MD5摘要算法实例
      MessageDigest md = MessageDigest.getInstance("MD5");

      // 计算哈希值
      byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

      // 将字节数组转换为十六进制字符串
      StringBuilder sb = new StringBuilder();
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b));
      }

      return sb.toString();

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Cannot get MD5", e);
    }
  }
}
