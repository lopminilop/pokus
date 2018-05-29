package cz.pokus.pokus.services;


import cz.pokus.pokus.MineSweeper.MineBoard;
import cz.pokus.pokus.Repositories.GameRepo;
import cz.pokus.pokus.Repositories.PlayerRepo;
import cz.pokus.pokus.dtos.GameResponseDTO;
import cz.pokus.pokus.enitities.Game;
import cz.pokus.pokus.enitities.Move;
import cz.pokus.pokus.enitities.Player;
import cz.pokus.pokus.enums.MoveType;
import cz.pokus.pokus.exceptions.GameWonException;
import cz.pokus.pokus.exceptions.MineExplodedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MineService {
    @Autowired
    GameRepo gameRepo;
    PlayerRepo playerRepo;


    public List<List<Character>> drawBoard(Player defaultPlayer){
        Game currentGame = gameRepo.loadOrCreateGameForUser(defaultPlayer);
        MineBoard board = currentGame.buildMineBoard();
        return board.drawBoard();
    }

    public List<List<Character>> getBoard(Player defaultPlayer){
        Game currentGame = gameRepo.loadOrCreateGameForUser(defaultPlayer);
        MineBoard board = currentGame.buildMineBoard();
        return board.drawBoard();
    }
    public List<List<Character>> buildNewBoard(Player defaultPlayer,Integer width,Integer height){
        Game currentGame = gameRepo.CreateGameForUser(defaultPlayer,width,height);
        MineBoard board = currentGame.buildMineBoard();
        return board.drawBoard();
    }
    public List<GameResponseDTO> lastGames(Player defaultPlayer){
        List<GameResponseDTO> lastGames=gameRepo.lastGames(defaultPlayer); ;

        return lastGames;
    }

    public List<List<Character>> markBoard(Integer x, Integer y,Player defaultPlayer){
        Game currentGame = gameRepo.loadOrCreateGameForUser(defaultPlayer);
        MineBoard board = currentGame.buildMineBoard();
        if(board.isMarkValid(x,y)){
            currentGame.getMoves().add(new Move(null, x, y, MoveType.MARK));
            board.mark(x,y);
        }
        return board.drawBoard();
    }


    public List<List<Character>> revealField(Integer x, Integer y,Player defaultPlayer) throws  MineExplodedException,GameWonException {
        Game currentGame = gameRepo.loadOrCreateGameForUser(defaultPlayer);
        MineBoard board = currentGame.buildMineBoard();
        if(board.isRevealValid(x,y)){
            currentGame.getMoves().add(new Move(null, x, y, MoveType.REVEAL));
            board.reveal(x,y);
        }
        return board.drawBoard();
    }
    public Integer minesLeft(Player defaultPlayer){
        Game currentGame = gameRepo.loadOrCreateGameForUser(defaultPlayer);
        MineBoard board = currentGame.buildMineBoard();
        return board.minesleft;
    }


}
