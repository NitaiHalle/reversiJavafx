package reversi;

import java.util.ArrayList;

public class GameModel {
	private Board board;
	private ArrayList <Pos> possibleMovesPlayer1 = new ArrayList();
	private ArrayList <Pos> possibleMovesPlayer2 = new ArrayList();
	private enum Direction {
		NORTH,
		SOUTH,
		WEST,
		EAST,
		NW,
		NE,
		SE,
		SW
	};
	
	public static int DEFAULT_BOARD_SIZE = 8;
	public static int MAX_BOARD_SIZE = Board.MAX_BOARD_SIZE;
	
	public GameModel(){
		board = new Board(DEFAULT_BOARD_SIZE);
		//updatePossibleMoves(PlayerNum.PLAYER1);
		//updatePossibleMoves(PlayerNum.PLAYER2);
		updatePossibleMoves(PlayerNum.PLAYER1);
		updatePossibleMoves(PlayerNum.PLAYER2);
	}
	
	public GameModel(int boardSize) {
		if (boardSize > MAX_BOARD_SIZE || boardSize < 3)
			board = new Board(DEFAULT_BOARD_SIZE);
		else
			board = new Board(boardSize);
		updatePossibleMoves(PlayerNum.PLAYER1);
		updatePossibleMoves(PlayerNum.PLAYER2);
	}
	
	public GameModel(GameModel otherModel) {
		board = new Board(otherModel.board);
		possibleMovesPlayer1 = otherModel.possibleMovesPlayer1;
		possibleMovesPlayer2 = otherModel.possibleMovesPlayer2;
	}
	
	public Cell getCellAt(Pos pos) {
		if (isOutOfBounds(pos)) {
			return Cell.CELL_ERROR;
		}
		return board.getCellAt(pos.getX()-1,pos.getY()-1); //conversion from board to array
	}
	
	public ArrayList<Pos> getPossibleMoves(PlayerNum player) {
		return (player==PlayerNum.PLAYER1)? possibleMovesPlayer1 : possibleMovesPlayer2;
	}
	

	public boolean isPossibleMove (PlayerNum player, Pos pos) {
		ArrayList<Pos> vec = (player==PlayerNum.PLAYER1)? possibleMovesPlayer1 : possibleMovesPlayer2;
		for (Pos p : vec){
			if (pos.equals(p))
				return true;
		}
		return false;
		/*if (vec.contains(pos))
			return true;
		return false;*/
	}
	
	public boolean place (PlayerNum player, Pos pos) {
		if (!isPossibleMove(player, pos))
			return false;

		Cell currPiece = (player == PlayerNum.PLAYER1)? Cell.CELL_PLAYER1 : Cell.CELL_PLAYER2;

		//move is possible, set current cell to player piece
		setCellAt(pos, currPiece);
		int row = pos.getX();
		int clmn = pos.getY();
		boolean south = false;
		boolean north = false;
		boolean east = false;
		boolean west = false;
		boolean nw = false;
		boolean ne = false;
		boolean sw = false;
		boolean se = false;

		//goTo (direction, currplayerpiece, nextposition, doflipIfValidMove, false
		south = goTo(Direction.SOUTH, currPiece,new  Pos(row+1, clmn), true, false);
		north = goTo(Direction.NORTH, currPiece,new  Pos(row-1, clmn), true, false);
		east = goTo(Direction.EAST, currPiece,new  Pos(row, clmn+1), true, false);
		west = goTo(Direction.WEST, currPiece,new  Pos(row, clmn-1), true, false);
		nw = goTo(Direction.NW, currPiece,new  Pos(row-1, clmn-1), true, false);
		ne = goTo(Direction.NE, currPiece,new  Pos(row-1, clmn+1), true, false);
		sw = goTo(Direction.SW, currPiece,new  Pos(row+1, clmn-1), true, false);
		se = goTo(Direction.SE, currPiece,new  Pos(row+1, clmn+1), true, false);

		updatePossibleMoves(player);
		PlayerNum otherPlayer = (player == PlayerNum.PLAYER1) ? PlayerNum.PLAYER2 : PlayerNum.PLAYER1;
		updatePossibleMoves(otherPlayer);
		return (north || south || east || west || nw || ne || sw || se);

	}
	public int calcScoreOf(PlayerNum player) {
		int score = 0;
		Cell currPiece = (player==PlayerNum.PLAYER1)? Cell.CELL_PLAYER1 : Cell.CELL_PLAYER2;
		for (int row = 1; row <= getBoardSize(); row++) {
			for (int clmn = 1; clmn <= getBoardSize(); clmn++) {
				Pos currPos = new Pos(row, clmn);
				if (getCellAt(currPos) == currPiece)
					score++;
			}
		}
		return score;
	}
	public void updatePossibleMoves(PlayerNum player) {
		ArrayList <Pos> vec;
		Cell currPiece;
		switch (player) {
			case PLAYER1:
				vec = possibleMovesPlayer1;
				currPiece = Cell.CELL_PLAYER1;
				break;
			case PLAYER2:
			default:
				vec = possibleMovesPlayer2;
				currPiece = Cell.CELL_PLAYER2;
				break;
		}

		vec.clear();
		for (int row = 1; row <= getBoardSize(); row++) {
			for (int clmn = 1; clmn <= getBoardSize(); clmn++) {
				Pos currPos = new Pos(row,clmn);
				boolean south = false;
				boolean north = false;
				boolean east = false;
				boolean west = false;
				boolean nw = false;
				boolean ne = false;
				boolean sw = false;
				boolean se = false;

				if (getCellAt(currPos) == Cell.CELL_EMPTY) { //ok, current cell is empty, check adjacent cells
					south = goTo(Direction.SOUTH, currPiece,new Pos(row+1, clmn), false, false);
					north = goTo(Direction.NORTH, currPiece, new Pos(row-1, clmn), false, false);
					east = goTo(Direction.EAST, currPiece, new Pos(row, clmn+1), false, false);
					west = goTo(Direction.WEST, currPiece, new Pos(row, clmn-1), false, false);
					nw = goTo(Direction.NW, currPiece, new Pos(row-1, clmn-1), false, false);
					ne = goTo(Direction.NE, currPiece, new Pos(row-1, clmn+1), false, false);
					sw = goTo(Direction.SW, currPiece, new Pos(row+1, clmn-1), false, false);
					se = goTo(Direction.SE, currPiece, new Pos(row+1, clmn+1), false, false);
				}
				if (north || south || east || west || nw || ne || sw || se)
					vec.add(currPos);
			}
		}

	}
	public boolean isAbleToMove(PlayerNum player) {
		ArrayList <Pos> vec = (player==PlayerNum.PLAYER1)? possibleMovesPlayer1 : possibleMovesPlayer2;
		return !(vec.isEmpty());
	}

