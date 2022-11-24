package algonquin.cst2335.yorg0002;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editText1) );

        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button1));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests password with uppercase missing
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText1));
        //type password
        appCompatEditText.perform(replaceText("password234$#@"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button1));

        //click the button
        materialButton.perform(click());
        //check the text
        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests password with lowercase missing
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText1));
        appCompatEditText.perform(click());
        appCompatEditText.perform(replaceText("PASSWORD123!@#$"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button1));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests password with number missing
     */
    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText1));
        appCompatEditText.perform(click());

        appCompatEditText.perform(replaceText("Password!@#$"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button1));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests password with special character missing
     */
    @Test
    public void testMissingSpecialChar() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText1));
        appCompatEditText.perform(click());

        appCompatEditText.perform(replaceText("Password123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button1));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests password that meets all requirements
     */
    @Test
    public void testMeetAllRequirements() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText1));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.editText1));
        appCompatEditText2.perform(replaceText("Password123!@#"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button1));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView1));
        textView.check(matches(withText("Your password meets the requirements.")));
    }
}
