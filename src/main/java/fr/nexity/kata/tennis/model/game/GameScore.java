package fr.nexity.kata.tennis.model.game;

import fr.nexity.kata.tennis.model.Score;
import fr.nexity.kata.tennis.model.Player;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class GameScore implements Score {

  public final static GameScore INITIAL = createInitialScore();
  public final static GameScore DEUCE = createDeuceScore();

  private final Map<Player, PlayerGameScore> gameScoreByPlayer;

  public GameScore(final Map<Player, PlayerGameScore> gameScoreByPlayer) {
    this.gameScoreByPlayer = gameScoreByPlayer;
  }

  private static GameScore createInitialScore() {
    final Map<Player, PlayerGameScore> gameScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> gameScoreByPlayer.put(player, PlayerGameScore.SCORE_0));
    return new GameScore(gameScoreByPlayer);
  }

  private static GameScore createDeuceScore() {
    final Map<Player, PlayerGameScore> gameScoreByPlayer = new EnumMap<>(Player.class);
    Arrays.stream(Player.values())
        .forEach(player -> gameScoreByPlayer.put(player, PlayerGameScore.SCORE_40));
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

  public boolean isDeuce() {
    return this.equals(DEUCE);
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
