package com.org.demowipro.ui;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.org.demowipro.R;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
public class DataAvailabilityTest {

    @Rule
    public final ActivityTestRule<InfoListActivity> main = new ActivityTestRule<>(InfoListActivity.class);

    @Before
    public void registerIdlingResources() {
        registerIdlingResource();
    }

    @Test
    public void shouldShowListIfDataAvailable() {
        try {
            onView(ViewMatchers.withId(R.id.recycler_view)).check(matches(isDisplayed()));
        } catch (AssertionFailedError ignored) {
            onView(withId(R.id.msg_textview)).check(matches(isDisplayed()));
        }
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(main.getActivity().idlingResource);
    }

    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(main.getActivity().idlingResource);
    }

}
