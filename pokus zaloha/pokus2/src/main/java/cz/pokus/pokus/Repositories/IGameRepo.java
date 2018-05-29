package cz.pokus.pokus.Repositories;

import cz.pokus.pokus.enitities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameRepo extends JpaRepository<Game, Long> {


}
