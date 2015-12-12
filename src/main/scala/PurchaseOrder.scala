import java.util.Date

//items key is an item ID which points to a array value with first entry stock level and second entry item cost
case class PurchaseOrder (ID: Int, items: Map[Int , Array[Double]], datePlaced : Date, dateExpected : Date, received : Boolean, supplier : String) {
}

object PurchaseOrderList {
  var purchaseOrders = Set(new PurchaseOrder(1111, Map(101 -> Array(3, 5.5)), new Date(95,0,28), new Date(16,0,28), true, "The gnome shop"), 
      new PurchaseOrder(1112, Map(101 -> Array(1, 6.5), 103 -> Array(3, 7.5)), new Date(115,0,28), new Date(116,0,28), false, "Gnomes R us"))
      
  def findOrderByID (ID : Int) : PurchaseOrder = {
    var order : PurchaseOrder = null 
    for (a <- purchaseOrders) {
      if (a.ID == ID) {
        order = a
      }
    }
    order
  }
  
  def receivedOrder (ID : Int) {
    var newOrders = Set.empty[PurchaseOrder]
    for (a <- purchaseOrders) {
      if (a.ID != ID) {
        newOrders += a
      } else {
        newOrders += new PurchaseOrder(a.ID, a.items, a.datePlaced, a.dateExpected, true, a.supplier)
      }
    }
    purchaseOrders = newOrders
  }
  
  def printPurchaseOrder (ID : Int) : String ={
    var orderString = ""
    var order = PurchaseOrderList.findOrderByID(ID)
    orderString += "ID " + ID.toString + "\nItems:\n"
    for (a <- order.items.keys) {
      var extra = ""
      extra += a.toString + "   " + StockList.findItemByID(a).name.toString + "   x   " + order.items(a)(0).toInt.toString + "   £" + order.items(a)(1).toString + "per item\n"
      orderString += extra
    }
    orderString += "Date order was placed: " + order.datePlaced.toString + "\nDate order is expected: " + order.dateExpected.toString + "\nOrder received? " + order.received.toString + "\nSupplier: " + order.supplier.toString
    orderString
  }
  
  def findNextPurchaseOrder : Int = {
    var NonReceivedOrders = Set.empty[PurchaseOrder]
    for (a <- purchaseOrders) {
      if (a.received == false) {
        NonReceivedOrders += a
      }
    }
    var closestDateID = NonReceivedOrders.head.ID
    var closestDate = NonReceivedOrders.head.dateExpected
    for (b <- NonReceivedOrders){
      if (b.dateExpected.before(closestDate)) {
        closestDate = b.dateExpected
        closestDateID = b.ID
      }
    }
    closestDateID
  }
  
  def nextPurchaseOrderNotification : String = {
    var orderNotification = "The next purchase order to arrive is order "
    orderNotification += PurchaseOrderList.findNextPurchaseOrder.toString
    orderNotification += ", it will arrive at: "
    orderNotification += PurchaseOrderList.findOrderByID(PurchaseOrderList.findNextPurchaseOrder).dateExpected.toString
    orderNotification
  }
  
}