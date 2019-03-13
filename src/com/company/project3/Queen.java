package com.company.project3;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);

	}

	public String type() {
		return "Queen";
		
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;
		//Bishop move1 = new Bishop(this.player());
		if(super.isValidMove(move, board)) {
					if ((move.toRow == move.fromRow) || (move.toColumn == move.fromColumn)) {
						valid = true;
					}
					if (Math.abs(move.toRow - move.fromRow) == Math.abs(move.toColumn - move.fromColumn)) {
						valid = true;
					}
				}
		// Rook move2 = new Rook(this.player());
		return valid;//move2.isValidMove(move, board) || move1.isValidMove(move, board)
	}
}
