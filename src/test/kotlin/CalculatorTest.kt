import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("A special test case")
class CalculatorTest {

    @Test
    @DisplayName("Custom test name containing spaces")
    fun testWithDisplayNameContainingSpaces() {
    }

    @Test
    @DisplayName("╯°□°）╯")
    fun testWithDisplayNameContainingSpecialCharacters() {
    }

    @Test
    @DisplayName("😱")
    fun testWithDisplayNameContainingEmoji() {
    }

    @Test
    @DisplayName("3 + 2")
    fun testAddition() {
        val expectedResult = 5
        val calculator = Calculator()

        assertEquals(expectedResult, calculator.addition(3, 2))
    }

    @Test
    @DisplayName("3 + 2")
    fun testAddition2() {
        val expectedResult = 5
        val calculator = Calculator()

        assertEquals(expectedResult, calculator.addition(3, 2))
    }
}