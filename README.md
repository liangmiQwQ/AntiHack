# AntiHack

## 功能

### 软禁OP玩家

检测该玩家使用任何除了`/tp`, `/kill`以外的op命令后, 不会产生效果, 并且将会延迟5秒, 将其/clear

### 监视玩家

`/invis`命令, 使自己隐身, 无法在tab中看到, 和旁观者模式搭配使用则可达到隐身效果

再次使用该命令或重启服务器, 即可取消该效果

### 全员半op

开放tp玩家和kill自己,切换旁观的功能

`tp`和`kill`保持原有命令名称和调用方法
`/gamemode spectator` 修改对应命名为 `/spectator`

依然保留 `/getop` 命令 给自己一个时长为1分钟的op 冷却时间20小时

## 使用方法

### 软禁OP玩家

放在mods文件夹里, 然后文件名包含你要软禁的人的UUID(小写, 带`-`符)的md5(32位, 大小写随意)

如我需要软禁`Bocchi_awa`

通过[UUID查询](https://mcuuid.net)或 Minecraft Api, 获取到该账户的UUID是:

```text
28679615-328f-47bf-ae66-e7419eb702c3
```

随后通过MD5, 可以获取到该段数据的 MD5 是

```text
a467d0ac5bad19c545370bfc99184cf6
```

最后 你可以将该jar下载 并且重命名为任意文字 只需要其中包含以上生成的MD5字符串

例如

```text
antihack-fabric-v1.0.0-a467d0ac5bad19c545370bfc99184cf6.jar
```

提示: 在文件名中不包含任何md5字符串则可以禁用该功能

### 监视玩家

`/invis`命令让自己从tab栏中移除, 该命令需要op才能使用, hop开启后所有玩家均可使用

## 全员半op

在jar名称中添加 `hop` 即可启用该功能

例如

```text
antihack-fabric-v1.0.0-hop.jar
```

## 许可

使用该程序, 需要阅读并且遵守 Apache-2.0 许可证。遵守该许可证即可自由访问代码。
