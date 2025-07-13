package fk.cheaters;

import fk.cheaters.commands.InvisCommand;
import fk.cheaters.commands.SpectatorCommand;
import fk.cheaters.lib.TickPunish;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

public class AntiHack implements ModInitializer {
  public static final String MOD_ID = "antihack";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
  public static ArrayList<UUID> invisiblePlayers = new ArrayList<>();

  @Override
  public void onInitialize() {
    LOGGER.info("AntiHack mod is running");

    InvisCommand.register();
    SpectatorCommand.register();
    ServerTickEvents.END_SERVER_TICK.register(TickPunish::onTick);
  }
}