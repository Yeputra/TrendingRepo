package id.gojek.trendingrepo

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.gojek.trendingrepo.CustomAssertions.Companion.hasItemCount
import id.gojek.trendingrepo.activity.MainActivity
import id.gojek.trendingrepo.R.id.*
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppToolbarBehaviour(){
        onView(withId(toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Trending"))
    }

    @Test
    fun testRecyclerViewBehaviour(){

        delay()
        onView(withId(rv_trending_repo))
            .check(matches(isDisplayed()))
        delay()
        onView(allOf(withId(civ_avatar), isDisplayed()))
        onView(allOf(withId(tv_author_name), isDisplayed()))
        onView(allOf(withId(tv_repo_name), isDisplayed()))

        onView(withId(rv_trending_repo)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))


        onView(withId(rv_trending_repo)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        onView(withRecyclerView(rv_trending_repo).atPosition(10))
            .check(matches(hasDescendant(withId(ll_repo_item))))
            .check(matches(hasDescendant(withId(tv_language))))
            .check(matches(hasDescendant(withId(tv_star_count))))
            .check(matches(hasDescendant(withId(tv_fork_count))))
            .check(matches(hasDescendant(withId(iv_language_color))))


        delay()


//        onView(withId(civ_avatar)).check(matches(isDisplayed()))
//        onView(withId(tv_author_name)).check(matches(isDisplayed()))
//        onView(withId(tv_repo_name)).check(matches(isDisplayed()))
//        onView(withId(tv_repo_description)).check(matches(isDisplayed()))
//        onView(withId(tv_language)).check(matches(isDisplayed()))
//        onView(withId(tv_star_count)).check(matches(isDisplayed()))
//        onView(withId(tv_fork_count)).check(matches(isDisplayed()))
    }

    @Test
    fun countRepo() {
        onView(withId(rv_trending_repo))
            .check(hasItemCount(24))
    }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun delay(){
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}