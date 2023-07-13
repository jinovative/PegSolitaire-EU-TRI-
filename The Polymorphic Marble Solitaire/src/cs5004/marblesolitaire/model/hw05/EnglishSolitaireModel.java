package cs5004.marblesolitaire.model.hw05;

/**
 * The {@code EnglishSolitaireModel} class represents the English Solitaire game model.
 * It implements the {@link MarbleSolitaireModel} interface.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {

  private final int size;
  private final SlotState[][] board;
  private int score;

  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  public EnglishSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);

  }

  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, (3 * armThickness - 2) / 2, (3 * armThickness - 2) / 2);
  }

  /**
   * Constructs an {@code EnglishSolitaireModel} object with the specified arm thickness
   * and position of the empty slot.
   *
   * @param armThickness the arm thickness of the game board
   * @param sRow         the row index of the empty slot
   * @param sCol         the column index of the empty slot
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number
   *                                  or the empty slot position is invalid
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    if (armThickness <= 0 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be a positive odd number");
    }

    this.size = 3 * armThickness - 2;
    this.board = new SlotState[size][size];
    this.score = 0;

    // Check if the empty cell position is valid
    if (armThickness >= 3 && sRow == 0 && sCol == 0 && sCol == sRow) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }

    initializeBoard(sRow, sCol);
  }

  /**
   * Initializes the game board by placing marbles and the empty slot in their initial positions.
   *
   * @param sRow the row index of the empty slot
   * @param sCol the column index of the empty slot
   */
  private void initializeBoard(int row, int col) {
    int size = getBoardSize();

    // Initialize all slots as INVALID
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = SlotState.Invalid;
      }
    }

    // Set valid slots as MARBLE
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (validPosition(i, j)) {
          board[i][j] = SlotState.Marble;
          score++;
        } else {
          board[i][j] = SlotState.Invalid;
        }
      }
    }

    // Set the given slot as EMPTY
    if (validPosition(row, col)) {
      board[row][col] = SlotState.Empty;
      score--;
    } else {
      throw new IllegalArgumentException(
              "Invalid empty cell position 2 (" + row + "," + col + ")");
    }
  }

  /**
   * Checks if the specified position is valid on the game board.
   *
   * @param row the row index
   * @param col the column index
   * @return {@code true} if the position is valid, {@code false} otherwise
   */
  private boolean validPosition(int row, int col) {
    int armThickness = (getBoardSize() + 2) / 3;
    int size = 3 * armThickness - 2;

    // Outside the board
    if (row < 0 || col < 0 || row >= size || col >= size) {
      return false;
    }

    // Invalid positions in Quadrant 1
    if (row < armThickness - 1 && col < armThickness - 1) {
      return false;
    }

    // Invalid positions in Quadrant 2
    if (row < armThickness - 1 && col >= size - armThickness + 1) {
      return false;
    }

    // Invalid positions in Quadrant 3
    if (row >= size - armThickness + 1 && col >= size - armThickness + 1) {
      return false;
    }

    // Invalid positions in Quadrant 4
    return row < size - armThickness + 1 || col >= armThickness - 1;

    // If none of the above conditions are met, it's a valid position
  }

  /**
   * Resets the game by creating a new game board and placing marbles and the empty slot
   * in their initial positions.
   */
  public void newGame() {
    initializeBoard(3, 3);
    score = 0;
  }

  /**
   * Returns a copy of the game board.
   *
   * @return a copy of the game board
   */
  public SlotState[][] getBoard() {
    SlotState[][] copyBoard = new SlotState[size][size];

    // Iterates over each cell of the game board and creates a copy of the board.
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (validPosition(row, col)) {
          copyBoard[col][row] = board[row][col];
        } else {
          copyBoard[row][col] = SlotState.Invalid;
        }
      }
    }
    return copyBoard;
  }



  @Override
  public int getBoardSize() {
    return size;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= size || col < 0 || col >= size) {
      throw new IllegalArgumentException("Invalid position (" + row + "," + col + ")");
    }
    return board[row][col];
  }

  @Override
  public int getScore() {
    int marblesCount = 0;

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (validPosition(row, col) && board[row][col] == SlotState.Marble) {
          marblesCount++; // Count the remaining marbles on the board
        }
      }
    }

    return marblesCount;
  }

  /**
   * Checks if a move from the specified position to the target position is a valid move.
   *
   * @param fromRow the row index of the starting position
   * @param fromCol the column index of the starting position
   * @param toRow   the row index of the target position
   * @param toCol   the column index of the target position
   * @return {@code true} if the move is valid, {@code false} otherwise
   */
  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    // Check if the from and to positions are valid
    if (!validPosition(fromRow, fromCol) || !validPosition(toRow, toCol)) {
      return false;
    }

    // Check if the from position contains a marble
    if (board[fromRow][fromCol] != SlotState.Marble) {
      return false; // No marble at the from position
    }

    // Calculate the differences between the from and to positions
    int rowDiff = Math.abs(toRow - fromRow);
    int colDiff = Math.abs(toCol - fromCol);

    // Check if the positions are two spaces apart in the same row or column
    if (!((rowDiff == 2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2))) {
      return false; // Positions are not adjacent or not in straight line
    }

    int midRow = (fromRow + toRow) / 2;
    int midCol = (fromCol + toCol) / 2;

    // Check if there's a marble at the middle position
    if (board[midRow][midCol] != SlotState.Marble) {
      return false; // No marble to jump over
    }

    // Check if the to position is empty
    return board[toRow][toCol] == SlotState.Empty; // Destination is not empty
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move");
    }

    board[fromRow][fromCol] = SlotState.Empty;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
  }

  @Override
  public boolean isGameOver() {
    // Check if there are any valid moves available
    if (getScore() == 0) {
      return true; // No marbles remaining, game is over
    }

    // Check if there are any valid moves available
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        // Check if the current position contains a marble
        if (board[row][col] == SlotState.Marble) {
          // Check if there are valid moves from this position
          if (isValidMove(row, col, row + 2, col)
                  || isValidMove(row, col, row - 2, col)
                  || isValidMove(row, col, row, col + 2)
                  || isValidMove(row, col, row, col - 2)) {
            // Found a valid move, so the game is not over
            return false;
          }
        }
      }
    }
    // No valid moves found and there are still marbles on the board, so the game is over
    return true;
  }
}

