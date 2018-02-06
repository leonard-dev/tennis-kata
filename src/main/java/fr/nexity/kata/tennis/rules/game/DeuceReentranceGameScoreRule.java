package fr.nexity.kata.tennis.rules.game;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameSituation;
import fr.nexity.kata.tennis.rules.ScoreRule;

public class DeuceReentranceGameScoreRule implements ScoreRule<GameScore> {

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
