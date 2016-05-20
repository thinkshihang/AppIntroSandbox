package com.github.paolorotolo.appintroexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.paolorotolo.appintroexample.animations.ColorAnimation;
import com.github.paolorotolo.appintroexample.animations.CustomAnimation;
import com.github.paolorotolo.appintroexample.animations.DepthAnimation;
import com.github.paolorotolo.appintroexample.animations.FadeAnimation;
import com.github.paolorotolo.appintroexample.animations.FlowAnimation;
import com.github.paolorotolo.appintroexample.animations.SlideOverAnimation;
import com.github.paolorotolo.appintroexample.animations.ZoomAnimation;
import com.github.paolorotolo.appintroexample.indicators.CustomColorIndicator;
import com.github.paolorotolo.appintroexample.indicators.CustomIndicator;
import com.github.paolorotolo.appintroexample.indicators.ProgressIndicator;
import com.github.paolorotolo.appintroexample.ui.MapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // todo hang: make intro activity run only once
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = sharedPreferences.getBoolean("firstTimeRunChangeJar", true);
                if (isFirstStart) {
                    Intent intent = new Intent(MainActivity.this, ChangeJarIntro.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTimeRunChangeJar", false);

                    editor.apply();
                }
            }
        });

        t.start();

        setContentView(R.layout.activity_main);
    }

    public void showMap(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    // todo hang: sand box here
    public void changeJarIntro(View v) {
        Intent intent = new Intent(this, ChangeJarIntro.class);
        startActivity(intent);
    }

    public void launchLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void startDefaultIntro(View v){
        Intent intent = new Intent(this, DefaultIntro.class);
        startActivity(intent);
    }
    public void customBackgroundView(View v){
        Intent intent = new Intent(this, IntroWithBackground.class);
        startActivity(intent);
    }

    public void startCustomIntro(View v){
        Intent intent = new Intent(this, CustomIntro.class);
        startActivity(intent);
    }

    public void disableSwipeIntro1(View v){
        Intent intent = new Intent(this, DisableSwipeIntro1.class);
        startActivity(intent);
    }

    public void disableSwipeIntro2(View v){
        Intent intent = new Intent(this, DisableSwipeIntro2.class);
        startActivity(intent);
    }

    public void startSecondLayoutIntro(View v){
        Intent intent = new Intent(this, SecondLayoutIntro.class);
        startActivity(intent);
    }

    public void startFadeAnimation(View v){
        Intent intent = new Intent(this, FadeAnimation.class);
        startActivity(intent);
    }

    public void startZoomAnimation(View v){
        Intent intent = new Intent(this, ZoomAnimation.class);
        startActivity(intent);
    }

    public void startFlowAnimation(View v){
        Intent intent = new Intent(this, FlowAnimation.class);
        startActivity(intent);
    }

    public void startDepthAnimation(View v){
        Intent intent = new Intent(this, DepthAnimation.class);
        startActivity(intent);
    }

    public void startSlideOverAnimation(View v){
        Intent intent = new Intent(this, SlideOverAnimation.class);
        startActivity(intent);
    }

    public void startCustomAnimation(View v){
        Intent intent = new Intent(this, CustomAnimation.class);
        startActivity(intent);
    }

    public void startProgressIndicator(View v){
        Intent intent = new Intent(this, ProgressIndicator.class);
        startActivity(intent);
    }

    public void startCustomIndicator(View v){
        Intent intent = new Intent(this, CustomIndicator.class);
        startActivity(intent);
    }

    public void startCustomColorIndicator(View v){
        Intent intent = new Intent(this, CustomColorIndicator.class);
        startActivity(intent);
    }

    public void startPermissionsIntro(View view) {
        Intent intent = new Intent(this, PermissionsIntro.class);
        startActivity(intent);
    }

    public void startPermissionsIntro2(View view) {
        Intent intent = new Intent(this, PermissionsIntro2.class);
        startActivity(intent);
    }

    public void startColorAnimation(View view) {
        Intent intent = new Intent(this, ColorAnimation.class);
        startActivity(intent);
    }
}