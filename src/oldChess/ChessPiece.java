package oldChess;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;

		//  THIS IS A START... More coding needed

		try {
			if (!((move.fromRow == move.toRow) && (move.fromColumn == move.toColumn))) { //If its new location is not the original location

				if (board[move.toRow][move.toColumn] == null || board[move.fromRow][move.fromColumn].player() != board[move.toRow][move.toColumn].player()) //Can only move into a space with no piece or a enemy piece
				{
					valid = true;
				}
				//}
			}
		}
		catch (IndexOutOfBoundsException e){

		}

		return valid;
	}
}
