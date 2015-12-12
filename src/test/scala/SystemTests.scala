import java.util.Date

class SystemTests extends TestBase {
  
  "The System" should "assign me an order" in {   //checks to see if returned order is of type Order
    var orderID = OrderList.findNonAssignedOrder
    var order = OrderList.findOrderByID(orderID)
    assert(order.isInstanceOf[Order])
  }
  
  "It" should "show me what items are in an order" in {    //checks to see if predicted string is returned
    var orderString = OrderList.printOrder(1111)
    var orderStringTest = "ID: 1111\nItems:\n102   TinyGnome   x   2\nDate order was placed: Thu Jan 28 00:00:00 GMT 1993\nHas order been completed? true\nHas order been assigned? true"
    assert(orderString == orderStringTest)     
  }
  
  "It" should "allow me to report damaged stock" in {    //checks to see if stock is decreased upon report
    var damagedStockID = 100
    var quantity = 2
    var originalQuantity = StockList.findItemByID(damagedStockID).quantity
    StockList.decreaseQuantity(damagedStockID, quantity)
    var currentQuantity = StockList.findItemByID(damagedStockID).quantity
    assert(originalQuantity == currentQuantity + quantity)
  }
  
  "It" should "allow me to clock in" in {    //checks to see if a new entry is added into workers clockIn attribute and if that new entry is of date type
    var WorkerID = 1111
    var originalClockInLength = WorkerList.findWorkerByID(WorkerID).clockIn.length
    WorkerList.clockIn(1111)
    var currentClockInLength = WorkerList.findWorkerByID(WorkerID).clockIn.length
    assert(originalClockInLength == currentClockInLength - 1)
    var clockInLengthEntry = WorkerList.findWorkerByID(WorkerID).clockIn.reverse.head
    assert(clockInLengthEntry.isInstanceOf[Date])
  }
  
  "It" should "notify me when a purchase order is being recieved" in {   //checks to see if notification string is as predicted
    var notificationString = "The next purchase order to arrive is order " + PurchaseOrderList.findNextPurchaseOrder.toString + ", it will arrive at: " + PurchaseOrderList.findOrderByID(PurchaseOrderList.findNextPurchaseOrder).dateExpected.toString
    assert(notificationString == PurchaseOrderList.nextPurchaseOrderNotification)
  }
  
  "It" should "allow me to check received purchase orders" in {   //checks to see if printed string is as predicted
    var orderID = 1111
    var orderString = PurchaseOrderList.printPurchaseOrder(orderID)
    var orderStringTest = "ID 1111\nItems:\n101   BigGnome   x   3   £5.5per item\nDate order was placed: Sat Jan 28 00:00:00 GMT 1995\nDate order is expected: Fri Jan 28 00:00:00 GMT 1916\nOrder received? true\nSupplier: The gnome shop"
    assert(orderString == orderStringTest)
  }
  
  "It" should "allow me to confirm that a purchase order has been received" in {   //checks if method upadtes received order var to true
    var orderID = 1111
    PurchaseOrderList.receivedOrder(orderID)
    var Received = PurchaseOrderList.findOrderByID(orderID).received
    assert(Received==true)
  }
  
  "It" should "give me the location of an item in the warehouse" in {    //checks to see if the location attribute is returned as expected
    var itemID=100
    var expectedLocation = 11111
    var itemLocation = StockList.findItemByID(itemID).location
    assert(expectedLocation == itemLocation)
  }
  
  "It" should "print a delivery label for an order" in {     //Checks to see if printed string is as predicted
    var orderID=1111
    var expectedString = "Jade Pencil\nBrackley Road\nTiers Cross\nSA62 2AA"
    var currentString = OrderList.printDeliveryLabel(orderID)
    assert(expectedString == currentString)
  }
  
  "It" should "tell me what size box I need for packing" in {    //checks to see if returned value for volume is as predicted
    var orderID = 1111
    var expectedSizeOfBox = 20
    var sizeOfBox = OrderList.findBoxSize(orderID)
    assert(sizeOfBox == expectedSizeOfBox)
  }
  
  "It" should "allow me to update an order as completed" in {     //checks to see if function updates the isCompleted variable to true
    var orderID = 1111
    OrderList.completedOrder(orderID)
    var completed = OrderList.findOrderByID(orderID).isCompleted
    assert(completed == true)
  }
  
  "It" should "not assign orders which are out of stock" in {    //checks to see if assigned order has all items in stock
    var orderID = OrderList.findNonAssignedOrder
    var order = OrderList.findOrderByID(orderID)
    var itemsInStock = true
    for (a <- order.items.keys) {
      if (StockList.findItemByID(a).quantity == 0) {
        itemsInStock = false
      }
    }
    assert(itemsInStock == true)
  }
  
  "It" should "allow me to update an order when it cannot be completed" in {    //A chosen order should be set to unassigned
    var orderID = 1112
    var order = OrderList.findOrderByID(orderID)
    OrderList.unassignOrder(orderID)
    var orderAssigned = order.isAssigned
    assert(orderAssigned == false)
  }
  
}