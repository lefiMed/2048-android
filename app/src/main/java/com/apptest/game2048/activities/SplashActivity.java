package com.apptest.game2048.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.apptest.game2048.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ivRotate = (ImageView) findViewById(R.id.iv_splash_rotate);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation.setFillAfter(true);
        ivRotate.startAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
                    finish();
                }
            }
        };
        thread.start();
    }
}