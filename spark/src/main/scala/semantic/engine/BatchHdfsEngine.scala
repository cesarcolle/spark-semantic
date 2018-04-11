package semantic.engine

import semantic.batch.Batch

class BatchHdfsEngine(batchList: List[Batch]) {

  def launchBatch(): Unit = {
    batchList.foreach(batch => {
      try {
        batch.launch()
      }
      catch {
        case exception : Exception => exception.printStackTrace()
        case ex : Throwable=> ex.printStackTrace()

      }

    })
  }

}
