package cs5004.marblesolitaire.model.hw07;

public class EuropeanSolitaireModel implements MarbleSolitaireModel {
  private final int sideLength;
  private final SlotState[][] board;
  private int score;

  public EuropeanSolitaireModel() {
    this(3);
  }

  public EuropeanSolitaireModel(int sideLength) {
    this(sideLength, sideLength / 2, sideLength / 2);
  }

  public EuropeanSolitaireModel(int row, int col) {
    this(3, row, col);
  }

  public EuropeanSolitaireModel(int sideLength, int row, int col) {
    if (sideLength < 3 || sideLength % 2 == 0) {
      throw new IllegalArgumentException("Side length must be an odd number greater than or equal to 3");
    }

    this.sideLength = sideLength;
    this.board = new SlotState[sideLength][sideLength];
    this.score = 0;

    initializeBoard(row, col);
  }

  private void initializeBoard(int row, int col) {
    for (int r = 0; r < sideLength; r++) {
      for (int c = 0; c < sideLength; c++) {
        if (isValidPosition(r, c)) {
          board[r][c] = SlotState.Marble;
          score++;
        } else {
          board[r][c] = SlotState.Invalid;
        }
      }
    }

    board[row][col] = SlotState.Empty;
    score--;
  }

  private boolean isValidPosition(int row, int col) {
    int mid = sideLength / 2;
    int offset = Math.abs(row - mid);
    return (col >= offset && col < sideLength - offset);
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move");
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

    return (rowDiff == 0 && colDiff == 2) || (rowDiff == 2 && colDiff == 0);
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
    if (!isValidPosition(row, col)) {
      throw new IllegalArgumentException("Invalid position (" + row + ", " + col + ")");
    }

    return board[row][col];
  }

  @Override
  public int getScore() {
    return score;
  }
}