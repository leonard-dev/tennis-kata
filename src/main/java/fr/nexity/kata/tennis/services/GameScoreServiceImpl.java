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

  private final static List<PlayerGameScore> PLAYER_GAME_SCORES_DEFAULT_SEQUENCE = Arrays.asList(
      PlayerGameScore.SCORE_0,
      PlayerGameScore.SCORE_15,
      PlayerGameScore.SCORE_30,
      PlayerGameScore.SCORE_40,
      new PlayerGameScore(PlayerGameSituation.WIN));

  @Override
  public GameScore createInitialScore() {
    return GameScore.INITIAL;
  }

  public GameScore increment(final GameScore score, final Player player) {
    if (score.hasWinner()) {
      throw new IllegalStateException(
          "Cannot increment a game with already a winner, please reset the game before!");
    }
    final PlayerGameScore playerGameScore = score.getPlayerGameScore(player);
    final PlayerGameScore opponentGameScore = score.getPlayerGameScore(player.getOpponent());
    if (score.isDeuce()) {
      return createNewScore(player, new PlayerGameScore(PlayerGameSituation.ADVANTAGE),
          opponentGameScore);
    } else if (playerGameScore.getSituation() == PlayerGameSituation.ADVANTAGE) {
      return createNewScore(player, new PlayerGameScore(PlayerGameSituation.WIN),
          opponentGameScore);
    } else if (opponentGameScore.getSituation() == PlayerGameSituation.ADVANTAGE) {
      // deuce re-entrance rule
      return createNewScore(player, PlayerGameScore.SCORE_40, PlayerGameScore.SCORE_40);
    } else {
      // default rule
      final int sequenceIndex = PLAYER_GAME_SCORES_DEFAULT_SEQUENCE.indexOf(playerGameScore);
      final PlayerGameScore newPlayerGameScore = PLAYER_GAME_SCORES_DEFAULT_SEQUENCE
          .get(sequenceIndex + 1);
      return createNewScore(player, newPlayerGameScore, opponentGameScore);
    }
  }

  private GameScore createNewScore(Player player, PlayerGameScore playerGameScore,
      PlayerGameScore opponentGameScore) {
    final Map<Player, PlayerGameScore> newGameScoreByPlayer = new EnumMap<>(Player.class);
    newGameScoreByPlayer.put(player, playerGameScore);
    newGameScoreByPlayer.put(player.getOpponent(), opponentGameScore);
    return new GameScore(newGameScoreByPlayer);
  }
}
