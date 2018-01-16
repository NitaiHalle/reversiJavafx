package reversi;

public interface View {
	
	public void drawBoard();
	//show the last move and ask the current player to make a move. the method assumes the player CAN move.
	public void drawTurn(PlayerNum player, Pos lastPlacePos);
	//show the last move and print that the current player has no possible moves
	public void drawNoPossibleMoves(PlayerNum player, Pos lastPlacePos);
	//selected move by current player is invalid
	public void drawMoveIsInvalid (Pos pos);
	//Endgame graphics. parameters represent the final score of each player.
	public void drawEndGame(int scoreP1, int scoreP2);
	public void setView(GameModel model); /*{
		m_model = &model;
	}*/

}
/*
public:
	//on construction - bind the view to the model for board drawing purposes (read-only)
	View(GameModel& model) : m_model(&model) {}
	virtual ~View() {}

	
protected:
	const GameModel* m_model;*/