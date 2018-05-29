package cz.pokus.pokus.MineSweeper;


import cz.pokus.pokus.exceptions.GameWonException;
import cz.pokus.pokus.exceptions.MineExplodedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MineBoard {

    private final Integer HEIGHT;
    private final Integer WIDTH;

    public boolean isGameOver = false;
    Integer leftToReveal;
    public  Integer minesleft;

    List<List<MineField>> mineFields;




    public MineBoard(Integer w, Integer h,Integer mineCount) {
        if (h > 0 && w > 0) {
            HEIGHT = h;
            WIDTH = w;
        } else {
            HEIGHT = 10;
            WIDTH = 10;
        }


        leftToReveal = (HEIGHT) * (WIDTH) - mineCount;
        mineFields = new ArrayList<>(HEIGHT);
        minesleft = mineCount;
        for (int y = 0; y < HEIGHT; y++) {
            mineFields.add(new ArrayList<>(WIDTH));
        }

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                mineFields.get(y).add(new MineField(false, false, false, 0));
            }
        }
    }



    public void mark(Integer x, Integer y) {
        if(isGameOver) {
            return;
        }
        if(mineFields.get(y).get(x).revealed){
           return;
        }
        if (!mineFields.get(y).get(x).getMarked()) {
            mineFields.get(y).get(x).setMarked(true);
            minesleft--;
        }
        else{
            mineFields.get(y).get(x).setMarked(false);
            minesleft++;
        }

    }


    public void  addMine(Integer x, Integer y){
        mineFields.get(y).get(x).setContainsMine(true);
        //leftSide top to bottom
        Integer xCoord = x-1;
        for (int yIter = -1; yIter < 2; yIter++) {
           Integer yCoord = y+yIter;
            if(isInBound(xCoord,yCoord)){
                Integer oldMineCount=mineFields.get(yCoord).get(xCoord).getSurroundingMineCount();
                mineFields.get(yCoord).get(xCoord).setSurroundingMineCount(oldMineCount+1);
            }
        }
        //right Side top to bottom
        xCoord = x+1;
        for (int yIter = -1; yIter < 2; yIter++) {
            Integer yCoord = y + yIter;
            if(isInBound(xCoord,yCoord)) {
                Integer oldMineCount = mineFields.get(yCoord).get(xCoord).getSurroundingMineCount();
                mineFields.get(yCoord).get(xCoord).setSurroundingMineCount(oldMineCount + 1);
            }
        }
        //top
        xCoord=x;
        Integer yCoord=y-1;
        if(isInBound(xCoord,yCoord)){
            Integer oldMineCount = mineFields.get(yCoord).get(xCoord).getSurroundingMineCount();
            mineFields.get(yCoord).get(xCoord).setSurroundingMineCount(oldMineCount + 1);

        }
        //bottom
        yCoord=y+1;
        if(isInBound(xCoord,yCoord)){
            Integer oldMineCount = mineFields.get(yCoord).get(xCoord).getSurroundingMineCount();
            mineFields.get(yCoord).get(xCoord).setSurroundingMineCount(oldMineCount + 1);

        }
    }
    public Boolean isRevealValid(Integer x, Integer y){
        if(mineFields.get(y).get(x).getMarked()) {
            return false;
        }
        if(mineFields.get(y).get(x).getRevealed()) {
            return false;
        }
        return true;
    }
    public Boolean isMarkValid(Integer x,Integer y){
        if(mineFields.get(y).get(x).getRevealed()){
            return false;
        }

        return true;
    }


    public void reveal(Integer x,Integer y) throws MineExplodedException, GameWonException {
        if(isGameOver) {
            return;
        }

        if(mineFields.get(y).get(x).revealed){
            return;
        }

        mineFields.get(y).get(x).setRevealed(true);
        leftToReveal--;
        if(mineFields.get(y).get(x).getContainsMine()) {
            isGameOver=true;

            throw new MineExplodedException("Kopnul jste do miny!");
        }



        if(mineFields.get(y).get(x).getSurroundingMineCount().equals(0)) {
            Integer xCoord = x-1;
            for (int yIter = -1; yIter < 2; yIter++) {
                Integer yCoord = y+yIter;
                if(isInBound(xCoord,yCoord)){
                    reveal(xCoord,yCoord);

                    //mineFields.get(xCoord).get(yCoord).setRevealed(true);
                }
            }
            //right Side top to bottom
            xCoord = x+1;
            for (int yIter = -1; yIter < 2; yIter++) {
                Integer yCoord = y + yIter;
                if(isInBound(xCoord,yCoord)) {
                    reveal(xCoord,yCoord);
                }
            }
            //top
            xCoord=x;
            Integer yCoord=y-1;
            if(isInBound(xCoord,yCoord)){
                reveal(xCoord,yCoord);

            }
            //bottom
            yCoord=y+1;
            if(isInBound(xCoord,yCoord)){
                reveal(xCoord,yCoord);

            }

        }
        if(leftToReveal.equals(0)){
            throw new GameWonException("Vyhral jste!");
        }
    }

    private boolean isInBound(Integer x, Integer y) {
        if ((x.compareTo(0) >= 0) && (y.compareTo(0) >= 0) && (x.compareTo(WIDTH) < 0) && (y.compareTo(HEIGHT) < 0))
            return true;
        else return false;
    }

    public List<List<Character>> drawBoard()  {
        //if(revealed)
        List<List<Character>> ret = new ArrayList<>(HEIGHT);
        for (int i = 0; i < HEIGHT; i++) {
            ret.add(new ArrayList<>(WIDTH));
        }
        if (leftToReveal > 0) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    ret.get(y).add(mineFields.get(y).get(x).draw());
                }
            }
            return ret;
        }
        else{
            isGameOver=true;
            return Collections.emptyList();

        }
    }







}
