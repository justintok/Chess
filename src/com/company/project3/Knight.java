package com.company.project3;

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}

	public String type() {
		return "Knight";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board){
		boolean valid = false;

		if(Math.abs(move.fromColumn - move.toColumn) == 2){
			if(Math.abs(move.fromRow - move.toRow) == 1){
				valid = true;
			}
		}else if(Math.abs(move.fromColumn - move.toColumn) == 1){
			if(Math.abs(move.fromRow - move.toRow) == 2){
				valid = true;
			}
		}

		return valid;
	}

}
