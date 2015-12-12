import scala.collection.mutable.ArrayBuffer   //mutable arrays
import java.io._
import scala.io.Source
import java.util.Date


object Test {
    def main(args: Array[String]) {
      
    var exit = true

    do {
      exit = true
      println(PurchaseOrderList.nextPurchaseOrderNotification)
      println()
      println("Please select what you'd like to do from below: ")
      println("(1): Be assigned a new order")
      println("(2): Report damaged stock")
      println("(3): Clocking in/out")    
      println("(4): View orders")
      println("(5): View a specific items location")
      println("(6): Pick box size")
      println("(7): Contact Courier")
      println("(8): Mark purchase order as recieved")
      println("(9): View purchase orders")
      println("(10): Mark an order as completed")
      println("(11): Print delivery label")
      println("(12): Assigned order cannot be completed")
      println("(13): View the location of items in of an order")
      var choice = scala.io.StdIn.readLine()
      println("You have chosen choice (" + choice +")")
      matchTest(choice)
      println("Type 'menu' to return to menu, otherwise program will exit")
      var menuOrExit = scala.io.StdIn.readLine()
      if (menuOrExit == "menu") {
        exit = false
      }
    } while (exit == false)
      
    def matchTest(x: String): Unit = x match {    
      case "1" => AssignNewOrder
      case "2" => DamagedStock
      case "3" => clockingInOut
      case "4" => viewOrders
      case "5" => viewItemLocation
      case "6" => findBox
      case "7" => contactCourier
      case "8" => purchaseOrderRecieved
      case "9" => checkRecievedPurchaseOrders
      case "10" => completedOrder
      case "11" => printDeliveryLabel
      case "12" => cancelAssignedOrder
      case "13" => viewOrderLocation
      case _ => println("Invalid Choice")
    }
    
    def AssignNewOrder {     
      var orderID = OrderList.findNonAssignedOrder
      println("you have been assigned order number " + orderID.toString)
      OrderList.assignOrder(orderID)
    }
    
    def DamagedStock {
      println("Please enter the item number of the damaged stock")
      var damStock = scala.io.StdIn.readLine()
      println("Please enter the quantity of stock which was damaged")
      var quanDamStock = scala.io.StdIn.readLine()
      StockList.decreaseQuantity(damStock.toInt, quanDamStock.toInt)
    }
    
    def viewOrders {
      println("please enter an order number")
      var orderNumber = scala.io.StdIn.readLine()
      println(OrderList.printOrder(orderNumber.toInt))
    }
    
    def viewItemLocation {
      println("Please enter item number")
      var itemNumber = scala.io.StdIn.readLine()
      println("Item is located at:")
      println(StockList.itemLocation(itemNumber.toInt))
    }
    
    def findBox {
      println("Please enter the order number")
      var orderNumber = scala.io.StdIn.readLine()
      var boxVolume = OrderList.findBoxSize(orderNumber.toInt)
      println("Required box volume is " + boxVolume.toString)
    }
    
    def contactCourier {
      println("The courier has been contacted")
    }
    
    def purchaseOrderRecieved {
      println("Please under the purchase order ID")
      var ID = scala.io.StdIn.readLine()
      PurchaseOrderList.receivedOrder(ID.toInt)
    }
    
    def checkRecievedPurchaseOrders {
      println("Please enter the purchase order ID")
      var ID = scala.io.StdIn.readLine()
      println(PurchaseOrderList.printPurchaseOrder(ID.toInt))
    }
    
    def completedOrder {
      println("Please enter Order ID")
      var ID = scala.io.StdIn.readLine()
      OrderList.completedOrder(ID.toInt)
    }
    
    def printDeliveryLabel {
      println("Please enter Order ID")
      var ID = scala.io.StdIn.readLine()
      println("Customer Address is:")
      println(OrderList.printDeliveryLabel(ID.toInt))
    }
    
    def clockingInOut {
      println("Please enter worker ID")
      var ID = scala.io.StdIn.readLine()
      println("Type 'In' or 'Out' for clocking In or clocking Out")
      var inOut = scala.io.StdIn.readLine()
      if (inOut == "In") {
        WorkerList.clockIn(ID.toInt)
      } else {
        WorkerList.clockOut(ID.toInt)
      }
    }
    
    def cancelAssignedOrder {
      println("Please enter order ID")
      var ID = scala.io.StdIn.readLine()
      OrderList.unassignOrder(ID.toInt)
    }
    
    def viewOrderLocation {
      print("Please enter order ID")
      var ID = scala.io.StdIn.readLine()
      var itemNumbers = ArrayBuffer.empty[Int]    
      var order = OrderList.findOrderByID(ID.toInt)
      for(a <- order.items.keys) {
        itemNumbers += a
      }
      itemNumbers = itemNumbers.sortWith(StockList.itemLocation(_) < StockList.itemLocation(_))
      println("The order items have been to give you the most efficient path to collect them:")
      for (b <- itemNumbers) {
        print(b)
        print("    ")
        print(StockList.findItemByID(b).name)
        print("    x    ")
        print(OrderList.findOrderByID(ID.toInt).items(b))
        print("    ")
        println(StockList.itemLocation(b))
      }
    }
    
  }
}