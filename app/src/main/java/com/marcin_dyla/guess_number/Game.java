package com.marcin_dyla.guess_number;

import java.util.Random;

public class Game {
    private int randomNumber;
    private int AttemptsAmount;

    public Game () { }

    public void newGame(int bounds) {
        Random random = new Random();
        randomNumber = random.nextInt(bounds + 1);

        AttemptsAmount = 0;
    }

    public int check(int value) {
        AttemptsAmount += 1;

        return value - randomNumber;
    }

    public int getAttemptsAmount(){
        return AttemptsAmount;
    }
}
