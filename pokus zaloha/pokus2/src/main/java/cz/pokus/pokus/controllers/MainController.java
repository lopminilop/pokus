package cz.pokus.pokus.controllers;


import cz.pokus.pokus.dtos.GameResponseDTO;
import cz.pokus.pokus.dtos.IdResponseDTO;
import cz.pokus.pokus.Repositories.IPlayerRepo;
import cz.pokus.pokus.Repositories.PlayerRepo;
import cz.pokus.pokus.dtos.ResponseDTO;
import cz.pokus.pokus.enitities.Player;
import cz.pokus.pokus.exceptions.GameWonException;
import cz.pokus.pokus.exceptions.MineExplodedException;
import cz.pokus.pokus.services.LoginService;
import cz.pokus.pokus.services.MineService;
import cz.pokus.pokus.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private MineService mineService;

    @Autowired
    private IPlayerRepo iPlayerRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    LoginService loginService;

    @Autowired
    RegisterService registerService;


    @Transactional
    @RequestMapping(value = "getBoard", method = RequestMethod.GET)
    public ResponseDTO getBoard(@RequestParam Long playerId){
        Player player = playerRepo.findPlayerById(playerId);
        return new ResponseDTO("",mineService.getBoard(player),mineService.minesLeft(player));
    }

    @RequestMapping(value="buildNewBoard", method=RequestMethod.GET)
    public ResponseDTO buildNewBoard(@RequestParam Long playerId,@RequestParam Integer width,@RequestParam Integer height){
        Player player = playerRepo.findPlayerById(playerId);
        return new ResponseDTO("",mineService.buildNewBoard(player,width,height),mineService.minesLeft(player));
    }
    @RequestMapping(value="showlastgames", method=RequestMethod.GET)
    public List<GameResponseDTO> showLastGames(@RequestParam Long playerId){
        Player player = playerRepo.findPlayerById(playerId);
        return mineService.lastGames(player);

    }


    @RequestMapping(value="reveal", method = RequestMethod.PUT)
    public ResponseDTO revealField(@RequestParam Integer x, @RequestParam Integer y,@RequestParam Long playerId){
        System.out.println("Zavolano reveal s argumenty " + x +"," + y);
        Player player = playerRepo.findPlayerById(playerId);

        try {
            List<List<Character>> boardState = mineService.revealField(x, y, player);
            if(boardState.isEmpty()) {
                return new ResponseDTO("VYHRAL SI!!!", boardState,mineService.minesLeft(player));
            }
            return new ResponseDTO("", boardState,mineService.minesLeft(player));

            //drawBoard
        } catch (MineExplodedException e) {
            return new ResponseDTO(e.getMessage(), mineService.drawBoard(player),mineService.minesLeft(player));
        } catch (GameWonException e) {
            return new ResponseDTO(e.getMessage(), mineService.drawBoard(player),mineService.minesLeft(player));
        }
    }

    @RequestMapping(value="mark", method = RequestMethod.PUT)
    public ResponseDTO MarkBoard(@RequestParam Integer x, @RequestParam Integer y,@RequestParam Long playerId){

        Player player = playerRepo.findPlayerById(playerId);
        return new ResponseDTO("", mineService.markBoard(x, y, player),mineService.minesLeft(player));
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
        public IdResponseDTO registerId(@RequestParam String name,@RequestParam String password){
        return new IdResponseDTO(registerService.register(name, password));

    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public IdResponseDTO getId(@RequestParam String name,@RequestParam String password){
       return new IdResponseDTO(loginService.login(name, password));


    }





}
