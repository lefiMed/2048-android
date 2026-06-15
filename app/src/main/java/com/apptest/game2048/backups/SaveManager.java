package com.apptest.game2048.backups;

import android.annotation.SuppressLint;
import android.content.Context;

import com.apptest.game2048.utils.MainGame;
import com.apptest.game2048.utils.MainView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveManager {

    private static final String FILE_NAME = "backup";
    @SuppressLint("StaticFieldLeak")
    private static SaveManager instance;
    private Context context;
    private GameProgress gameProgress;

    public SaveManager(Context context) {
        this.context = context;
        gameProgress = loadFromFile();
        if (instance == null) {
            instance = this;
        }
    }

    public static void build(Context context) {
        instance = new SaveManager(context);
    }

    public static SaveManager getInstance() {
        return instance;
    }

    public GameProgress getGameProgress() {
        return gameProgress;
    }

    public void loadBackup(MainView view) {
        gameProgress = loadFromFile();
        if (view != null && gameProgress != null) {
            switch (MainGame.numSquaresX) {
                case 3:
                    gameProgress.load3by3(view);
                    break;
                case 4:
                    gameProgress.load4by4(view);
                    break;
                case 5:
                    gameProgress.load5by5(view);
                    break;
                case 6:
                    gameProgress.load6by6(view);
                    break;
                default:
                    break;
            }
        }
    }

    public void saveBackup(MainView view) {
        if (gameProgress == null) {
            gameProgress = new GameProgress();
        }
        switch (MainGame.numSquaresX) {
            case 3:
                gameProgress.save3by3(view);
                break;
            case 4:
                gameProgress.save4by4(view);
                break;
            case 5:
                gameProgress.save5by5(view);
                break;
            case 6:
                gameProgress.save6by6(view);
                break;
            default:
                break;
        }
        saveInFile();
    }

    private void saveInFile() {
        File file = new File(context.getCacheDir() + "/" + File.separator + "save");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(gameProgress);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GameProgress loadFromFile() {
        File file = new File(context.getCacheDir() + "/" + File.separator + "save");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream fis = null;
        GameProgress gameProgress = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            gameProgress = (GameProgress) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

}
