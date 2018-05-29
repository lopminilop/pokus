package cz.pokus.pokus.Repositories;

import cz.pokus.pokus.enitities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerRepo extends JpaRepository<Player, Long> {
}
