package com.github.lunatrius.ingameinfo.editor.geom;

public enum Direction {
    NORTH_WEST(0, 0),
    NORTH(0, 1),
    NORTH_EAST(0, 2),
    WEST(1, 0),
    CENTER(1, 1),
    EAST(1, 2),
    SOUTH_WEST(2, 0),
    SOUTH(2, 1),
    SOUTH_EAST(2, 2);

    private final int row, col;
    private static final Direction[][] grid = new Direction[3][3];

    static {
        for (Direction d : values()) {
            grid[d.row][d.col] = d;
        }
    }

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Size scale(int factor) {
        return new Size((col - 1) * factor, (row - 1) * factor);
    }

    public static Direction get(int row, int col) {
        return grid[row][col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Direction withRow(int row) {
        return get(row, col);
    }

    public Direction withCol(int col) {
        return get(row, col);
    }

    public Direction mirrorRow() {
        return get(2 - row, col);
    }

    public Direction mirrorCol() {
        return get(row, 2 - col);
    }

    public Direction mirror() {
        return get(2 - row, 2 - col);
    }

    public boolean isNorth() {
        return row == 0;
    }

    public boolean isSouth() {
        return row == 2;
    }

    public boolean isWest() {
        return col == 0;
    }

    public boolean isEast() {
        return col == 2;
    }

    public boolean isCenter() {
        return row == 1 && col == 1;
    }

    public static Direction fromAlignment(com.github.lunatrius.ingameinfo.Alignment alignment) {
        if (alignment == null) return CENTER;
        switch (alignment) {
            case TOPLEFT: return NORTH_WEST;
            case TOPCENTER: return NORTH;
            case TOPRIGHT: return NORTH_EAST;
            case MIDDLELEFT: return WEST;
            case MIDDLECENTER: return CENTER;
            case MIDDLERIGHT: return EAST;
            case BOTTOMLEFT: return SOUTH_WEST;
            case BOTTOMCENTER: return SOUTH;
            case BOTTOMRIGHT: return SOUTH_EAST;
            default: return CENTER;
        }
    }

    public com.github.lunatrius.ingameinfo.Alignment toAlignment() {
        switch (this) {
            case NORTH_WEST: return com.github.lunatrius.ingameinfo.Alignment.TOPLEFT;
            case NORTH: return com.github.lunatrius.ingameinfo.Alignment.TOPCENTER;
            case NORTH_EAST: return com.github.lunatrius.ingameinfo.Alignment.TOPRIGHT;
            case WEST: return com.github.lunatrius.ingameinfo.Alignment.MIDDLELEFT;
            case CENTER: return com.github.lunatrius.ingameinfo.Alignment.MIDDLECENTER;
            case EAST: return com.github.lunatrius.ingameinfo.Alignment.MIDDLERIGHT;
            case SOUTH_WEST: return com.github.lunatrius.ingameinfo.Alignment.BOTTOMLEFT;
            case SOUTH: return com.github.lunatrius.ingameinfo.Alignment.BOTTOMCENTER;
            case SOUTH_EAST: return com.github.lunatrius.ingameinfo.Alignment.BOTTOMRIGHT;
        }
        return com.github.lunatrius.ingameinfo.Alignment.TOPLEFT;
    }
}
