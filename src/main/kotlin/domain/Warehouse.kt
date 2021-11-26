package domain

import domain.model.Item

interface Warehouse {

    fun getInventory(item: Item): Int?

    fun loadItem(item: Item, quantity: Int) : Int

    fun update(item: Item, quantity: Int) : Int
}