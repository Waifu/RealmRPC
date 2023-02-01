package com.github.waifu.handlers;

import com.github.waifu.packets.Packet;
import com.github.waifu.packets.data.StatData;
import com.github.waifu.packets.data.enums.Class;
import com.github.waifu.packets.incoming.MapInfoPacket;
import com.github.waifu.packets.incoming.UpdatePacket;
import com.github.waifu.rpc.RichPresence;
import org.w3c.dom.Document;


/**
 * To be documented.
 */
public final class PacketHandler {

  /**
   * To be documented.
   */
  private PacketHandler() {

  }

  /**
   * To be documented.
   */
  private static Document equipmentData;
  /**
   * To be documented.
   */
  private static Document classData;

  /**
   * Sets the world using the map packet.
   *
   * @param packet MapInfoPacket
   */
  public static void handleMapPacket(final Packet packet) {
    MapInfoPacket mapInfoPacket = (MapInfoPacket) packet;
    String world = mapInfoPacket.getName();

    /*
      Some world names exist such as "Guild Hall 5", etc.
     */
    if (world.contains("Guild Hall")) {
      world = "Guild Hall";
    } else if (world.contains("Pet Yard")) {
      world = "Pet Yard";
    }

    if (!RichPresence.getMetadata().getWorld().equals(world)) {
      RichPresence.getMetadata().setWorld(world);
      RichPresence.updateRPC();
    }
  }

  /**
   * To be documented.
   *
   * @param packet To be documented.
   */
  public static void handleUpdatePacket(final Packet packet) {
    final UpdatePacket updatePacket = (UpdatePacket) packet;

    for (int i = 0; i < updatePacket.getNewObjects().length; i++) {
      final StatData[] stats = updatePacket.getNewObjects()[i].getStatus().getStatData();
      boolean hasVitalityStat = false;
      String username = "";
      String classType = "";
      int level = 0;
      int boostHp = 0;
      int boostMp = 0;
      int boostAtt = 0;
      int boostDef = 0;
      int boostSpd = 0;
      int boostDex = 0;
      int boostVit = 0;
      int boostWis = 0;
      int hp = 0;
      int mp = 0;
      int att = 0;
      int def = 0;
      int spd = 0;
      int dex = 0;
      int vit = 0;
      int wis = 0;

      for (final StatData stat : stats) {
        switch (stat.getStatType()) {
          case NAME_STAT -> username = stat.getStringStatValue();
          case LEVEL_STAT -> level = stat.getStatValue();
          case PLAYER_ID -> classType = String.valueOf(updatePacket.getNewObjects()[i].getObjectType());
          case MAX_HP_BOOST_STAT -> boostHp = stat.getStatValue();
          case MAX_MP_BOOST_STAT -> boostMp = stat.getStatValue();
          case ATTACK_BOOST_STAT -> boostAtt = stat.getStatValue();
          case DEFENSE_BOOST_STAT -> boostDef = stat.getStatValue();
          case SPEED_BOOST_STAT -> boostSpd = stat.getStatValue();
          case DEXTERITY_BOOST_STAT -> boostDex = stat.getStatValue();
          case VITALITY_BOOST_STAT -> boostVit = stat.getStatValue();
          case WISDOM_BOOST_STAT -> boostWis = stat.getStatValue();
          case MAX_HP_STAT -> hp = stat.getStatValue();
          case MAX_MP_STAT -> mp = stat.getStatValue();
          case ATTACK_STAT -> att = stat.getStatValue();
          case DEFENSE_STAT -> def = stat.getStatValue();
          case SPEED_STAT -> spd = stat.getStatValue();
          case DEXTERITY_STAT -> dex = stat.getStatValue();
          case VITALITY_STAT -> {
            vit = stat.getStatValue();
            hasVitalityStat = true;
          }
          case WISDOM_STAT -> wis = stat.getStatValue();
          default -> {

          }
        }
      }

      /*
        Checks if the stats acquired are for the client.
       */
      if (hasVitalityStat) {
        int maxHp = hp - boostHp;
        int maxMp = mp - boostMp;
        int maxAtt = att - boostAtt;
        int maxDef = def - boostDef;
        int maxSpd = spd - boostSpd;
        int maxDex = dex - boostDex;
        int maxVit = vit - boostVit;
        int maxWis = wis - boostWis;
        RichPresence.getMetadata().setUsername(username);
        RichPresence.getMetadata().setLevel(level);
        RichPresence.getMetadata().setClassType(getCharacterMetadata(classType, maxHp, maxMp, maxAtt, maxDef, maxSpd, maxDex, maxVit, maxWis));
        RichPresence.updateRPC();
        break;
      }
    }

  }

  /**
   * Gets the data of the current character.
   *
   * @param classType name of the class.
   * @param maxHp max hp.
   * @param maxMp max mp.
   * @param maxAtt max att.
   * @param maxDef max def.
   * @param maxSpd max spd.
   * @param maxDex max dex.
   * @param maxVit max vit.
   * @param maxWis max wis.
   * @return string formatted as "?\8 ClassName".
   */
  public static String getCharacterMetadata(final String classType, final int maxHp, final int maxMp, final int maxAtt, final int maxDef, final int maxSpd, final int maxDex, final int maxVit, final int maxWis) {
    try {
      Class c = Class.findClass(Integer.parseInt(classType));
      int stat = Class.getMaxStatCount(c, maxHp, maxMp, maxAtt, maxDef, maxSpd, maxDex, maxVit, maxWis);
      return stat + "/8 " + c.name();
    } catch (Exception e) {
      return "";
    }
  }
}
