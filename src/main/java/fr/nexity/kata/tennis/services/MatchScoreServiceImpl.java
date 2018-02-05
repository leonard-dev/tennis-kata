package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.MatchScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.TypedGameScore;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import javax.inject.Inject;

public class MatchScoreServiceImpl implements MatchScoreService {

  private final TypedGameScoreService typedGameScoreService;
  private final SetScoreService setScoreService;

  @Inject
  public MatchScoreServiceImpl(TypedGameScoreService typedGameScoreService,
      SetScoreService setScoreService) {
    this.typedGameScoreService = typedGameScoreService;
    this.setScoreService = setScoreService;
  }

  @Override
  public MatchScore increment(final MatchScore score, final Player player) {
    final TypedGameScore newGameScore = incrementGameScore(score, player);
    final SetScore newSetScore = incrementSetScoreIfNeeded(score, player, newGameScore);
    final TypedGameScore finalGameScore = resetGameScoreIfNeeded(score, newGameScore, newSetScore);
    return new MatchScore(newSetScore, finalGameScore);
  }

  private TypedGameScore incrementGameScore(final MatchScore score, Player player) {
    return typedGameScoreService.increment(score.getTypedGameScore(), player);
  }

  private SetScore incrementSetScoreIfNeeded(final MatchScore score, final Player player,
      final TypedGameScore newGameScore) {
    if (newGameScore.hasWinner()) {
      return setScoreService.increment(score.getSetScore(), player);
    }
    return score.getSetScore();
  }

  private TypedGameScore resetGameScoreIfNeeded(final MatchScore score,
      final TypedGameScore newGameScore, final SetScore newSetScore) {
    if (newGameScore.hasWinner()) {
      if (newSetScore.isTiebreak()) {
        return TiebreakScore.INITIAL; // prepare the new tiebreak
      }
      if (!score.getSetScore().isTiebreak()) {
        return GameScore.INITIAL; // prepare the new game
      }
    }
    return newGameScore;
  }

}
