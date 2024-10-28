def splitCollection(numbers: Seq[Int]): (Seq[Int], Seq[Int]) = {
    val evenSeq = numbers.filter(x => x%2==0)
    val oddSeq = numbers.filter(x => x%2!=0)
    return (evenSeq, oddSeq)
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