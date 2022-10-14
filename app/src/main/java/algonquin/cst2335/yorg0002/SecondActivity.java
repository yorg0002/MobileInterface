package algonquin.cst2335.yorg0002;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SecondActivity extends AppCompatActivity {
    ImageView Imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView welcomeMessage = findViewById(R.id.welcomeView);
        welcomeMessage.setText("Welcome " + emailAddress);

        EditText EditText = findViewById(R.id.editTextTextPersonName);
        EditText EditTextPhone = findViewById(R.id.editTextPhone);

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:" + EditTextPhone));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(clk -> {
            String phone = fromPrevious.getStringExtra("PhoneNumber");

            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            EditText phoneNumberEdit = findViewById(R.id.editTextPhone);
            call.setData(Uri.parse("tel:" + phoneNumberEdit.getText()));

            startActivity(call);


        });

        if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SecondActivity.this, new String[] {
                    Manifest.permission.CAMERA}, 100
            );
        }
        Button changePicture = findViewById(R.id.button4);
        this.Imageview = findViewById(R.id.avatar);
        changePicture.setOnClickListener(click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
//            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        onActivityResult(cameraResult);
//
//                    });
//            cameraResult.launch(cameraIntent);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Imageview.setImageBitmap(thumbnail);

                FileOutputStream fOut = null;

                try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                    fOut.flush();

                    fOut.close();

                }

                catch (FileNotFoundException e)

                { e.printStackTrace();

                }
            }
        }

    }
}