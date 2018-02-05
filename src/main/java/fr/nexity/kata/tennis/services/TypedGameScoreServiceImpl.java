package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.TypedGameScore;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import javax.inject.Inject;

public class TypedGameScoreServiceImpl implements TypedGameScoreService {

  private final GameScoreService gameScoreService;
  private final TiebreakScoreService tiebreakScoreService;

  @Inject
  public TypedGameScoreServiceImpl(GameScoreService gameScoreService,
      TiebreakScoreService tiebreakScoreService) {
    this.gameScoreService = gameScoreService;
    this.tiebreakScoreService = tiebreakScoreService;
  }

  @Override
  public TypedGameScore increment(final TypedGameScore score, final Player pointWinner) {
    switch (score.getGameType()) {
      case GAME:
        return gameScoreService.increment((GameScore) score, pointWinner);
      case TIEBREAK:
        return tiebreakScoreService.increment((TiebreakScore) score, pointWinner);
      default:
        throw new IllegalStateException();
    }
  }

}
