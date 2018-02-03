package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.set.PlayerSetScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import java.util.EnumMap;
import java.util.Map;

public class SetScoreServiceImpl implements SetScoreService {


  @Override
  public SetScore createInitialScore() {
    return SetScore.INITIAL;
  }

  public SetScore increment(final SetScore score, final Player player) {
    if (score.hasWinner()) {
      throw new IllegalStateException(
          "Cannot increment a set with already a winner, please reset the set before!");
    }
    final PlayerSetScore playerSetScore = score.getPlayerSetScore(player);
    final PlayerSetScore opponentSetScore = score.getPlayerSetScore(player.getOpponent());
    final int newPlayerSetGames = playerSetScore.getGames() + 1;
    final boolean isWin =
        newPlayerSetGames == 7 || (newPlayerSetGames == 6 && opponentSetScore.getGames() <= 4);
    return createNewScore(player, new PlayerSetScore(newPlayerSetGames, isWin),
        opponentSetScore);
  }

  private SetScore createNewScore(Player player, PlayerSetScore playerSetScore,
      PlayerSetScore opponentSetScore) {
    final Map<Player, PlayerSetScore> newSetScoreByPlayer = new EnumMap<>(Player.class);
    newSetScoreByPlayer.put(player, playerSetScore);
    newSetScoreByPlayer.put(player.getOpponent(), opponentSetScore);
    return new SetScore(newSetScoreByPlayer);
  }
}
