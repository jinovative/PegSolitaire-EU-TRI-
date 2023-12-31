package cs5004.marblesolitaire.model.hw07;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;

public class EuropeanSolitaireModel implements MarbleSolitaireModel {
  private final int sideLength;
  private final SlotState[][] board;
  private int score;

  public EuropeanSolitaireModel() {
    this(3);
  }

  public EuropeanSolitaireModel(int sideLength) {
    this(sideLength, sideLength, sideLength);
  }

  public EuropeanSolitaireModel(int row, int col) {
    this(3, row, col);
  }

  public EuropeanSolitaireModel(int sideLength, int row, int col) {
    if (sideLength < 3 || sideLength % 2 == 0) {
      throw new IllegalArgumentException("Side length must be greater than or equal to 3");
    }

    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Initial empty cell position (" + row + "," + col + ")");
    }

    this.sideLength = sideLength;
    int size = 3 * sideLength - 2;
    this.board = new SlotState[size][size];
    this.score = 0;

    initializeBoard(row, col);
  }


  private void initializeBoard(int row, int col) {
    int size = 3 * sideLength - 2;

    // Initialize all slots as INVALID
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = SlotState.Invalid;
      }
    }

    // Set valid slots as MARBLE
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (isValidPosition(i, j)) {
          board[i][j] = SlotState.Marble;
          score++;
        } else {
          board[i][j] = SlotState.Invalid;
        }
      }
    }

    // Set the given slot as EMPTY
    if (isValidPosition(row, col)) {
      board[row][col] = SlotState.Empty;
      score--;
    }
    else {
      throw new IllegalArgumentException(
              "Invalid empty cell position 2 (" + row + "," + col + ")");
    }
  }

  private boolean isValidPosition(int row, int col) {
    int armThickness = sideLength;
    int size = 3 * armThickness - 2;  // Assuming size = 3 * armThickness - 2
    // Outside the board
    if (row < 0 || col < 0 || row >= size || col >= size) {
      return false;
    }

    // Additional valid positions
    for (int i = 1; i <= armThickness - 2; i++) {
      for (int j = 1; j <= i; j++) {
        // Quadrant 1
        if ((row == i && col == armThickness - j - 1) ||
                (row == armThickness - j - 1 && col == i)) {
          return true;
        }
        // Quadrant 2
        if ((row == i && col == size - armThickness + j) ||
                (row == armThickness - j - 1 && col == size - i - 1)) {
          return true;
        }
        // Quadrant 3
        if ((row == size - i - 1 && col == size - armThickness + j) ||
                (row == size - armThickness + j && col == size - i - 1)) {
          return true;
        }
        // Quadrant 4
        if ((row == size - i - 1 && col == armThickness - j - 1) ||
                (row == size - armThickness + j && col == i)) {
          return true;
        }
      }
    }

    // In the corners of the board
    if (((row < (armThickness - 1)) && (col < (armThickness - 1)))
            || ((row < (armThickness - 1)) && (col >= ((size - armThickness) + 1)))
            || ((row >= ((size - armThickness) + 1)) && (col < (armThickness - 1)))
            || ((row >= ((size - armThickness) + 1)) && (col >= ((size - armThickness) + 1)))) {
      return false;
    }

    return true;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move 33");
    }

    board[fromRow][fromCol] = SlotState.Empty;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    score--;
  }

  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) {
      return false;
    }

    int rowDiff = Math.abs(toRow - fromRow);
    int colDiff = Math.abs(toCol - fromCol);

    if ((rowDiff == 0 && colDiff == 2) || (rowDiff == 2 && colDiff == 0)) {
      int midRow = (fromRow + toRow) / 2;
      int midCol = (fromCol + toCol) / 2;

      return board[midRow][midCol] == SlotState.Marble;
    }

    return false;
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < sideLength; row++) {
      for (int col = 0; col < sideLength; col++) {
        if (board[row][col] == SlotState.Marble) {
          if (isValidMove(row, col, row, col + 2) ||
                  isValidMove(row, col, row, col - 2) ||
                  isValidMove(row, col, row + 2, col) ||
                  isValidMove(row, col, row - 2, col)) {
            return false; // At least one valid move available, game is not over
          }
        }
      }
    }

    return true; // No valid moves available, game is over
  }

  @Override
  public int getBoardSize() {
    return sideLength;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    int boardSize = board.length;
    if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
      throw new IllegalArgumentException("Invalid position (" + row + ", " + col + ")");
    }

    return board[row][col];
  }

  @Override
  public int getScore() {
    return score;
  }

  public String getGameState() {
    StringBuilder builder = new StringBuilder();
    int size = 3 * sideLength - 2;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!isValidPosition(i, j)) {
          builder.append("  "); // For invalid positions
        } else {
          switch (board[i][j]) {
            case Marble:
              builder.append("O ");
              break;
            case Empty:
              builder.append("_ ");
              break;
            default:
              builder.append("  "); // For invalid positions
              break;
          }
        }
      }
      // Remove extra space at the end of each line and add newline character
      builder.setLength(builder.length() - 1);
      builder.append("\n");
    }
    // Remove extra newline character at the end of the final string
    builder.setLength(builder.length() - 1);

    return builder.toString();
  }

  public static void main(String[] args) {
//    try {
//      EuropeanSolitaireModel model = new EuropeanSolitaireModel(3, 3, 3);
//      System.out.println("Initial game state:");
//      System.out.println(model.getGameState());
//    } catch (IllegalArgumentException e) {
//      e.printStackTrace();
//    }

    EuropeanSolitaireModel model = new EuropeanSolitaireModel();

    try {
      model.move(0, 2, 0, 4); // trying to move from an empty position
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    // Print the game status after trying to make the invalid move
    System.out.println(model.getGameState());
  }
  //  public static void main(String[] args) {
//    printBoard(3);  // armThickness = 3
//  }
//
//  public static void printBoard(int armThickness) {
//    enum SlotState {
//      INVALID, MARBLE, EMPTY
//    }
//    int size = 3 * armThickness - 2;
//    SlotState[][] board = new SlotState[size][size];
//
//    // Initialize all slots as VALID
//    for (int i = 0; i < size; i++) {
//      for (int j = 0; j < size; j++) {
//        board[i][j] = SlotState.MARBLE;
//      }
//    }
//
//    // Set INVALID slots
//    for (int i = 0; i < armThickness - 1; i++) {
//      for (int j = 0; j < armThickness - 1; j++) {
//        board[i][j] = SlotState.INVALID;  // Top-left rectangle
//        board[i][j + 2 * armThickness - 1] = SlotState.INVALID;  // Top-right rectangle
//        board[i + 2 * armThickness - 1][j] = SlotState.INVALID;  // Bottom-left rectangle
//        board[i + 2 * armThickness - 1][j + 2 * armThickness - 1] = SlotState.INVALID;
//        board[armThickness][armThickness] = SlotState.EMPTY;// Bottom-right rectangle
//      }
//    }
//
//    // Set MARBLE slots
//    board[1][1] = SlotState.MARBLE; // (1,1)
//    board[1][5] = SlotState.MARBLE; // (1,5)
//    board[5][1] = SlotState.MARBLE; // (5,1)
//    board[5][5] = SlotState.MARBLE; // (5,5)

//    // Print the board
//    for (int i = 0; i < size; i++) {
//      for (int j = 0; j < size; j++) {
//        if (board[i][j] == SlotState.MARBLE) {
//          System.out.print("O ");
//        } else if (board[i][j] == SlotState.EMPTY) {
//          System.out.print("_ ");
//        } else {
//          System.out.print("  ");
//        }
//      }
//      System.out.println();
//    }
//  }
}