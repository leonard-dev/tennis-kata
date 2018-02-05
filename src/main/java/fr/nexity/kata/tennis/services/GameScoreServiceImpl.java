package fr.nexity.kata.tennis.services;

import com.google.common.base.Preconditions;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.rules.ScoreResolver;
import fr.nexity.kata.tennis.rules.game.DefaultGameScoreRule;
import fr.nexity.kata.tennis.rules.game.DeuceGameScoreRule;
import fr.nexity.kata.tennis.rules.game.DeuceReentranceGameScoreRule;
import javax.inject.Inject;

public class GameScoreServiceImpl implements GameScoreService {

  private final ScoreResolver<GameScore> scoreResolver;

  @Inject
  public GameScoreServiceImpl(DefaultGameScoreRule defaultGameScoreRule,
      DeuceGameScoreRule deuceGameScoreRule,
      DeuceReentranceGameScoreRule deuceReentranceGameScoreRule) {
    this.scoreResolver = new ScoreResolver<>(deuceGameScoreRule, deuceReentranceGameScoreRule,
        defaultGameScoreRule);
  }

  @Override
  public GameScore increment(final GameScore score, final Player pointWinner) {
    Preconditions
        .checkState(!score.hasWinner(), "Cannot increment a point with already a winner of game !");
    return scoreResolver.increment(score, pointWinner);
  }

}
