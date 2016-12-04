/** *****************************************************************************
  * Copyright 2010 Maxime Lévesque
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  * **************************************************************************** */
package org.squeryl


import java.sql
import java.sql.{Date, Timestamp}
import java.util.{UUID, Date => JDate}

import org.squeryl.dsl._
import org.squeryl.dsl.ast._
import org.squeryl.internals.{ArrayTEF, FieldMapper}

@deprecated("the PrimitiveTypeMode companion object is deprecated, you should define a mix in the trait for your application. See : http://squeryl.org/0.9.6.html",
  "0.9.6")
object PrimitiveTypeMode extends PrimitiveTypeMode

private[squeryl] object InternalFieldMapper extends PrimitiveTypeMode

trait PrimitiveTypeMode extends QueryDsl with FieldMapper {


  // =========================== Non Numerical =========================== 
  implicit val stringTEF: TypedExpressionFactory[String, TString] with PrimitiveJdbcMapper[String] = PrimitiveTypeSupport.stringTEF
  implicit val optionStringTEF: TypedExpressionFactory[Option[String], TOptionString] with DeOptionizer[String, String, TString, Option[String], TOptionString] = PrimitiveTypeSupport.optionStringTEF
  implicit val dateTEF: TypedExpressionFactory[JDate, TDate] with PrimitiveJdbcMapper[JDate] = PrimitiveTypeSupport.dateTEF
  implicit val optionDateTEF: TypedExpressionFactory[Option[JDate], TOptionDate] with DeOptionizer[JDate, JDate, TDate, Option[JDate], TOptionDate] = PrimitiveTypeSupport.optionDateTEF
  implicit val sqlDateTEF: TypedExpressionFactory[sql.Date, TDate] with PrimitiveJdbcMapper[sql.Date] = PrimitiveTypeSupport.sqlDateTEF
  implicit val optionSqlDateTEF: TypedExpressionFactory[Option[Date], TOptionDate] with DeOptionizer[Date, Date, TDate, Option[Date], TOptionDate] = PrimitiveTypeSupport.optionSqlDateTEF
  implicit val timestampTEF: TypedExpressionFactory[Timestamp, TTimestamp] with PrimitiveJdbcMapper[Timestamp] = PrimitiveTypeSupport.timestampTEF
  implicit val optionTimestampTEF: TypedExpressionFactory[Option[Timestamp], TOptionTimestamp] with DeOptionizer[Timestamp, Timestamp, TTimestamp, Option[Timestamp], TOptionTimestamp] = PrimitiveTypeSupport.optionTimestampTEF
  implicit val doubleArrayTEF: ArrayTEF[Double, TDoubleArray] = PrimitiveTypeSupport.doubleArrayTEF
  implicit val intArrayTEF: ArrayTEF[Int, TIntArray] = PrimitiveTypeSupport.intArrayTEF
  implicit val longArrayTEF: ArrayTEF[Long, TLongArray] = PrimitiveTypeSupport.longArrayTEF
  implicit val stringArrayTEF: ArrayTEF[String, TStringArray] = PrimitiveTypeSupport.stringArrayTEF

  // =========================== Numerical Integral =========================== 
  implicit val byteTEF: IntegralTypedExpressionFactory[Byte, TByte, Float, TFloat] with PrimitiveJdbcMapper[Byte] = PrimitiveTypeSupport.byteTEF
  implicit val optionByteTEF: IntegralTypedExpressionFactory[Option[Byte], TOptionByte, Option[Float], TOptionFloat] with DeOptionizer[Byte, Byte, TByte, Option[Byte], TOptionByte] = PrimitiveTypeSupport.optionByteTEF
  implicit val intTEF: IntegralTypedExpressionFactory[Int, TInt, Float, TFloat] with PrimitiveJdbcMapper[Int] = PrimitiveTypeSupport.intTEF
  implicit val optionIntTEF: IntegralTypedExpressionFactory[Option[Int], TOptionInt, Option[Float], TOptionFloat] with DeOptionizer[Int, Int, TInt, Option[Int], TOptionInt] = PrimitiveTypeSupport.optionIntTEF
  implicit val longTEF: IntegralTypedExpressionFactory[Long, TLong, Double, TDouble] with PrimitiveJdbcMapper[Long] = PrimitiveTypeSupport.longTEF
  implicit val optionLongTEF: IntegralTypedExpressionFactory[Option[Long], TOptionLong, Option[Double], TOptionDouble] with DeOptionizer[Long, Long, TLong, Option[Long], TOptionLong] = PrimitiveTypeSupport.optionLongTEF

