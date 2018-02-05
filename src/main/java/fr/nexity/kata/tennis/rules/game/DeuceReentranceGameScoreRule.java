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

public class DeuceReentranceGameScoreRule implements ScoreRule<GameScore> {

  private final static List<PlayerGameScore> SCORE_SEQUENCE = Arrays.asList(
      new PlayerGameScore(PlayerGameSituation.ADVANTAGE),
      new PlayerGameScore(PlayerGameSituation.WIN));

  private final PlayerScoresHelper playerScoresHelper;

  @Inject
  public DeuceReentranceGameScoreRule(PlayerScoresHelper playerScoresHelper) {
    this.playerScoresHelper = playerScoresHelper;
  }

  @Override
  public boolean isActivated(GameScore score, Player pointWinner) {
    return score.getPlayerScore(pointWinner.getOpponent()).getSituation()
        == PlayerGameSituation.ADVANTAGE;
  }

  @Override
  public GameScore increment(final GameScore score, final Player player) {
    return GameScore.DEUCE;
  }
}
