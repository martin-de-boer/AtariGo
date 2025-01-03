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

    //lower value = more exploration
    //higher value = more exploitation
    //standard is 10
    static final int WIN_SCORE = 10;


    static final int TIME_LIMIT = 5000;

    //higher value = more exploration
    //lower value = more exploitation
    //standard is 1.41
    static final double UCT_CONSTANT = 6;

    public static void main(String[] args) {
        Board board = new Board();

        board.setField(2, Color.BLACK);  // (0,2)

        board.setField(9, Color.BLACK);  // (1,2)
        board.setField(10, Color.BLACK);  // (1,3)
        board.setField(11, Color.BLACK); // (1,4)

        board.setField(15, Color.WHITE); // (2,1)
        board.setField(16, Color.BLACK); // (2,2)
        board.setField(17, Color.WHITE); // (2,3)
        board.setField(18, Color.WHITE); // (2,4)
        board.setField(19, Color.WHITE); // (2,5)

        board.setField(22, Color.WHITE); // (3,1)
        board.setField(23, Color.WHITE); // (3,2)
        board.setField(24, Color.BLACK); // (3,3)
        board.setField(25, Color.BLACK); // (3,4)
        board.setField(26, Color.WHITE); // (3,5)

        board.setField(30, Color.BLACK); // (4,1)
        board.setField(31, Color.WHITE); // (4,2)
        board.setField(32, Color.BLACK); // (4,3)

        board.setField(38, Color.WHITE); // (5,2)

        board.setField(43, Color.BLACK); // (6,0)


        Node root = new Node(board);

        MCTS mcts = new MCTS();

        mcts.search(root);
    }

    /**
     * determines the best Move for a game.
     * @param game
     * @return
     */
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

    /**
     * the main body of mcts
     * simulates games and chooses the best move based on the simulations
     * @param root
     * @return
     */
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
        //printScores(root);
        //System.out.format("%nThe optimal node is: %02d%n", root.children.indexOf(winner) + 1);
        //System.out.println(winner.board.toString());

        return winner;
    }

    /**
     * adds all possible children to a node
     * with one move played on the board.
     * isPlayerTurn is flipped
     * @param node
     */
    //TODO add dynamic expansion
    public void addChildNodes(Node node) {
//        System.out.println(node.toString());
        if(!node.board.isGameOver()) {
            Move winingMove = findWinningMove(node.board);
            if(winingMove == null) {
                for (int i : node.board.getFields(Color.EMPTY)) {
                    Board board = node.board.deepCopy();
                    board.setField(i, board.getTurn());
                    node.children.add(new Node(node, !node.isPlayerTurn, board));
                }
            } else {
                Board board = node.board.deepCopy();
                board.setField(winingMove.getField(), board.getTurn());
                node.children.add(new Node(node, !node.isPlayerTurn, board));
            }
        }
    }

    //@ pure;
    public Move findWinningMove(Board board) {
        for (Set<Integer> group : board.getGroups(board.getTurn().other())) {
            Set<Integer> liberties = board.getLiberties(group);
            if(liberties.size() == 1) {
                return new Move(liberties.iterator().next(), board.getTurn()) ;
            }
        }
        return null;
    }

    /**
     * finds a promising node
     * nodes not yet visited are favored otherwise decided by UCT
     * see UCT_CONSTANT
     * @param rootNode
     * @return
     */
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

//        System.out.println(promisingNode.board.toString());
//        System.out.println(promisingNode.isPlayerTurn);
//        System.out.println(game.getBoard().toString());

        promisingNode.playerWon = game.getWinner() == playerColor;

//        System.out.println(promisingNode.playerWon);

        // Back propagation of the random play.
        while (tempNode != null) {
            tempNode.visitCount++;

            // Add wining scores to booth player and opponent depending on the turn.
            if (promisingNode.playerWon) {
                tempNode.score += WIN_SCORE;
            }

            tempNode = tempNode.parent;
        }

//        System.out.println(promisingNode.score);
//        System.out.println("-----------------------------------------------------------------");
    }

    /**
     * "Picker method" gets the node with the highest score
     * @param rootNode
     * @return
     */
    public Node getWinnerNode(Node rootNode) {
        if(rootNode.children.isEmpty()) {
            return null;
        }
        return Collections.max(rootNode.children, Comparator.comparing(c -> c.score));
    }

    /**
     * prints the scores of each node for testing purposes
     * @param rootNode
     */
    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, rootNode.children.get(i).score, rootNode.children.get(i).visitCount);
            System.out.printf("%02d\t%.2f%n", i + 1, (float) (rootNode.children.get(i).score / 10) / (float) rootNode.children.get(i).visitCount);
            System.out.println(rootNode.children.get(i).board.toString());
        }
    }
}
