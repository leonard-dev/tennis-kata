package fr.nexity.kata.tennis.model;

import java.util.Arrays;

public enum Player {

  PLAYER_1,
  PLAYER_2;

  public Player getOpponent() {
    return Arrays.stream(values()).filter(value -> this != value).findFirst().get();
  }

}
