package cz.pokus.pokus.enitities;


import cz.pokus.pokus.MineSweeper.MineBoard;
import cz.pokus.pokus.Repositories.IGameRepo;
import cz.pokus.pokus.enums.MoveType;
import cz.pokus.pokus.exceptions.GameWonException;
import cz.pokus.pokus.exceptions.MineExplodedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "game")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    Player player;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "minecount")
    private Integer mineCount;

    @Column(name="failed")
    private Boolean failed;

    @Column(name="completed")
    private Boolean completed;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mines")
    List<Mine> mines = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "moves")
    List<Move> moves = new ArrayList<>();

    @Column(name ="date")
    LocalDate date;


    public Game(Player player, Integer w, Integer h, Integer minesCount){
        this.player = player;
        this.width = w;
        this.height = h;
        this.mineCount = minesCount;
        this.completed = false;
        this.failed = false;
        this.date = LocalDate.now();
        this.name = "Automaticky vytvorena hra";
        this.moves = new ArrayList<>();
        this.mines = new ArrayList<>();
    }













    public void addRandomMines(){
        for (int i = 0; i < mineCount; i++) {

            Integer y = ThreadLocalRandom.current().nextInt(0, height);
            Integer x = ThreadLocalRandom.current().nextInt(0, width);

            Mine newMine = new Mine(null,x,y);

            if (mines.contains(newMine)) {
                i--;
                continue;
            }
            else{
                mines.add(newMine);
            }
        }
    }


    public MineBoard buildMineBoard() {
        MineBoard board = new MineBoard(width, height, mineCount);
        for (Mine mine: mines) {
            board.addMine(mine.getPositionX(), mine.getPositionY());
        }
        for (Move move : moves) {
            if(completed || failed){
                break;
            }
            if(move.getType().equals(MoveType.MARK)){
                board.mark(move.getPositionX(),move.getPositionY());
            }
            if(move.getType().equals(MoveType.REVEAL)){
                try {
                    board.reveal(move.getPositionX(),move.getPositionY());
                } catch (MineExplodedException e) {
                    this.failed = true;
                    break;
                } catch (GameWonException e) {
                    this.completed = true;
                    break;
                }
            }
        }
        return board;
    }
}
