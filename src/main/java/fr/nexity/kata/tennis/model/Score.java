package fr.nexity.kata.tennis.model;

public interface Score {

  Player getWinner();

  default boolean hasWinner() {
    return getWinner() != null;
  }

}
