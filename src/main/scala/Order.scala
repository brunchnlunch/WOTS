import java.util.Date
import scala.util.control.Breaks._


//items is a map from item ID to required quantity
case class Order (ID: Int, items: Map[Int , Int], datePlaced: Date, isCompleted : Boolean, isAssigned : Boolean, customerAddress : String) {
}

object OrderList {
  var Orders = Set(new Order(1111, Map(102 -> 2), new Date(93,0,28), true, true, "Jade Pencil\nBrackley Road\nTiers Cross\nSA62 2AA"),
      new Order(1112, Map(100 -> 1, 101 -> 1), new Date(115,0,28), false, false, "Imogen Faulkner \n95 South Western Terrace \nMinsterley \nSY5 3QA"),
      new Order(1113, Map(103 -> 5), new Date(115,2,28), false, false, "Oliver Fox \n18 Davids Lane \nStubbins \nBL0 7ZJ"))
  
  def printOrder (ID : Int) : String = {
    var orderString : String = ""
    orderString += "ID: " + ID.toString + "\nItems:\n"
    var order : Order = null
      for (a <- Orders){
        if (ID == a.ID) {
          order = a
        }
      }
    for (b <- order.items.keys) {
      var extra : String = ""
      extra += b.toString + "   " + StockList.findItemByID(b).name.toString + "   x   " + order.items(b).toString + "\n"
      orderString += extra
    }
    orderString += "Date order was placed: " + order.datePlaced.toString + "\n"
    orderString += "Has order been completed? " + order.isCompleted.toString + "\n"
    orderString += "Has order been assigned? " + order.isAssigned.toString
    orderString
  }
  
  def printDeliveryLabel (ID : Int) : String = {
    var Label = OrderList.findOrderByID(ID).customerAddress.toString
    Label
  }
  
  def findNonAssignedOrder : Int = {       //finds a non assigned order which has all items in stock
    var ID = 0                //NB: does not pick the order with the latest date!!
    breakable{ for (a <- Orders) {
      if (a.isAssigned == false) {
        var numberOfUniqueItems = a.items.size
        for (b <- a.items.keys) {
          if (StockList.findItemByID(b).quantity > 0) {
            numberOfUniqueItems -= 1
          }
        }
        if (numberOfUniqueItems == 0) {
          ID = a.ID
          break
        }
      }
    }
    }
    ID
  }
  
  def findOrderByID (ID : Int) : Order = {
    var order : Order = null
    for (a <- Orders) {
      if (ID == a.ID) {
        order = a
      }
    }
    order
  }
  
  def completedOrder(ID: Int) {              //Also decrements stock
    var newOrders = Set.empty[Order]
    for(order <- Orders) {
      if(order.ID != ID) {
        newOrders += order
      } else {
        newOrders += new Order(order.ID, order.items, order.datePlaced, true, order.isAssigned, order.customerAddress)
        for (itemID <- order.items.keys) {
          StockList.decreaseQuantity(itemID, order.items(itemID))
        }
      }
    }
  Orders = newOrders
  }
  
  def assignOrder(ID: Int) {              
    var newOrders = Set.empty[Order]
    for(order <- Orders) {
      if(order.ID != ID) {
        newOrders += order
      } else {
        newOrders += new Order(order.ID, order.items, order.datePlaced, order.isCompleted, true, order.customerAddress)
      }
    }
  Orders = newOrders
  }
  
  def unassignOrder(ID: Int) {              
    var newOrders = Set.empty[Order]
    for(order <- Orders) {
      if(order.ID != ID) {
        newOrders += order
      } else {
        newOrders += new Order(order.ID, order.items, order.datePlaced, order.isCompleted, false, order.customerAddress)
      }
    }
  Orders = newOrders
  }
  
  def findBoxSize (ID : Int) : Int = {
    var boxVolume = 0
    var order = OrderList.findOrderByID(ID)
      for (a <- order.items.keys) {
        boxVolume += (StockList.findItemByID(a).volume * order.items(a))
    }
    boxVolume
  }
  
}