package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.MatchScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import javax.inject.Inject;

public class MatchScoreServiceImpl implements MatchScoreService {

  private final GameScoreService gameScoreService;
  private final TiebreakScoreService tiebreakScoreService;
  private final SetScoreService setScoreService;

  @Inject
  public MatchScoreServiceImpl(GameScoreService gameScoreService,
      TiebreakScoreService tiebreakScoreService, SetScoreService setScoreService) {
    this.gameScoreService = gameScoreService;
    this.tiebreakScoreService = tiebreakScoreService;
    this.setScoreService = setScoreService;
  }

  @Override
  public MatchScore increment(final MatchScore score, final Player player) {
    final SetScore setScore = score.getSetScore();
    if (setScore.isTiebreak()) {
      final TiebreakScore tiebreakScore = score.getTiebreakScore();
      final TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, player);
      if (newTiebreakScore.hasWinner()) {
        final SetScore newSetScore = setScoreService.increment(setScore, player);
        return new MatchScore(newSetScore, newTiebreakScore); // keep the tiebreak score
      } else {
        return new MatchScore(setScore, newTiebreakScore);
      }
    } else {
      final GameScore gameScore = score.getGameScore();
      final GameScore newGameScore = gameScoreService.increment(gameScore, player);
      if (newGameScore.hasWinner()) {
        final SetScore newSetScore = setScoreService.increment(setScore, player);
        if (newSetScore.isTiebreak()) {
          return new MatchScore(newSetScore, TiebreakScore.INITIAL); // prepare the new tiebreak
        } else {
          return new MatchScore(newSetScore, GameScore.INITIAL); // prepare the new game
        }
      } else {
        return new MatchScore(setScore, newGameScore);
      }
    }
  }
}
