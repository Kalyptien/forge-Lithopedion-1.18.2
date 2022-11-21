package com.kalyptien.lithopedion.variant;

        import java.util.Arrays;
        import java.util.Comparator;

public enum SanctuaryVariant {
    NONE(0),
    FIRE(1),
    WATER(2),
    EARTH(3),
    AIR(4),
    FUNGUS(5),
    VOID(6);

    private static final SanctuaryVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SanctuaryVariant::getId)).toArray(SanctuaryVariant[]::new);
    private final int id;

    SanctuaryVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SanctuaryVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