	private boolean goTo (Direction direction,Cell currPlayerPiece, Pos pos, boolean doFlip, boolean found) {
		Cell currCell = getCellAt(pos);
		if (currCell==Cell.CELL_ERROR || currCell == Cell.CELL_EMPTY) //reached out of bounds or an empty cell
			return false;
		if (currCell==currPlayerPiece) {
			if (!found)
				return false;
			if (found)
				return true;
		}
		Pos nextPos = new Pos(1,1);
		switch (direction) {
			case NORTH:
				nextPos.setX(pos.getX()-1);
				nextPos.setY(pos.getY());
				break;
			case SOUTH:
				nextPos.setX(pos.getX()+1);
				nextPos.setY(pos.getY());
				break;
			case EAST:
				nextPos.setX(pos.getX());
				nextPos.setY(pos.getY()+1);
				break;
			case WEST:
				nextPos.setX(pos.getX());
				nextPos.setY(pos.getY()-1);
				break;
			case NW:
				nextPos.setX(pos.getX()-1);
				nextPos.setY(pos.getY()-1);
				break;
			case NE:
				nextPos.setX(pos.getX()-1);
				nextPos.setY(pos.getY()+1);
				break;
			case SW:
				nextPos.setX(pos.getX()+1);
				nextPos.setY(pos.getY()-1);
				break;
			case SE:
				nextPos.setX(pos.getX()+1);
				nextPos.setY(pos.getY()+1);
				break;
			default:
				nextPos.setX(0);
				nextPos.setY(0);
				break;
			}
		if (goTo(direction, currPlayerPiece, nextPos, doFlip, true)) { //current cell contains opponent
			if (doFlip)
				flip(pos);
			return true;
		}
		return false;
	}
	private void flip(Pos pos) {
		Cell currPiece = getCellAt(pos);
		if (currPiece==Cell.CELL_ERROR || currPiece == Cell.CELL_EMPTY) {
			return;
		}
		if (currPiece== Cell.CELL_PLAYER1)
			setCellAt(pos, Cell.CELL_PLAYER2);
		else
			setCellAt(pos, Cell.CELL_PLAYER1);

	}
	
	private boolean isOutOfBounds(Pos pos) {
		int boardSize = getBoardSize();
		if (pos.getX() < 1 || pos.getY() < 1 || pos.getX() > boardSize || pos.getY() > boardSize)
			return true;
		return false;
	}
	
	private void setCellAt(Pos pos, Cell piece) {
		assert(!isOutOfBounds(pos));
		board.setCellValue(pos.getX() - 1, pos.getY() - 1, piece);
	}
	public int getBoardSize() {
		return this.board.getBoardSize();
		}
	public Board getBoard(){
		return this.board;
	}
}

