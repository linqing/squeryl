package org.squeryl.dsl.boilerplate

import org.squeryl.dsl.ast.GroupArg
import org.squeryl.dsl.fsm.{GroupQueryYield, QueryElements, GroupByState}

trait GroupBySignatures {
  self: QueryElements =>

  def groupBy[T1](e1: =>GroupArg[T1]): GroupByState[T1] =
    new GroupQueryYield[T1](this,
      ()=>List(e1)
    )

  def groupBy[T1,T2](e1: =>GroupArg[T1], e2: =>GroupArg[T2]): GroupByState[(T1,T2)] =
    new GroupQueryYield[(T1,T2)](this,
      ()=>List(e1, e2)
    )

  def groupBy[T1,T2,T3](e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3]): GroupByState[(T1,T2,T3)] =
    new GroupQueryYield[(T1,T2,T3)](this,
      ()=>List(e1, e2, e3)
    )

  def groupBy[T1,T2,T3,T4](e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3], e4: =>GroupArg[T4]): GroupByState[(T1,T2,T3,T4)] =
    new GroupQueryYield[(T1,T2,T3,T4)](this,
      ()=>List(e1, e2, e3, e4)
    )

  def groupBy[T1,T2,T3,T4,T5](e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3], e4: =>GroupArg[T4], e5: =>GroupArg[T5]): GroupByState[(T1,T2,T3,T4,T5)] =
    new GroupQueryYield[(T1,T2,T3,T4,T5)](this,
      ()=>List(e1, e2, e3, e4, e5)
    )

  def groupBy[T1,T2,T3,T4,T5,T6]
    (e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3], e4: =>GroupArg[T4],
     e5: =>GroupArg[T5], e6: =>GroupArg[T6]):
     GroupByState[(T1,T2,T3,T4,T5,T6)] =
    new GroupQueryYield[(T1,T2,T3,T4,T5,T6)](this,
      ()=>List(e1, e2, e3, e4, e5, e6)
    )

  def groupBy[T1,T2,T3,T4,T5,T6,T7]
    (e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3], e4: =>GroupArg[T4],
     e5: =>GroupArg[T5], e6: =>GroupArg[T6], e7: =>GroupArg[T7]):
     GroupByState[(T1,T2,T3,T4,T5,T6,T7)] =
    new GroupQueryYield[(T1,T2,T3,T4,T5,T6,T7)](this,
      ()=>List(e1, e2, e3, e4, e5, e6, e7)
    )

  def groupBy[T1,T2,T3,T4,T5,T6,T7,T8]
    (e1: =>GroupArg[T1], e2: =>GroupArg[T2], e3: =>GroupArg[T3], e4: =>GroupArg[T4],
     e5: =>GroupArg[T5], e6: =>GroupArg[T6], e7: =>GroupArg[T7], e8: =>GroupArg[T8]):
     GroupByState[(T1,T2,T3,T4,T5,T6,T7,T8)] =
    new GroupQueryYield[(T1,T2,T3,T4,T5,T6,T7,T8)](this,
      ()=>List(e1, e2, e3, e4, e5, e6, e7, e8)
    )
}