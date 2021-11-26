package domain

import domain.model.Item
import domain.model.Order
import domain.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class SubmitOrderUseCaseTest {

    private val mockWarehouseService: WarehouseService = mockk()
    private val mockMailerService: MailerService = mockk()

    private val sut = SubmitOrderUseCase(
        warehouseService = mockWarehouseService,
        mailerService = mockMailerService
    )

    @Test
    fun `GIVEN an order WHEN the quantity item is available, THEN submit order transaction is successful `() {
        val fakeOrder = Order(
            item = Item.CocaCola,
            quantity = 2,
            user = User(
                name = "Luis",
                email = "luis@gmail.com",
                address = "Neverland 999"
            )
        )

        every { mockWarehouseService.getInventory(any()) } returns 10
        every { mockWarehouseService.update(any(), any()) } returns 8
        every { mockMailerService.sendEmail(any()) } returns true

        val response = sut.execute(
            order = fakeOrder
        )

        verify(exactly = 1) { mockWarehouseService.getInventory(any()) }
        verify(exactly = 1) { mockWarehouseService.update(any(), any()) }
        verify(exactly = 1) { mockMailerService.sendEmail(any()) }

        assertEquals(SubmitOrderUseCase.Response.Success, response)
    }

    @Test
    fun `GIVEN an order WHEN the quantity item is not available, THEN submit order transaction fails`() {
        val fakeOrder = Order(
            item = Item.CocaCola,
            quantity = 11,
            user = User(
                name = "Luis",
                email = "luis@gmail.com",
                address = "Neverland 999"
            )
        )

        every { mockWarehouseService.getInventory(any()) } returns 10
        every { mockMailerService.sendEmail(any()) } returns false

        val response = sut.execute(
            order = fakeOrder
        )

        verify(exactly = 1) { mockWarehouseService.getInventory(any()) }
        verify(exactly = 0) { mockWarehouseService.update(any(), any()) }
        verify(exactly = 0) { mockMailerService.sendEmail(any()) }

        assertEquals(
            SubmitOrderUseCase.Response.Error(
                errorCode = OrderErrors.OUT_OF_STOCK
            ), response
        )
    }

    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to empty-order`(){
        // Create unit test :)
    }

    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to out-of-stock`(){
        // Create unit test :)
    }

    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to unknown-item`(){
        // Create unit test :)
    }


    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to order-already-fulfilled`(){
        // Create unit test :)
    }
}