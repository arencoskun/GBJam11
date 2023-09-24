package me.aren.gbjam.util;

import java.util.Random;

public class GRandom {
    private Random random;

    public GRandom() {
        random = new Random();
    }

    public int nextInt(int origin, int bound) {
        int result = random.nextInt(bound);
        while(result < origin) {
            result = random.nextInt(bound);
        }

        return result;
    }
}
