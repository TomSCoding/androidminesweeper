package com.example.minesweeper;

// Custom square object which is responsible for each square on the grid
public class Square {
    private int x;
    private int y;
    private boolean visible;
    private boolean mine;
    private boolean flagged;
    private int mine_number;

    public Square(int x, int y, boolean visible, boolean mine, boolean flagged, int mine_number) {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.mine = mine;
        this.flagged = flagged;
        this.mine_number = mine_number;
    }

    public int getX() {
        return x;
    }

    public int getMine_number() {
        return mine_number;
    }

    public void setMine_number(int mine_number) {
        this.mine_number = mine_number;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFlagged() {
        return flagged;
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                ", visible=" + visible +
                ", mine=" + mine +
                ", flagged=" + flagged +
                '}';
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}
