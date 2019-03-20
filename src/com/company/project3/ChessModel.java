package com.company.project3;

import javax.swing.*;

public class ChessModel implements IChessModel {
    protected IChessPiece[][] board;
	private Player player;

	protected static boolean kingFirstMove;
	protected static boolean rookLeftFirstMove;
	protected static boolean rookRightFirstMove;
	protected static String castling;

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

		//Adds Black pieces to the board
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		board[1][0] = new Pawn(Player.BLACK);
		board[1][1] = new Pawn(Player.BLACK);
		board[1][2] = new Pawn(Player.BLACK);
		board[1][3] = new Pawn(Player.BLACK);
		board[1][4] = new Pawn(Player.BLACK);
		board[1][5] = new Pawn(Player.BLACK);
		board[1][6] = new Pawn(Player.BLACK);
		board[1][7] = new Pawn(Player.BLACK);

		//Adds White pieces to the board
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		board[6][0] = new Pawn(Player.WHITE);
		board[6][1] = new Pawn(Player.WHITE);
		board[6][2] = new Pawn(Player.WHITE);
		board[6][3] = new Pawn(Player.WHITE);
		board[6][4] = new Pawn(Player.WHITE);
		board[6][5] = new Pawn(Player.WHITE);
		board[6][6] = new Pawn(Player.WHITE);
		board[6][7] = new Pawn(Player.WHITE);

		kingFirstMove = true;
		rookLeftFirstMove = true;
		rookRightFirstMove = true;
		castling = "";

	}

	public boolean isComplete() {
		boolean valid = false;
		Move testMove = new Move();

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (pieceAt(r, c).type().equals("King")) {
					testMove.fromRow = r;
					testMove.fromColumn = c;

				}
			}
		}

		return valid;
	}


	public boolean isValidMove(Move move) {

		if (pieceAt(move.fromRow,move.fromColumn).type()=="King")
			if (inCheck(currentPlayer()))
				return false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board) == true)
                return true;

		return false;
	}

	public boolean inCheck(Player p){
		Player p2;
		if(p == player.WHITE){
			p2 = player.BLACK;
		}else{
			p2 = player.WHITE;
		}

		Rook rookTest = new Rook(p2);
		Knight knightTest = new Knight(p2);
		Queen queenTest = new Queen(p2);
		Pawn pawnTest = new Pawn(p2);
		Bishop bishopTest = new Bishop(p2);
		King kingTest = new King(p2);

		int kingR=0;
		int kingC=0;

		for (kingR = 0; kingR < 8; kingR++) {
            for (kingC = 0; kingC < 8; kingC++) {
                if (pieceAt(kingR, kingC) != null && pieceAt(kingR, kingC).player() != null && pieceAt(kingR, kingC).player() == p) {

                    if (pieceAt(kingR, kingC).type().equals("King")) {
                        break;
                    }
                }

            }
            if (kingC < 8) {
                if (pieceAt(kingR, kingC).player() != null && pieceAt(kingR, kingC).player() == p) {
                    if (pieceAt(kingR, kingC).type().equals("King")) {
                        break;
                    }
                }
            }
        }


		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if ((pieceAt(r, c) != null))
				if (pieceAt(r, c).player() == p2){
					if (pieceAt(r, c).type().equals("Rook")) {
						Move m = new Move(r,c,kingR,kingC);
						if (rookTest.isValidMove(m,board)){
							return true;
						}
					}
					if (pieceAt(r, c).type().equals("Knight")) {
						Move m = new Move(r,c,kingR,kingC);
						if (knightTest.isValidMove(m,board)){
							return true;
						}
					}
					if (pieceAt(r, c).type().equals("Queen")) {
						Move m = new Move(r,c,kingR,kingC);
						if (queenTest.isValidMove(m,board)){
							return true;
						}
					}
					if (pieceAt(r, c).type().equals("Pawn")) {
						Move m = new Move(r,c,kingR,kingC);
						if (pawnTest.isValidMove(m,board)){
							return true;
						}
					}
					if (pieceAt(r, c).type().equals("Bishop")) {
						Move m = new Move(r,c,kingR,kingC);
						if (bishopTest.isValidMove(m,board)){
							return true;
						}
					}
					if (pieceAt(r, c).type().equals("King")) {
						Move m = new Move(r,c,kingR,kingC);
						if (kingTest.isValidMove(m,board)){
							return true;
						}
					}
				}
			}
		}


		return false;
	}



	public void move(Move move) {
		checkForFirstMove(move);

		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		int rPos = move.toRow;
		int cPos = move.toColumn;
		if(castling.equals("L") && board[rPos][cPos].type().equals("King") && cPos == 2){
			board[rPos][3] = board[rPos][0];
			board[rPos][0] = null;
		}else if(castling.equals("R") && board[rPos][cPos].type().equals("King") && cPos == 6) {
			board[rPos][5] = board[rPos][7];
			board[rPos][7] = null;
		}
	}

	public void checkForFirstMove(Move move){
		if(board[move.fromRow][move.fromColumn].type() == "King"){
			kingFirstMove = false;
		}
		if(board[move.fromRow][move.fromColumn].type() == "Rook"){
			if(move.fromColumn == 0){
				rookLeftFirstMove = false;
			}else if(move.fromColumn == 7){
				rookRightFirstMove = false;
			}
		}
	}


	public Player currentPlayer() {
		return player;
	}

	public int numRows() {
		return 8;
	}

	public int numColumns() {
		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	public void setNextPlayer() {
		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	public void AI() {
		/*
		 * Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 *c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 *d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

		}
}
