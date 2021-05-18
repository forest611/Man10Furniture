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

    fun setCommand(item: ItemStack,cmd:String):ItemStack{
        val meta = item.itemMeta
        meta.persistentDataContainer.set(NamespacedKey(Man10Furniture.plugin,"command"), PersistentDataType.STRING,
            cmd)

        item.itemMeta = meta
        return item

    }

    fun isFurnitureItem(item:ItemStack?):Boolean{

        if (item ==null || !item.hasItemMeta())return false

        return item.itemMeta.persistentDataContainer[NamespacedKey(Man10Furniture.plugin,"furniture"),
                PersistentDataType.STRING]?:"" == name
    }

    fun getCommand(item:ItemStack?):String{

        if (item ==null || !item.hasItemMeta())return ""

        return item.itemMeta.persistentDataContainer[NamespacedKey(Man10Furniture.plugin,"command"),
                PersistentDataType.STRING]?:""
    }


}