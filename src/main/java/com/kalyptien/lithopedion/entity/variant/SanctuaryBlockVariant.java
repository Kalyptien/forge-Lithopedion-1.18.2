package com.kalyptien.lithopedion.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SanctuaryBlockVariant {

    SANCTUARY(0),
    STONE(1),
    STATUS(2);

    private static final SanctuaryBlockVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SanctuaryBlockVariant::getId)).toArray(SanctuaryBlockVariant[]::new);
    private final int id;

    SanctuaryBlockVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SanctuaryBlockVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
