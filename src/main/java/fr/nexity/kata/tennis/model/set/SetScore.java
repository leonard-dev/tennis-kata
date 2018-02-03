package fr.nexity.kata.tennis.model.set;

import fr.nexity.kata.tennis.model.HasWinner;
import fr.nexity.kata.tennis.model.Player;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class SetScore implements HasWinner {

  public final static SetScore INITIAL = createInitialScore();
  public final static SetScore TIEBREAK = createTiebreakScore();

  private final Map<Player, PlayerSetScore> setScoreByPlayer;

  public SetScore(final Map<Player, PlayerSetScore> setScoreByPlayer) {
    this.setScoreByPlayer = setScoreByPlayer;
  }

  private static SetScore createInitialScore() {
    final Map<Player, PlayerSetScore> setScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> setScoreByPlayer.put(player, PlayerSetScore.INITIAL));
    return new SetScore(setScoreByPlayer);
  }

  private static SetScore createTiebreakScore() {
    final Map<Player, PlayerSetScore> setScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> setScoreByPlayer.put(player, new PlayerSetScore(6, false)));
    return new SetScore(setScoreByPlayer);
  }

  public PlayerSetScore getPlayerSetScore(Player player) {
    return setScoreByPlayer.get(player);
  }

  @Override
  public Player getWinner() {
    return setScoreByPlayer.entrySet().stream()
        .filter(entry -> entry.getValue().isWin())
        .map(Entry::getKey)
        .findAny().orElse(null);
  }

  public boolean isTiebreak() {
    return this.equals(TIEBREAK);
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SetScore)) {
      return false;
    }
    SetScore setScore = (SetScore) o;
    return Objects.equals(setScoreByPlayer, setScore.setScoreByPlayer);
  }

  @Override
  public int hashCode() {

    return Objects.hash(setScoreByPlayer);
  }

  @Override
  public String toString() {
    return "SetScore{" +
        "setScoreByPlayer=" + setScoreByPlayer +
        '}';
  }
}
