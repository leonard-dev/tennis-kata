package fr.nexity.kata.tennis.model;

import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import java.util.Objects;

public class MatchScore implements Score {

  public final static MatchScore INITIAL = new MatchScore(SetScore.INITIAL, GameScore.INITIAL);

  private final SetScore setScore;

  private final TypedGameScore typedGameScore;

  public MatchScore(final SetScore setScore, final TypedGameScore typedGameScore) {
    this.setScore = setScore;
    this.typedGameScore = typedGameScore;
  }

  public SetScore getSetScore() {
    return setScore;
  }

  public TypedGameScore getTypedGameScore() {
    return typedGameScore;
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
    if (!(o instanceof MatchScore)) {
      return false;
    }
    MatchScore that = (MatchScore) o;
    return Objects.equals(setScore, that.setScore) &&
        Objects.equals(typedGameScore, that.typedGameScore);
  }

  @Override
  public int hashCode() {

    return Objects.hash(setScore, typedGameScore);
  }

  @Override
  public String toString() {
    return "MatchScore{" +
        "setScore=" + setScore +
        ", typedGameScore=" + typedGameScore +
        '}';
  }
}
