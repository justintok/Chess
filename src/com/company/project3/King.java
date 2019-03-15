package com.company.project3;

public class King extends ChessPiece {

	//protected static boolean kingFirstMove;

	public King(Player player) {
		super(player);
		//kingFirstMove = true;
	}

	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;

		Queen move1 = new Queen(board[move.fromRow][move.fromColumn].player());

			if (ChessModel.kingFirstMove) { //Castling Function
				if (move.toRow == 7) {
					if (move.toColumn == 2 && ChessModel.rookLeftFirstMove) {
						ChessModel.castling = "L";
						valid = true;
					} else if (move.toColumn == 6 && ChessModel.rookRightFirstMove) {
						ChessModel.castling = "R";
						valid = true;
					}
				}
				//else, check for a normal move
				if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1)))
					valid = true;
			}
			//else, check for a normal move
			else if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1)))
				valid = true;

		return valid;
	}
}
