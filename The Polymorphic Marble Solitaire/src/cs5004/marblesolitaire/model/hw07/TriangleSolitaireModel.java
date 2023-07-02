package cs5004.marblesolitaire.model.hw07;

public class TriangleSolitaireModel implements MarbleSolitaireModel {
  private final int dimensions;
  private final SlotState[][] board;
  private int score;

  public TriangleSolitaireModel() {
    this(6, 0, 0);
  }

  public TriangleSolitaireModel(int dimensions) {
    this(dimensions, 0, 0);
  }

  public TriangleSolitaireModel(int row, int col) {
    this(6, row, col);
  }

  public TriangleSolitaireModel(int dimensions, int row, int col) {
    if (dimensions <= 0) {
      throw new IllegalArgumentException("Dimensions must be a positive number");
    }
    if (row < 0 || row >= dimensions || col < 0 || col > row) {
      throw new IllegalArgumentException("Invalid position (" + row + "," + col + ")");
    }

    this.dimensions = dimensions;
    this.board = new SlotState[dimensions][];
    this.score = 0;

    initializeBoard();
  }

  private void initializeBoard() {
    for (int row = 0; row < dimensions; row++) {
      board[row] = new SlotState[row + 1];
      for (int col = 0; col <= row; col++) {
        board[row][col] = SlotState.Marble;
      }
    }
    board[0][0] = SlotState.Empty; // Set the top slot as empty
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move");
    }

    board[fromRow][fromCol] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
    score--;
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < dimensions; row++) {
      for (int col = 0; col <= row; col++) {
        if (board[row][col] == SlotState.Marble) {
          if (isValidMove(row, col, row + 2, col)
                  || isValidMove(row, col, row + 2, col + 2)
                  || isValidMove(row, col, row, col + 2)) {
            return false; // At least one valid move available, game is not over
          }
        }
      }
    }
    return true; // No valid moves available, game is over
  }

  @Override
  public int getBoardSize() {
    return dimensions;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= dimensions || col < 0 || col > row) {
      throw new IllegalArgumentException("Invalid position (" + row + "," + col + ")");
    }
    return board[row][col];
  }

  @Override
  public int getScore() {
    return score;
  }

  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (toRow < 0 || toRow >= dimensions || toCol < 0 || toCol > toRow) {
      return false; // Destination position is out of bounds
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
}