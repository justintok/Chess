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
				valid = true;
			}
		}
		return valid;
        // More code is needed
		
	}
}
