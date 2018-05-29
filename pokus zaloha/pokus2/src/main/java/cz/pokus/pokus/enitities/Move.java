package cz.pokus.pokus.enitities;

import cz.pokus.pokus.enums.MoveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "move")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column (name = "position_x")
    private Integer positionX;

    @Column (name = "position_y")
    private Integer positionY;

    @Column(name = "type")
    private MoveType type;
}