package fr.nexity.kata.tennis.rules;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.Score;

public interface ScoreRule<T extends Score> {

  boolean isActivated(T score, Player pointWinner);

  T increment(T score, Player pointWinner);

}
