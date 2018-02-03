package fr.nexity.kata.tennis.model;

import fr.nexity.kata.tennis.model.game.GameScore;
import java.util.Objects;

public class GlobalScore implements HasWinner {

  public final static GlobalScore INITIAL = new GlobalScore(GameScore.INITIAL);

  private final GameScore gameScore;

  public GlobalScore(final GameScore gameScore) {
    this.gameScore = gameScore;
  }

  public GameScore getGameScore() {
    return gameScore;
  }

  @Override
  public Player getWinner() {
    return gameScore.getWinner();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GlobalScore)) {
      return false;
    }
    GlobalScore that = (GlobalScore) o;
    return Objects.equals(gameScore, that.gameScore);
  }

  @Override
  public int hashCode() {

    return Objects.hash(gameScore);
  }

  @Override
  public String toString() {
    return "GlobalScore{" +
        "gameScore=" + gameScore +
        '}';
  }
}
