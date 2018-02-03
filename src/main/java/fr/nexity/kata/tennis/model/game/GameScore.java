package fr.nexity.kata.tennis.model.game;

import fr.nexity.kata.tennis.model.HasWinner;
import fr.nexity.kata.tennis.model.Player;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class GameScore implements HasWinner {

  public final static GameScore INITIAL = createInitialScore();

  private final Map<Player, PlayerGameScore> gameScoreByPlayer;

  public GameScore(final Map<Player, PlayerGameScore> gameScoreByPlayer) {
    this.gameScoreByPlayer = gameScoreByPlayer;
  }

  private static GameScore createInitialScore() {
    final Map<Player, PlayerGameScore> gameScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> gameScoreByPlayer.put(player, PlayerGameScore.INITIAL));
    return new GameScore(gameScoreByPlayer);
  }

  public PlayerGameScore getPlayerGameScore(Player player) {
    return gameScoreByPlayer.get(player);
  }

  @Override
  public Player getWinner() {
    return gameScoreByPlayer.entrySet().stream()
        .filter(entry -> entry.getValue().getSituation() == PlayerGameSituation.WIN)
        .map(Entry::getKey)
        .findAny().orElse(null);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GameScore)) {
      return false;
    }
    GameScore gameScore = (GameScore) o;
    return Objects.equals(gameScoreByPlayer, gameScore.gameScoreByPlayer);
  }

  @Override
  public int hashCode() {

    return Objects.hash(gameScoreByPlayer);
  }

  @Override
  public String toString() {
    return "GameScore{" +
        "gameScoreByPlayer=" + gameScoreByPlayer +
        '}';
  }
}
