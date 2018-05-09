case class Player(id: Int,
                  name: String,
                  position: Int = 1,
                  actions: List[Action] = List(Forward, Seq(Forward, Choice(Backward, Forward))),
                  reagents: List[Reagent] = List())
