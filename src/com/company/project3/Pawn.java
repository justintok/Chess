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
				//White pawn charge
				if (move.fromRow - move.toRow == 2 && move.toColumn == move.fromColumn && move.fromRow == 6 && board[move.fromRow-1][move.fromColumn] == null) {
					valid = true;

					//White pawn normal move
				} else if (move.fromRow - move.toRow == 1 && move.toColumn == move.fromColumn && board[move.fromRow-1][move.fromColumn] == null) {
					valid = true;
				}
				else if (move.fromRow - move.toRow == 1 && (move.toColumn-move.fromColumn ==1 || move.toColumn-move.fromColumn == -1)){ //If it is going sideways check if there is a enemy piece there.
					if (board[move.toRow][move.toColumn] != null && board[move.toRow][move.toColumn].player() != player() ) {
						valid = true;
					}
				}
			}else{
				//Black pawn charge
				if (move.fromRow - move.toRow == -2 && move.toColumn == move.fromColumn && move.fromRow == 1 && board[move.fromRow+1][move.fromColumn] == null) {
					valid = true;

					//Black pawn normal move
				} else if (move.fromRow - move.toRow == -1 && move.toColumn == move.fromColumn && board[move.fromRow+1][move.fromColumn] == null) {
					valid = true;
				}
				else if (move.fromRow - move.toRow == -1 && (move.toColumn-move.fromColumn ==1 || move.toColumn-move.fromColumn == -1)){ //If it is going sideways check if there is a enemy piece there.
					if (board[move.toRow][move.toColumn] != null && board[move.toRow][move.toColumn].player() != player() ) {
						valid = true;
					}
				}

			}
		}

		return valid;
	}

//	public void showValidMove(int row, int col,IChessPiece[][] board,Player p){}
}
