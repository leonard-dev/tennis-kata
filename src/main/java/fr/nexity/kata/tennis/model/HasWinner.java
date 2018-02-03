package fr.nexity.kata.tennis.model;

public interface HasWinner {

  Player getWinner();

  default boolean hasWinner() {
    return getWinner() != null;
  }

}
