package semantic.batch


trait Batch {
  val fetch: FetchData

  def launch()

}
