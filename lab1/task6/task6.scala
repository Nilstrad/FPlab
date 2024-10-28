def compose[A, B, C](f: B => C, g: A => B): A => C = {
    (x: A) => f(g(x)) 
}
def addOne(x: Int): Int = x + 1
def multiplyByTwo(x: Int): Int = x * 2

@main def runApp() = {
    val composedFunc = compose(multiplyByTwo,addOne)
    val result = composedFunc(4)
    println(result)
}