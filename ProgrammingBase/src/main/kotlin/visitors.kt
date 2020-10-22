
interface Node {
    fun <R> accept(visitor : Visitor<R>) : R
}

class nodePlus(val lson : Node? = null, val rson : Node? = null) : Node {
    override fun <R> accept(visitor: Visitor<R>): R {
        return visitor.visitNodePlus(this)
    }
}

class nodeMulti(val lson : Node? = null, val rson : Node? = null) : Node {
    override fun <R> accept(visitor: Visitor<R>): R {
        return visitor.visitNodeMulti(this)
    }
}

class nodeLit(val num : Int) : Node {
    override fun <R> accept(visitor: Visitor<R>): R {
        return visitor.visiNodeLit(this)
    }
}

interface Visitor<R> {
    fun visitNodePlus(node : nodePlus): R

    fun visitNodeMulti(node : nodeMulti): R

    fun visiNodeLit (node : nodeLit) : R
}

class PrintVisitor : Visitor<String> {
    override fun visitNodeMulti(node: nodeMulti): String {
        return "(${node.lson?.accept(this)} * ${node.rson?.accept(this)})"
    }

    override fun visitNodePlus(node: nodePlus): String {
        return "(${node.lson?.accept(this)} + ${node.rson?.accept(this)})"
    }

    override fun visiNodeLit(node: nodeLit): String {
        return node.num.toString()
    }
}

class CalculateVisitor : Visitor<Int?> {
    override fun visitNodeMulti(node: nodeMulti): Int? {
        return node.lson?.accept(this)?.times(node.rson?.accept(this)!!)
    }

    override fun visitNodePlus(node: nodePlus): Int? {
        return node.lson?.accept(this)?.plus((node.rson?.accept(this)!!))
    }

    override fun visiNodeLit(node: nodeLit): Int? {
        return node.num
    }
}

class ExpandVisitor : Visitor<List<String>> {
    override fun visitNodePlus(node: nodePlus): List<String> {
        val left = node.lson?.accept(this)
        val right = node.rson?.accept(this)
        val ret = mutableListOf<String>()
        if (left != null) {
            left.forEach { it -> ret.add(it) }
        }
        if (right != null) {
            right.forEach { it -> ret.add(it) }
        }
        return ret
    }

    override fun visitNodeMulti(node: nodeMulti): List<String> {
        val left = node.lson?.accept(this)
        val right = node.rson?.accept(this)
        val ret = mutableListOf<String>()
        left?.forEach { first ->
            right?.forEach { second ->
                ret.add("$first * $second")
            }
        }
        return ret
    }

    override fun visiNodeLit(node: nodeLit): List<String> {
        return listOf(node.num.toString())
    }

}

fun main() {
    val root : Node = nodeMulti(nodeLit(10), nodeMulti(nodePlus(nodeLit(2), nodeLit(3)), nodeLit(5)))
    val vis = PrintVisitor()
    val cal = CalculateVisitor()
    val exp = ExpandVisitor()
    println(root.accept(vis))
    println(root.accept(cal))
    val expRes = root.accept(exp)
    for (i in 0 until expRes.size){
        if (i > 0){
            print(" + ")
        }
        print(expRes[i])
    }
    println()
}
