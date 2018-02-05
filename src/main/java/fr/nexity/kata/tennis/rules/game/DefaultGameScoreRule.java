package fr.nexity.kata.tennis.rules.game;

import fr.nexity.kata.tennis.helper.PlayerScoresHelper;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameSituation;
import fr.nexity.kata.tennis.rules.ScoreRule;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class DefaultGameScoreRule implements ScoreRule<GameScore> {

  private final static List<PlayerGameScore> SCORE_SEQUENCE = Arrays.asList(
      PlayerGameScore.SCORE_0,
      PlayerGameScore.SCORE_15,
      PlayerGameScore.SCORE_30,
      PlayerGameScore.SCORE_40,
      new PlayerGameScore(PlayerGameSituation.WIN));

  private final PlayerScoresHelper playerScoresHelper;

  @Inject
  public DefaultGameScoreRule(PlayerScoresHelper playerScoresHelper) {
    this.playerScoresHelper = playerScoresHelper;
  }

  @Override
  public boolean isActivated(GameScore score, Player pointWinner) {
    return true;
  }

  @Override
  public GameScore increment(final GameScore score, final Player player) {
    final PlayerGameScore playerScore = score.getPlayerScore(player);
    final PlayerGameScore opponentScore = score.getPlayerScore(player.getOpponent());
    final int sequenceIndex = SCORE_SEQUENCE.indexOf(playerScore);
    final PlayerGameScore newPlayerScore = SCORE_SEQUENCE.get(sequenceIndex + 1);
    return new GameScore(
        playerScoresHelper.createPlayerScoreMap(player, newPlayerScore, opponentScore));
  }
}
