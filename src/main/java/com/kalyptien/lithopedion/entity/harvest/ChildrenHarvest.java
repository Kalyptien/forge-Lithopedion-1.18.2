package com.kalyptien.lithopedion.entity.harvest;

import java.util.Arrays;
import java.util.Comparator;

public enum ChildrenHarvest {
    NONE(0),
    FABRIC(1),
    WOOD(2),
    STONE(3);

    private static final ChildrenHarvest[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ChildrenHarvest::getId)).toArray(ChildrenHarvest[]::new);
    private final int id;

    ChildrenHarvest(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ChildrenHarvest byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
