package com.apptest.game2048.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.apptest.game2048.backups.SaveManager;
import com.apptest.game2048.utils.MainGame;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import com.apptest.game2048.R;
import com.apptest.game2048.models.Tile;
import com.apptest.game2048.utils.MainView;

public class GameBoardActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCORE = "score";
    private static final String HIGH_SCORE = "high score temp";
    private static final String UNDO_SCORE = "undo score";
    private static final String CAN_UNDO = "can undo";
    private static final String UNDO_GRID = "undo";
    private static final String GAME_STATE = "game state";
    private static final String UNDO_GAME_STATE = "undo game state";
    private static final String MAX_TILE = "max tile";
    private static final String TIMER = "timer";

    private MainView view;
    private static String rewardedAdsID;
    public static RewardedVideoAd rewardedVideoAd;
    public static InterstitialAd mInterstitialAd;
    Thread thread;
    volatile public static boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        view = (MainView) findViewById(R.id.view);
        /*new code*/
        rewardedAdsID = getResources().getString(R.string.rewarded_video_Ad_id);

        //Admob ads
        MobileAds.initialize(this, getResources().getString(R.string.admob_appid));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Código intersticial:

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdClosed() {

                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {

                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }



        });


        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        // Bottom Toolbar
        Toolbar bottomBar = (Toolbar) findViewById(R.id.bottom_bar);
        bottomBar.inflateMenu(R.menu.bottom_menu);
        bottomBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first:
//                        Toast.makeText(MainActivity.this, "Rate this app :)", Toast.LENGTH_SHORT).show();
                        rateApp();
                        break;
                    case R.id.second:
//                        Toast.makeText(MainActivity.this, "More apps by us :)", Toast.LENGTH_SHORT).show();
                        openUrl(getString(R.string.play_store_link));
                        break;
                }
                return onOptionsItemSelected(item);
            }
        });

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        view.hasSaveState = settings.getBoolean("save_state", false);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
                //load();
            }
        }

        // Elapsed Time Counter
        thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (running) {
                                    view.setElTime();
                                }
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        thread.start();
    }

    //Interstitial Show Code -  StickerPackListActivity.ShowAd();
    public static void ShowAd(){

        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            view.game.move(2);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            view.game.move(0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            view.game.move(3);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            view.game.move(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

/*    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("hasState", true);
        save();
    }
*/
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
        ShowAd();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SaveManager.getInstance().loadBackup(view);
       // Log.i("onResume","load");
        running = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //SaveManager.getInstance().saveBackup(view.game);
        //Log.i("onPause","save");
        running = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //save();
        if(view.game.gameState == MainGame.GAME_LOST){
            view.game.newGame(false);
        }else {
            SaveManager.getInstance().saveBackup(view);
        }
        Log.i("onStop","save");
        running = false;
    }

    private void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        Tile[][] field = view.game.grid.field;
        Tile[][] undoField = view.game.grid.undoField;
        editor.putInt(WIDTH, field.length);
        editor.putInt(HEIGHT, field.length);
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    editor.putInt(xx + " " + yy, field[xx][yy].getValue());
                } else {
                    editor.putInt(xx + " " + yy, 0);
                }

                if (undoField[xx][yy] != null) {
                    editor.putInt(UNDO_GRID + xx + " " + yy, undoField[xx][yy].getValue());
                } else {
                    editor.putInt(UNDO_GRID + xx + " " + yy, 0);
                }
            }
        }
        editor.putLong(SCORE, view.game.score);
        editor.putLong(HIGH_SCORE, view.game.highScore);
        editor.putLong(UNDO_SCORE, view.game.lastScore);
        editor.putBoolean(CAN_UNDO, view.game.canUndo);
        editor.putInt(GAME_STATE, view.game.gameState);
        editor.putInt(UNDO_GAME_STATE, view.game.lastGameState);
        editor.putLong(MAX_TILE, view.game.maxTile);
        editor.putInt(TIMER, view.elTime);
        editor.commit();
    }

    private void load() {
        //Stopping all animations
        view.game.aGrid.cancelAnimations();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        for (int xx = 0; xx < view.game.grid.field.length; xx++) {
            for (int yy = 0; yy < view.game.grid.field[0].length; yy++) {
                int value = settings.getInt(xx + " " + yy, -1);
                if (value > 0) {
                    view.game.grid.field[xx][yy] = new Tile(xx, yy, value);
                } else if (value == 0) {
                    view.game.grid.field[xx][yy] = null;
                }

                int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
                if (undoValue > 0) {
                    view.game.grid.undoField[xx][yy] = new Tile(xx, yy, undoValue);
                } else if (value == 0) {
                    view.game.grid.undoField[xx][yy] = null;
                }
            }
        }

        view.game.score = settings.getLong(SCORE, view.game.score);
        view.game.highScore = settings.getLong(HIGH_SCORE, view.game.highScore);
        view.game.lastScore = settings.getLong(UNDO_SCORE, view.game.lastScore);
        view.game.canUndo = settings.getBoolean(CAN_UNDO, view.game.canUndo);
        view.game.gameState = settings.getInt(GAME_STATE, view.game.gameState);
        view.game.lastGameState = settings.getInt(UNDO_GAME_STATE, view.game.lastGameState);
        view.game.maxTile = settings.getLong(MAX_TILE, view.game.maxTile);
        view.elTime = settings.getInt(TIMER, view.elTime);
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl(getString(R.string.rate_app_link));
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public static void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd(rewardedAdsID, new AdRequest.Builder().build());
        //ca-app-pub-3325618649122260/2252006470
    }

    @Override
    public void onRewardedVideoAdLoaded() {


    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        loadRewardedVideoAd();
        Toast.makeText(GameBoardActivity.this, getString(R.string.prize),
                Toast.LENGTH_LONG).show();
                view.game.revertUndoState();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoCompleted() {

        loadRewardedVideoAd();

    }
}