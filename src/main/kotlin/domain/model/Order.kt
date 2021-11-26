package domain.model

import domain.Warehouse

data class Order(val item: Item, val quantity: Int) {
    var isFilled = false

    fun fill(warehouse: Warehouse) {
        val stock = warehouse.getInventory(item)
        if (!isFilled) {
            stock?.let {
                when {
                    quantity > 0 && quantity <= stock -> {
                        warehouse.update(item, stock - quantity)
                        isFilled = true
                    }
                    quantity > 0 && quantity >= stock -> {
                        isFilled = false
                    }
                    else -> {
                        isFilled = false
                    }
                }
            } ?: run {
                isFilled = false
            }
        }
    }
}