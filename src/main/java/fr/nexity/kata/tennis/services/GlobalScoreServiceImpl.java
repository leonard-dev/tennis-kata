package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.GlobalScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import javax.inject.Inject;

public class GlobalScoreServiceImpl implements GlobalScoreService {

  private GameScoreService gameScoreService;
  private SetScoreService setScoreService;

  @Inject
  public GlobalScoreServiceImpl(GameScoreService gameScoreService,
      SetScoreService setScoreService) {
    this.gameScoreService = gameScoreService;
    this.setScoreService = setScoreService;
  }

  @Override
  public GlobalScore createInitialScore() {
    return GlobalScore.INITIAL;
  }

  public GlobalScore increment(final GlobalScore score, final Player player) {
    final GameScore gameScore = score.getGameScore();
    final GameScore newGameScore = gameScoreService.increment(gameScore, player);
    if (newGameScore.hasWinner()) {
      final SetScore setScore = score.getSetScore();
      final SetScore newSetScore = setScoreService.increment(setScore, player);
      return new GlobalScore(newSetScore, GameScore.INITIAL);
    } else {
      return new GlobalScore(score.getSetScore(), newGameScore);
    }
  }
}
