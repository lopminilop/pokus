package cz.pokus.pokus.dtos;

import cz.pokus.pokus.enitities.Game;
import cz.pokus.pokus.enums.GameStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;



@Data
@AllArgsConstructor
public class GameResponseDTO {
    public GameResponseDTO(LocalDate date, boolean completed, boolean failed){
        this.date = date;
        if ( !completed && ! failed ){
            state = GameStateEnum.UNFINISHED;
        }

        if ( completed && ! failed){
            state = GameStateEnum.LOST;
        }

        if ( !completed && failed){
            state = GameStateEnum.WON;
        }

        if ( completed && failed){
            state = GameStateEnum.INVALID;
        }
    }
    private LocalDate date;
    private GameStateEnum state;
}