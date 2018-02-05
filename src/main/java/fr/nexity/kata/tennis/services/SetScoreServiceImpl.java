package fr.nexity.kata.tennis.services;

import com.google.common.base.Preconditions;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.rules.ScoreResolver;
import fr.nexity.kata.tennis.rules.set.DefaultSetScoreRule;
import fr.nexity.kata.tennis.rules.set.WinSetScoreRule;
import javax.inject.Inject;

public class SetScoreServiceImpl implements SetScoreService {

  private final ScoreResolver<SetScore> scoreResolver;

  @Inject
  public SetScoreServiceImpl(DefaultSetScoreRule defaultSetScoreRule,
      WinSetScoreRule winSetScoreRule) {
    this.scoreResolver = new ScoreResolver<>(winSetScoreRule, defaultSetScoreRule);
  }

  @Override
  public SetScore increment(final SetScore score, final Player pointWinner) {
    Preconditions
        .checkState(!score.hasWinner(), "Cannot increment a game with already a winner of set !");
    return scoreResolver.increment(score, pointWinner);
  }

}
