package com.company.project3;

import org.junit.*;
import static org.junit.Assert.*;

public class ChessTest {


// Testing the isComplete method ---------------------------------------------

    @Test
    public void testWhiteCheckmate(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;
        test.setPiece(4,3,new Pawn(test.currentPlayer()));
        test.setPiece(5,3,new Pawn(test.currentPlayer()));
        test.setPiece(4,5,new Pawn(test.currentPlayer()));
        test.setPiece(5,5,new Pawn(test.currentPlayer()));

        test.setPiece(2,4,new Rook(Player.BLACK));

        assertTrue(test.isComplete());
    }

    @Test
    public void testBlackIsComplete(){
        ChessModel test = new ChessModel();
        test.board[2][4] = test.board[0][4];
        test.board[0][4] = null;
        test.setNextPlayer();
        test.setPiece(2,3,new Pawn(test.currentPlayer()));
        test.setPiece(3,3,new Pawn(test.currentPlayer()));
        test.setPiece(2,5,new Pawn(test.currentPlayer()));
        test.setPiece(3,5,new Pawn(test.currentPlayer()));

        test.setPiece(5,4,new Rook(Player.WHITE));

        assertTrue(test.isComplete());
    }


// Testing inCheck method ------------------------------------------------------

    @Test
    public void testWhiteInCheckbyRook(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,4,new Rook(Player.BLACK));

        assertTrue(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testBlackInCheckByRook(){
        ChessModel test = new ChessModel();
        test.board[2][4] = test.board[0][4];
        test.board[0][4] = null;
        test.setNextPlayer();

        test.setPiece(5,4,new Rook(Player.WHITE));

        assertTrue(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testWhiteInCheckByKnight(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(3,5,new Knight(Player.BLACK));

        assertTrue(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testWhiteInCheckByBishop(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,7,new Bishop(Player.BLACK));

        assertTrue(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testWhiteInCheckByQueen(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,7,new Queen(Player.BLACK));
        assertTrue(test.inCheck(test.currentPlayer()));
        test.board[2][7] = null;

        test.setPiece(2,4,new Queen(Player.BLACK));
        assertTrue(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testWhiteInCheckByPawn(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(4,5,new Pawn(Player.BLACK));
        assertTrue(test.inCheck(test.currentPlayer()));
        test.board[4][5] = null;

        test.setPiece(4,4,new Pawn(Player.BLACK));
        assertFalse(test.inCheck(test.currentPlayer()));
        test.board[4][4] = null;

        test.setPiece(4,3,new Pawn(Player.BLACK));
        assertTrue(test.inCheck(test.currentPlayer()));
        test.board[4][5] = null;

        test.board[4][4] = test.board[5][4];
        test.board[5][4] = null;

        test.setPiece(5,4,new Pawn(Player.BLACK));
        assertFalse(test.inCheck(test.currentPlayer()));
    }

    @Test
    public void testWhiteInCheckByKing(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(4,4,new King(Player.BLACK));

        assertTrue(test.inCheck(test.currentPlayer()));
    }


// Testing the inDanger method --------------------------------------------------

    @Test
    public void testWhiteMoveIntoCheckByRook(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,4,new Rook(Player.BLACK));

        Move testMove = new Move(5,4,4,4);

        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }

    @Test
    public void testWhiteMoveIntoCheckByKnight(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,5,new Knight(Player.BLACK));

        Move testMove = new Move(5,4,4,4);

        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }

    @Test
    public void testWhiteMoveIntoCheckByBishop(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,6,new Bishop(Player.BLACK));

        Move testMove = new Move(5,4,4,4);

        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }

    @Test
    public void testWhiteMoveIntoCheckByQueen(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        Move testMove = new Move(5,4,4,4);

        test.setPiece(2,6,new Queen(Player.BLACK));
        assertFalse(test.isValidMove(testMove));
        test.board[2][6] = null;

        test.setPiece(2,4,new Queen(Player.BLACK));
        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }

    @Test
    public void testWhiteMoveIntoCheckByPawn(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        Move testMove = new Move(5,4,4,4);

        test.setPiece(3,5,new Pawn(Player.BLACK));
        assertTrue(test.inDanger(testMove,test.currentPlayer()));
        test.board[3][5] = null;

        test.setPiece(3,4,new Pawn(Player.BLACK));
        assertFalse(test.inDanger(testMove,test.currentPlayer()));
        test.board[4][4] = null;

        test.setPiece(3,3,new Pawn(Player.BLACK));
        assertTrue(test.inDanger(testMove,test.currentPlayer()));
        test.board[4][5] = null;

        test.board[4][4] = test.board[5][4];
        test.board[5][4] = null;
        testMove = new Move(4,4,4,3);

        test.setPiece(5,4,new Pawn(Player.BLACK));
        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }

    @Test
    public void testWhiteMoveIntoCheckByKing(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        Move testMove = new Move(5,4,4,4);

        test.setPiece(3,4,new King(Player.BLACK));

        assertTrue(test.inDanger(testMove,test.currentPlayer()));
    }


// Testing the isValidMove method -----------------------------------------------

    @Test
    public void testWhiteQueenMoveOntoSame(){
        ChessModel test = new ChessModel();

        Move testMove = new Move(7,3,6,3);

        assertFalse(test.isValidMove(testMove));
    }

    @Test
    public void testCantMoveOtherPieceIfWhiteKingInCheck(){
        ChessModel test = new ChessModel();
        test.board[5][4] = test.board[7][4];
        test.board[7][4] = null;

        test.setPiece(2,4,new Rook(Player.BLACK));

        Move testMove = new Move(6,0,5,0); //Move a pawn

        assertFalse(test.isValidMove(testMove));
    }


// Testing the move method ------------------------------------------------------

    @Test
    public void testWhiteKingFirstMove(){
        ChessModel test = new ChessModel();

        Move testMove = new Move(7,4,5,4);
        test.move(testMove);

        assertTrue(test.board[5][4].type().equals("King"));
        assertTrue(test.whiteKingFirstMove == false);
    }

    @Test
    public void testWhiteLeftRookFirstMove(){
        ChessModel test = new ChessModel();

        Move testMove = new Move(7,0,5,0);
        test.move(testMove);

        assertTrue(test.board[5][0].type().equals("Rook"));
        assertTrue(test.whiteRookLeftFirstMove == false);
    }

    @Test
    public void testWhiteRightRookFirstMove(){
        ChessModel test = new ChessModel();

        Move testMove = new Move(7,7,5,7);
        test.move(testMove);

        assertTrue(test.board[5][7].type().equals("Rook"));
        assertTrue(test.whiteRookRightFirstMove == false);
    }

    @Test
    public void testWhiteLeftCastling(){
        ChessModel test = new ChessModel();
        test.board[7][1] = null;
        test.board[7][2] = null;
        test.board[7][3] = null;

        Move testMove = new Move(7,4,7,2);
        test.isValidMove(testMove);
        test.move(testMove);

        assertTrue(test.board[7][2].type().equals("King"));
        assertTrue(test.board[7][3].type().equals("Rook"));
        assertTrue(test.castling == "L");
    }

    @Test
    public void testWhiteRightCastling(){
        ChessModel test = new ChessModel();
        test.board[7][5] = null;
        test.board[7][6] = null;

        Move testMove = new Move(7,4,7,6);
        test.isValidMove(testMove);
        test.move(testMove);

        assertTrue(test.board[7][6].type().equals("King"));
        assertTrue(test.board[7][5].type().equals("Rook"));
        assertTrue(test.castling == "R");
    }

}
