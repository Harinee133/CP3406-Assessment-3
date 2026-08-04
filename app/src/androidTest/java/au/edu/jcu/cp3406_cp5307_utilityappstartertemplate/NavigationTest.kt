package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationToSettings() {
        // Start on landing page
        composeTestRule.onNodeWithText("Primary Learning Hub", substring = true).assertExists()
        
        // Navigate to settings (Parents & Help card)
        composeTestRule.onNodeWithText("Parents & Help").performClick()
        
        // Verify settings screen
        composeTestRule.onNodeWithText("Curriculum Overview").assertExists()
        
        // Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        
        // Verify back on landing
        composeTestRule.onNodeWithText("Primary Learning Hub", substring = true).assertExists()
    }

    @Test
    fun testNavigationToStats() {
        // Navigate to stats (My Trophies card)
        composeTestRule.onNodeWithText("My Trophies").performClick()
        
        // Verify stats screen title
        composeTestRule.onNodeWithText("My Trophies", substring = true).assertExists()
        
        // Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        
        // Verify back on landing
        composeTestRule.onNodeWithText("Primary Learning Hub", substring = true).assertExists()
    }
    
    @Test
    fun testNavigationToActivitySelection() {
        // Navigate to Play & Learn
        composeTestRule.onNodeWithText("Play & Learn").performClick()
        
        // Verify activity selection screen (Adventure Hub)
        composeTestRule.onNodeWithText("Adventure Hub", substring = true).assertExists()
        
        // Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        
        // Verify back on landing
        composeTestRule.onNodeWithText("Primary Learning Hub", substring = true).assertExists()
    }
}
