package fr.nexity.kata.tennis.services;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameSituation;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GameScoreServiceImplTest {

  @Test
  public void shouldBeOtoOGivenInitialScore() {
    // GIVEN
    GameScore initialGameScore = GameScore.INITIAL;
    // THEN
    Assertions.assertThat(initialGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(0));
    Assertions.assertThat(initialGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerGameScore(0));
  }

  @Test
  public void shouldBe15toOifPlayer1wins1Point() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(15));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerGameScore(0));
  }

  @Test
  public void shouldBe30toOifPlayer1wins2Points() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(30));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerGameScore(0));
  }

  @Test
  public void shouldBe40toOifPlayer1wins3Points() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(PlayerGameScore.SCORE_40);
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerGameScore(0));
  }

  @Test
  public void shouldHaveWinnerIfPlayer1wins4Points() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(PlayerGameSituation.WIN));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerGameScore(0));
  }

  @Test
  public void shouldThrowAnExceptionIfAlreadyAWinner() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = new GameScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerGameScore(PlayerGameSituation.WIN),
            Player.PLAYER_2, new PlayerGameScore(30)));
    // WHEN
    try {
      gameScoreService.increment(gameScore, Player.PLAYER_1);
      Assertions.fail("Should have failed");
    } catch (IllegalStateException e) {
      // THEN
      Assertions.assertThat(e.getMessage()).contains("already a winner");
    }
  }

  @Test
  public void shouldBe30to40IfPlayer1wins2PointsAndPlayer2Wins3Points() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(30));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(PlayerGameScore.SCORE_40);
  }

  @Test
  public void shouldBeDeuceIfPlayer1wins3PointsAndPlayer2Wins3Points() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = GameScore.INITIAL;
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_1);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    newGameScore = gameScoreService.increment(newGameScore, Player.PLAYER_2);
    // THEN
    Assertions.assertThat(newGameScore.isDeuce()).isTrue();
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(PlayerGameScore.SCORE_40);
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(PlayerGameScore.SCORE_40);
  }

  @Test
  public void shouldBeAdvantageIfPlayer1wins1PointFromDeuceSituation() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore deuceScore = new GameScore(
        ImmutableMap.of(
            Player.PLAYER_1, PlayerGameScore.SCORE_40,
            Player.PLAYER_2, PlayerGameScore.SCORE_40));
    // WHEN
    GameScore newGameScore = gameScoreService.increment(deuceScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.isDeuce()).isFalse();
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(PlayerGameSituation.ADVANTAGE));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(PlayerGameScore.SCORE_40);
  }

  @Test
  public void shouldBeDeuceIfPlayer1loses1PointFromAdvantageSituation() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = new GameScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerGameScore(PlayerGameSituation.ADVANTAGE),
            Player.PLAYER_2, PlayerGameScore.SCORE_40));
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_2);
    // THEN
    Assertions.assertThat(newGameScore.isDeuce()).isTrue();
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(PlayerGameScore.SCORE_40);
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(PlayerGameScore.SCORE_40);
  }

  @Test
  public void shouldBeWinIfPlayer1wins1PointFromAdvantageSituation() {
    // GIVEN
    final Injector injector = Guice.createInjector(new GameScoreServiceImplTest.TestModule());
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    GameScore gameScore = new GameScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerGameScore(PlayerGameSituation.ADVANTAGE),
            Player.PLAYER_2, PlayerGameScore.SCORE_40));
    // WHEN
    GameScore newGameScore = gameScoreService.increment(gameScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerGameScore(PlayerGameSituation.WIN));
    Assertions.assertThat(newGameScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(PlayerGameScore.SCORE_40);
  }

  public static class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(GameScoreService.class).to(GameScoreServiceImpl.class);
    }
  }

}
