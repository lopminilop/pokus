package cz.pokus.pokus.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkException extends Exception {
    private String message;
}
