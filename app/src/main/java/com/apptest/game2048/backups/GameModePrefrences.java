package com.apptest.game2048.backups;

import com.apptest.game2048.models.Tile;
import com.apptest.game2048.utils.Grid;
import com.apptest.game2048.utils.MainView;

import java.io.Serializable;

public class GameModePrefrences implements Serializable {
    private int[][] gridValue;
    private long score;
    private long hightScore;
    private long lastScore;
    private long maxTile;
    private boolean canUndo;
    private int gameState;
    private int lastGameState;
    private int elTime;

    public GameModePrefrences(MainView view, int Girdsize) {
        gridValue = new int[Girdsize][Girdsize];
        update(view);
    }

    public void update(MainView view) {
        this.score = view.game.score;
        this.hightScore = view.game.highScore;
        this.lastScore = view.game.lastScore;
        this.maxTile = view.game.maxTile;
        this.canUndo = view.game.canUndo;
        this.gameState = view.game.gameState;
        this.lastGameState = view.game.lastGameState;
        this.elTime = view.elTime;
        save(view.game.grid);
    }

    public void prefrencesToView(MainView view) {
        view.game.score = score;
        view.game.highScore = hightScore;
        view.game.lastScore = lastScore;
        view.game.maxTile = maxTile;
        view.game.canUndo = canUndo;
        view.game.gameState = gameState;
        view.game.lastGameState = lastGameState;
        view.elTime = elTime;
        load(view.game.grid);
    }

    private void save(Grid grid) {
        for (int i = 0; i < grid.field.length; i++) {
            for (int j = 0; j < grid.field.length; j++) {
                Tile tile = grid.field[i][j];
                if (tile == null) {
                    gridValue[i][j] = 0;
                } else {
                    gridValue[i][j] = tile.getValue();
                }
            }
        }
    }

    private void load(Grid grid) {
        for (int i = 0; i < grid.field.length; i++) {
            for (int j = 0; j < grid.field.length; j++) {
                int value = gridValue[i][j];
                if (value == 0) {
                    grid.field[i][j] = null;
                } else {
                    grid.field[i][j] = new Tile(i, j, value);
                }
            }
        }
    }

}
