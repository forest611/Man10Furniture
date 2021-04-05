package red.man10.man10furniture

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object FurnitureBlock : Listener {


    private fun setFurniture(center: Location, item:ItemStack){

        center.block.type = Material.BARRIER

        val loc = center.clone()

        var yaw: Float = loc.yaw

        if (yaw < 0) {
            yaw += 360f
        }
        if (yaw >= 315 || yaw < 45) {
            loc.yaw = 0.0F
        } else if (yaw < 135) {
            loc.yaw = 90F
        } else if (yaw < 225) {
            loc.yaw = 180F
        } else if (yaw < 315) {
            loc.yaw = -90F
        }

        loc.x+=0.5
        loc.z+=0.5
//        loc.y -= 1

        val armor = center.world.spawn(loc, ArmorStand :: class.java)
        armor.setGravity(false)
        armor.canPickupItems = false
        armor.isVisible = false
        armor.setItem(EquipmentSlot.HEAD,item.clone())

        item.amount = item.amount - 1

        center.world.playSound(center, Sound.BLOCK_ANVIL_PLACE,1.0F,1.0F)

    }

    private fun breakFurniture(location:Location){

        val stand = getArmorStand(location)?:return

//        val item = stand.getItem(EquipmentSlot.HEAD)

        location.block.type = Material.AIR

        stand.remove()

        location.world.playSound(location,Sound.BLOCK_STONE_BREAK,1.0F,1.0F)

    }

    private fun canSetFurniture(location: Location):Boolean{

        val chunk = location.chunk

        var count = 0

        for (entity in chunk.entities){
            if (entity.type != EntityType.ARMOR_STAND)continue
            count ++
        }

        if (count >= Man10Furniture.maxFurniture)return false

        return true

    }

    private fun getArmorStand(location: Location):ArmorStand?{

        val fixedLoc = location.clone()
        fixedLoc.x += 0.5
        fixedLoc.z += 0.5

        val entities = location.world.getNearbyEntities(fixedLoc,0.5,0.5,0.5)

        for (entity in entities){
            if (entity.type != EntityType.ARMOR_STAND)continue
            if (entity !is ArmorStand)continue
            return entity
        }

        return null

    }

    @EventHandler( ignoreCancelled = false)
    fun interactEvent(e:PlayerInteractEvent){

        val item = e.item?:return
        val p = e.player

        if (e.hand != EquipmentSlot.HAND)return

        when(e.action){
            Action.RIGHT_CLICK_BLOCK->{

                if (e.blockFace != BlockFace.UP)return

                if (!FurnitureItem.isFurnitureItem(item))return

                e.isCancelled = true

                val location = e.clickedBlock!!.location

                if (!canSetFurniture(location)){
                    p.sendMessage("§cこの場所にはもう家具は置けない！")
                    return
                }

                val furnitureLocation = location.clone()


                furnitureLocation.yaw = p.location.yaw
                furnitureLocation.y += 1.0

                setFurniture(furnitureLocation,item)
            }

            Action.LEFT_CLICK_BLOCK->{

                val loc = e.clickedBlock!!.location

                val stand = getArmorStand(loc)?:return

                if (!FurnitureItem.isFurnitureItem(stand.getItem(EquipmentSlot.HEAD)))return

                e.isCancelled = true

                breakFurniture(loc)

                p.sendMessage("§a家具を撤去した！")

                p.inventory.addItem(stand.getItem(EquipmentSlot.HEAD))

            }

            else ->return
        }

    }
}