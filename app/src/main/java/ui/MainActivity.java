package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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



      //  variableBinding.textview.setText(model.editString);
        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
          // model.editString = variableBinding.myedittext.getText().toString();
           //variableBinding.textview.setText("Your edit text has: " + model.editString);
        });

        model.editString.observe(this, s -> {
          variableBinding.textview.setText("Your edit text has" + s);

            });

      //  TextView mytext = variableBinding.textview;
      //  EditText myedit = variableBinding.myedittext;
     //   final Button btn = variableBinding.mybutton;
        //btn.setOnClickListener(new View.OnClickListener() {
           // public void onClick(View v) {
              //  String editString = myedit.getText().toString();
                //mytext.setText( "Your edit text has: " + editString);
            //}
            //});






    }
}