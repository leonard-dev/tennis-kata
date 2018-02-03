package fr.nexity.kata.tennis;

import com.google.inject.AbstractModule;
import fr.nexity.kata.tennis.services.GameScoreService;
import fr.nexity.kata.tennis.services.GameScoreServiceImpl;
import fr.nexity.kata.tennis.services.GlobalScoreService;
import fr.nexity.kata.tennis.services.GlobalScoreServiceImpl;
import fr.nexity.kata.tennis.services.SetScoreService;
import fr.nexity.kata.tennis.services.SetScoreServiceImpl;
import fr.nexity.kata.tennis.services.TiebreakScoreService;
import fr.nexity.kata.tennis.services.TiebreakScoreServiceImpl;

public class TennisKataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(GameScoreService.class).to(GameScoreServiceImpl.class);
    bind(TiebreakScoreService.class).to(TiebreakScoreServiceImpl.class);
    bind(SetScoreService.class).to(SetScoreServiceImpl.class);
    bind(GlobalScoreService.class).to(GlobalScoreServiceImpl.class);
  }
}
