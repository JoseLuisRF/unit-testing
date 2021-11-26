package domain.model

import domain.Warehouse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class OrderTest {
    /**
     * The collaborators as "secondary objects" used by the SUT
     *
     * The collaborator isn't a warehouse object, instead it's a mock warehouse - technically an instance of the class MockK.
     */
    private val warehouse: Warehouse = mockk()

    @Test
    fun testOrderIsFilledIfEnoughInWarehouse() {

        /**
         * The SUT is the same - an order.
         */

        // setup - data
        val order = Order(
            item = Item.CocaCola,
            quantity = 10
        )

        /**
         * The expectations indicate which methods should be called on the mocks when the SUT is exercised.
         */
        // setup - expectations
        every { warehouse.getInventory(any()) } returns 10
        every { warehouse.update(any(), any()) } returns 0

        /**
         * Once all the expectations are in place the SUT is exercised.
         */
        // exercise
        order.fill(warehouse)

        /**
         *  After the exercise, then verification is done, which has two aspects.
         *
         *  - Run asserts against the SUT
         *  - Verify the mocks - checking that they were called according to their expectations.
         */
        // verify
        verify(exactly = 1) { warehouse.getInventory(any()) }
        verify(exactly = 1) { warehouse.update(any(), any()) }

        assertTrue(order.isFilled) // <- Here it is the SUT

        /**
         * Mocks use behavior verification,
         * where we instead check to see if the order made the correct calls on the warehouse.
         *
         * We do this check by telling the mock what to expect during setup and asking the mock
         * to verify itself during verification. Only the order is checked using asserts,
         * and if the method doesn't change the state of the order there's no asserts at all.
         */
    }

    @Test
    fun testOrderDoesNotRemoveIfNotEnough() {
        // setup - data
        val order = Order(
            item = Item.CocaCola,
            quantity = 11
        )

        // setup - expectations
        every { warehouse.getInventory(any()) } returns 10
        every { warehouse.update(any(), any()) } returns 0

        // exercise
        order.fill(warehouse)

        // verify
        verify(atMost = 1) { warehouse.getInventory(any()) }
        verify(exactly = 0) { warehouse.update(any(), any()) }

        assertFalse(order.isFilled)
    }
}