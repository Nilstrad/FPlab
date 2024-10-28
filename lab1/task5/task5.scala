def matchTest(x: Int): String = x match
  case 1 => "one"
  case 2 => "two"
  case _ => "other"

@main def runApp() = {
  println(matchTest(10))
  println(matchTest(1))
  println(matchTest(2))
}