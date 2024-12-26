package go.ai.mcts;

import go.model.game.Board;
import java.util.ArrayList;

public class Node {
    Node parent;
    ArrayList<Node> children;
    boolean isPlayerTurn;
    boolean playerWon;
    int score;
    int visitCount;
    Board board;

    public Node() {
        children = new ArrayList<Node>();
        isPlayerTurn = true;
        playerWon = false;
        score = 0;
        visitCount = 0;
        board = new Board();
    }

    public Node(Board board) {
        this();
        this.board = board;
    }

    public Node(Node parent, boolean isPlayerTurn) {
        this();
        this.parent = parent;
        this.isPlayerTurn = isPlayerTurn;
    }

    public Node(Node parent, boolean isPlayerTurn, Board board) {
        this(parent, isPlayerTurn);
        this.board = board;
    }
}
