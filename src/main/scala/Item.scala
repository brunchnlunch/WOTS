//Include item price like in purchase order??

case class Item (ID: Int, name: String, quantity: Int, volume: Int, location: Int) {

}

object StockList {
  var Items = Set(Item(100, "ChristmasGnome", 40, 25, 11111), Item(101, "BigGnome", 0, 50, 11112), Item(102, "TinyGnome", 60, 10, 11113), Item(103, "PurpleGnome", 15, 30, 11114))
  
  def increaseQuantity(ID: Int, quant : Int) {
    var newItems = Set.empty[Item]
    for(item <- Items) {
      if(item.ID != ID) {
        newItems += item
      } else {
        newItems += new Item(item.ID, item.name, item.quantity+quant, item.volume, item.location)
      }
    }
  Items = newItems
  }
  
  def decreaseQuantity(ID: Int, quant: Int) {
    var newItems = Set.empty[Item]
    for(item <- Items) {
      if(item.ID != ID) {
        newItems += item
      } else {
        newItems += new Item(item.ID, item.name, item.quantity-quant, item.volume, item.location)
      }
    }
  Items = newItems
  }
  
  def printNames : String = {
    var itemNames = ""
    for(item <- Items) {
      itemNames += item.name.toString
    }
    itemNames
  }
  
  def findItemByID (ID : Int) : Item = {
    var a : Item = null
    for (item <- Items) {
      if (item.ID == ID) {
        a = item
      }
    }
    a
  }
  
  def findItemByName (Name : String) : Item = {
    var a : Item = null
    for(item <- Items) {
      if(item.name == Name){
        a = item
      }
    }
    a
  }
  
  def itemLocation (ID : Int) : Int = {
    var a =0
    for(item <- Items) {
      if(item.ID == ID) {
        a = item.location
      }
    }
    a
  }
  
}