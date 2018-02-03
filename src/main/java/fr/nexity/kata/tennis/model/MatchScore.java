package fr.nexity.kata.tennis.model;

import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import java.util.Objects;

public class MatchScore implements Score {

  public final static MatchScore INITIAL = new MatchScore(SetScore.INITIAL, GameScore.INITIAL);

  private final SetScore setScore;

  // either game or tiebreak
  private final TiebreakScore tiebreakScore;
  private final GameScore gameScore;

  public MatchScore(final SetScore setScore, final GameScore gameScore) {
    this.setScore = setScore;
    this.tiebreakScore = null;
    this.gameScore = gameScore;
  }

  public MatchScore(final SetScore setScore, final TiebreakScore tiebreakScore) {
    this.setScore = setScore;
    this.tiebreakScore = tiebreakScore;
    this.gameScore = null;
  }

  public boolean hasTiebreakScore() {
    return tiebreakScore != null;
  }

  public SetScore getSetScore() {
    return setScore;
  }

  public TiebreakScore getTiebreakScore() {
    return tiebreakScore;
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
    if (!(o instanceof MatchScore)) {
      return false;
    }
    MatchScore that = (MatchScore) o;
    return Objects.equals(setScore, that.setScore) &&
        Objects.equals(tiebreakScore, that.tiebreakScore) &&
        Objects.equals(gameScore, that.gameScore);
  }

  @Override
  public int hashCode() {

    return Objects.hash(setScore, tiebreakScore, gameScore);
  }

  @Override
  public String toString() {
    return "MatchScore{" +
        "setScore=" + setScore +
        ", tiebreakScore=" + tiebreakScore +
        ", gameScore=" + gameScore +
        '}';
  }
}
