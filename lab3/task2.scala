import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object ActorServer:
    case class Message(a: Int, b: Int, replyTo: ActorRef[Int])
    def apply(): Behavior[Message] = Behaviors.receive { (context, message) =>
        context.log.info(message.a.toString() + ' ' + message.b.toString() + '\n')
        message.replyTo ! message.a + message.b
        Behaviors.same
    }

object ActorClient:
    def apply(serverRef: ActorRef[ActorServer.Message]): Behavior[Int] = Behaviors.setup { (context) =>
        def sendMessage(): Unit = {
            val a = Random.between(0,1000)
            val b = Random.between(0,1000)
            serverRef ! ActorServer.Message(a, b, context.self)
        }

        sendMessage()

        Behaviors.receiveMessage{ message => 
            context.log.info(message.toString() + '\n')
            sendMessage()
            Behaviors.same
        }
    }

object MyActorSystem:
    case class Message()
    def apply(): Behavior[Message] = Behaviors.setup{ (context) =>
        val server = context.spawn(ActorServer(),"server")
        val client = context.spawn(ActorClient(server),"client")
        
        Behaviors.empty
    }

@main def main(): Unit = {
    val actorSystem: ActorSystem[MyActorSystem.Message] = ActorSystem(MyActorSystem(), "actorSystem")
}