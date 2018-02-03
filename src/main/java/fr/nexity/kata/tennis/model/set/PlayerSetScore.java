package fr.nexity.kata.tennis.model.set;

import java.util.Objects;

public class PlayerSetScore {

  public final static PlayerSetScore INITIAL = new PlayerSetScore(0, false);

  private final int games;
  private final boolean isWin;

  public PlayerSetScore(final int games, final boolean isWin) {
    this.games = games;
    this.isWin = isWin;
  }

  public int getGames() {
    return games;
  }

  public boolean isWin() {
    return isWin;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerSetScore)) {
      return false;
    }
    PlayerSetScore that = (PlayerSetScore) o;
    return games == that.games &&
        isWin == that.isWin;
  }

  @Override
  public int hashCode() {

    return Objects.hash(games, isWin);
  }

  @Override
  public String toString() {
    return "PlayerSetScore{" +
        "games=" + games +
        ", isWin=" + isWin +
        '}';
  }
}
