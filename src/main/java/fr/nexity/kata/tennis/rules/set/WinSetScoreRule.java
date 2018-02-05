package fr.nexity.kata.tennis.rules.set;

import fr.nexity.kata.tennis.helper.PlayerScoresHelper;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.set.PlayerSetScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.rules.ScoreRule;
import javax.inject.Inject;

public class WinSetScoreRule implements ScoreRule<SetScore> {

  private final PlayerScoresHelper playerScoresHelper;

  @Inject
  public WinSetScoreRule(PlayerScoresHelper playerScoresHelper) {
    this.playerScoresHelper = playerScoresHelper;
  }

  @Override
  public boolean isActivated(SetScore score, Player pointWinner) {
    return isWinnerOfSet(score, pointWinner);
  }

  private boolean isWinnerOfSet(SetScore score, Player pointWinner) {
    final int newGames = score.getPlayerScore(pointWinner).getGames() + 1;
    return newGames == 7 || (newGames == 6
        && score.getPlayerScore(pointWinner.getOpponent()).getGames() <= 4);
  }

  @Override
  public SetScore increment(final SetScore score, final Player player) {
    final PlayerSetScore newPlayerScore = new PlayerSetScore(
        score.getPlayerScore(player).getGames() + 1, true);
    final PlayerSetScore opponentScore = score.getPlayerScore(player.getOpponent());
    return new SetScore(
        playerScoresHelper.createPlayerScoreMap(player, newPlayerScore, opponentScore));
  }
}
