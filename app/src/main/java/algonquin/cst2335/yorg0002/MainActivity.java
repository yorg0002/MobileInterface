package algonquin.cst2335.yorg0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.yorg0002.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//    ImageView imgView;
//    Switch sw;
//    Button loginButton;
//    EditText emailEditText, passwordEditText;
//    String emailAddress;


    protected String cityName;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // RequestQueue queue = null;
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);

        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

     //   RequestQueue finalQueue = queue;
        binding.getForecast.setOnClickListener(click -> {
            cityName = binding.editText.getText().toString();

            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                    + URLEncoder.encode(cityName)
                    + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            cityName = binding.editText.getText().toString();

            try {
                stringURL = new StringBuilder()
                        .append("https://api.openweathermap.org/data/2.5/weather?q=")
                        .append(URLEncoder.encode(cityName, "UTF-8"))
                        .append("&appid=7e943c97096a9784391a981c4d878b22&units=metric").toString();
            } catch(UnsupportedEncodingException e) { e.printStackTrace(); }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try {
                            JSONObject coord = response.getJSONObject("coord");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    (error) -> {});

            queue.add(request);



        });
    }



}