  // =========================== Numerical Floating Point ===========================   
  implicit val floatTEF: FloatTypedExpressionFactory[Float, TFloat] with PrimitiveJdbcMapper[Float] = PrimitiveTypeSupport.floatTEF
  implicit val optionFloatTEF: FloatTypedExpressionFactory[Option[Float], TOptionFloat] with DeOptionizer[Float, Float, TFloat, Option[Float], TOptionFloat] = PrimitiveTypeSupport.optionFloatTEF
  implicit val doubleTEF: FloatTypedExpressionFactory[Double, TDouble] with PrimitiveJdbcMapper[Double] = PrimitiveTypeSupport.doubleTEF
  implicit val optionDoubleTEF: FloatTypedExpressionFactory[Option[Double], TOptionDouble] with DeOptionizer[Double, Double, TDouble, Option[Double], TOptionDouble] = PrimitiveTypeSupport.optionDoubleTEF
  implicit val bigDecimalTEF: FloatTypedExpressionFactory[BigDecimal, TBigDecimal] with PrimitiveJdbcMapper[BigDecimal] = PrimitiveTypeSupport.bigDecimalTEF
  implicit val optionBigDecimalTEF: FloatTypedExpressionFactory[Option[BigDecimal], TOptionBigDecimal] with DeOptionizer[BigDecimal, BigDecimal, TBigDecimal, Option[BigDecimal], TOptionBigDecimal] = PrimitiveTypeSupport.optionBigDecimalTEF

  type TE[A, B] = TypedExpression[A, B]

  implicit def stringToTE(s: String): TE[String, TString] = stringTEF.create(s)

  implicit def optionStringToTE(s: Option[String]): TE[Option[String], TOptionString] = optionStringTEF.create(s)

  implicit def dateToTE(s: JDate): TE[JDate, TDate] = dateTEF.create(s)

  implicit def optionDateToTE(s: Option[JDate]): TE[Option[JDate], TOptionDate] = optionDateTEF.create(s)

  implicit def timestampToTE(s: Timestamp): TE[Timestamp, TTimestamp] = timestampTEF.create(s)

  implicit def optionTimestampToTE(s: Option[Timestamp]): TE[Option[Timestamp], TOptionTimestamp] = optionTimestampTEF.create(s)

  implicit def booleanToTE(s: Boolean): TE[Boolean, TBoolean] = PrimitiveTypeSupport.booleanTEF.create(s)

  implicit def optionBooleanToTE(s: Option[Boolean]): TE[Option[Boolean], TOptionBoolean] = PrimitiveTypeSupport.optionBooleanTEF.create(s)

  implicit def uuidToTE(s: UUID): TE[UUID, TUUID] = PrimitiveTypeSupport.uuidTEF.create(s)

  implicit def optionUUIDToTE(s: Option[UUID]): TE[Option[UUID], TOptionUUID] = PrimitiveTypeSupport.optionUUIDTEF.create(s)

  implicit def binaryToTE(s: Array[Byte]): TE[Array[Byte], TByteArray] = PrimitiveTypeSupport.binaryTEF.create(s)

  implicit def optionByteArrayToTE(s: Option[Array[Byte]]): TE[Option[Array[Byte]], TOptionByteArray] = PrimitiveTypeSupport.optionByteArrayTEF.create(s)

