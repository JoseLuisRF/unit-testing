package domain.model

import domain.WarehouseImpl
import kotlin.test.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OrderTest {
    /**
     * The collaborators as "secondary objects" used by the SUT
     */
    private val warehouse = WarehouseImpl()

    @BeforeEach
    fun setup() {
        warehouse.loadItem(Item.CocaCola, 10)
    }

    @Test
    fun testOrderIsFilledIfEnoughInWarehouse() {
        val order = Order(
            item = Item.CocaCola,
            quantity = 10
        )

        /**
         * Order is the class that we are testing
         * Order is the object that we are focused on testing.
         * Testing-oriented people like to use terms like object-under-test or system-under-test to name
         *
         * SUT
         */
        order.fill(warehouse)

        assertTrue(order.isFilled)
        assertEquals(0, warehouse.getInventory(Item.CocaCola))

        /**
         * This style of testing uses state verification: which means that we determine whether the exercised method
         * worked correctly by examining the state of the SUT and its collaborators after the method was exercised
         */
    }

    @Test
    fun testOrderDoesNotRemoveIfNotEnough() {
        val order = Order(
            item = Item.CocaCola,
            quantity = 11
        )

        order.fill(warehouse)

        assertFalse(order.isFilled)
        assertEquals(10, warehouse.getInventory(Item.CocaCola))
    }
}