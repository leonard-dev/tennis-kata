package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.GlobalScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import javax.inject.Inject;

public class GlobalScoreServiceImpl implements GlobalScoreService {

  private GameScoreService gameScoreService;

  @Inject
  public GlobalScoreServiceImpl(GameScoreService gameScoreService) {
    this.gameScoreService = gameScoreService;
  }

  @Override
  public GlobalScore createInitialScore() {
    return GlobalScore.INITIAL;
  }

  public GlobalScore incrementPoint(final GlobalScore score, final Player player) {
    final GameScore gameScore = score.getGameScore();
    final GameScore newGameScore = gameScoreService.incrementPoint(gameScore, player);
    return new GlobalScore(newGameScore);
  }
}
