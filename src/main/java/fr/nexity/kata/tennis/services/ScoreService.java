package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.HasWinner;
import fr.nexity.kata.tennis.model.Player;

public interface ScoreService<T extends HasWinner> {

  T createInitialScore();

  T incrementPoint(T score, Player player);

}
