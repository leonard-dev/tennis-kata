package fr.nexity.kata.tennis.model.tiebreak;

import fr.nexity.kata.tennis.model.Score;
import fr.nexity.kata.tennis.model.Player;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TiebreakScore implements Score {

  public final static TiebreakScore INITIAL = createInitialScore();

  private final Map<Player, PlayerTiebreakScore> tiebreakScoreByPlayer;

  public TiebreakScore(final Map<Player, PlayerTiebreakScore> tiebreakScoreByPlayer) {
    this.tiebreakScoreByPlayer = tiebreakScoreByPlayer;
  }

  private static TiebreakScore createInitialScore() {
    final Map<Player, PlayerTiebreakScore> tiebreakScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> tiebreakScoreByPlayer.put(player, PlayerTiebreakScore.INITIAL));
    return new TiebreakScore(tiebreakScoreByPlayer);
  }

  public PlayerTiebreakScore getPlayerTiebreakScore(Player player) {
    return tiebreakScoreByPlayer.get(player);
  }

  @Override
  public Player getWinner() {
    return tiebreakScoreByPlayer.entrySet().stream()
        .filter(entry -> entry.getValue().isWin())
        .map(Entry::getKey)
        .findAny().orElse(null);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TiebreakScore)) {
      return false;
    }
    TiebreakScore that = (TiebreakScore) o;
    return Objects.equals(tiebreakScoreByPlayer, that.tiebreakScoreByPlayer);
  }

  @Override
  public int hashCode() {

    return Objects.hash(tiebreakScoreByPlayer);
  }

  @Override
  public String toString() {
    return "TiebreakScore{" +
        "tiebreakScoreByPlayer=" + tiebreakScoreByPlayer +
        '}';
  }
}
