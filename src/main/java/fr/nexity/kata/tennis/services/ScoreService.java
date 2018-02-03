package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.Score;
import fr.nexity.kata.tennis.model.Player;

public interface ScoreService<T extends Score> {

  T increment(T score, Player player);

}
