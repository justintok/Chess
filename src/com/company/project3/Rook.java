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
				valid = true;
			}
		}

        return valid;
	}
	
}
