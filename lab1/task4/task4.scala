def squaredNumbers(numbers: Seq[Int]): Seq[Int] = {
    numbers.map(x => x * x)
}

@main def runApp(): Unit = {
    val numbers = Seq(1, 2, 3, 4, 5, 6)

    val squareFunc = squaredNumbers
   
    val squareNumbersResult = squareFunc(numbers)

    println(s"Квадраты: $squareNumbersResult")
    println(s"Адрес функции: ${squareFunc.toString}") 
}