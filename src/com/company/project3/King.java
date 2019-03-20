package com.company.project3;

public class 	King extends ChessPiece {

	//protected static boolean kingFirstMove;

	public King(Player player) {
		super(player);
		//kingFirstMove = true;
	}

	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {

		//Uses a Queen object to check for valid move
		Queen move1 = new Queen(board[move.fromRow][move.fromColumn].player());

		//determines which King (Black/White) is being moved, and then which row they are in..
		int c;
		if(board[move.fromRow][move.fromColumn].player() == Player.BLACK)
			c = 0;
		else
			c = 7;

		if (ChessModel.kingFirstMove) { //Castling Function
			if (move.toRow == c) {
				if (move.toColumn == 2 && ChessModel.rookLeftFirstMove) {
					ChessModel.castling = "L";
					return true;
				} else if (move.toColumn == 6 && ChessModel.rookRightFirstMove) {
					ChessModel.castling = "R";
					return true;
				}
			}
			//else, check for a normal move
			if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1)))
				return true;
		}
		//else, check for a normal move
		else if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1)))
			return true;

		return false;
	}

	public void showValidMove(int row, int col,IChessPiece[][] board,Player p){}
}
