package red.man10.man10furniture

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Man10Furniture : JavaPlugin() {

    companion object{
        lateinit var plugin : Man10Furniture
        const val maxFurniture = 8
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        server.pluginManager.registerEvents(FurnitureBlock,this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (!sender.hasPermission("man10furniture.op"))return false

        if (sender !is Player)return false

        if (args.isEmpty()){
            sender.sendMessage("/furniture set : 手持ちのアイテムを家具にする")
        }

        when(args[0]){

            "set" ->{
                FurnitureItem.setFurnitureItem(sender.inventory.itemInMainHand)
                return true
            }

            "command" ->{
                FurnitureItem.setCommand(sender.inventory.itemInMainHand,args[1])
                return true
            }

        }


        return false
    }
}