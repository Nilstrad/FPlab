import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine
import scala.util.{Try, Failure, Success}
import scala.concurrent.duration.Duration

//1.
def integral(f:Double => Double, l:Double, r:Double, step:Int) : Double ={
   val delta = (r-l)/step
   val values = Range.inclusive(0,step-1).map((a: Int) => delta/2 + delta*a).map((a:Double) => f(a)*delta)
   return values.reduce(_+_)
}


//2. 
// def goodEnoughPassword(password:String): Boolean
// Option

def hasNSymbols(a:String,set:String, n:Int): Boolean = a.filter(set.contains(_)).length() >= n

def goodEnoughPassword(password:String): Option[Boolean] = {
    val minLength = 8
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "1234567890"
    val specials = "!@#$%^&*()[]{};:,./<>?|"
    var requirements: Seq[Boolean] =Seq(password.length() >= minLength &&
                                        hasNSymbols(password,lowercase,1) &&
                                        hasNSymbols(password,uppercase,1) &&
                                        hasNSymbols(password,numbers,1) &&
                                        hasNSymbols(password,specials,1))
    val isValid = requirements.reduce(_ && _)
    if (isValid) Some(true) else None
}

//3.
//def goodEnoughPassword(password:String):Either[Boolean, String]
//Try

def goodEnoughPassword2(password: String): Either[Boolean, String] = {
    
    val minLength = 8
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "1234567890"
    val specials = "!@#$%^&*()[]{};:,./<>?|"
    Try{
    val requirements = Seq((password.length >= minLength, s"Пароль должен быть длиной хотя бы $minLength символов"),
                           (hasNSymbols(password, lowercase, 1), "Пароль должен содержать хотя бы одну строчную букву"),
                           (hasNSymbols(password, uppercase, 1), "Пароль должен содержать хотя бы одну заглавную букву"),
                           (hasNSymbols(password, numbers, 1), "Пароль должен содержать хотя бы одну цифру"),
                           (hasNSymbols(password, specials, 1), "Пароль должен содержать хотя бы один специальный символ")
)
   

    val errorMessage = requirements.collect {// https://www.geeksforgeeks.org/how-does-collect-function-work-in-scala/ 2 пример
        case (false, message) => message
    } 
    if (errorMessage.isEmpty) Left(true) else Right(errorMessage.mkString("\n"))
    } match {
        case Success(value) => value
        case Failure(exception) => Right("Какая-то ошибка")
    }

}

        

def readPassword():Future[String] = {
    val res = Future {
        var validPassword = false
        var password = ""

        while (!validPassword) {
            password = readLine("Please enter a password: ")

            goodEnoughPassword2(password) match{
                case Left(value) => 
                    println("Успех!")
                    validPassword = true
                case Right(errorMessage) => println(errorMessage)
            }
        }

        password
    }
    
    res.onComplete{
        case Success(value) => println(s"Введенный пароль: $value")
        case Failure(exception) => println(s"Произошла ошибка!")
    }
    res
}

@main def runApp = {
    val password1 = "StrongPass123!"
    val password2 = "weak"
    println(integral((x: Double) => x*2,0,2,20))

    println(goodEnoughPassword(password1)) // Some(true)
    println(goodEnoughPassword(password2)) // None
    println(goodEnoughPassword2(password1))
    println(goodEnoughPassword2(password2 ))
   
}

//3. Опишите свои типы Monad и  Functor, описывающие соответственно монады и функторы в наиболее общем виде.

//https://habr.com/ru/articles/490112/ , https://typelevel.org/cats/typeclasses/functor.html

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Monad[M[_]] extends Functor[M] {
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
    def unit[A](a: A): M[A]
}
