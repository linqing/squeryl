package org.squeryl.internals

import org.squeryl.{Schema, View}

object PosoLifecycleEvent extends Enumeration {
  type PosoLifeCycleEvent = Value
  val BeforeInsert, AfterInsert,
  BeforeDelete, AfterDelete,
  BeforeUpdate, AfterUpdate,
  AfterSelect, Create = Value
}


class LifecycleEventInvoker(i: Iterable[LifecycleEvent], owner: View[_]) extends PosoLifecycleEventListener {

  import PosoLifecycleEvent._

  private val _beforeInsert = i.filter(_.e == BeforeInsert).map(_.callback)
  private val _afterInsert = i.filter(_.e == AfterInsert).map(_.callback)
  private val _beforeDelete = i.filter(_.e == BeforeDelete).map(_.callback)
  private val _afterDelete = i.filter(_.e == AfterDelete).map(_.callback)
  private val _beforeUpdate = i.filter(_.e == BeforeUpdate).map(_.callback)
  private val _afterUpdate = i.filter(_.e == AfterUpdate).map(_.callback)
  private val _afterSelect = i.filter(_.e == AfterSelect).map(_.callback)

  private val _factory: AnyRef => AnyRef = {

    val f = i.filter(_.e == Create).map(_.callback)
    if (f.size > 1) org.squeryl.internals.Utils.throwError(owner.name + " has more than one factory defined.")
    f.headOption.getOrElse((_: AnyRef) => {
      null
    })
  }

  override def hasBeforeDelete: Boolean = _beforeDelete != Nil

  override def hasAfterDelete: Boolean = _afterDelete != Nil

  private def applyFuncs(fs: Traversable[AnyRef => AnyRef], a: AnyRef) = {

    var res = a

    for (f <- fs) {
      res = f(res)
    }

    res
  }


  def create: AnyRef = _factory(null)

  def beforeInsert(a: AnyRef): AnyRef = applyFuncs(_beforeInsert, a)

  def afterInsert(a: AnyRef): AnyRef = applyFuncs(_afterInsert, a)

  def beforeDelete(a: AnyRef): AnyRef = applyFuncs(_beforeDelete, a)

  def afterDelete(a: AnyRef): AnyRef = applyFuncs(_afterDelete, a)

  def beforeUpdate(a: AnyRef): AnyRef = applyFuncs(_beforeUpdate, a)

  def afterUpdate(a: AnyRef): AnyRef = applyFuncs(_afterUpdate, a)

  def afterSelect(a: AnyRef): AnyRef = applyFuncs(_afterSelect, a)
}

trait BaseLifecycleEventPercursor {

  protected def createLCEMap[A](t: Traversable[View[_]], e: PosoLifecycleEvent.Value, f: A => A) =
    new LifecycleEvent(t, e, f.asInstanceOf[AnyRef => AnyRef])

  protected def createLCECall[A](t: Traversable[View[_]], e: PosoLifecycleEvent.Value, f: A => Unit): LifecycleEvent =
    createLCEMap[A](
      t,
      e,
      (ar: A) => {
        f(ar.asInstanceOf[A])
        ar
      }
    )
}

class PosoFactoryPercursorTable[A](target: View[_]) extends BaseLifecycleEventPercursor {
  def is(f: => A) = new LifecycleEvent(Seq(target), PosoLifecycleEvent.Create,
    (_: AnyRef) => {
      val a = f
      a.asInstanceOf[AnyRef]
    }
  )
}

class LifecycleEventPercursorTable[A](target: View[_], e: PosoLifecycleEvent.Value) extends BaseLifecycleEventPercursor {

  def call(f: A => Unit): LifecycleEvent = createLCECall(Seq(target), e, f)

  def map(a: A => A): LifecycleEvent = createLCEMap(Seq(target), e, a)
}

class LifecycleEventPercursorClass[A](target: Class[_], schema: Schema, e: PosoLifecycleEvent.Value) extends BaseLifecycleEventPercursor {

  def call(f: A => Unit): LifecycleEvent = createLCECall(schema.findAllTablesFor(target), e, f)

  def map(a: A => A): LifecycleEvent = createLCEMap(schema.findAllTablesFor(target), e, a)

}

class LifecycleEvent(val target: Traversable[View[_]], val e: PosoLifecycleEvent.Value, val callback: AnyRef => AnyRef)


trait PosoLifecycleEventListener {

  def hasBeforeDelete = false

  def hasAfterDelete = false

  def create: AnyRef

  def beforeInsert(a: AnyRef): AnyRef

  def afterInsert(a: AnyRef): AnyRef

  def beforeDelete(a: AnyRef): AnyRef

  def afterDelete(a: AnyRef): AnyRef

  def beforeUpdate(a: AnyRef): AnyRef

  def afterUpdate(a: AnyRef): AnyRef

  def afterSelect(a: AnyRef): AnyRef
}


object NoOpPosoLifecycleEventListener extends PosoLifecycleEventListener {
  def create: AnyRef = null

  def beforeInsert(a: AnyRef): AnyRef = a

  def afterInsert(a: AnyRef): AnyRef = a

  def beforeDelete(a: AnyRef): AnyRef = a

  def afterDelete(a: AnyRef): AnyRef = a

  def beforeUpdate(a: AnyRef): AnyRef = a

  def afterUpdate(a: AnyRef): AnyRef = a

  def afterSelect(a: AnyRef): AnyRef = a
}
