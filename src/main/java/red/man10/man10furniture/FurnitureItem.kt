package red.man10.man10furniture

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object FurnitureItem {

    private const val name = "WWWWWWW"

    fun setFurnitureItem(item:ItemStack):ItemStack{
        val meta = item.itemMeta
        meta.persistentDataContainer.set(NamespacedKey(Man10Furniture.plugin,"furniture"), PersistentDataType.STRING,
            name)

        item.itemMeta = meta
        return item
    }

    fun isFurnitureItem(item:ItemStack):Boolean{
        return item.itemMeta.persistentDataContainer[NamespacedKey(Man10Furniture.plugin,"furniture"),
                PersistentDataType.STRING]?:"" == name
    }


}