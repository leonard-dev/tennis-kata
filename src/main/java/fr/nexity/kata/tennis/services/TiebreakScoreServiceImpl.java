package fr.nexity.kata.tennis.services;

import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.tiebreak.PlayerTiebreakScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import java.util.EnumMap;
import java.util.Map;

public class TiebreakScoreServiceImpl implements TiebreakScoreService {

  @Override
  public TiebreakScore increment(final TiebreakScore score, final Player player) {
    if (score.hasWinner()) {
      throw new IllegalStateException(
          "Cannot increment a tiebreak with already a winner !");
    }
    final PlayerTiebreakScore playerTiebreakScore = score.getPlayerTiebreakScore(player);
    final PlayerTiebreakScore opponentTiebreakScore = score
        .getPlayerTiebreakScore(player.getOpponent());
    final int newPlayerTiebreakPoints = playerTiebreakScore.getPoints() + 1;
    final boolean isWin =
        newPlayerTiebreakPoints >= 7
            && (newPlayerTiebreakPoints - opponentTiebreakScore.getPoints() >= 2);
    return createNewScore(player, new PlayerTiebreakScore(newPlayerTiebreakPoints, isWin),
        opponentTiebreakScore);
  }

  private TiebreakScore createNewScore(Player player, PlayerTiebreakScore playerTiebreakScore,
      PlayerTiebreakScore opponentTiebreakScore) {
    final Map<Player, PlayerTiebreakScore> newTiebreakScoreByPlayer = new EnumMap<>(Player.class);
    newTiebreakScoreByPlayer.put(player, playerTiebreakScore);
    newTiebreakScoreByPlayer.put(player.getOpponent(), opponentTiebreakScore);
    return new TiebreakScore(newTiebreakScoreByPlayer);
  }
}
