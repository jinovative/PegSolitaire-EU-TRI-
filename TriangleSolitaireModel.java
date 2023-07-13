package cs5004.marblesolitaire.model.hw07;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;

public class TriangleSolitaireModel implements MarbleSolitaireModel {
  private final int dimensions;
  private final SlotState[][] board;
  private int score;
  private final int emptyRow;
  private final int emptyCol;

  public TriangleSolitaireModel()
  {
    this(5, 0, 0);
  }

  public TriangleSolitaireModel(int dimensions) throws  IllegalArgumentException
  {
    this(dimensions, 0, 0);
  }

  public TriangleSolitaireModel(int row, int col) throws IllegalArgumentException
  {
    this(5, row, col);
  }

  public TriangleSolitaireModel(int dimensions, int row, int col) throws IllegalArgumentException
  {
    if (dimensions <= 0) {
      throw new IllegalArgumentException("Dimensions must be a positive number");
    }
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Invalid position 1 (" + row + "," + col + ")");
    }

    this.dimensions = dimensions;
    this.emptyRow = row;
    this.emptyCol = col;
    this.board = new SlotState[dimensions][];
    this.score = (dimensions * (dimensions + 1)) / 2 - 1;

    initializeBoard();
  }

  private void initializeBoard() {
    for (int row = 0; row < dimensions; row++) {
      board[row] = new SlotState[dimensions];  // Make every row the same length
      for (int col = 0; col < dimensions; col++) {
        if (col > row) {  // This cell is not within the triangle
          board[row][col] = SlotState.Invalid;
        } else {  // This cell is within the triangle
          board[row][col] = SlotState.Marble;
        }
      }
    }
    // Set the specified slot as empty
    if(emptyRow < dimensions && emptyCol < board[emptyRow].length && emptyCol <= emptyRow) {
      board[emptyRow][emptyCol] = SlotState.Empty;
    } else {
      throw new IllegalArgumentException("Invalid empty slot position");
    }
  }


  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow < 0 || fromRow >= dimensions || fromCol < 0 || fromCol >= board[fromRow].length || fromRow < fromCol) {
      return false; // Starting position is out of bounds or invalid
    }

    if (toRow < 0 || toRow >= dimensions || toCol < 0 || toCol >= board[toRow].length || toRow < toCol) {
      return false; // Destination position is out of bounds or invalid
    }


    int rowDiff = Math.abs(toRow - fromRow);
    int colDiff = Math.abs(toCol - fromCol);

    if (!((rowDiff == 2 && colDiff == 0) || (rowDiff == 2 && colDiff == 2)
            || (rowDiff == 0 && colDiff == 2))) {
      return false; // Invalid move, positions are not 2 spaces apart
    }

    int midRow = (fromRow + toRow) / 2;
    int midCol = (fromCol + toCol) / 2;

    if (board[fromRow][fromCol] == SlotState.Marble && board[toRow][toCol] == SlotState.Empty &&
            board[midRow][midCol] == SlotState.Marble) {
      return true; // Valid move, positions contain marbles and an empty slot
    }

    return false; // Invalid move
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move 3");
    }

    if (fromRow < fromCol || toRow < toCol) {
      throw new IllegalArgumentException("Invalid position: row cannot be less than col");
    }

    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid movedasdasdasd");
    }

    board[fromRow][fromCol] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
    score--;
  }

  @Override
  public boolean isGameOver() {
    // 1. No more valid move
    for (int row = 0; row < dimensions; row++) {
      for (int col = 0; col <= row; col++) {
        if (board[row][col] == SlotState.Marble) {
          // check the other positions
          int[][] potentialMoves = {{row - 2, col}, {row + 2, col}, {row, col - 2}, {row, col + 2},
                  {row + 2, col + 2}, {row - 2, col - 2}};
          for (int[] move : potentialMoves) {
            int newRow = move[0];
            int newCol = move[1];
            if (newRow >= 0 && newCol >= 0 && newRow < dimensions && newCol <= newRow &&
                    isValidMove(row, col, newRow, newCol)) {
              return false; // there has valid move.
            }
          }
        }
      }
    }

    // 2. all the pegs are removed
    int marbleCount = 0;
    for (int row = 0; row < dimensions; row++) {
      for (int col = 0; col <= row; col++) {
        if (board[row][col] == SlotState.Marble) {
          marbleCount++;
        }
      }
    }
    if (marbleCount > 1) {
      return false;
    }


    return true;
  }


  @Override
  public int getBoardSize() {
    return dimensions;
  }




  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0 || row >= dimensions || col >= dimensions) {
      throw new IllegalArgumentException("Invalid position kk  (" + row + "," + col + ")");
    }

    return board[row][col];
  }




  @Override
  public int getScore() {
    return score;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (int row = 0; row < dimensions; row++) {  // Use < instead of <=
      for (int col = 0; col <= row; col++) {
        switch (board[row][col]) {
          case Marble:
            builder.append("O ");
            break;
          case Empty:
            builder.append("_ ");
            break;
          default:  // Invalid
            builder.append("  ");
            break;
        }
      }
      builder.append("\n");
    }

    return builder.toString();
  }


  public void gameStatus() {
    System.out.println(this.toString());
  }

  public static void main(String[] args) {
    TriangleSolitaireModel game = new TriangleSolitaireModel(5, 0, 0);
    game.gameStatus();
  }

}
