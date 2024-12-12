import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.GreeterMain.SayHello

//актор, который приветствует пользователя и отправляет ответное сообщение
object Greeter {
  // Сообщение для приветствия: содержит имя пользователя и ссылку на актора для ответа
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  //ответ после приветствия
  final case class Greeted(whom: String, from: ActorRef[Greet])

  // Поведение актора Greeter
  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    // Логируем сообщение с именем
    context.log.info("Привет, {}!", message.whom)
    // Отправляем ответное сообщение Greeted
    message.replyTo ! Greeted(message.whom, context.self)
    // Поведение без изменений
    Behaviors.same
  }
}

object GreeterBot {

  //бот с максимальным количеством приветствий
  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  // Рекурсивная функция для обработки состояния бота
  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      // Увеличиваем счетчик приветствий
      val n = greetingCounter + 1
      // Логируем текущее приветствие и имя пользователя
      context.log.info("Приветствие {} для {}", n, message.whom)
      // Если достигли максимума, останавливаем актора
      if (n == max) {
        Behaviors.stopped // Остановка актороа
      } else {
        // Отправляем сообщение обратно Greeter
        message.from ! Greeter.Greet(message.whom, context.self)
        // Увеличил n
        bot(n, max)
      }
    }
}


object GreeterMain { // Тот самый актор создающий другие акторы

  // Сообщение для запуска приветствия
  final case class SayHello(name: String)

  // Поведение главного актора
  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      // Создаем актора Greeter
      val greeter = context.spawn(Greeter(), "greeter")

      // Обработка сообщений SayHello
      Behaviors.receiveMessage { message =>
        // Создаем актора GreeterBot 
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)
        greeter ! Greeter.Greet(message.name, replyTo)
        // Поведение без изменений
        Behaviors.same
      }
    }
}

object AkkaQuickstart extends App {
  val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")

  greeterMain ! SayHello("Charles")
}
