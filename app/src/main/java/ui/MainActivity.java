package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.yorg0002.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.myimagebutton.setOnClickListener(click ->
        {
           int height = variableBinding.myimagebutton.getMeasuredHeight();
           int width = variableBinding.myimagebutton.getMeasuredWidth();

            Context c = getApplicationContext();
            CharSequence text = "The height is: " + height + ", and the width is: " + width ;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(c, text, duration);
            toast.show();

            Toast.makeText(c, text, duration).show();
            });


        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());

        });

        model.editString.observe(this, s -> {
          variableBinding.textview.setText("Your edit text has " + s);

            });

        model.variable.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

            Context context = getApplicationContext();
            CharSequence text = "The value is now: " + selected;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Toast.makeText(context, text, duration).show();


        });

        variableBinding.checkBox.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.variable.postValue(isChecked);
        } );

        variableBinding.radioButton.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.variable.postValue(isChecked);
        } );

        variableBinding.switch1.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.variable.postValue(isChecked);
        } );




    }
}