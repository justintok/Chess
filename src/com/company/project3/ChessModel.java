package com.company.project3;

import javax.swing.*;
import java.util.Random;
import java.util.*;

public class ChessModel implements IChessModel {
    protected IChessPiece[][] board;
	private Player player;

	protected static boolean whiteKingFirstMove;
	protected static boolean whiteRookLeftFirstMove;
	protected static boolean whiteRookRightFirstMove;
    protected static boolean blackKingFirstMove;
    protected static boolean blackRookLeftFirstMove;
    protected static boolean blackRookRightFirstMove;
	protected static String castling;
	protected boolean firstMove;
    LinkedList<Move> undo = new LinkedList<Move>();
    LinkedList<IChessPiece> undoPiece = new LinkedList<IChessPiece>();

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

		whiteKingFirstMove = true;
		whiteRookLeftFirstMove = true;
		whiteRookRightFirstMove = true;
        blackKingFirstMove = true;
        blackRookLeftFirstMove = true;
        blackRookRightFirstMove = true;
		castling = "";

		firstMove = true;
	}

	/****************************************************************************************************
	 * Checks for a Checkmate
	 * @return True if current player is in Checkmate
	 */
	public boolean isComplete() {
		boolean valid = true;
		Move testMove = new Move();
		int r = 0;
		int c = 0;

		findKing_loop:
		for (r = 0; r < 8; r++) {
			for (c = 0; c < 8; c++) {
				if (pieceAt(r,c) != null && pieceAt(r, c).type().equals("King") && pieceAt(r,c).player() == currentPlayer()) {
					testMove.fromRow = r;
					testMove.fromColumn = c;
					break findKing_loop;
				}
			}
		}

		if(inCheck(currentPlayer())) {
			for (int x = 0; x < numRows(); x++) {
				for (int y = 0; y < numColumns(); y++) {
					testMove.toRow = x;
					testMove.toColumn = y;
					if (isValidMove(testMove)) {
						IChessPiece temp = board[testMove.toRow][testMove.toColumn];
						board[testMove.toRow][testMove.toColumn] = board[r][c];
						board[r][c] = null;
						if (!inCheck(currentPlayer()))
							valid = false;
						board[r][c] = board[testMove.toRow][testMove.toColumn];
						board[testMove.toRow][testMove.toColumn] = temp;
					}
				}
			}
		}else{
			valid = false;
		}

		return valid;
	}

	/****************************************************************************************************
	 * Checks if the given move is a valid one
	 * @param move a {@link W18project3.Move} object describing the move to be made
	 * @return True if the given move is a valid one
	 */
	public boolean isValidMove(Move move) {

		//Cant move a different piece if the king is in check
		if ((pieceAt(move.fromRow, move.fromColumn) != null && !pieceAt(move.fromRow,move.fromColumn).type().equals("King"))) {
			if (inCheck(currentPlayer()))
				return false;
		}

		//Cannot select a blank location
		if (board[move.fromRow][move.fromColumn] != null) {
			//Calls the ChessPiece level isValidMove method
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
				//If you are moving the king, makes sure you cannot move into an inCheck position
				if(board[move.fromRow][move.fromColumn].type().equals("King")) {
					if (inDanger(move,currentPlayer())){
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/****************************************************************************************************
	 * Checks if the given move will put the moved piece in danger
	 * @param move a object describing the move to be made
	 * @param p an object describing the player color of the piece to be moved
	 * @return True if the move will put the moved piece in danger
	 */
	public boolean inDanger(Move move, Player p){

		//finds current player and opposing player
		Player p2;
		if(p == player.WHITE){
			p2 = player.BLACK;
		}else{
			p2 = player.WHITE;
		}

		//Initializes test moves for all piece types
		Rook rookTest = new Rook(p2);
		Knight knightTest = new Knight(p2);
		Queen queenTest = new Queen(p2);
		Pawn pawnTest = new Pawn(p2);
		Bishop bishopTest = new Bishop(p2);
		King kingTest = new King(p2);

		//Sets the 'to' location to null in order to correctly check inDanger locations
		IChessPiece moveToTemp = board[move.toRow][move.toColumn];
		board[move.toRow][move.toColumn] = null;

		//Checks all opposing pieces to see if they can put the king in danger
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if ((pieceAt(r, c) != null))
					if (pieceAt(r, c).player() == p2){
						if (pieceAt(r, c).type().equals("Rook")) {
							Move m = new Move(r,c,move.toRow,move.toColumn);
							IChessPiece temp = board[move.fromRow][move.fromColumn];
							board[move.fromRow][move.fromColumn] = null;
							if (rookTest.isValidMove(m,board)){
                                board[move.fromRow][move.fromColumn] = temp;
								board[move.toRow][move.toColumn] = moveToTemp;
								return true;
							}
                            board[move.fromRow][move.fromColumn] = temp;
						}
						if (pieceAt(r, c).type().equals("Knight")) {
							Move m = new Move(r,c,move.toRow,move.toColumn);
							if (knightTest.isValidMove(m,board)){
								board[move.toRow][move.toColumn] = moveToTemp;
								return true;
							}
						}
						if (pieceAt(r, c).type().equals("Queen")) {
                            Move m = new Move(r,c,move.toRow,move.toColumn);
                            IChessPiece temp = board[move.fromRow][move.fromColumn];
                            board[move.fromRow][move.fromColumn] = null;
                            if (queenTest.isValidMove(m,board)){
                                board[move.fromRow][move.fromColumn] = temp;
								board[move.toRow][move.toColumn] = moveToTemp;
                                return true;
                            }
                            board[move.fromRow][move.fromColumn] = temp;
						}
						if (pieceAt(r, c).type().equals("Pawn")) {
							Move m = new Move(r, c, move.toRow, move.toColumn);
							if (p2 == player.BLACK) {
								if (move.toRow - r == 1 && Math.abs(move.toColumn - c) == 1) {
									board[move.toRow][move.toColumn] = moveToTemp;
									return true;
								}
							}
							if (p2 == player.WHITE) {
								if (move.toRow - r == -1 && Math.abs(move.toColumn - c) == 1) {
									board[move.toRow][move.toColumn] = moveToTemp;
									return true;
								}
							}
						}
						if (pieceAt(r, c).type().equals("Bishop")) {
                            Move m = new Move(r,c,move.toRow,move.toColumn);
                            IChessPiece temp = board[move.fromRow][move.fromColumn];
                            board[move.fromRow][move.fromColumn] = null;
                            if (bishopTest.isValidMove(m,board)){
                                board[move.fromRow][move.fromColumn] = temp;
								board[move.toRow][move.toColumn] = moveToTemp;
                                return true;
                            }
                            board[move.fromRow][move.fromColumn] = temp;
						}
						if (pieceAt(r, c).type().equals("King")) {
							Move m = new Move(r,c,move.toRow,move.toColumn);
							if (kingTest.isValidMove(m,board)){
								board[move.toRow][move.toColumn] = moveToTemp;
								return true;
							}
						}
					}
			}
		}

		board[move.toRow][move.toColumn] = moveToTemp;

		return false;

	}

	/****************************************************************************************************
	 * Checks if the king is in check
	 * @param  p {@link W18project3.Move} the Player being checked
	 * @return True if the king is in check
	 */
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

	/****************************************************************************************************
	 * Undoes the previous move
	 */
	public void undo(){
	    Move move =(undo.getLast());
	    boolean cap = false;
	    moveUndo(undo.pollLast());
	    setPiece(move.fromRow, move.fromColumn, undoPiece.pollLast());
	    setNextPlayer();
	    if(undo.size() == 0){
	        firstMove = true;
        }
    }

	/****************************************************************************************************
	 * Moves the previously moved piece back to its original location
	 * @param move an object describing the move to be made
	 */
	public void moveUndo(Move move) {
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

	/****************************************************************************************************
	 * Moves a piece
	 * @param move a {@link W18project3.Move} object describing the move to be made
	 */
	public void move(Move move) {
		checkForFirstMove(move);

		firstMove = false;
        undoPiece.addLast(pieceAt(move.toRow,move.toColumn));
		undo.addLast(new Move(move.toRow, move.toColumn, move.fromRow, move.fromColumn));

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

	/****************************************************************************************************
	 * Checks if the move is the first move of any king or rook (used for Castling)
	 * @param move an object describing the move to be made
	 */
	public void checkForFirstMove(Move move){

		//Checks if it's the king's first move
		if(board[move.fromRow][move.fromColumn].type() == "King") {
            if (board[move.fromRow][move.fromColumn].player() == player.WHITE){
                whiteKingFirstMove = false;
            }else{
                blackKingFirstMove = false;
            }
		}

		//Checks if it's any of the Rook's first move
		if(board[move.fromRow][move.fromColumn].type() == "Rook"){
			if(board[move.fromRow][move.fromColumn].player() == player.WHITE) {
                if (move.fromColumn == 0) {
                    whiteRookLeftFirstMove = false;
                } else if (move.fromColumn == 7) {
                    whiteRookRightFirstMove = false;
                }
            }else{
                if (move.fromColumn == 0) {
                    blackRookLeftFirstMove = false;
                } else if (move.fromColumn == 7) {
                    blackRookRightFirstMove = false;
                }
            }
		}
	}

	/****************************************************************************************************
	 * Returns the current player (White/Black)
	 * @return the player instance variable
	 */
	public Player currentPlayer() {
		return player;
	}

	/****************************************************************************************************
	 * Returns the number of rows on the board
	 * @return 8
	 */
	public int numRows() {
		return 8;
	}

	/****************************************************************************************************
	 * Returns the number of columns on the board
	 * @return 8
	 */
	public int numColumns() {
		return 8;
	}

	/****************************************************************************************************
	 * Returns the piece at the given row and column coordinates
	 * @param row an integer that represents the row number on the board
	 * @param column an integer that represents the column number on the board
	 * @return the chess piece at the row and column coordinates given
	 */
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	/****************************************************************************************************
	 * Changes the current player to the other player
	 */
	public void setNextPlayer() {
		Player winner = player;
		player = player.next();
		if(isComplete()){
			JOptionPane.showMessageDialog(null, "Checkmate! " + winner.toString() + " wins!");
			System.exit(0);
		}
	}

	/****************************************************************************************************
	 * Sets a chess piece at the location at the given row and column coordinates
	 * @param row an integer that represents the row number on the board
	 * @param column an integer that represents the column number on the board
	 * @param piece an object that describes the chess piece to be placed
	 */
	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	/****************************************************************************************************
	 * Makes an Artificial intelligence move
	 */
	public void AI() {
		Random rNum = new Random();
		Player p2;
		Player p = currentPlayer();
		if(p == player.WHITE){
			p2 = player.BLACK;
		}else{
			p2 = player.WHITE;
		}

		//Move King out of check
		int random = rNum.nextInt(7);
		if (inCheck(currentPlayer())) {
			int kingR = 0;
			int kingC = 0;

			findKing_loop: //Find the king
			for (kingR = 0; kingR < 8;kingR++) {
				for (kingC = 0; kingC < 8; kingC++) {
					if (pieceAt(kingR,kingC) != null && pieceAt(kingR, kingC).type().equals("King") && pieceAt(kingR,kingC).player() == currentPlayer()) {
						break findKing_loop;
					}
				}
			}

			//Find the number of Valid moves for the king
			int numValidMoves = 0;
			for (int r = 0; r < numRows(); r++) {
			    for (int c = 0; c < numColumns(); c++) {
			        if (isValidMove(new Move(kingR, kingC, r, c))) {
			            numValidMoves++;
			        }
			    }
			}

			//Moves the king to a random valid location
			random = rNum.nextInt(numValidMoves);
			int incr = 0;
			for (int r = 0; r < numRows(); r++) {
			    for (int c = 0; c < numColumns(); c++) {
			        if (isValidMove(new Move(kingR, kingC, r, c))) {
			            if(incr == random){
			                move(new Move(kingR, kingC, r, c));
			                return;
                        }else
                            incr++;
			        }
			    }
			}


		//If king not in check, move a random piece
		}else{
			//Takes enemy piece if in possible
			
			for (int r = 0; r < numRows(); r++) {
				for (int c = 0; c < numColumns(); c++) {
					if (board[r][c] != null && pieceAt(r,c).player()!=currentPlayer() &&inDanger(new Move(r,c,r,c),p2)) {
						for (int newR = 0; newR < numRows(); newR++) {
							for (int newC = 0; newC < numColumns(); newC++) {
								if (isValidMove(new Move(newR, newC, r, c))) {
									move(new Move(newR, newC, r, c));
									return;

								}
							}
						}



					}
				}
			}


			//Finds the number of pieces
			int numPieces = 0;
			for (int r = 0; r < numRows(); r++) {
				for (int c = 0; c < numColumns(); c++) {
					if (board[r][c] != null && pieceAt(r, c).player() == currentPlayer()) {
						numPieces++;
					}
				}
			}

			boolean repeat = true;

			//Finds a random piece
			findRandPieceLoop:
			while(repeat) {
				random = rNum.nextInt(numPieces);
				int incr = 0;
				for (int r = 0; r < numRows(); r++) {
					for (int c = 0; c < numColumns(); c++) {
						if (board[r][c] != null && pieceAt(r, c).player() == currentPlayer()) {

							//Moves that piece randomly
							if (incr == random) {

								//Find the enemy king
								int kingR = 0;
								int kingC = 0;
								findKing_loop:
								//Find the king
								for (kingR = 0; kingR < 8; kingR++) {
									for (kingC = 0; kingC < 8; kingC++) {
										if (pieceAt(kingR, kingC) != null && pieceAt(kingR, kingC).type().equals("King") && pieceAt(kingR, kingC).player() == currentPlayer().next()) {
											break findKing_loop;
										}
									}
								}

								int numValidMoves = 0;

								//Finds all valid moves. Will put opposing king in check if possible
								for (int x = 0; x < numRows(); x++) {
									for (int y = 0; y < numColumns(); y++) {
										if (isValidMove(new Move(r, c, x, y))) {
											numValidMoves++;
											if (pieceAt(r, c).type().equals("Rook")) {
												if (x == kingR || y == kingC) {
													move(new Move(r, c, x, y));
													return;
												}
											}
											if (pieceAt(r, c).type().equals("Bishop")) {
												if (Math.abs(x - kingR) == Math.abs(y - kingC)) {
													move(new Move(r, c, x, y));
													return;
												}
											}
											if (pieceAt(r, c).type().equals("Knight")) {
												if ((Math.abs(x - kingR) == 2 && Math.abs(y - kingC) == 1) || (Math.abs(x - kingR) == 1 && Math.abs(y - kingC) == 2)) {
													move(new Move(r, c, x, y));
													return;
												}
											}
											if (pieceAt(r, c).type().equals("Queen")) {
												if (Math.abs(x - kingR) == Math.abs(y - kingC) || x == kingR || y == kingC) {
													move(new Move(r, c, x, y));
													return;
												}
											}
										}
									}
								}

								//Moves the piece to random valid location
								if (numValidMoves > 0) {
									int randomMove = rNum.nextInt(numValidMoves);
									int incrMove = 0;
									for (int x = 0; x < numRows(); x++) {
										for (int y = 0; y < numColumns(); y++) {
											if (isValidMove(new Move(r, c, x, y))) {
												if (incrMove == randomMove) {
													move(new Move(r, c, x, y));
													return;
												} else {
													incrMove++;
												}
											}
										}
									}
								}
							} else
								incr++;
						}
					}
				}
			}
		}
	}

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




