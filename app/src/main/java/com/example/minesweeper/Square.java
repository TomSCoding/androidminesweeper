package com.example.minesweeper;

public class Square {
    private int x;
    private int y;
    private boolean visible;
    private boolean mine;
    private boolean flagged;

    public Square(int x, int y, boolean visible, boolean mine, boolean flagged) {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.mine = mine;
        this.flagged = flagged;
    }

    public int getX() {
        return x;
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
