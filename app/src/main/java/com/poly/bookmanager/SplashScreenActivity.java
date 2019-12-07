package com.poly.bookmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class SplashScreenActivity extends AppCompatActivity {
    private Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startAnimation();
    }
    private void startAnimation() {
        mThread = new Thread() {
            @Override
            public void run() {
                super.run();

                int waited = 0;
                while (waited < 2500) {
                    try {
                        sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class).setFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY));
                Animatoo.animateZoom(SplashScreenActivity.this);
                SplashScreenActivity.this.finish();
            }
        };
        mThread.start();
    }
}
