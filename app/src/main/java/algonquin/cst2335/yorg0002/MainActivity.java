package algonquin.cst2335.yorg0002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ImageView imgView;
            Switch sw;

            imgView = findViewById(R.id.imageView);
            sw = findViewById(R.id.spin_switch);

            sw.setOnCheckedChangeListener( (btn, isChecked) -> {
                if (isChecked)
                {
                    RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(5000);
                    rotate.setRepeatCount(Animation.INFINITE);
                    rotate.setInterpolator(new LinearInterpolator());

                    imgView.startAnimation(rotate);
                }
                else {
                    imgView.clearAnimation();
                }
            });
        }
    }
