package part1recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaRecap extends App {

  // values and variables
  val aBoolean: Boolean = false

  // expressions
  val anIfExpression = if(2 > 3) "bigger" else "smaller"

  // instructions vs expressions(instructions are represented as Unit in Scala , expressions can be evaluated in functiona l prog)
  val theUnit = println("Hello, Scala") // Unit = "no meaningful value" = void in other languages

  // functions
  def myFunction(x: Int) = 42

  // OOP
  class Animal
  class Cat extends Animal
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("Crunch!")
  }

  // singleton pattern - in single line we define an instance of the
  object MySingleton

  // companions
  object Carnivore //in the same scope we have the class and the object - they can access each other's private members

  // generics
  trait MyList[A]

  // method notation
  val x = 1 + 2
  val y = 1.+(2)// plus is a method

  // Functional Programming
  val incrementer: Int => Int = x => x + 1//anonymous function or lambda
  val incremented = incrementer(42)

  // map, flatMap, filter are called higher order functions - they can be received by functions as arguments
  val processedList = List(1,2,3).map(incrementer)

  // Pattern Matching
  val unknown: Any = 45
  val ordinal = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  // try-catch
  try {
    throw new NullPointerException
  } catch {
    case _: NullPointerException => "some returned value"
    case _: Throwable => "something else"
  }

  // Future
  import scala.concurrent.ExecutionContext.Implicits.global// global is an implicit value to run threads
  val aFuture = Future {
    // some expensive computation, runs on another thread
    42
  }
  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"I've found $meaningOfLife")
    case Failure(ex) => println(s"I have failed: $ex")
  }

  // Partial functions
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 56
    case _ => 999
  }

  // Implicits

  // auto-injection by the compiler
  def methodWithImplicitArgument(implicit x: Int) = x + 43
  implicit val implicitInt = 67
  val implicitCall = methodWithImplicitArgument

  // implicit conversions - implicit defs
  case class Person(name: String) {
    def greet = println(s"Hi, my name is $name")
  }

  implicit def fromStringToPerson(name: String) = Person(name)
  "Bob".greet // fromStringToPerson("Bob").greet

  // implicit conversion - implicit classes
  implicit class Dog(name: String) {
    def bark = println("Bark!")
  }
  "Lassie".bark

  /*
    - local scope
    - imported scope
    - companion objects of the types involved in the method call
   */

}
