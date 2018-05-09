import StripRunner.State
import scala.util.{Failure, Success, Try}
import scala.io.StdIn.{readInt, readLine}

object IO {
  private def genericPrompt[T](prompt: String,
                               inputHandler: => T,
                               constraint: T => Boolean = (_: T) => true): T = {
    println(prompt)
    print("> ")
    Try(inputHandler) match {
      case Failure(_) => print("I could not parse your input. ")
      case Success(v) => if (constraint(v)) {
        return v
      } else {
        print("I parsed your input, but it was not sane. ")
      }
    }

    genericPrompt(prompt, inputHandler, constraint)
  }

  def chooseOnePrompt[T](options: List[T], description: String = "option"): T = {
    val head = s"Choose one of the following ${description}s:"
    val prompt = (head :: options.zipWithIndex.map{case (o, i) => s"  ${i+1}: $o"}).mkString("\n")
    val constraint = (i: Int) => 0 < i && i <= options.length
    val choice = genericPrompt(prompt, readInt, constraint)

    options(choice - 1)
  }

  def printWelcome(): Unit = println("Welcome to StripRunner!")

  def requestBoardSize(): Int = genericPrompt("What size should the board be?", readInt)
  def requestNumberOfPlayers(): Int = genericPrompt("How many players will be playing?", readInt)
  def requestName(playerID: Int): String = genericPrompt(s"Player $playerID, what is your name?", readLine)

  def printTurnStart(st: State): Unit = {
    println("===")
    println(s"We are at turn ${st.turn}.")
    println(s"${st.current.name} (player ${st.current.id}), it is your turn.")
    println(s"You are located at tile ${st.current.position} out of ${st.boardSize}.")
  }
}
