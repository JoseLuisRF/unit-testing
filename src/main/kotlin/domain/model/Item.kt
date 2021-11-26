package domain.model

sealed class Item(val name: String) {

    object CocaCola : Item("coca cola")

    object Pepsi : Item("pepsi")
}