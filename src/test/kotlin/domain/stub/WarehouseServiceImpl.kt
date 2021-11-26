package domain.stub

import domain.WarehouseService
import domain.model.Item

class WarehouseServiceImpl : WarehouseService {

    private val dataset = mutableMapOf<String, Int>()

    override fun getInventory(item: Item): Int? = dataset[item.name]

    override fun loadItem(item: Item, quantity: Int): Int {
        val currentQuantity = dataset[item.name] ?: 0
        dataset[item.name] = currentQuantity + quantity
        return dataset[item.name] ?: 0
    }

    override fun update(item: Item, quantity: Int): Int {
        dataset[item.name] = quantity
        return dataset[item.name] ?: 0
    }
}