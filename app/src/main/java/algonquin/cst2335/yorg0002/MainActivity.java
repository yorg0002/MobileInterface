package algonquin.cst2335.yorg0002;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    Bitmap image;
    String url = null;
    String iconName = null;
    ImageRequest imgReq;

   // String imgUrl = "https://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.getForecastButton.setOnClickListener(click -> {
            cityName = binding.cityEditView.getText().toString();

            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                    + URLEncoder.encode(cityName)
                    + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            cityName = binding.cityEditView.getText().toString();

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
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            String description = position0.getString("description");
                            String iconName = position0.getString("icon");
                            JSONObject mainObject = response.getJSONObject("main");
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");
                            String pathname = getFilesDir() + "/" + iconName + ".png";
                            File file = new File(pathname);
                            if (file.exists()) {
                                image = BitmapFactory.decodeFile(pathname);
                            } else {
                                ImageRequest imgReq = new ImageRequest("https://openweathermap.org/img/w/" + iconName + ".png", new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        image = bitmap;
                                        FileOutputStream fOut = null;
                                        try {
                                           // fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                            image.compress(Bitmap.CompressFormat.PNG, 100,MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                          //  fOut.flush();
                                            //fOut.close();
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } //catch (IOException e) {
                                            //e.printStackTrace();
                                        //}

                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                                });

                                queue.add(imgReq);
                            }
                            runOnUiThread(() -> {

                                binding.tempText.setText("The current temperature is " + current);
                                binding.tempText.setVisibility(View.VISIBLE);

                                binding.minText.setText("The min temperature is " + min);
                                binding.minText.setVisibility(View.VISIBLE);

                                binding.maxText.setText("The max temperature is " + max);
                                binding.maxText.setVisibility(View.VISIBLE);

                                binding.humidityText.setText("The humidity is " + humidity + "%");
                                binding.humidityText.setVisibility(View.VISIBLE);

                                binding.icon.setImageBitmap(image);
                                binding.icon.setVisibility(View.VISIBLE);

                                binding.descriptionText.setText(description);
                                binding.descriptionText.setVisibility(View.VISIBLE);

                            });

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    (error) -> {});

            queue.add(request);



        });
    }



}