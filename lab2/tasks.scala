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
    val isValid = password.length() >= minLength &&
                  hasNSymbols(password,lowercase,1) &&
                  hasNSymbols(password,uppercase,1) &&
                  hasNSymbols(password,numbers,1) &&
                  hasNSymbols(password,specials,1)
    if (isValid) Some(true) else None
}

//3.
//def goodEnoughPassword(password:String):Either[Boolean, String]
//Try

def goodEnoughPassword2(password: String): Either[Boolean, String] = {
    Try {
    val minLength = 8
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "1234567890"
    val specials = "!@#$%^&*()[]{};:,./<>?|"

    val result: Try[(Boolean, Boolean, Boolean, Boolean, Boolean)] = Try {
        (password.length >= minLength, 
    hasNSymbols(password, lowercase, 1), 
    hasNSymbols(password, uppercase, 1), 
    hasNSymbols(password, numbers, 1), 
    hasNSymbols(password, specials, 1))}

    result match
    case Success((true, true, true, true, true)) => Left(true) 
    case Success((false,_,_,_,_)) => Right(s"Длина пароля должна быть не менее $minLength символов")
    case Success((_,false,_,_,_)) => Right("Пароль должен содержать хотя бы одну строчную букву")
    case Success((_,_,false,_,_)) => Right("Пароль должен содержать хотя бы одну заглавную букву")
    case Success((_,_,_,false,_)) => Right("Пароль должен содержать хотя бы одну цифру")
    case Success((_,_,_,_,false)) => Right("Пароль должен содержать хотя бы один специальный символ")
    case Failure(_) => Right("Какая-то ошибка")
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
