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
    } else {
      throw new IllegalArgumentException(
              "Invalid empty cell position 2 (" + row + "," + col + ")");
    }
  }

  private boolean isValidPosition(int row, int col) {
    int armThickness = sideLength;
    int size = 3 * armThickness - 2;

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

    // Outside the board
    if (row < 0 || col < 0 || row >= size || col >= size) {
      return false;
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
      throw new IllegalArgumentException("Invalid move");
    }

    // Check if 'from' position contains a marble
    if (board[fromRow][fromCol] != SlotState.Marble) {
      throw new IllegalArgumentException("No marble at 'from' position");
    }

    // Check if 'to' position is empty
    if (board[toRow][toCol] != SlotState.Empty) {
      throw new IllegalArgumentException("'to' position is not empty");
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

    if ((rowDiff == 2 && colDiff == 0) || (colDiff == 2 && rowDiff == 0)) {
      int midRow = (fromRow + toRow) / 2;
      int midCol = (fromCol + toCol) / 2;

      return board[midRow][midCol] == SlotState.Marble;
    }

    return false;
  }

  @Override
  public boolean isGameOver() {
    int size = 3 * sideLength - 2;
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (board[row][col] == SlotState.Marble) {
          // Check if any possible moves can be made
          // Horizontal and Vertical moves
          for (int d = -2; d <= 2; d += 4) {
            // Horizontal
            if (isValidMove(row, col, row, col + d)) {
              return false; // At least one valid move available, game is not over
            }
            // Vertical
            if (isValidMove(row, col, row + d, col)) {
              return false; // At least one valid move available, game is not over
            }
          }

          // Diagonal moves
          for (int dr = -2; dr <= 2; dr += 4) {
            for (int dc = -2; dc <= 2; dc += 4) {
              if (isValidMove(row, col, row + dr, col + dc)) {
                return false; // At least one valid move available, game is not over
              }
            }
          }
        }
      }
    }

    // If we reach this point, it means there are no valid moves available, thus the game is over
    return true;
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
      StringBuilder lineBuilder = new StringBuilder();
      for (int j = 0; j < size; j++) {
        if (!isValidPosition(i, j)) {
          lineBuilder.append("  "); // For invalid positions
        } else {
          switch (board[i][j]) {
            case Marble:
              lineBuilder.append("O ");
              break;
            case Empty:
              lineBuilder.append("_ ");
              break;
            default:
              lineBuilder.append("  "); // For invalid positions
              break;
          }
        }
      }
      // Remove extra space at the end of each line and add newline character
      String line = lineBuilder.toString().stripTrailing();
      builder.append(line);

      if (i < size - 1) {
        builder.append("\n");
      }
    }

    return builder.toString();
  }

  public static void main(String[] args) {
    try {
      EuropeanSolitaireModel model = new EuropeanSolitaireModel(3, 3, 3);
      System.out.println("Initial game state:");
      System.out.println(model.getGameState());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

//    EuropeanSolitaireModel model = new EuropeanSolitaireModel();
//    String expectedGameState = """
//                O O O
//              O O O O O
//            O O O O O O O
//            O O O _ O O O
//            O O O O O O O
//              O O O O O
//                O O O""";
//
//    System.out.println("Initial game state:");
//    System.out.println(model.getGameState());
//
//    if (model.getGameState().equals(expectedGameState)) {
//      System.out.println("Test passed! The game state matches the expected state.");
//    } else {
//      System.out.println("Test failed. The game state doesn't match the expected state.");
//    }
    //  public static void main(String[] args) {
//    printBoard(3);  // armThickness = 3
//  }
//

  }
}