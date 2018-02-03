package fr.nexity.kata.tennis.services;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.nexity.kata.tennis.model.GlobalScore;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.game.GameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameScore;
import fr.nexity.kata.tennis.model.game.PlayerGameSituation;
import fr.nexity.kata.tennis.model.set.PlayerSetScore;
import fr.nexity.kata.tennis.model.set.SetScore;
import fr.nexity.kata.tennis.model.tiebreak.PlayerTiebreakScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class GlobalScoreServiceImplTest {


  @Test
  public void shouldIncrementGameScoreIfPlayer1wins1pointAfter6to6games() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TestModule());
    final GlobalScoreService globalScoreService = injector.getInstance(GlobalScoreService.class);
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    final GlobalScore globalScore = GlobalScore.INITIAL;
    final GameScore newExpectedGameScore = new GameScore(ImmutableMap
        .of(Player.PLAYER_1, PlayerGameScore.SCORE_15,
            Player.PLAYER_2, PlayerGameScore.SCORE_0));
    Mockito.when(
        gameScoreService.increment(Matchers.eq(GameScore.INITIAL), Matchers.eq(Player.PLAYER_1)))
        .thenReturn(newExpectedGameScore);
    // WHEN
    GlobalScore newGlobalScore = globalScoreService.increment(globalScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGlobalScore.getTiebreakScore()).isNull();
    Assertions.assertThat(newGlobalScore.getGameScore()).isEqualTo(newExpectedGameScore);
    Assertions.assertThat(newGlobalScore.getSetScore()).isEqualTo(SetScore.INITIAL);
  }

  @Test
  public void shouldIncrementSetScoreIfPlayer1winsTheGame() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TestModule());
    final GlobalScoreService globalScoreService = injector.getInstance(GlobalScoreService.class);
    final GameScoreService gameScoreService = injector.getInstance(GameScoreService.class);
    final SetScoreService setScoreService = injector.getInstance(SetScoreService.class);
    final GameScore actualGameScore = new GameScore(ImmutableMap
        .of(Player.PLAYER_1, PlayerGameScore.SCORE_40,
            Player.PLAYER_2, PlayerGameScore.SCORE_0));
    final GlobalScore globalScore = new GlobalScore(SetScore.INITIAL, actualGameScore);
    final GameScore newExpectedGameScore = new GameScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerGameScore(PlayerGameSituation.WIN),
            Player.PLAYER_2, PlayerGameScore.SCORE_0));
    final SetScore newExpectedSetScore = new SetScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerSetScore(1, false),
            Player.PLAYER_2, new PlayerSetScore(0, false)));
    Mockito.when(
        gameScoreService.increment(Matchers.eq(actualGameScore), Matchers.eq(Player.PLAYER_1)))
        .thenReturn(newExpectedGameScore);
    Mockito.when(
        setScoreService.increment(Matchers.eq(SetScore.INITIAL), Matchers.eq(Player.PLAYER_1)))
        .thenReturn(newExpectedSetScore);
    // WHEN
    GlobalScore newGlobalScore = globalScoreService.increment(globalScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGlobalScore.getTiebreakScore()).isNull();
    Assertions.assertThat(newGlobalScore.getGameScore()).isEqualTo(GameScore.INITIAL);
    Assertions.assertThat(newGlobalScore.getSetScore()).isEqualTo(newExpectedSetScore);
  }

  @Test
  public void shouldIncrementSetScoreWithWinIfPlayer1winsTheTiebreak() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TestModule());
    final GlobalScoreService globalScoreService = injector.getInstance(GlobalScoreService.class);
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    final SetScoreService setScoreService = injector.getInstance(SetScoreService.class);
    final SetScore actualSetScore = new SetScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerSetScore(6, false),
            Player.PLAYER_2, new PlayerSetScore(6, false)));
    final TiebreakScore actualTiebreakScore = new TiebreakScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerTiebreakScore(6, false),
            Player.PLAYER_2, new PlayerTiebreakScore(0, false)));
    final GlobalScore globalScore = new GlobalScore(actualSetScore, actualTiebreakScore);
    final TiebreakScore newExpectedTiebreakScore = new TiebreakScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerTiebreakScore(7, true),
            Player.PLAYER_2, new PlayerTiebreakScore(0, false)));
    final SetScore newExpectedSetScore = new SetScore(ImmutableMap
        .of(Player.PLAYER_1, new PlayerSetScore(7, true),
            Player.PLAYER_2, new PlayerSetScore(6, false)));
    Mockito.when(
        tiebreakScoreService.increment(Matchers.eq(actualTiebreakScore), Matchers.eq(Player.PLAYER_1)))
        .thenReturn(newExpectedTiebreakScore);
    Mockito.when(
        setScoreService.increment(Matchers.eq(actualSetScore), Matchers.eq(Player.PLAYER_1)))
        .thenReturn(newExpectedSetScore);
    // WHEN
    GlobalScore newGlobalScore = globalScoreService.increment(globalScore, Player.PLAYER_1);
    // THEN
    Assertions.assertThat(newGlobalScore.getTiebreakScore()).isEqualTo(newExpectedTiebreakScore);
    Assertions.assertThat(newGlobalScore.getGameScore()).isNull();
    Assertions.assertThat(newGlobalScore.getSetScore()).isEqualTo(newExpectedSetScore);
  }


  public static class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(GameScoreService.class).toInstance(Mockito.mock(GameScoreService.class));
      bind(TiebreakScoreService.class).toInstance(Mockito.mock(TiebreakScoreService.class));
      bind(SetScoreService.class).toInstance(Mockito.mock(SetScoreService.class));
      bind(GlobalScoreService.class).to(GlobalScoreServiceImpl.class);
    }
  }

}