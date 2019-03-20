package com.company.project3;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);

	}

	public String type() {
		return "Queen";
		
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Bishop move1 = new Bishop(this.player());
		Rook move2 = new Rook(this.player());
		return move2.isValidMove(move, board) || move1.isValidMove(move, board);
	}

//	public void showValidMove(int row, int col,IChessPiece[][] board,Player p){}
}
