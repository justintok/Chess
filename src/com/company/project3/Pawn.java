package com.company.project3;

public class Pawn extends ChessPiece {

	private boolean firstMove;

	public Pawn(Player player) {
		super(player);
		firstMove = true;
	}

	public String type() {
		return "Pawn";
	}

	// determines if the move is valid for a pawn piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		boolean valid = false;

		if(super.isValidMove(move, board)) {

			if (player() == Player.WHITE) {
				if (firstMove) {
					if (move.fromRow - move.toRow <= 2 && move.toColumn == move.fromColumn) {
						valid = true;
						firstMove = false;
					}
				} else if (move.fromRow - move.toRow == 1 && move.toColumn == move.fromColumn) {
					valid = true;
				}
			}
		}

		return valid;
	}
}
