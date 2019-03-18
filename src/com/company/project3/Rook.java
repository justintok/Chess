package com.company.project3;

public class Rook extends ChessPiece {

	protected boolean rook1FirstMove;
	protected boolean rook2FirstMove;

	public Rook(Player player) {
		
		super(player);

		rook1FirstMove = true;
		rook2FirstMove = true;

	}

	public String type() {
		
		return "Rook";
		
	}
	
	// determines if the move is valid for a rook piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;

		if(super.isValidMove(move, board)) {
			if ((move.toRow == move.fromRow) || (move.toColumn == move.fromColumn)) {
				if (!noJump(move,board)) {
					valid = true;
				}
			}
		}

        return valid;
	}

	public boolean noJump(Move move,IChessPiece[][] board) {
		//**Update** All directions should be working.

		// It is suppose to count the number of spaced you move, then go through all those spaced and find out if there is a
		//piece there. If there is one, return true.
		int movedSpaces = 0;
		movedSpaces = (move.fromRow - move.toRow) + (move.fromColumn - move.toColumn); //counts the number of spaces moved
		if (movedSpaces > 0) { // checks if you are going up or left.
			for (int i = 1; i < movedSpaces; i++) {
				if (move.toColumn == move.fromColumn)
					if (board[move.fromRow - i][move.fromColumn] != null) {
						return true;
					}
				if (move.toRow == move.fromRow)
					if (board[move.fromRow][move.fromColumn - i] != null) {

						return true;
					}
			}
		}
		if (movedSpaces < 0) { //checks if you are going down or right.
			for (int i = -1; i > movedSpaces; i--) {
				if (move.toColumn == move.fromColumn)
					if (board[move.fromRow - i][move.fromColumn ] != null) {
						return true;
					}
				if (move.toRow == move.fromRow)
					if (board[move.fromRow][move.fromColumn - i] != null) {

						return true;
					}
			}

		}
		return false;
	}
	
}
