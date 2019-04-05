package oldChess;

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}

	public String type() {
		return "Knight";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board){
		boolean valid = false;
		if(super.isValidMove(move,board)) {
			if (Math.abs(move.fromColumn - move.toColumn) == 2) {
				if (Math.abs(move.fromRow - move.toRow) == 1) {
					valid = true;
				}
			} else if (Math.abs(move.fromColumn - move.toColumn) == 1) {
				if (Math.abs(move.fromRow - move.toRow) == 2) {
					valid = true;
				}
			}
		}

		return valid;
	}

//	public void showValidMove(int row, int col,IChessPiece[][] board,Player p){//Up Left
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
//	}
}
