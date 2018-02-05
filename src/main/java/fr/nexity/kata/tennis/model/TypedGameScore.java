package fr.nexity.kata.tennis.model;

public interface TypedGameScore extends Score {

  PlayerScore getPlayerScore(Player player);

  GameType getGameType();

}
