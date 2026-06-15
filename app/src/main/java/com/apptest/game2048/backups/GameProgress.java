package com.apptest.game2048.backups;

import com.apptest.game2048.utils.MainView;

import java.io.Serializable;

public class GameProgress implements Serializable {

    public GameModePrefrences mode3by3;
    public GameModePrefrences mode4by4;
    public GameModePrefrences mode5by5;
    public GameModePrefrences mode6by6;

    public GameProgress() {
    }


    public void save3by3(MainView view) {
        if (mode3by3 == null) {
            mode3by3 = new GameModePrefrences(view, 3);
        } else {
            mode3by3.update(view);
        }
    }

    public void save4by4(MainView view) {
        if (mode4by4 == null) {
            mode4by4 = new GameModePrefrences(view, 4);
        } else {
            mode4by4.update(view);
        }
    }

    public void save5by5(MainView view) {
        if (mode5by5 == null) {
            mode5by5 = new GameModePrefrences(view, 5);
        } else {
            mode5by5.update(view);
        }
    }

    public void save6by6(MainView view) {
        if (mode6by6 == null) {
            mode6by6 = new GameModePrefrences(view, 6);
        } else {
            mode6by6.update(view);
        }
    }

    public void load3by3(MainView view) {
        if (mode3by3 != null)
            mode3by3.prefrencesToView(view);
    }

    public void load4by4(MainView view) {
        if (mode4by4 != null)
            mode4by4.prefrencesToView(view);
    }

    public void load5by5(MainView view) {
        if (mode5by5 != null)
            mode5by5.prefrencesToView(view);
    }

    public void load6by6(MainView view) {
        if (mode6by6 != null)
            mode6by6.prefrencesToView(view);
    }
}

