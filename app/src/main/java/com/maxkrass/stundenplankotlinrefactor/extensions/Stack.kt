package com.maxkrass.stundenplankotlinrefactor.extensions

/**
 * Max made this for StundenplanKotlinRefactor on 11.12.2017.
 */
data class Stack<T : Comparable<T>>(private val items: MutableList<T> = mutableListOf()) : MutableCollection<T>, Cloneable {
    override fun clone(): Stack<T> {
        return Stack(items)
    }

    override fun add(element: T): Boolean = items.add(element)

    override fun addAll(elements: Collection<T>): Boolean = items.addAll(elements)

    override fun remove(element: T): Boolean = items.remove(element)

    override fun removeAll(elements: Collection<T>): Boolean = items.removeAll(elements)

    override fun retainAll(elements: Collection<T>): Boolean = items.retainAll(elements)

    override val size: Int
        get() = this.items.count()

    override fun clear() {
        items.clear()
    }

    operator fun get(index: Int): T = items[index]

    override fun contains(element: T): Boolean = items.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = items.containsAll(elements)

    override fun iterator(): MutableIterator<T> = items.iterator()

    override fun equals(other: Any?): Boolean = if (other is Stack<*>) {
        items == other.items
    } else {
        false
    }

    override fun isEmpty(): Boolean = items.isEmpty()

    fun push(element: T) {
        val position = size
        this.items.add(position, element)
    }

    override fun toString() = items.toString()

    fun pop(): T? {
        return if (this.isEmpty()) {
            null
        } else {
            val item = this.items.count() - 1
            this.items.removeAt(item)
        }
    }

    fun peek(): T? {
        return if (isEmpty()) {
            null
        } else {
            this.items[this.items.count() - 1]
        }
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

}