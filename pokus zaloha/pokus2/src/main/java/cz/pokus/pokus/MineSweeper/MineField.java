package cz.pokus.pokus.MineSweeper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MineField {
    public Boolean revealed;
    public Boolean marked;
    private Boolean containsMine;
    private Integer surroundingMineCount;


    public Character draw() {
        if (revealed) {
            if (containsMine) {

                return '*';
            } else {
                String count = surroundingMineCount.toString();
                switch (count) {
                    case "0":
                        return ' ';
                    case "1":
                        return '1';
                    case "2":
                        return '2';
                    case "3":
                        return '3';
                    case "4":
                        return '4';
                    case "5":
                        return '5';
                    case "6":
                        return '6';
                    case "7":
                        return '7';
                    case "8":
                        return '8';
                    default: return ' ';
                }
            }
        } else {
            if (marked) {
                return 'M';
            } else {
                return 'x';
            }

        }

    }
}



