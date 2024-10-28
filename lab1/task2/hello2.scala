def  printHelloSeveralTimes(n: Int):Unit = {
    for (i <- 0 until n) {
        val x = if (i%2 == 0) i else n-i
        println(s"hello $x")

    }
}

@main def runApp(): Unit = {
    printHelloSeveralTimes(10)
}


