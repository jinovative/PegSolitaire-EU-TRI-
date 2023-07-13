package cs5004.marblesolitaire.model.hw07;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;

/**
 * This class represents the European version of the Marble Solitaire game.
 */
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

  /**
   * Initializes a new European Solitaire game with a specific side length and initial empty cell.
   *
   * @param sideLength the side length of the game board must be an odd number and at least 3.
   * @param row the row index of the initial empty cell, which must be a non-negative integer.
   * @param col the column index of the initial empty cell, which must be a non-negative integer.
   * @throws IllegalArgumentException if the side length is less than 3 or even.
   * @throws IllegalArgumentException if the row or column index is negative.
   */
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

  /**
   * Initializes the game board with a specific initial empty cell position.
   * This method first sets all slots on the board as INVALID.
   * Then, it sets the valid slots as MARBLE, and increments the score accordingly.
   * Finally, it sets the given slot as EMPTY and decrements the score.
   *
   * @param row the row index empty cell must be a valid position on the board.
   * @param col the column index empty cell, which must be a valid position on the board.
   * @throws IllegalArgumentException if the given row and col not valid position on the board.
   */
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
        if ((row == i && col == armThickness - j - 1)
                || (row == armThickness - j - 1 && col == i)) {
          return true;
        }
        // Quadrant 2
        if ((row == i && col == size - armThickness + j)
                || (row == armThickness - j - 1 && col == size - i - 1)) {
          return true;
        }
        // Quadrant 3
        if ((row == size - i - 1 && col == size - armThickness + j)
                || (row == size - armThickness + j && col == size - i - 1)) {
          return true;
        }
        // Quadrant 4
        if ((row == size - i - 1 && col == armThickness - j - 1)
                || (row == size - armThickness + j && col == i)) {
          return true;
        }
      }
    }

    // Outside the board
    if (row < 0 || col < 0 || row >= size || col >= size) {
      return false;
    }
    // In the corners of the board
    return ((row >= (armThickness - 1)) || (col >= (armThickness - 1)))
            && ((row >= (armThickness - 1)) || (col < ((size - armThickness) + 1)))
            && ((row < ((size - armThickness) + 1)) || (col >= (armThickness - 1)))
            && ((row < ((size - armThickness) + 1)) || (col < ((size - armThickness) + 1)));
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
    if (getScore() == 1) {
      return true; // One marble left, game is over.
    }

    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (this.board[i][j] == SlotState.Marble) {
          // Check all four directions around the marble
          if ((i - 2 >= 0 && this.board[i - 1][j] == SlotState.Marble
                  && this.board[i - 2][j] == SlotState.Empty)
                  || (i + 2 < this.board.length && this.board[i + 1][j] == SlotState.Marble
                          && this.board[i + 2][j] == SlotState.Empty)
                  || (j - 2 >= 0 && this.board[i][j - 1] == SlotState.Marble
                          && this.board[i][j - 2] == SlotState.Empty)
                  || (j + 2 < this.board[i].length && this.board[i][j + 1] == SlotState.Marble
                          && this.board[i][j + 2] == SlotState.Empty)) {
            // There is a possible move
            return false;
          }
        }
      }
    }

    // No more possible moves
    return true;
  }

  @Override
  public int getBoardSize() {
    return sideLength * 3 - 2;
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

  //  public static void main(String[] args) {
  //    try {
  //      EuropeanSolitaireModel model = new EuropeanSolitaireModel(3, 3, 3);
  //      System.out.println("Initial game state:");
  //      System.out.println(model.getGameState());
  //    } catch (IllegalArgumentException e) {
  //      e.printStackTrace();
  //    }
  //  }
}