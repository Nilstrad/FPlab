def splitCollection(numbers: Seq[Int]): (Seq[Int], Seq[Int]) = {
     val evenSeq = numbers.zipWithIndex.filter { case (_, index) => index % 2 == 0 }.map { case (value, _) => value }
  val oddSeq = numbers.zipWithIndex.filter { case (_, index) => index % 2 != 0 }.map { case (value, _) => value }
  (evenSeq, oddSeq)
}

def findMax(numbers: Seq[Int]): Int = {
    numbers.reduce((max, x) => if(x > max) x else max)
    
}

def squaredNumbers(numbers: Seq[Int]): (Seq[Int]) = {
    val squaredNumbers = numbers.map(x => x*x)
    return squaredNumbers   
}

@main def runApp(): Unit = {
    val numbers = Seq(1, 2, 3, 4, 5, 6)
    val (evenSeq, oddSeq) = splitCollection(numbers)
    println(s"Четные: $evenSeq")
    println(s"Нечетные: $oddSeq")
    val squareNumbers = squaredNumbers(numbers)
    println(s"Квадраты $squareNumbers")
    val maximum = findMax(numbers)
    println(maximum)
}