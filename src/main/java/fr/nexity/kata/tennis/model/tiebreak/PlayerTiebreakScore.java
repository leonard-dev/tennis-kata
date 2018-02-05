package fr.nexity.kata.tennis.model.tiebreak;

import fr.nexity.kata.tennis.model.PlayerScore;
import java.util.Objects;

public class PlayerTiebreakScore implements PlayerScore {

  public final static PlayerTiebreakScore INITIAL = new PlayerTiebreakScore(0, false);

  private final int points;
  private final boolean isWin;

  public PlayerTiebreakScore(final int points, final boolean isWin) {
    this.points = points;
    this.isWin = isWin;
  }

  public int getPoints() {
    return points;
  }

  public boolean isWin() {
    return isWin;
  }

  @Override
  public String getFormattedScore() {
    return "" + getPoints();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerTiebreakScore)) {
      return false;
    }
    PlayerTiebreakScore that = (PlayerTiebreakScore) o;
    return points == that.points &&
        isWin == that.isWin;
  }

  @Override
  public int hashCode() {

    return Objects.hash(points, isWin);
  }

  @Override
  public String toString() {
    return "PlayerTiebreakScore{" +
        "points=" + points +
        ", isWin=" + isWin +
        '}';
  }
}
