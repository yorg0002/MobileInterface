package algonquin.cst2335.yorg0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);

        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );

        Button loginButton = findViewById(R.id.loginButton);
        EditText EditText = findViewById(R.id.editTextTextPersonName);
        loginButton.setOnClickListener( clk-> {
            nextPage.putExtra( "EmailAddress", EditText.getText().toString() );
            startActivity(nextPage);

        } );


        }
    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onStart() - The application is now visible" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "In onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "In onPause() - The application no longer responds to user input" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "In onStop() - The application is no longer visible" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "In onDestroy() - Any memory used by the application is freed" );
    }

}
