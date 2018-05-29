package cz.pokus.pokus.dtos;

import cz.pokus.pokus.MineSweeper.MineField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private List<List<Character>> boardState;
    private Integer minesLeft;


}
