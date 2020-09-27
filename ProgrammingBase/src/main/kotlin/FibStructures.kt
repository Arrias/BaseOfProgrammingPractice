class FibIterator(private var currentElement: Int, private var prevElement : Int, private var id : Int, private val n : Int) : Iterator<Int> {
    override fun hasNext(): Boolean {
        return id < n
    }

    override fun next(): Int {
        val x = currentElement
        currentElement += prevElement
        prevElement = x
        id++
        return x
    }
}

class FibIterable(private val n: Int) : Iterable<Int> {
    override fun iterator(): Iterator<Int> {
        return FibIterator(1, 0, 0, n)
    }
}

class FibCollection(override val size: Int) : Collection<Int> {
    override fun contains(element: Int): Boolean {
        for (i in FibIterable(size))
            if (i == element)
                return true
        return false
    }

    override fun containsAll(elements: Collection<Int>): Boolean {
        for (element in elements){
            if (!contains((element)))
                return false
        }
        return true
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun iterator(): Iterator<Int> {
        return FibIterator(1, 0, 0, size)
    }
}

class FibList(private val Base: List<Int>) : List<Int> by Base {
    constructor(n: Int) : this(FibIterable(n).toList())
}

fun main() {
    val n = 15
    for (i in FibIterable(n)) {
        println(i)
    }
    var set = FibList(n)
    println(set.isEmpty())
    println(set.indexOf(1))
    println(set.lastIndexOf(1))
    println(set.get(5))
}
