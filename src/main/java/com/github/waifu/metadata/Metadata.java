package com.github.waifu.metadata;

/**
 * Stores the metadata for the RPC.
 */
public class Metadata {

  /**
   * Username of the client player.
   */
  private String username;
  /**
   * Name of the class they are using.
   */
  private String classType;
  /**
   * Their current level.
   */
  private int level;
  /**
   * The world they are in (found in bottom right of the game).
   */
  private String world;
  /**
   * Time the RPC started.
   */
  private long startTime;

  /**
   * Constructs Metadata object with no information.
   */
  public Metadata() {
    this.username = "";
    this.classType = "";
    this.level = 0;
    this.world = "";
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Gets the username.
   *
   * @return username as a String.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the class name.
   *
   * @return class name as a String.
   */
  public String getClassType() {
    return classType;
  }

  /**
   * Gets the level.
   *
   * @return level as an int.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Gets the world name.
   *
   * @return world name as a String.
   */
  public String getWorld() {
    return world;
  }

  /**
   * Gets the start time.
   *
   * @return start time as a long.
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Set the username.
   *
   * @param username username of the player.
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /**
   * Set class name.
   *
   * @param classType name of the class the player is using.
   */
  public void setClassType(final String classType) {
    this.classType = classType;
  }

  /**
   * Set the level.
   *
   * @param level of the character being used.
   */
  public void setLevel(final int level) {
    this.level = level;
  }

  /**
   * Set the RPC start time.
   *
   * @param startTime start time.
   */
  public void setStartTime(final long startTime) {
    this.startTime = startTime;
  }

  /**
   * Set the world name.
   *
   * @param world world name.
   */
  public void setWorld(final String world) {
    this.world = world;
  }

}
