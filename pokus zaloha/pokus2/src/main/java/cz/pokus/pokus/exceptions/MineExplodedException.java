package cz.pokus.pokus.exceptions;

import cz.pokus.pokus.MineSweeper.MineBoard;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MineExplodedException extends Exception {
    private String message;


}
