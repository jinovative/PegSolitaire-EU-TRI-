package cs5004.marblesolitaire.view;

import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;
import static cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel.SlotState;

/**
 * This class represents the view for a Triangle Solitaire game.
 */
public class TriangleSolitaireTextView implements MarbleSolitaireView {
  private final MarbleSolitaireModelState model;
  private Appendable ap;

  /**
   * Constructor for TriangleSolitaireTextView that uses System.out as the Appendable.
   *
   * @param model the MarbleSolitaireModelState instance
   * @throws IllegalArgumentException if the model is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

  /**
   * Constructor for TriangleSolitaireTextView that accepts a custom Appendable.
   *
   * @param model the MarbleSolitaireModelState instance
   * @param ap the Appendable instance
   * @throws IllegalArgumentException if the model or the Appendable is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Neither Model nor Appendable can be null");
    }
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    int size = model.getBoardSize();
    StringBuilder stringBuilder = new StringBuilder();

    int longestRowLength = 2 * size - 1;

    for (int row = 0; row < size; row++) {
      StringBuilder lineBuilder = new StringBuilder();

      int currentRowLength = 2 * row + 1;
      int leadingSpaces = (longestRowLength - currentRowLength) / 2;

      // add leading spaces
      for (int i = 0; i < leadingSpaces; i++) {
        lineBuilder.append(' ');
      }

      for (int col = 0; col <= row; col++) {
        SlotState slotState = model.getSlotAt(row, col);
        char slotChar;

        if (SlotState.Marble == slotState) {
          slotChar = 'O';
        } else if (slotState == SlotState.Empty) {
          slotChar = '_';
        } else {
          slotChar = ' ';
        }

        lineBuilder.append(slotChar);

        // Add space after the slot, except for the last slot in the row
        if (col < row) {
          lineBuilder.append(' ');
        }
      }

      String line = lineBuilder.toString();
      stringBuilder.append(line);

      // Add newline after each line, except for the last line
      if (row < size - 1) {
        stringBuilder.append('\n');
      }
    }

    return stringBuilder.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    ap.append(toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    ap.append(message);
  }
}