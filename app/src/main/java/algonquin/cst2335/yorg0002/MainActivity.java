package algonquin.cst2335.yorg0002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to check the password requirements and provide the correct toast message.
 * @author Ece Selin Yorgancilar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the centre of the screen*/
    private TextView tv = null;

    /** This holds the text entered by the user in the edittext*/
    private EditText et = null;

    /** This holds the login button*/
    private Button btn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            checkPasswordComplexity( password );
            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements.");
            }
            else {
                tv.setText("You shall not pass!");
            }
        });

    }

    /**
     * Use this function to check the requirements of the password. If missing any of the requirements show a toast message.
     * @param password The String object that we are checking
     * @return Returns true if the password is complex enough, if not return false.
     */
    boolean checkPasswordComplexity (String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            if (Character.isDigit(password.charAt(i))) {
                foundNumber = true;
            } else if (Character.isUpperCase(password.charAt(i))) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                foundLowerCase = true;
            } else if (isSpecialCharacter(password.charAt(i))) {
                foundSpecial = true;
            }

        }//end loop

        if(!foundUpperCase)
        {

            Toast.makeText(this, "You are missing an upper case letter.", Toast.LENGTH_SHORT).show();

            return false;

        }

        else if( !foundLowerCase)
        {
            Toast.makeText(this, "You are missing a lower case letter.", Toast.LENGTH_SHORT).show();

            return false;

        }

        else if( !foundNumber) {
            Toast.makeText(this, "You are missing a number.", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(!foundSpecial) {
            Toast.makeText(this, "You are missing a special character.", Toast.LENGTH_SHORT).show();

            return false;
        }

        else
            Toast.makeText(this, "Your password meets the requirements.", Toast.LENGTH_LONG).show();
            return true; //only get here if they're all true

    }

    /**
     * This function is to see whether there is a special character or not.
     * @param c character to be used to check the case statements.
     * @return true if c is one of these cases, false if not.
     */
    boolean isSpecialCharacter(char c)
    {
        switch (c)
        {
            case '#':
            case '?':
            case '*':
            case '%':
            case '^':
            case '&':
            case '!':
            case '@':

                return true;
            default:
                return false;

        } //end switch

    }//end isSpecialCharacter
}