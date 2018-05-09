import StripRunner.State
import IO.chooseOnePrompt
import Console._

sealed trait Action {
  def allowed(state: State): Boolean = true
  def perform(state: State): State

  private val bgColour = RED_B
  private val textColour = BLACK

  def println(s: String): Unit = System.out.println(s"$bgColour$textColour$s$RESET")
}

case object Forward extends Action {
  override def toString: String = "->"
  def perform(st: State): State = {
    println(s"${st.current.name} (player ${st.current.id}) hops to tile ${st.current.position + 1}.")
    State(st.boardSize, st.current.copy(position = st.current.position + 1) :: st.players.tail, st.turn)
  }
}

case object Backward extends Action {
  override def toString: String = "<-"
  def perform(st: State): State = {
    println(s"${st.current.name} (player ${st.current.id}) hops to tile ${st.current.position - 1}.")
    State(st.boardSize, st.current.copy(position = st.current.position - 1) :: st.players.tail, st.turn)
  }
}

case class Seq(x: Action, y: Action) extends Action {
  override def toString: String = s"($x . $y)"
  def perform(state: State): State = y.perform(x.perform(state))
}

case class Choice(x: Action, y: Action) extends Action {
  override def toString: String = s"($x + $y)"
  def perform(state: State): State = chooseOnePrompt(List(x,y), "action").perform(state)
}