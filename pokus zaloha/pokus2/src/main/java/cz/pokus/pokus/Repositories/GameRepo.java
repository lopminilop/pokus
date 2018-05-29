package cz.pokus.pokus.Repositories;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cz.pokus.pokus.dtos.GameResponseDTO;
import cz.pokus.pokus.enitities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GameRepo {

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    IGameRepo iGameRepo;

    @Transactional
    public Game loadOrCreateGameForUser(Player _player)
    {


        QGame game = QGame.game;
        QPlayer player = QPlayer.player;

        Game lastGame =

        queryFactory.selectFrom(game)
                    .where( player.id.eq(_player.getId()),
                            game.completed.isFalse(),
                            game.failed.isFalse())
                    .orderBy(game.id.desc())
                    .fetchFirst();
        if (lastGame == null){
            Game newGame = new Game(_player,10,10,12);
            newGame.addRandomMines();
            iGameRepo.save(newGame);
            return newGame;
        }
        return lastGame;
    }

    public Game CreateGameForUser(Player _player, Integer width, Integer height) {
        if (width==null){
            width=10;
        }
        if (height==null){
            height=10;
        }

        Game newGame = new Game(_player,width,height,width*height/9);
        newGame.addRandomMines();
        iGameRepo.save(newGame);
        return newGame;
    }
   public List<GameResponseDTO> lastGames(Player _player){
        QGame game = QGame.game;
        QPlayer player = QPlayer.player;


        return queryFactory
                .select(Projections.constructor(
                        GameResponseDTO.class,
                        game.date,
                        game.failed,
                        game.completed
                ))
                .from(game)
                .where( player.id.eq(_player.getId()))
                .where(game.completed.isTrue()
                            .or(
                        game.failed.isTrue())
                )

                .orderBy(game.id.desc())
                .fetch();
    }
}
