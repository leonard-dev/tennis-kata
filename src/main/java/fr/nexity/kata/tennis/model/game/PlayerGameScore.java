package fr.nexity.kata.tennis.model.game;

import com.google.common.base.Preconditions;
import fr.nexity.kata.tennis.model.PlayerScore;
import java.util.Objects;

public class PlayerGameScore implements PlayerScore {

  public final static PlayerGameScore SCORE_0 = new PlayerGameScore(0);
  public final static PlayerGameScore SCORE_15 = new PlayerGameScore(15);
  public final static PlayerGameScore SCORE_30 = new PlayerGameScore(30);
  public final static PlayerGameScore SCORE_40 = new PlayerGameScore(40);

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

  public boolean hasPoints() {
    return points != null;
  }

  public int getPoints() {
    return points;
  }

  public boolean hasSituation() {
    return situation != null;
  }

  public PlayerGameSituation getSituation() {
    return situation;
  }

  @Override
  public String getFormattedScore() {
    return hasSituation() ? getSituation().name() : "" + getPoints();
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
