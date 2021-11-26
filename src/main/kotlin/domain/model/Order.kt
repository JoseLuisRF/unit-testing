package domain.model

import domain.WarehouseService

data class User(
    val name: String,
    val email: String,
    val address: String
)
data class Order(
    val item: Item,
    val quantity: Int,
    val user: User
    ) {
    var isFilled = false

}