package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameSituation;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameScoreServiceImpl implements GameScoreService {

  private final static List<PlayerGameScore> PLAYER_GAME_SCORES_SEQUENCE = Arrays.asList(
      new PlayerGameScore(0),
      new PlayerGameScore(15),
      new PlayerGameScore(30),
      new PlayerGameScore(40),
      new PlayerGameScore(PlayerGameSituation.WIN));

  @Override
  public GameScore createInitialScore() {
    return GameScore.INITIAL;
  }

  public GameScore incrementPoint(final GameScore score, final Player player) {
    if (score.hasWinner()) {
      throw new IllegalStateException(
          "Cannot increment a game with already a winner, please reset the game before!");
    }
    final PlayerGameScore playerGameScore = score.getPlayerGameScore(player);
    final int sequenceIndex = PLAYER_GAME_SCORES_SEQUENCE.indexOf(playerGameScore);
    final PlayerGameScore newPlayerGameScore = PLAYER_GAME_SCORES_SEQUENCE.get(sequenceIndex + 1);
    return createNewScore(score, player, newPlayerGameScore);
  }

  private GameScore createNewScore(GameScore oldScore, Player player,
      PlayerGameScore newPlayerGameScore) {
    final Map<Player, PlayerGameScore> newGameScoreByPlayer = new EnumMap<>(Player.class);
    newGameScoreByPlayer.put(player, newPlayerGameScore);
    player.getOpponentStream().forEach(
        opponent -> newGameScoreByPlayer.put(opponent, oldScore.getPlayerGameScore(opponent)));
    return new GameScore(newGameScoreByPlayer);
  }
}
