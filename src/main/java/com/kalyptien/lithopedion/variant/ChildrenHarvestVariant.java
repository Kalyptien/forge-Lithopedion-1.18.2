package com.kalyptien.lithopedion.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ChildrenHarvestVariant {
    NONE(0),
    FABRIC(1),
    WOOD(2),
    STONE(3);

    private static final ChildrenHarvestVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ChildrenHarvestVariant::getId)).toArray(ChildrenHarvestVariant[]::new);
    private final int id;

    ChildrenHarvestVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ChildrenHarvestVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
