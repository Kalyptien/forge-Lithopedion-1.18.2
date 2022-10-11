package com.kalyptien.lithopedion.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ChildrenVariant {
    DEFAULT(0),
    DIORITE(1),
    ANDESITE(2),
    GRANITE(3);

    private static final ChildrenVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ChildrenVariant::getId)).toArray(ChildrenVariant[]::new);
    private final int id;

    ChildrenVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ChildrenVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
