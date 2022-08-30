package com.keygenqt.core.db

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import kotlin.reflect.KProperty

/**
 * Custom sub delegate for relations sub query with condition
 */
abstract class IntSubQueryEntityClass<out E : IntEntity> constructor(
    table: IdTable<Int>,
    entityType: Class<E>? = null,
    entityCtor: ((EntityID<Int>) -> E)? = null,
) : EntityClass<Int, E>(table, entityType, entityCtor) {

    var selectCountCondition: HashMap<KProperty<*>, Op<Boolean>> = hashMapOf()
    var isHasCondition: HashMap<KProperty<*>, Op<Boolean>> = hashMapOf()

    /**
     * Delegate check count != 0
     */
    class IsHas(val column: Column<EntityID<Int>>) {
        operator fun getValue(thisRef: IntEntity, property: KProperty<*>): Boolean {
            (thisRef.klass as IntSubQueryEntityClass).isHasCondition[property]?.let {
                return column.table.select { (column eq thisRef.id and it) }.count() > 0
            } ?: run {
                return column.table.select { (column eq thisRef.id) }.count() > 0
            }
        }
    }

    /**
     * Delegate get count sub select
     */
    class SelectCount(val column: Column<EntityID<Int>>) {
        operator fun getValue(thisRef: IntEntity, property: KProperty<*>): Long {
            (thisRef.klass as IntSubQueryEntityClass).selectCountCondition[property]?.let {
                return column.table.select { (column eq thisRef.id and it) }.count()
            } ?: run {
                return column.table.select { (column eq thisRef.id) }.count()
            }
        }
    }

    fun isHas(prop: KProperty<*>, op: SqlExpressionBuilder.() -> Op<Boolean>): IntSubQueryEntityClass<E> {
        isHasCondition[prop] = SqlExpressionBuilder.op()
        return this
    }

    fun selectCount(prop: KProperty<*>, op: SqlExpressionBuilder.() -> Op<Boolean>): IntSubQueryEntityClass<E> {
        selectCountCondition[prop] = SqlExpressionBuilder.op()
        return this
    }

    companion object {
        infix fun Boolean.Companion.isHas(column: Column<EntityID<Int>>) = IsHas(column)
        infix fun Long.Companion.selectCount(column: Column<EntityID<Int>>) = SelectCount(column)
    }
}