package fr.nexity.kata.tennis.services;

import com.google.common.base.Preconditions;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import fr.nexity.kata.tennis.rules.ScoreResolver;
import fr.nexity.kata.tennis.rules.tiebreak.DefaultTiebreakScoreRule;
import fr.nexity.kata.tennis.rules.tiebreak.WinTiebreakScoreRule;
import javax.inject.Inject;

public class TiebreakScoreServiceImpl implements TiebreakScoreService {

  private final ScoreResolver<TiebreakScore> scoreResolver;

  @Inject
  public TiebreakScoreServiceImpl(DefaultTiebreakScoreRule defaultTiebreakScoreRule,
      WinTiebreakScoreRule winTiebreakScoreRule) {
    this.scoreResolver = new ScoreResolver<>(winTiebreakScoreRule, defaultTiebreakScoreRule);
  }

  @Override
  public TiebreakScore increment(final TiebreakScore score, final Player pointWinner) {
    Preconditions
        .checkState(!score.hasWinner(),
            "Cannot increment a point with already a winner of tiebreak !");
    return scoreResolver.increment(score, pointWinner);
  }

}
