import scala.collection.mutable.ArrayBuffer   //mutable arrays
import java.util.Date

case class Worker (ID: Int, name: String, clockIn: ArrayBuffer[Date], clockOut: ArrayBuffer[Date]){ //is forklift qualified? has order assigned?
}

object WorkerList {
  var Workers = Set(new Worker(1111, "Terry Bobbers", ArrayBuffer(new Date(93, 0, 28)), ArrayBuffer(new Date(93, 0, 29))),
      new Worker(1112, "Joe Foot", ArrayBuffer(new Date(93, 0, 28)), ArrayBuffer(new Date(94, 0, 28))),
      new Worker(1113, "Ted Desk", ArrayBuffer(new Date(100, 0, 28), new Date(110, 0, 28)), ArrayBuffer(new Date(101, 0, 28))))
      
  def clockIn (ID : Int) {
    var newWorkers = Set.empty[Worker]
    for (a <- Workers) {
      if (ID != a.ID) {
        newWorkers += a
      } else {
        var b = a.clockIn
        newWorkers += new Worker(a.ID, a.name, b+= new Date(), a.clockOut)  
      }
    }
    Workers = newWorkers
  }
  
  def clockOut (ID : Int) {
    var newWorkers = Set.empty[Worker]
    for (a <- Workers) {
      if (ID != a.ID) {
        newWorkers += a
      } else {
        var b = a.clockOut
        newWorkers += new Worker(a.ID, a.name, a.clockIn, b+= new Date())  
      }
    }
    Workers = newWorkers
  }
  
  def isAtWork (ID : Int) : Boolean = {
    var worker : Worker = null
    for (a <- Workers) {
      if (ID == a.ID) {
        worker = a
      }
    }
    if (worker.clockIn.reverse.head.after(worker.clockOut.reverse.head)) {
      true
    } else {
      false
    }
  }
  
  def findWorkerByID (ID : Int) : Worker = {
    var worker : Worker = null
    for (a <- Workers) {
      if (ID == a.ID) {
        worker = a
      }
    }
    worker
  }
}