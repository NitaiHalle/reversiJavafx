package reversi;

public class Board {
	private int boardSize;
	private Cell [][] board;
	
	static int DEFAULT_BOARD_SIZE = 8;
	static int MAX_BOARD_SIZE = 30;
	

	public Board(int size) {
		boardSize = size;
		board = new Cell [boardSize][boardSize];
		initBoard();
	}

	public Board(Board otherBoard) {
		boardSize = otherBoard.getBoardSize();
		board = new Cell[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				setCellValue(i, j, otherBoard.getCellAt(i,j));
			}
		}
	}
	public int getBoardSize(){
		return boardSize;
	}
	public void setCellValue(int x, int y, Cell cell) {
		board[x][y] = cell;
	}

	public Cell getCellAt(int x, int y) {
		return board[x][y];
	}

	private void initBoard() {
		assert(boardSize > 3 || boardSize < MAX_BOARD_SIZE);
			int center = boardSize / 2;
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if ((j == center - 1 && i == center - 1) || (j == center && i == center))
						setCellValue(i, j, Cell.CELL_PLAYER2);
					else if ((j == center - 1 && i == center) || (j == center && i == center - 1))
						setCellValue(i, j, Cell.CELL_PLAYER1);
					else
						setCellValue(i, j, Cell.CELL_EMPTY);
				}
			}
	}
}
