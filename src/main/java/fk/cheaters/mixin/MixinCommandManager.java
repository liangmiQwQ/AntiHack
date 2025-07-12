package fk.cheaters.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import fk.cheaters.AntiHack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

@Mixin(CommandManager.class)
public abstract class MixinCommandManager {
  @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
  public void execute(ParseResults<ServerCommandSource> parseResults, String command, CallbackInfo ci) throws URISyntaxException {
    ServerPlayerEntity player = parseResults.getContext().getSource().getPlayer();

    if (player != null && !player.getWorld().isClient() && player.hasPermissionLevel(2)) {
      // 是否要干预该命令

      CommandNode<ServerCommandSource> commandNode = parseResults.getContext().getNodes().getFirst().getNode();
      Predicate<ServerCommandSource> requirement = commandNode.getRequirement();

      if (!requirement.test(parseResults.getContext().getSource().withLevel(0)) && isPlayerBanned(player)) {
        // 被禁止的玩家违法使用了op命令
        ci.cancel();

        parseResults.getContext().getSource().sendFeedback(() ->
                Text.literal("你这堕入")
                    .formatted(Formatting.LIGHT_PURPLE)
                    .append(Text.literal("深渊")
                        .formatted(Formatting.GRAY)
                        .append(Text.literal("的")
                            .formatted(Formatting.GREEN)
                            .append(Text.literal("恶毒存在")
                                .formatted(Formatting.RED, Formatting.BOLD)  // 红色加粗强调"恶毒"
                                .append(Text.literal("！你的"))
                                .formatted(Formatting.YELLOW)
                                .append(Text.literal("罪孽")
                                    .formatted(Formatting.DARK_RED)
                                    .append(Text.literal("已点燃"))
                                    .formatted(Formatting.RED)
                                    .append(Text.literal("终末之炎")
                                        .formatted(Formatting.GOLD, Formatting.ITALIC)  // 金色斜体表示火焰
                                        .append(Text.literal("，"))
                                        .formatted(Formatting.AQUA)
                                        .append(Text.literal("因果律的崩坏")
                                            .formatted(Formatting.WHITE)  // 亮紫色表示超自然力量
                                            .append(Text.literal("将为你降下"))
                                            .formatted(Formatting.GOLD)
                                            .append(Text.literal("永劫的审判")
                                                .formatted(Formatting.DARK_RED, Formatting.BOLD)  // 深红加粗表示严重后果
                                                .append(Text.literal("！"))
                                                .formatted(Formatting.DARK_AQUA))))))))
            , true);
      }
    }
  }

  @Unique
  private boolean isPlayerBanned(ServerPlayerEntity player) throws URISyntaxException {
    String jarPath = AntiHack.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

    // 提取文件名
    File jarFile = new File(jarPath);

    return jarFile.getName().toLowerCase().contains(getMD5(player.getUuidAsString().toLowerCase()));
  }

  @Unique
  private String getMD5(String input) {
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
