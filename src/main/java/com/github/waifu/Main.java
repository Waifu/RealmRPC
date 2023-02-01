package com.github.waifu;

import com.github.waifu.handlers.PacketHandler;
import com.github.waifu.packets.PacketType;
import com.github.waifu.packets.packetcapture.PacketProcessor;
import com.github.waifu.packets.packetcapture.register.Register;
import com.github.waifu.rpc.RichPresence;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import net.arikia.dev.drpc.DiscordRPC;

/**
 * Main class.
 */
public final class Main {

  /**
   * Unused constructor.
   */
  private Main() {

  }

  /**
   * Handles packets.
   */
  private static PacketProcessor packetProcessor;
  /**
   * Counts instances where the game is not running.
   */
  private static int counter = 0;

  /**
   * Starts the program.
   *
   * @param args any arguments passed into the program.
   */
  public static void main(final String[] args) {
    System.out.println("Waiting for Realm of the Mad God to open...");
    if (findGame()) {
      System.out.println("Found game, starting RealmRPC");
      RichPresence rpc = new RichPresence();
      startSniffer();
      rpc.start();
      RichPresence.updateRPC();

      do {
        DiscordRPC.discordRunCallbacks();
      } while (isGameRunning());

      System.out.println("Detected game closing. Shutting down RealmRPC...");
      packetProcessor.stopSniffer();
      DiscordRPC.discordShutdown();
      // initialize program again.
      main(args);
    }
  }

  /**
   * Start the sniffer to gather data.
   */
  private static void startSniffer() {
    Register.setInstance(new Register());
    Register.getInstance().register(PacketType.UPDATE, PacketHandler::handleUpdatePacket);
    Register.getInstance().register(PacketType.MAPINFO, PacketHandler::handleMapPacket);
    packetProcessor = new PacketProcessor();
    packetProcessor.start();
  }

  /**
   * Runs until the game is found open.
   *
   * @return true if game is found.
   */
  private static boolean findGame() {
    while (true) {
      if (isGameOpen()) {
        counter = 0;
        return true;
      }
    }
  }

  /**
   * Runs while the game is open.
   *
   * @return false if the game is found closed.
   */
  private static boolean isGameRunning() {
    while (true) {
      if (isGameOpen()) {
        counter = 0;
        return true;
      } else {
        counter++;
      }

      /*
        Sometimes the HWND returns null when finding window,
        check for consistent null returns before exiting.
       */
      if (counter >= 10) {
        return false;
      }
    }
  }

  /**
   * Looks for the Realm of the Mad God window title in processes.
   *
   * @return boolean if the game is currently open.
   */
  private static boolean isGameOpen() {
    final User32 user32 = User32.INSTANCE;
    WinDef.HWND hwnd  = user32.FindWindowEx(null, null, "UnityWndClass", "RotMGExalt");
    return hwnd != null;
  }
}
