package fr.nexity.kata.tennis.services;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.nexity.kata.tennis.model.Player;
import fr.nexity.kata.tennis.model.tiebreak.PlayerTiebreakScore;
import fr.nexity.kata.tennis.model.tiebreak.TiebreakScore;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TiebreakScoreServiceImplTest {

  @Test
  public void shouldBeOtoOGivenInitialScore() {
    // GIVEN
    TiebreakScore initialTiebreakScore = TiebreakScore.INITIAL;
    // THEN
    Assertions.assertThat(initialTiebreakScore).isSameAs(TiebreakScore.INITIAL);
    Assertions.assertThat(initialTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(0, false));
    Assertions.assertThat(initialTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(0, false));
  }

  @Test
  public void shouldBe1to0WithoutWinIfPlayer1wins1point() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = TiebreakScore.INITIAL;
    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(1, false));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(0, false));
  }

  @Test
  public void shouldBe6to5WithoutWinIfPlayer1winsLastPointFrom5to5() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(5, false),
            Player.PLAYER_2, new PlayerTiebreakScore(5, false)));

    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isFalse();
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(6, false));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(5, false));
  }

  @Test
  public void shouldBe7to5WithWinIfPlayer1winsLastPointFrom6to5() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(6, false),
            Player.PLAYER_2, new PlayerTiebreakScore(5, false)));

    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isTrue();
    Assertions.assertThat(newTiebreakScore.getWinner()).isEqualTo(Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(7, true));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(5, false));
  }

  @Test
  public void shouldBe7to6WithoutWinIfPlayer1winsLastGameFrom6to6() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(6, false),
            Player.PLAYER_2, new PlayerTiebreakScore(6, false)));

    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isFalse();
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(7, false));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(6, false));
  }

  @Test
  public void shouldBe7to7WithoutWinIfPlayer1winsLastPointFrom6to7() {
    // GIVEN
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(6, false),
            Player.PLAYER_2, new PlayerTiebreakScore(7, false)));

    // WHEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isFalse();
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(7, false));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(7, false));
  }

  @Test
  public void shouldBe8to7WithoutWinIfPlayer1winsLastPointFrom7to7() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(7, false),
            Player.PLAYER_2, new PlayerTiebreakScore(7, false)));

    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isFalse();
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(8, false));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(7, false));
  }

  @Test
  public void shouldBe9to7WithWinIfPlayer1winsLastPointFrom8to7() {
    // GIVEN
    final Injector injector = Guice.createInjector(new TiebreakScoreServiceImplTest.TestModule());
    final TiebreakScoreService tiebreakScoreService = injector
        .getInstance(TiebreakScoreService.class);
    TiebreakScore tiebreakScore = new TiebreakScore(
        ImmutableMap.of(
            Player.PLAYER_1, new PlayerTiebreakScore(8, false),
            Player.PLAYER_2, new PlayerTiebreakScore(7, false)));

    // WHEN
    TiebreakScore newTiebreakScore = tiebreakScoreService.increment(tiebreakScore, Player.PLAYER_1);
    Assertions.assertThat(newTiebreakScore.hasWinner()).isTrue();
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_1))
        .isEqualTo(new PlayerTiebreakScore(9, true));
    Assertions.assertThat(newTiebreakScore.getPlayerScore(Player.PLAYER_2))
        .isEqualTo(new PlayerTiebreakScore(7, false));
  }

  public static class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(TiebreakScoreService.class).to(TiebreakScoreServiceImpl.class);
    }
  }


}
