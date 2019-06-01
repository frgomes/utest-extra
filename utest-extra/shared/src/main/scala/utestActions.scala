package utest.extra

trait utestActions {
  def ignored(action:  Unit): Unit = ignored
  def ignoredif(condition: Boolean)(action:  Unit): Unit = if(condition) ignored else action
  def pending(action:  Unit): Unit = {
    try {
      action
      pending("PENDING", new TestPendingException)
    } catch {
      case t: Throwable => pending("FAILED", new TestFailedException(t))
    }
  }

  private def ignored: Unit = {
    val t = new TestIgnoredException
    val it = t.getStackTrace.iterator
    val e = (1 to 5).map(_ => it.next).last
    report("IGNORED", e)
  }

  private def pending(state: String, t: Throwable): Unit = {
    val pos = t.getStackTrace.zipWithIndex.find(p => p._1.getMethodName == "pending").get._2
    val it = t.getStackTrace.iterator
    val e = (1 to pos+4).map(_ => it.next).last
    report(state, e)
  }

  private def report(state: String, e: StackTraceElement): Unit = {
    val _class  = e.getClassName
    val _method = e.getMethodName
    val _file   = e.getFileName
    val _line   = e.getLineNumber
    System.err.println(s"@@@@@@@@ ${state}:: ${_class}.${_method} at ${_file}:${_line}")
  }
}
class TestPendingException extends Exception
class TestIgnoredException extends Exception
class TestFailedException(cause: Throwable) extends Exception(cause)
