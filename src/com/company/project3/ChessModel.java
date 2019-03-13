package com.company.project3;

public class ChessModel implements IChessModel {	 
    private IChessPiece[][] board;
	private Player player;

	protected static boolean kingFirstMove;
	protected static boolean rookLeftFirstMove;
	protected static boolean rookRightFirstMove;
	protected static String castling;

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);

		kingFirstMove = true;
		rookLeftFirstMove = true;
		rookRightFirstMove = true;
		castling = "";

	}

	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board) == true)
                return true;

		return valid;
	}

	public void move(Move move) {
		checkForFirstMove(move);

		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		if(castling.equals("L")){
			board[7][3] = board[7][0];
			board[7][0] = null;
		}else if(castling.equals("R")) {
			board[7][5] = board[7][7];
			board[7][7] = null;
		}
	}

	public void checkForFirstMove(Move move){
		if(board[move.fromRow][move.fromColumn].type() == "King"){
			kingFirstMove = false;
		}
		if(board[move.fromRow][move.fromColumn].type() == "Rook"){
			if(move.fromColumn == 0){
				rookLeftFirstMove = false;
			}else if(move.fromColumn == 7){
				rookRightFirstMove = false;
			}
		}
	}

	public boolean inCheck(Player p) {
		boolean valid = false;
		return valid;
	}


	public Player currentPlayer() {
		return player;
	}

	public int numRows() {
		return 8;
	}

	public int numColumns() {
		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	public void setNextPlayer() {
		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	public void AI() {
		/*
		 * Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 *c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 *d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

		}
}
