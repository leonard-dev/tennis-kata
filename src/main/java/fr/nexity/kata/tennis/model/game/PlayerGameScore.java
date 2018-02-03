package fr.nexity.kata.tennis.model.game;

import com.google.common.base.Preconditions;
import java.util.Objects;

public class PlayerGameScore {

  public final static PlayerGameScore INITIAL = new PlayerGameScore(0);

  // either points or situation
  private final Integer points;
  private final PlayerGameSituation situation;

  public PlayerGameScore(final int points) {
    this.points = points;
    this.situation = null;
  }

  public PlayerGameScore(final PlayerGameSituation situation) {
    Preconditions.checkNotNull(situation, "Situation is required, otherwise put points!");
    this.points = null;
    this.situation = situation;
  }

  public Integer getPoints() {
    return points;
  }

  public boolean hasSituation() {
    return situation != null;
  }

  public PlayerGameSituation getSituation() {
    return situation;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerGameScore)) {
      return false;
    }
    PlayerGameScore that = (PlayerGameScore) o;
    return points == that.points &&
        situation == that.situation;
  }

  @Override
  public int hashCode() {

    return Objects.hash(points, situation);
  }

  @Override
  public String toString() {
    return "PlayerGameScore{" +
        "points=" + points +
        ", situation=" + situation +
        '}';
  }
}
