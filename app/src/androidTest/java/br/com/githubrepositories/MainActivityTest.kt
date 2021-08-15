package br.com.githubrepositories

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get: Rule
    val activityRule : ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testActivity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testVisibility_progressBar() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.defaultProgress))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.loadMoreProgress))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * RecycleView comes into view
     */
    @Test
    fun testRecycleViewVisible(){
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))

    }

}