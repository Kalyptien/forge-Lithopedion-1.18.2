package com.kalyptien.lithopedion.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SoldierVariant {
    STONE(0),
    DIORITE(1),
    ANDESITE(2),
    GRANITE(3);

    private static final SoldierVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SoldierVariant::getId)).toArray(SoldierVariant[]::new);
    private final int id;

    SoldierVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SoldierVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
