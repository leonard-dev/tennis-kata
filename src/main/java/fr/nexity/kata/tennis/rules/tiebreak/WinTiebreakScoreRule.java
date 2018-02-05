package fr.nexity.kata.tennis.rules.tiebreak;

import fr.nexity.kata.tennis.helper.PlayerScoresHelper;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.tiebreak.PlayerTiebreakScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import fr.nexity.kata.tennis.rules.ScoreRule;
import javax.inject.Inject;

public class WinTiebreakScoreRule implements ScoreRule<TiebreakScore> {

  private final PlayerScoresHelper playerScoresHelper;

  @Inject
  public WinTiebreakScoreRule(PlayerScoresHelper playerScoresHelper) {
    this.playerScoresHelper = playerScoresHelper;
  }

  @Override
  public boolean isActivated(TiebreakScore score, Player pointWinner) {
    return isWinnerOfTiebreak(score, pointWinner);
  }

  private boolean isWinnerOfTiebreak(TiebreakScore score, Player pointWinner) {
    final int newPoints = score.getPlayerScore(pointWinner).getPoints() + 1;
    return newPoints >= 7
        && (newPoints - score.getPlayerScore(pointWinner.getOpponent()).getPoints() >= 2);
  }

  @Override
  public TiebreakScore increment(final TiebreakScore score, final Player player) {
    final PlayerTiebreakScore newPlayerScore = new PlayerTiebreakScore(
        score.getPlayerScore(player).getPoints() + 1, true);
    final PlayerTiebreakScore opponentScore = score.getPlayerScore(player.getOpponent());
    return new TiebreakScore(
        playerScoresHelper.createPlayerScoreMap(player, newPlayerScore, opponentScore));
  }
}
