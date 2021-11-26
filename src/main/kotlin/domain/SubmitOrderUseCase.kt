package domain

import domain.model.Order

class SubmitOrderUseCase constructor(
    private val warehouseService: WarehouseService,
    private val mailerService: MailerService
) {

    fun execute(order: Order): Response {
        val stock = warehouseService.getInventory(order.item)
        val response = if (!order.isFilled) {
            stock?.let {
                when {
                    order.quantity > ITEM_UNAVAILABLE && order.quantity <= stock -> {
                        warehouseService.update(order.item, stock - order.quantity)
                        order.isFilled = true
                        Response.Success
                    }
                    order.quantity > ITEM_UNAVAILABLE && order.quantity >= stock -> {
                        order.isFilled = false
                        Response.Error(errorCode = OrderErrors.OUT_OF_STOCK)
                    }
                    else -> {
                        order.isFilled = false
                        Response.Error(errorCode = OrderErrors.EMPTY_ORDER)
                    }
                }
            } ?: let {
                order.isFilled = false
                Response.Error(errorCode = OrderErrors.UNKNOWN_ITEM)
            }
        } else {
            Response.Error(errorCode = OrderErrors.ORDER_ALREADY_FULFILLED)
        }

        if (response is Response.Success) {
            mailerService.sendEmail(order.user.email)
        }

        return response
    }

    sealed class Response {
        object Success : Response()
        data class Error(val errorCode: OrderErrors) : Response()
    }

    companion object {
        const val ITEM_UNAVAILABLE = 0
    }
}