package fr.nexity.kata.tennis.model;

import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import java.util.Objects;

public class GlobalScore implements HasWinner {

  public final static GlobalScore INITIAL = new GlobalScore(SetScore.INITIAL, GameScore.INITIAL);

  private final SetScore setScore;
  private final GameScore gameScore;

  public GlobalScore(final SetScore setScore, final GameScore gameScore) {
    this.setScore = setScore;
    this.gameScore = gameScore;
  }

  public SetScore getSetScore() {
    return setScore;
  }

  public GameScore getGameScore() {
    return gameScore;
  }

  @Override
  public Player getWinner() {
    return setScore.getWinner();
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
    return Objects.equals(setScore, that.setScore) &&
        Objects.equals(gameScore, that.gameScore);
  }

  @Override
  public int hashCode() {

    return Objects.hash(setScore, gameScore);
  }

  @Override
  public String toString() {
    return "GlobalScore{" +
        "setScore=" + setScore +
        ", gameScore=" + gameScore +
        '}';
  }
}
