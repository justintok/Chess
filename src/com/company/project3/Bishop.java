package com.company.project3;

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
	}

	public String type() {
		return "Bishop";
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;

		if(super.isValidMove(move,board)) {
			if (Math.abs(move.toRow - move.fromRow) == Math.abs(move.toColumn - move.fromColumn)) {
				if (!noJump(move,board)) {
					valid = true;
				}
			}
		}
		return valid;
        // More code is needed
		
	}
	public boolean noJump(Move move,IChessPiece[][] board) {
		//This method is not working correctly yet. I do not think this is the best way to do this either.
		//**Update** All directions should be working. I simply changed the < sign to a > in the right/down loop.

		// It is suppose to count the number of spaced you move, then go through all those spaced and find out if there is a
		//piece there. If there is one, return true.

		int moveRow = (move.fromRow - move.toRow);
		int moveColumn = (move.fromColumn - move.toColumn);//counts the number of spaces moved
		if (moveRow > 0) { //going up
			if (moveColumn < 0) { //gong right
				for (int i = 1; i < moveRow; i++) {
					if (board[move.fromRow - i][move.fromColumn + i] != null) {
						return true;
					}
				}
			}
			if (moveColumn > 0) { //gong left
					for (int i = 1; i < moveRow; i++) {
						if (board[move.fromRow - i][move.fromColumn - i] != null) {
							return true;
						}
					}
			}
		}
			if (moveRow < 0) { //going down
				if (moveColumn < 0) { //gong right
					for (int i = -1; i > moveRow; i--) {
						if (board[move.fromRow - i][move.fromColumn - i] != null) {
							return true;
						}
					}
				}
				if (moveColumn > 0) { //gong left
					for (int i = -1; i > moveRow; i--) {
						if (board[move.fromRow - i][move.fromColumn + i] != null) {
							return true;
						}
					}
				}
			}
		return false;
	}
}
