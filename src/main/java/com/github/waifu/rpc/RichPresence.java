package com.github.waifu.rpc;

import com.github.waifu.metadata.Metadata;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

/**
 * Discord RichPresence class.
 */
public class RichPresence {

  /**
   * Stores info to send to the RichPresence.
   */
  private static Metadata metadata;

  /**
   * Unused constructor.
   */
  public RichPresence() {
    metadata = new Metadata();
    metadata.setStartTime(System.currentTimeMillis());
  }

  /**
   * Connects the application to discord and creates the RichPresence.
   */
  public void start() {
    final String appId = "1062901123301777508";
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
      System.out.println("Connected to discord: " + user.username + "#" + user.discriminator);
    }).build();
    DiscordRPC.discordInitialize(appId, handlers, true);
  }

  /**
   * Updates the RPC with data.
   */
  public static void updateRPC() {
    String username = metadata.getUsername();
    DiscordRichPresence rich;

    /*
      Allows seamless loading of data as packets don't update at the same time.
     */
    if (metadata.getWorld().equals("") || metadata.getClassType().equals("")) {
      rich = new DiscordRichPresence.Builder("")
              .setStartTimestamps(metadata.getStartTime())
              .setDetails("Awaiting data...")
              .setBigImage("icon", "")
              .build();
    } else {
      rich = new DiscordRichPresence.Builder("Exploring " + metadata.getWorld())
              .setStartTimestamps(metadata.getStartTime())
              .setDetails("Playing as " + metadata.getClassType() + ", Level " + metadata.getLevel())
              .setBigImage("icon", "IGN: " + username)
              .build();
    }
    DiscordRPC.discordUpdatePresence(rich);
  }

  /**
   * Gets metadata object.
   *
   * @return metadata.
   */
  public static Metadata getMetadata() {
    return metadata;
  }
}
