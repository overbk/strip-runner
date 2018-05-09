object StripRunner extends App {
  case class State(boardSize: Int, players: List[Player], turn: Int) {
    val current: Player = players.head
    def advance: State = State(boardSize, players.tail :+ players.head, turn + 1)
  }

  private def initialize(): State = {
    IO.printWelcome()

    val boardSize = IO.requestBoardSize()
    val players = (1 to IO.requestNumberOfPlayers()).map(id => Player(id, IO.requestName(id))).toList

    State(boardSize, players, 1)
  }

  def processTurn(st: State): Unit = {
    IO.printTurnStart(st)
    val action = IO.chooseOnePrompt(st.current.actions, "action")
    processTurn(action.perform(st).advance)
  }

  processTurn(initialize())
}
