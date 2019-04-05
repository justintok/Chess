package oldChess;

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

//	public void showValidMove(int row, int col,IChessPiece[][] board, Player p){
//		int x = row;
//		int y = col;
//		int i = 1;
//
//		Player p2;
//		if(p == Player.BLACK){
//			p2 = Player.WHITE;
//		}else{
//			p2 = Player.BLACK;
//		}
//
//		//Up
//		if(x > 0) {
//			while (x-i >= 0 && (board[x - i][y] == null || board[x - i][y].player() == p2)) {
//				ChessPanel.board[x - i][y].setBackground(Color.GREEN);
//				if(board[x - i][y] != null && board[x - i][y].player() == p2){
//					break;
//				}
//				i++;
//			}
//			i = 1;
//		}
//
//		//Down
//		if(x < 7) {
//			while (x+i <= 7 && (board[x + i][y] == null || board[x + i][y].player() == p2)) {
//				ChessPanel.board[x + i][y].setBackground(Color.GREEN);
//				if(board[x + i][y] != null && board[x + i][y].player() == p2){
//					break;
//				}
//				i++;
//			}
//			i = 1;
//		}
//
//		//Left
//		if(y > 0) {
//			while (y-i >= 0 && (board[x][y - i] == null || board[x][y - i].player() == p2)) {
//				ChessPanel.board[x][y - i].setBackground(Color.GREEN);
//				if(board[x][y - i] != null && board[x][y - i].player() == p2){
//					break;
//				}
//				i++;
//			}
//			i = 1;
//		}
//
//		//Right
//		if(y < 7) {
//			while (y+i <= 7 && (board[x][y + i] == null || board[x][y + i].player() == p2)) {
//				ChessPanel.board[x][y + i].setBackground(Color.GREEN);
//				if(board[x][y + i] != null && board[x][y + i].player() == p2){
//					break;
//				}
//				i++;
//			}
//		}
//	}
	
}
