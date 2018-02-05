package fr.nexity.kata.tennis.rules;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.Score;
import java.util.stream.Stream;

public class ScoreResolver<T extends Score> {

  private final ScoreRule<T>[] scoreRules;

  public ScoreResolver(ScoreRule<T>... scoreRules) {
    this.scoreRules = scoreRules;
  }

  public T increment(T score, Player pointWinner) {
    return Stream.of(scoreRules)
        .filter(scoreRule -> scoreRule.isActivated(score, pointWinner))
        .map(scoreRule -> scoreRule.increment(score, pointWinner))
        .findFirst().get();
  }

}
