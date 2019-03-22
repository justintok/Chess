package com.company.project3;

public class 	King extends ChessPiece {

	//protected static boolean kingFirstMove;

	public King(Player player) {
		super(player);
		//kingFirstMove = true;
	}

	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {

		//Uses a Queen object to check for valid move
		Queen move1 = new Queen(board[move.fromRow][move.fromColumn].player());

		//determines which King (Black/White) is being moved, and then which row they are in..
		int r;
		if(board[move.fromRow][move.fromColumn].player() == Player.BLACK)
			r = 0;
		else
			r = 7;

		//Castling Function
		if (ChessModel.kingFirstMove) {
			boolean valid = true;
			if (move.toRow == r) {
				if (move.toColumn == 2 && ChessModel.rookLeftFirstMove) {
					for (int c = 1; c < 4; c++) {
						if (board[r][c] != null)
							valid = false;
					}
					if (valid)
						ChessModel.castling = "L";
					return valid;
				} else if (move.toColumn == 6 && ChessModel.rookRightFirstMove) {
					for (int c = 5; c < 7; c++) {
						if (board[r][c] != null)
							valid = false;
					}
					if (valid)
						ChessModel.castling = "R";
					return valid;
				}
			}
			//else, check for a normal move
			if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1))){
				ChessModel.castling = "";
				return true;
			}
		}
		//else, check for a normal move
		else if (move1.isValidMove(move, board) && ((Math.abs(move.toRow - move.fromRow) == 1) || (Math.abs(move.toColumn - move.fromColumn) == 1))) {
			ChessModel.castling = "";
			return true;
		}


		return false;
	}

//	public void showValidMove(int row, int col,IChessPiece[][] board,Player p){}
}
