
fun Int.isPrimeFunction() : Int{
    if (this < 2) return 0
    var i = 2
    while (i * i <= this){
        if (this % i == 0)
            return 0
        i++
    }
    return 1
}

val Int.isPrime: Int
    get() = this.isPrimeFunction()

fun main(){
    println(15.isPrime)
}