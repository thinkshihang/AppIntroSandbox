package com.github.paolorotolo.appintroexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by hangshi on 2016-04-12.
 */
public class ChangeJarIntro extends AppIntro{

    private ImageView image;

    @Override
    public void init(Bundle savedInstanceState) {

        LinearLayout LL = new LinearLayout(this);

        LL.setBackgroundColor(Color.GRAY);
        LL.setOrientation(LinearLayout.VERTICAL);

        LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        LL.setWeightSum(3f);
        LL.setLayoutParams(LLParams);

        image = new ImageView(this);
        image.setImageResource(R.drawable.onboarding_1);
        image.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2f));

        LL.addView(image);

        TextView sepratorView = (TextView)findViewById(R.id.bottom_separator);
        sepratorView.setVisibility(View.INVISIBLE);

        View viewPager = findViewById(R.id.view_pager);
        ViewGroup rl = ((ViewGroup)viewPager.getParent());
        ((ViewGroup)rl.getParent()).removeView(rl);
        rl.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        LL.addView(rl);

        setContentView(LL);


//        addSlide(SampleSlide.newInstance(R.layout.intro_layout));

        addSlide(SampleSlide.newInstance(R.layout.changejar_intro_1));
        addSlide(SampleSlide.newInstance(R.layout.changejar_intro_2));
        addSlide(SampleSlide.newInstance(R.layout.changejar_intro_3));

    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNextPressed() {
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(),
                getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

        int imageId = 0;
        switch (this.pager.getCurrentItem()) {
            case 0:
                imageId = R.drawable.onboarding_1;
                break;
            case 1:
                imageId = R.drawable.onboarding_2;
                break;
            case 2:
                imageId = R.drawable.onboarding_3;
                break;
        }

//        System.out.println(R.id.changejar_icon1);

//        ImageView image = (ImageView)findViewById(R.id.changejar_icon1);
        if (image != null) {
            image.setImageResource(imageId);
        }
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
