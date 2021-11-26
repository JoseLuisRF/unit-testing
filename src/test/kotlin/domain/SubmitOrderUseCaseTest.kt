package domain

import domain.model.Item
import domain.model.Order
import domain.model.User
import domain.stub.WarehouseServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Ignore
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SubmitOrderUseCaseTest {

    private val stubWarehouseService: WarehouseService = WarehouseServiceImpl()
    private val mockMailerService: MailerService = mockk()

    private val sut = SubmitOrderUseCase(
        warehouseService = stubWarehouseService,
        mailerService = mockMailerService
    )

    @BeforeEach
    fun setup() {
        stubWarehouseService.loadItem(Item.CocaCola, 10)
    }

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

        every { mockMailerService.sendEmail(any()) } returns true

        val response = sut.execute(
            order = fakeOrder
        )

        verify(exactly = 1) { mockMailerService.sendEmail(any()) }

        assertEquals(8, stubWarehouseService.getInventory(Item.CocaCola))
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

        every { mockMailerService.sendEmail(any()) } returns false

        val response = sut.execute(
            order = fakeOrder
        )

        verify(exactly = 0) { mockMailerService.sendEmail(any()) }

        assertEquals(10, stubWarehouseService.getInventory(Item.CocaCola))
        assertEquals(
            SubmitOrderUseCase.Response.Error(
                errorCode = OrderErrors.OUT_OF_STOCK
            ), response
        )
    }

    @Ignore
    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to empty-order`(){
        // Create unit test :)
    }

    @Ignore
    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to out-of-stock`(){
        // Create unit test :)
    }

    @Ignore
    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to unknown-item`(){
        // Create unit test :)
    }

    @Ignore
    @Test
    fun `GIVEN an order WHEN the order is empty, THEN verify the transaction fails and error code equals to order-already-fulfilled`(){
        // Create unit test :)
    }
}