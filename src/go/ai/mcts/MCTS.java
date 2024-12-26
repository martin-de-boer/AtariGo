package go.ai.mcts;

import go.ai.interfaces.Strategy;
import go.ai.strategy.SimpleStrategy;
import go.model.game.Board;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.player.ComputerPlayer;
import java.util.*;

public class MCTS implements Strategy {
    static final int WIN_SCORE = 10;
    static final int TIME_LIMIT = 5000;

    //higher value = more exploration
    //lower value = more exploitation
    //standard is 1.41
    static final double UCT_CONSTANT = 7;

    @Override
    public Move determineMove(Game game) {
        System.out.println("MCTS simulating");
        MCTS mcts = new MCTS();

        Board oldBoard = game.getBoard();
        Color turn = oldBoard.getTurn();

        Node node = mcts.search(new Node(oldBoard));

        Board newBoard = node.board;

        Set<Integer> moves = newBoard.getFields(turn);
        moves.removeAll(oldBoard.getFields(turn));

        return new Move(moves.iterator().next(),turn);
    }

    public Node search(Node root) {
        Node winner;
        double timeLimit;

        addChildNodes(root);

        timeLimit = System.currentTimeMillis() + TIME_LIMIT;

        while (System.currentTimeMillis() < timeLimit) {

            Node promisingNode = getPromisingNode(root);

            if (promisingNode.children.isEmpty()) {
                addChildNodes(promisingNode);
            }

            simulateRandomPlay(promisingNode);
        }

        winner = getWinnerNode(root);
        printScores(root);
        System.out.format("%nThe optimal node is: %02d%n", root.children.indexOf(winner) + 1);
        System.out.println(winner.board.toString());

        return winner;
    }

    public void addChildNodes(Node node) {
        if(!node.board.isGameOver()) {
            for (int i : node.board.getFields(Color.EMPTY)) {
                Board board = node.board.deepCopy();
                board.setField(i, board.getTurn());
                node.children.add(new Node(node, !node.isPlayerTurn, board));
            }
        }
    }


    public Node getPromisingNode(Node rootNode) {
        Node promisingNode = rootNode;

        // Iterate until a node that hasn't been expanded is found.
        while (!promisingNode.children.isEmpty()) {
            double uctIndex = Double.MIN_VALUE;
            int nodeIndex = 0;

            for (int i = 0; i < promisingNode.children.size(); i++) {
                Node childNode = promisingNode.children.get(i);
                double uctTemp;

                if (childNode.visitCount == 0) {
                    return childNode;
                }

                uctTemp = ((double) childNode.score / childNode.visitCount) + UCT_CONSTANT * Math.sqrt(Math.log(promisingNode.visitCount) / (double) childNode.visitCount);

                if (uctTemp > uctIndex || (uctTemp == uctIndex && Math.random() > 0.5)) {
                    uctIndex = uctTemp;
                    nodeIndex = i;
                }
            }

            promisingNode = promisingNode.children.get(nodeIndex);
        }

        return promisingNode;
    }

    /**
     * Simulates a random play from a nodes current state and back propagates
     * the result.
     *
     * @param promisingNode Node that will be simulated.
     */
    public void simulateRandomPlay(Node promisingNode) {

        Node tempNode = promisingNode;

        ComputerPlayer p1 = new ComputerPlayer(new SimpleStrategy(), Color.BLACK);
        ComputerPlayer p2 = new ComputerPlayer(new SimpleStrategy(), Color.WHITE);
        Game game = new Game(p1, p2, promisingNode.board);

        Color playerColor = promisingNode.isPlayerTurn ? promisingNode.board.getTurn() : promisingNode.board.getTurn().other();


        while(!game.isGameOver()) {
            Move move = game.getTurn().determineMove(game);
            game.doMove(move);
        }

        promisingNode.playerWon = game.getWinner() == playerColor;

        // Back propagation of the random play.
        while (tempNode != null) {
            tempNode.visitCount++;

            // Add wining scores to booth player and opponent depending on the turn.
            if (promisingNode.playerWon) {
                tempNode.score += WIN_SCORE;
            }

            //System.out.println(tempNode.score);
            tempNode = tempNode.parent;
        }
    }

    public Node getWinnerNode(Node rootNode) {
        if(rootNode.children.isEmpty()) {
            return null;
        }
        return Collections.max(rootNode.children, Comparator.comparing(c -> c.score));
    }

    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, rootNode.children.get(i).score, rootNode.children.get(i).visitCount);
            System.out.println(rootNode.children.get(i).board.toString());
        }
    }
}
