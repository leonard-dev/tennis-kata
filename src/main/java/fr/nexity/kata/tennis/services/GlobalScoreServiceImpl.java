package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.GlobalScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import javax.inject.Inject;

public class GlobalScoreServiceImpl implements GlobalScoreService {

  private final GameScoreService gameScoreService;
  private final TiebreakScoreService tiebreakScoreService;
  private final SetScoreService setScoreService;

  @Inject
  public GlobalScoreServiceImpl(GameScoreService gameScoreService,
      TiebreakScoreService tiebreakScoreService, SetScoreService setScoreService) {
    this.gameScoreService = gameScoreService;
    this.tiebreakScoreService = tiebreakScoreService;
    this.setScoreService = setScoreService;
  }

  @Override
  public GlobalScore createInitialScore() {
    return GlobalScore.INITIAL;
  }

  public GlobalScore increment(final GlobalScore score, final Player player) {
    final SetScore setScore = score.getSetScore();
    if (setScore.isTiebreak()) {
      final TiebreakScore tiebreakScore = score.getTiebreakScore();
      final TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, player);
      if (newTiebreakScore.hasWinner()) {
        final SetScore newSetScore = setScoreService.increment(setScore, player);
        return new GlobalScore(newSetScore, newTiebreakScore); // keep the tiebreak score
      } else {
        return new GlobalScore(setScore, newTiebreakScore);
      }
    } else {
      final GameScore gameScore = score.getGameScore();
      final GameScore newGameScore = gameScoreService.increment(gameScore, player);
      if (newGameScore.hasWinner()) {
        final SetScore newSetScore = setScoreService.increment(setScore, player);
        if (newSetScore.isTiebreak()) {
          return new GlobalScore(newSetScore, TiebreakScore.INITIAL); // prepare the new tiebreak
        } else {
          return new GlobalScore(newSetScore, GameScore.INITIAL); // prepare the new game
        }
      } else {
        return new GlobalScore(setScore, newGameScore);
      }
    }
  }
}
