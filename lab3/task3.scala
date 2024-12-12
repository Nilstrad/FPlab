import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.util.Try

import scala.math.*

object IntegrateActor:
    case class IntegrateTask(integral: DefiniteIntegral, replyTo: ActorRef[Double])
    
    def apply(): Behavior[IntegrateTask] = Behaviors.receive { (context, message) =>
        message match
            case IntegrateTask(DefiniteIntegral(f, l, r, steps), replyTo) => {
                    val n = steps*2
                    val step: Double = (r-l)/n
                    
                    val values = Seq(f(l),
                                     Range.inclusive(1,n/2-1)
                                          .map(j => l + step*2*j)
                                          .map(x => f(x))
                                          .sum * 4,
                                     Range.inclusive(1,n/2)
                                          .map(j => l + step*2*(j - 1))
                                          .map(x => f(x))
                                          .sum * 2,
                                     f(r)) 
                    
                    replyTo ! values.sum*(step/3)
                }
        Behaviors.same
    }

object SumActor:
    def apply(maxSteps: Int, replyTo: ActorRef[Double]):Behavior[Double] = Behaviors.setup { (context) =>
        receiver(0.0, maxSteps, replyTo)
    }
    
    private def receiver(currentSum: Double, remainingSteps: Int, replyTo: ActorRef[Double]): Behavior[Double] =
        Behaviors.receiveMessage { partialResult =>
            if remainingSteps > 1 
                then {
                    receiver(currentSum + partialResult, remainingSteps - 1, replyTo)
                }
                else {
                    replyTo ! currentSum + partialResult
                    Behaviors.stopped
                }
        }

object DoubleLogger: 
    def apply():Behavior[Double] = Behaviors.receive { (context, message) =>
        context.log.info("received Double: " + message.toString())
        Behaviors.same
    }

object IntegrateSystem:
    case class Message(integral: DefiniteIntegral, taskCount: Int, replyTo: ActorRef[Double])
    
    def apply():Behavior[Message] = Behaviors.setup { (context) => 
        val integrateActors = Seq(context.spawn(IntegrateActor(), "integrateActor0"),
                                  context.spawn(IntegrateActor(), "integrateActor1"),
                                  context.spawn(IntegrateActor(), "integrateActor2"),
                                  context.spawn(IntegrateActor(), "integrateActor3"))
        val countOfIntegrateActors = integrateActors.length
        var countOfMessages = 0
        Behaviors.receiveMessage { (message) =>
            message match
                case Message(DefiniteIntegral(f, l, r, i), taskCount, replyTo) => {

                    val sumActor = context.spawn(SumActor(taskCount,replyTo), "sumActor" + countOfMessages.toString())
                    countOfMessages += 1
                    
                    val step: Double = (r-l)/t
                    for (i2 <- Range(0,taskCount)) {
                        integrateActors(i2 % countOfIntegrateActors) ! IntegrateActor.Message(DefiniteIntegral(f,l+i2*step,l+(i2+1)*step,i),
                                                                                          sumActor)
                    }
                }
            Behaviors.same
        }
    }

case class DefiniteIntegral(f: Double=>Double,l: Double,r: Double,i: Int)

@main def main(): Unit = {
    val integrateSystem: ActorSystem[IntegrateSystem.Message] = ActorSystem(IntegrateSystem(), "integrateSystem")
    val doubleLogger: ActorSystem[Double] = ActorSystem(DoubleLogger(), "doubleLogger")

    integrateSystem ! IntegrateSystem.Message(DefiniteIntegral(x => sin(x),0,Pi*2,100),100,doubleLogger)
    integrateSystem ! IntegrateSystem.Message(DefiniteIntegral(x => x*0.5,0,2,100),100,doubleLogger)
}