  implicit def enumValueToTE[A >: Enumeration#Value <: Enumeration#Value](e: A): TE[A, TEnumValue[A]] =
    PrimitiveTypeSupport.enumValueTEF[A](e).create(e)

  implicit def optionEnumcValueToTE[A >: Enumeration#Value <: Enumeration#Value](e: Option[A]): TE[Option[A], TOptionEnumValue[A]] =
    PrimitiveTypeSupport.optionEnumValueTEF[A](e).create(e)

  implicit def byteToTE(f: Byte): TE[Byte, TByte] = byteTEF.create(f)

  implicit def optionByteToTE(f: Option[Byte]): TE[Option[Byte], TOptionByte] = optionByteTEF.create(f)

  implicit def intToTE(f: Int): TE[Int, TInt] = intTEF.create(f)

  implicit def optionIntToTE(f: Option[Int]): TE[Option[Int], TOptionInt] = optionIntTEF.create(f)

  implicit def longToTE(f: Long): TE[Long, TLong] = longTEF.create(f)

  implicit def optionLongToTE(f: Option[Long]): TE[Option[Long], TOptionLong] = optionLongTEF.create(f)

  implicit def floatToTE(f: Float): TE[Float, TFloat] = floatTEF.create(f)

  implicit def optionFloatToTE(f: Option[Float]): TE[Option[Float], TOptionFloat] = optionFloatTEF.create(f)

  implicit def doubleToTE(f: Double): TE[Double, TDouble] = doubleTEF.create(f)

  implicit def optionDoubleToTE(f: Option[Double]): TE[Option[Double], TOptionDouble] = optionDoubleTEF.create(f)

  implicit def bigDecimalToTE(f: BigDecimal): TE[BigDecimal, TBigDecimal] = bigDecimalTEF.create(f)

  implicit def optionBigDecimalToTE(f: Option[BigDecimal]): TE[Option[BigDecimal], TOptionBigDecimal] = optionBigDecimalTEF.create(f)

  implicit def doubleArrayToTE(f: Array[Double]): TE[Array[Double], TDoubleArray] = doubleArrayTEF.create(f)

  implicit def intArrayToTE(f: Array[Int]): TE[Array[Int], TIntArray] = intArrayTEF.create(f)

  implicit def longArrayToTE(f: Array[Long]): TE[Array[Long], TLongArray] = longArrayTEF.create(f)

  implicit def stringArrayToTE(f: Array[String]): TE[Array[String], TStringArray] = stringArrayTEF.create(f)


  implicit def logicalBooleanToTE(l: LogicalBoolean): TypedExpressionConversion[Boolean, TBoolean] =
    PrimitiveTypeSupport.booleanTEF.convert(l)

  type QVEN[A, B] = QueryValueExpressionNode[A, B]
  implicit def queryStringToTE(q: Query[String]): QVEN[String, TString] =
    new QVEN[String, TString](q.copy(asRoot = false, Nil).ast, stringTEF.createOutMapper)

  implicit def queryOptionStringToTE(q: Query[Option[String]]): QVEN[Option[String], TOptionString] =
    new QVEN[Option[String], TOptionString](q.copy(asRoot = false, Nil).ast, optionStringTEF.createOutMapper)

  implicit def queryStringGroupedToTE(q: Query[Group[String]]): QVEN[String, TString] =
    new QVEN[String, TString](q.copy(asRoot = false, Nil).ast, stringTEF.createOutMapper)

  implicit def queryOptionStringGroupedToTE(q: Query[Group[Option[String]]]): QVEN[Option[String], TOptionString] =
    new QVEN[Option[String], TOptionString](q.copy(asRoot = false, Nil).ast, optionStringTEF.createOutMapper)

  implicit def queryStringMeasuredToTE(q: Query[Measures[String]]): QVEN[String, TString] =
    new QVEN[String, TString](q.copy(asRoot = false, Nil).ast, stringTEF.createOutMapper)

  implicit def queryOptionStringMeasuredToTE(q: Query[Measures[Option[String]]]): QVEN[Option[String], TOptionString] =
    new QVEN[Option[String], TOptionString](q.copy(asRoot = false, Nil).ast, optionStringTEF.createOutMapper)

  implicit def queryDateToTE(q: Query[JDate]): QVEN[JDate, TDate] =
    new QVEN[JDate, TDate](q.copy(asRoot = false, Nil).ast, dateTEF.createOutMapper)

  implicit def queryOptionDateToTE(q: Query[Option[JDate]]): QVEN[Option[JDate], TOptionDate] =
    new QVEN[Option[JDate], TOptionDate](q.copy(asRoot = false, Nil).ast, optionDateTEF.createOutMapper)

  implicit def queryDateGroupedToTE(q: Query[Group[JDate]]): QVEN[JDate, TDate] =
    new QVEN[JDate, TDate](q.copy(asRoot = false, Nil).ast, dateTEF.createOutMapper)

  implicit def queryOptionDateGroupedToTE(q: Query[Group[Option[JDate]]]): QVEN[Option[JDate], TOptionDate] =
    new QVEN[Option[JDate], TOptionDate](q.copy(asRoot = false, Nil).ast, optionDateTEF.createOutMapper)

  implicit def queryDateMeasuredToTE(q: Query[Measures[JDate]]): QVEN[JDate, TDate] =
    new QVEN[JDate, TDate](q.copy(asRoot = false, Nil).ast, dateTEF.createOutMapper)

  implicit def queryOptionDateMeasuredToTE(q: Query[Measures[Option[JDate]]]): QVEN[Option[JDate], TOptionDate] =
    new QVEN[Option[JDate], TOptionDate](q.copy(asRoot = false, Nil).ast, optionDateTEF.createOutMapper)

  implicit def queryTimestampToTE(q: Query[Timestamp]): QVEN[Timestamp, TTimestamp] =
    new QVEN[Timestamp, TTimestamp](q.copy(asRoot = false, Nil).ast, timestampTEF.createOutMapper)

  implicit def queryOptionTimestampToTE(q: Query[Option[Timestamp]]): QVEN[Option[Timestamp], TOptionTimestamp] =
    new QVEN[Option[Timestamp], TOptionTimestamp](q.copy(asRoot = false, Nil).ast, optionTimestampTEF.createOutMapper)

  implicit def queryTimestampGroupedToTE(q: Query[Group[Timestamp]]): QVEN[Timestamp, TTimestamp] =
    new QVEN[Timestamp, TTimestamp](q.copy(asRoot = false, Nil).ast, timestampTEF.createOutMapper)

  implicit def queryOptionTimestampGroupedToTE(q: Query[Group[Option[Timestamp]]]): QVEN[Option[Timestamp], TOptionTimestamp] =
    new QVEN[Option[Timestamp], TOptionTimestamp](q.copy(asRoot = false, Nil).ast, optionTimestampTEF.createOutMapper)

  implicit def queryTimestampMeasuredToTE(q: Query[Measures[Timestamp]]): QVEN[Timestamp, TTimestamp] =
    new QVEN[Timestamp, TTimestamp](q.copy(asRoot = false, Nil).ast, timestampTEF.createOutMapper)

  implicit def queryOptionTimestampMeasuredToTE(q: Query[Measures[Option[Timestamp]]]): QVEN[Option[Timestamp], TOptionTimestamp] =
    new QVEN[Option[Timestamp], TOptionTimestamp](q.copy(asRoot = false, Nil).ast, optionTimestampTEF.createOutMapper)

  implicit def queryBooleanToTE(q: Query[Boolean]): QVEN[Boolean, TBoolean] =
    new QVEN[Boolean, TBoolean](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.booleanTEF.createOutMapper)

  implicit def queryOptionBooleanToTE(q: Query[Option[Boolean]]): QVEN[Option[Boolean], TOptionBoolean] =
    new QVEN[Option[Boolean], TOptionBoolean](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.optionBooleanTEF.createOutMapper)

  implicit def queryUUIDToTE(q: Query[UUID]): QVEN[UUID, TUUID] =
    new QVEN[UUID, TUUID](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.uuidTEF.createOutMapper)

  implicit def queryOptionUUIDToTE(q: Query[Option[UUID]]): QVEN[Option[UUID], TOptionUUID] =
    new QVEN[Option[UUID], TOptionUUID](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.optionUUIDTEF.createOutMapper)

  implicit def queryByteArrayToTE(q: Query[Array[Byte]]): QVEN[Array[Byte], TByteArray] =
    new QVEN[Array[Byte], TByteArray](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.binaryTEF.createOutMapper)

  implicit def queryOptionByteArrayToTE(q: Query[Option[Array[Byte]]]): QVEN[Option[Array[Byte]], TOptionByteArray] =
    new QVEN[Option[Array[Byte]], TOptionByteArray](q.copy(asRoot = false, Nil).ast, PrimitiveTypeSupport.optionByteArrayTEF.createOutMapper)

  implicit def queryByteToTE(q: Query[Byte]): QVEN[Byte, TByte] =
    new QVEN[Byte, TByte](q.copy(asRoot = false, Nil).ast, byteTEF.createOutMapper)

  implicit def queryOptionByteToTE(q: Query[Option[Byte]]): QVEN[Option[Byte], TOptionByte] =
    new QVEN[Option[Byte], TOptionByte](q.copy(asRoot = false, Nil).ast, optionByteTEF.createOutMapper)

  implicit def queryByteGroupedToTE(q: Query[Group[Byte]]): QVEN[Byte, TByte] =
    new QVEN[Byte, TByte](q.copy(asRoot = false, Nil).ast, byteTEF.createOutMapper)

  implicit def queryOptionByteGroupedToTE(q: Query[Group[Option[Byte]]]): QVEN[Option[Byte], TOptionByte] =
    new QVEN[Option[Byte], TOptionByte](q.copy(asRoot = false, Nil).ast, optionByteTEF.createOutMapper)

  implicit def queryByteMeasuredToTE(q: Query[Measures[Byte]]): QVEN[Byte, TByte] =
    new QVEN[Byte, TByte](q.copy(asRoot = false, Nil).ast, byteTEF.createOutMapper)

  implicit def queryOptionByteMeasuredToTE(q: Query[Measures[Option[Byte]]]): QVEN[Option[Byte], TOptionByte] =
    new QVEN[Option[Byte], TOptionByte](q.copy(asRoot = false, Nil).ast, optionByteTEF.createOutMapper)

  implicit def queryIntToTE(q: Query[Int]): QVEN[Int, TInt] =
    new QVEN[Int, TInt](q.copy(asRoot = false, Nil).ast, intTEF.createOutMapper)

  implicit def queryOptionIntToTE(q: Query[Option[Int]]): QVEN[Option[Int], TOptionInt] =
    new QVEN[Option[Int], TOptionInt](q.copy(asRoot = false, Nil).ast, optionIntTEF.createOutMapper)

  implicit def queryIntGroupedToTE(q: Query[Group[Int]]): QVEN[Int, TInt] =
    new QVEN[Int, TInt](q.copy(asRoot = false, Nil).ast, intTEF.createOutMapper)

  implicit def queryOptionIntGroupedToTE(q: Query[Group[Option[Int]]]): QVEN[Option[Int], TOptionInt] =
    new QVEN[Option[Int], TOptionInt](q.copy(asRoot = false, Nil).ast, optionIntTEF.createOutMapper)

  implicit def queryIntMeasuredToTE(q: Query[Measures[Int]]): QVEN[Int, TInt] =
    new QVEN[Int, TInt](q.copy(asRoot = false, Nil).ast, intTEF.createOutMapper)

  implicit def queryOptionIntMeasuredToTE(q: Query[Measures[Option[Int]]]): QVEN[Option[Int], TOptionInt] =
    new QVEN[Option[Int], TOptionInt](q.copy(asRoot = false, Nil).ast, optionIntTEF.createOutMapper)

  implicit def queryLongToTE(q: Query[Long]): QVEN[Long, TLong] =
    new QVEN[Long, TLong](q.copy(asRoot = false, Nil).ast, longTEF.createOutMapper)

  implicit def queryOptionLongToTE(q: Query[Option[Long]]): QVEN[Option[Long], TOptionLong] =
    new QVEN[Option[Long], TOptionLong](q.copy(asRoot = false, Nil).ast, optionLongTEF.createOutMapper)

  implicit def queryLongGroupedToTE(q: Query[Group[Long]]): QVEN[Long, TLong] =
    new QVEN[Long, TLong](q.copy(asRoot = false, Nil).ast, longTEF.createOutMapper)

  implicit def queryOptionLongGroupedToTE(q: Query[Group[Option[Long]]]): QVEN[Option[Long], TOptionLong] =
    new QVEN[Option[Long], TOptionLong](q.copy(asRoot = false, Nil).ast, optionLongTEF.createOutMapper)

  implicit def queryLongMeasuredToTE(q: Query[Measures[Long]]): QVEN[Long, TLong] =
    new QVEN[Long, TLong](q.copy(asRoot = false, Nil).ast, longTEF.createOutMapper)

  implicit def queryOptionLongMeasuredToTE(q: Query[Measures[Option[Long]]]): QVEN[Option[Long], TOptionLong] =
    new QVEN[Option[Long], TOptionLong](q.copy(asRoot = false, Nil).ast, optionLongTEF.createOutMapper)

  implicit def queryFloatToTE(q: Query[Float]): QVEN[Float, TFloat] =
    new QVEN[Float, TFloat](q.copy(asRoot = false, Nil).ast, floatTEF.createOutMapper)

  implicit def queryOptionFloatToTE(q: Query[Option[Float]]): QVEN[Option[Float], TOptionFloat] =
    new QVEN[Option[Float], TOptionFloat](q.copy(asRoot = false, Nil).ast, optionFloatTEF.createOutMapper)

  implicit def queryFloatGroupedToTE(q: Query[Group[Float]]): QVEN[Float, TFloat] =
    new QVEN[Float, TFloat](q.copy(asRoot = false, Nil).ast, floatTEF.createOutMapper)

  implicit def queryOptionFloatGroupedToTE(q: Query[Group[Option[Float]]]): QVEN[Option[Float], TOptionFloat] =
    new QVEN[Option[Float], TOptionFloat](q.copy(asRoot = false, Nil).ast, optionFloatTEF.createOutMapper)

  implicit def queryFloatMeasuredToTE(q: Query[Measures[Float]]): QVEN[Float, TFloat] =
    new QVEN[Float, TFloat](q.copy(asRoot = false, Nil).ast, floatTEF.createOutMapper)

  implicit def queryOptionFloatMeasuredToTE(q: Query[Measures[Option[Float]]]): QVEN[Option[Float], TOptionFloat] =
    new QVEN[Option[Float], TOptionFloat](q.copy(asRoot = false, Nil).ast, optionFloatTEF.createOutMapper)

  implicit def queryDoubleToTE(q: Query[Double]): QVEN[Double, TDouble] =
    new QVEN[Double, TDouble](q.copy(asRoot = false, Nil).ast, doubleTEF.createOutMapper)

  implicit def queryOptionDoubleToTE(q: Query[Option[Double]]): QVEN[Option[Double], TOptionDouble] =
    new QVEN[Option[Double], TOptionDouble](q.copy(asRoot = false, Nil).ast, optionDoubleTEF.createOutMapper)

  implicit def queryDoubleGroupedToTE(q: Query[Group[Double]]): QVEN[Double, TDouble] =
    new QVEN[Double, TDouble](q.copy(asRoot = false, Nil).ast, doubleTEF.createOutMapper)

  implicit def queryOptionDoubleGroupedToTE(q: Query[Group[Option[Double]]]): QVEN[Option[Double], TOptionDouble] =
    new QVEN[Option[Double], TOptionDouble](q.copy(asRoot = false, Nil).ast, optionDoubleTEF.createOutMapper)

  implicit def queryDoubleMeasuredToTE(q: Query[Measures[Double]]): QVEN[Double, TDouble] =
    new QVEN[Double, TDouble](q.copy(asRoot = false, Nil).ast, doubleTEF.createOutMapper)

  implicit def queryOptionDoubleMeasuredToTE(q: Query[Measures[Option[Double]]]): QVEN[Option[Double], TOptionDouble] =
    new QVEN[Option[Double], TOptionDouble](q.copy(asRoot = false, Nil).ast, optionDoubleTEF.createOutMapper)

  implicit def queryBigDecimalToTE(q: Query[BigDecimal]): QVEN[BigDecimal, TBigDecimal] =
    new QVEN[BigDecimal, TBigDecimal](q.copy(asRoot = false, Nil).ast, bigDecimalTEF.createOutMapper)

  implicit def queryOptionBigDecimalToTE(q: Query[Option[BigDecimal]]): QVEN[Option[BigDecimal], TOptionBigDecimal] =
    new QVEN[Option[BigDecimal], TOptionBigDecimal](q.copy(asRoot = false, Nil).ast, optionBigDecimalTEF.createOutMapper)

  implicit def queryBigDecimalGroupedToTE(q: Query[Group[BigDecimal]]): QVEN[BigDecimal, TBigDecimal] =
    new QVEN[BigDecimal, TBigDecimal](q.copy(asRoot = false, Nil).ast, bigDecimalTEF.createOutMapper)

  implicit def queryOptionBigDecimalGroupedToTE(q: Query[Group[Option[BigDecimal]]]): QVEN[Option[BigDecimal], TOptionBigDecimal] =
    new QVEN[Option[BigDecimal], TOptionBigDecimal](q.copy(asRoot = false, Nil).ast, optionBigDecimalTEF.createOutMapper)

  implicit def queryBigDecimalMeasuredToTE(q: Query[Measures[BigDecimal]]): QVEN[BigDecimal, TBigDecimal] =
    new QVEN[BigDecimal, TBigDecimal](q.copy(asRoot = false, Nil).ast, bigDecimalTEF.createOutMapper)

  implicit def queryOptionBigDecimalMeasuredToTE(q: Query[Measures[Option[BigDecimal]]]): QVEN[Option[BigDecimal], TOptionBigDecimal] =
    new QVEN[Option[BigDecimal], TOptionBigDecimal](q.copy(asRoot = false, Nil).ast, optionBigDecimalTEF.createOutMapper)

}
