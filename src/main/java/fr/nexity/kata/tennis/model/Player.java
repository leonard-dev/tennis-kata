package fr.nexity.kata.tennis.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Player {

  PLAYER_1,
  PLAYER_2;

  public Stream<Player> getOpponentStream() {
    return Arrays.stream(values()).filter(value -> this != value);
  }

}
