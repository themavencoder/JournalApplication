package com.aloineinc.journalapplication;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.aloineinc.journalapplication.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserLoginActivityWithEmailTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void userLoginActivityWithEmailTest() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(60000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction textInputEditText = onView(
allOf(withId(R.id.email),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(60000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction textInputEditText2 = onView(
allOf(withId(R.id.email),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText2.perform(replaceText("eniolaplus08@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
allOf(withId(R.id.password),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText3.perform(replaceText("111111"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.btn_login), withText("Login"),
childAtPosition(
childAtPosition(
withId(R.id.coordinator_layout),
0),
2),
isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText4 = onView(
allOf(withId(R.id.password), withText("111111"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText4.perform(replaceText("1111"));

        ViewInteraction textInputEditText5 = onView(
allOf(withId(R.id.password), withText("1111"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
allOf(withId(R.id.email), withText("eniolaplus08@gmail.com"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText6.perform(replaceText("aloineinc@gmail.com"));

        ViewInteraction textInputEditText7 = onView(
allOf(withId(R.id.email), withText("aloineinc@gmail.com"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
allOf(withId(R.id.password), withText("1111"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText8.perform(replaceText("111111"));

        ViewInteraction textInputEditText9 = onView(
allOf(withId(R.id.password), withText("111111"),
childAtPosition(
childAtPosition(
withClassName(is("android.support.design.widget.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
allOf(withId(R.id.btn_login), withText("Login"),
childAtPosition(
childAtPosition(
withId(R.id.coordinator_layout),
0),
2),
isDisplayed()));
        appCompatButton2.perform(click());

        }

        private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
