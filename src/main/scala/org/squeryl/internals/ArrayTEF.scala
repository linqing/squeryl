package org.squeryl.internals

import java.sql.ResultSet

import org.squeryl.Session
import org.squeryl.dsl.{ArrayJdbcMapper, TypedExpressionFactory}
import scala.{Array => ScalaArray}
abstract class ArrayTEF[P, TE] extends TypedExpressionFactory[ScalaArray[P], TE] with ArrayJdbcMapper[java.sql.Array, ScalaArray[P]] {
  // must define "sample" that includes an element. e.g. ScalaArray[Int](0)
  def sample: ScalaArray[P]

  def toWrappedJDBCType(element: P): java.lang.Object

  def fromWrappedJDBCType(element: ScalaArray[java.lang.Object]): ScalaArray[P]

  val defaultColumnLength = 1

  //noinspection TypeAnnotation
  def extractNativeJdbcValue(rs: ResultSet, i: Int) = rs.getArray(i)

  def convertToJdbc(v: ScalaArray[P]): java.sql.Array = {
    val content: ScalaArray[java.lang.Object] = v.map(toWrappedJDBCType)
    val s = Session.currentSession
    val con = s.connection
    var rv: java.sql.Array = null
    try {
      //asInstanceOf required for 2.9.0-1 to compile
      val typ = s.databaseAdapter.arrayCreationType(sample(0).asInstanceOf[ {def getClass: Class[_]}].getClass)
      rv = con.createArrayOf(typ, content)
    } catch {
      case e: Exception => s.log("Cannot create JDBC array: " + e.getMessage)
    }
    rv
  }

  def convertFromJdbc(v: java.sql.Array): ScalaArray[P] = {
    val s = Session.currentSession
    var rv: ScalaArray[P] = sample.take(0)
    try {
      val obj = v.getArray()
      rv = fromWrappedJDBCType(obj.asInstanceOf[ScalaArray[java.lang.Object]])
    } catch {
      case e: Exception => s.log("Cannot obtain array from JDBC: " + e.getMessage)
    }
    rv
  }
}