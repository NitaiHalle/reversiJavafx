package reversi;

public class Controller {
	private View v;
	private GameModel model;
	private Player player1;
	private Player player2;
	private boolean isCurPlayerHuman;
	private PlayerNum currentPlayerNum;
	//has game ended?
	boolean gameEnded;

	
	public Controller (GameModel model,View view, Player player1, Player player2) {
		this.model = model;
		this.v = view;
		this.player1 = new Player();
		this.player2 = new Player();
		this.gameEnded = false;
		this.currentPlayerNum = PlayerNum.PLAYER1;
	}
	
	public void beginGame() {
		Pos lastMove = new Pos(0, 0);
		gameEnded = false;
		while (!gameEnded) {

			//both players can't move
			if (!model.isAbleToMove(PlayerNum.PLAYER1) && !model.isAbleToMove(PlayerNum.PLAYER2)) {
				v.drawBoard();
				gameEnded = true;
				continue;
			}

			//current player has no moves
			if (!model.isAbleToMove(currentPlayerNum)) {
				v.drawBoard();
				v.drawNoPossibleMoves(currentPlayerNum, lastMove);
				lastMove = new Pos(0, 0);
				switchCurrentPlayer();
				continue;
			}
			
			//current player has moves

			
			v.drawBoard();
			v.drawTurn(currentPlayerNum, lastMove);
			
			Pos wantedMove = new Pos(0,0);
			boolean moveValid = false;
			//loop until player selects a valid move
			do {
				wantedMove = getCurrentPlayer().makeMove(model);
				if (model.isPossibleMove(currentPlayerNum, wantedMove)) {
					moveValid = true;
				}
				else 
					v.drawMoveIsInvalid(wantedMove);			
			} while (!moveValid);

			//move is valid, lets place the piece
			model.place(currentPlayerNum, wantedMove);
			lastMove = wantedMove;
			switchCurrentPlayer();
		}

		//game has ended
		int score1 = model.calcScoreOf(PlayerNum.PLAYER1);
		int score2 = model.calcScoreOf(PlayerNum.PLAYER2);
		v.drawEndGame(score1, score2);
	}
	
	public void switchCurrentPlayer() {
		currentPlayerNum = (currentPlayerNum == PlayerNum.PLAYER1)? PlayerNum.PLAYER2 : PlayerNum.PLAYER1;
		//isCurPlayerHuman = (currentPlayerNum == PlayerNum.PLAYER1)? isPlayer1Human : isPlayer2Human;
	}
	public Player getCurrentPlayer() {
		return (currentPlayerNum == PlayerNum.PLAYER1) ? player1 : player2;
	}

}

