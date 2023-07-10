package cs5004.marblesolitaire.view;

import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;
import static cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel.SlotState;

public class TriangleSolitaireTextView implements MarbleSolitaireView {
  private final MarbleSolitaireModelState model;
  private Appendable ap;

  public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

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

    for (int row = 0; row < size; row++) {
      StringBuilder lineBuilder = new StringBuilder();

      for (int col = 0; col < size; col++) {
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
        if (col < size - 1) {
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
