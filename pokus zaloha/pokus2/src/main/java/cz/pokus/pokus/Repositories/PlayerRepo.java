package cz.pokus.pokus.Repositories;


import com.querydsl.jpa.impl.JPAQueryFactory;
import cz.pokus.pokus.enitities.Player;
import cz.pokus.pokus.enitities.QPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepo {
    @Autowired
    JPAQueryFactory queryFactory;

    public Player findPlayerById(Long id){
        QPlayer player = QPlayer.player;
        return queryFactory .selectFrom(player)
                            .where(player.id.eq(id))
                            .fetchOne();
    }

    public Player findPlayerByName(String name){
        QPlayer player=QPlayer.player;
        return queryFactory .selectFrom(player)
                .where(player.name.eq(name))
                .fetchOne();
    }

}
