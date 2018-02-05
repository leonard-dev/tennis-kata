package fr.nexity.kata.tennis.helper;

import fr.nexity.kata.tennis.model.Player;
import java.util.EnumMap;
import java.util.Map;

public class PlayerScoresHelper {

  public <T> Map<Player, T> createPlayerScoreMap(Player player, T playerScore, T opponentScore) {
    final Map<Player, T> playerScoreMap = new EnumMap<>(Player.class);
    playerScoreMap.put(player, playerScore);
    playerScoreMap.put(player.getOpponent(), opponentScore);
    return playerScoreMap;
  }

}
