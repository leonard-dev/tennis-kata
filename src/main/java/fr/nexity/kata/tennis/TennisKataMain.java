package fr.nexity.kata.tennis;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.nexity.kata.tennis.model.MatchScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.PlayerGameScore;
import fr.nexity.kata.tennis.model.set.PlayerSetScore;
import fr.nexity.kata.tennis.model.tiebreak.PlayerTiebreakScore;
import fr.nexity.kata.tennis.services.MatchScoreService;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Hello world!
 */
public class TennisKataMain {

  private final static String SCORE_FORMAT = "| %1$10s | %2$10s | %3$10s |";

  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new TennisKataModule());
    final MatchScoreService matchScoreService = injector.getInstance(MatchScoreService.class);
    final Scanner console = new Scanner(System.in);
    MatchScore score = MatchScore.INITIAL;
    printScore(score);
    while (!score.hasWinner()) {
      Player player = null;
      do {
        printPrompt();
        final String playerString = console.next();
        if (playerString.matches("[12]")) {
          player = Player.valueOf("PLAYER_" + playerString);
          score = matchScoreService.increment(score, player);
        } else if (playerString.matches("(exit|quit)")) {
          System.exit(0);
        }
      } while (player == null);
      printScore(score);
    }
    printWinner(score.getWinner());
  }

  private final static void printScore(MatchScore score) {
    System.out.println(String.format(SCORE_FORMAT, "", "Points", "Games"));
    Arrays.stream(Player.values()).forEach(player ->
        System.out.println(
            String.format(SCORE_FORMAT,
                player.name(),
                formatGameOrTiebreak(score, player),
                formatSetScore(score.getSetScore().getPlayerSetScore(player)))));

  }

  private final static String formatGameOrTiebreak(MatchScore score, Player player) {
    if (score.hasTiebreakScore()) {
      return formatGameTiebreak(score.getTiebreakScore().getPlayerTiebreakScore(player));
    } else {
      return formatGameScore(score.getGameScore().getPlayerGameScore(player));
    }
  }

  private final static String formatGameScore(PlayerGameScore playerGameScore) {
    return playerGameScore.hasSituation() ? playerGameScore.getSituation().name()
        : "" + playerGameScore.getPoints();
  }

  private final static String formatGameTiebreak(PlayerTiebreakScore playerTiebreakScore) {
    return "" + playerTiebreakScore.getPoints();
  }

  private final static String formatSetScore(PlayerSetScore playerSetScore) {
    return "" + playerSetScore.getGames();
  }

  private final static void printPrompt() {
    System.out.print("Which player has won the point (1 or 2) ? ");
  }

  private final static void printWinner(Player player) {
    System.out.println(String.format("The winner is %s !", player.name()));
  }

}
