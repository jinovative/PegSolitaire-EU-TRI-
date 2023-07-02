package cs5004.marblesolitaire.view;

import cs5004.marblesolitaire.model.hw07.MarbleSolitaireModel;

import java.io.IOException;

public class TriangleSolitaireTextView implements MarbleSolitaireView {
  private final MarbleSolitaireModel model;

  public TriangleSolitaireTextView(MarbleSolitaireModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    int size = model.getBoardSize();
    StringBuilder sb = new StringBuilder();

    for (int row = 0; row < size; row++) {
      appendSpaces(sb, size - row - 1);
      for (int col = 0; col <= row; col++) {
        sb.append(getSlotCharacter(row, col)).append(" ");
      }
      sb.setLength(sb.length() - 1); // Remove the trailing space
      sb.append("\n");
    }

    return sb.toString();
  }

  private void appendSpaces(StringBuilder sb, int count) {
    for (int i = 0; i < count; i++) {
      sb.append(" ");
    }
  }

  private char getSlotCharacter(int row, int col) {
    MarbleSolitaireModel.SlotState slotState = model.getSlotAt(row, col);
    switch (slotState) {
      case Marble:
        return 'O';
      case Empty:
        return '_';
      default:
        return ' ';
    }
  }

  @Override
  public void renderBoard() throws IOException {
    System.out.println(toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    System.out.println(message);
  }
}
