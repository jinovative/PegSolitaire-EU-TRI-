package cs5004.marblesolitaire;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.model.hw07.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw07.EuropeanSolitaireModel;
import cs5004.marblesolitaire.model.hw07.TriangleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;
import cs5004.marblesolitaire.view.TriangleSolitaireTextView;


public final class MarbleSolitaire {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java MarbleSolitaire <board_shape> [-size N] [-hole R C]");
      return;
    }

    String boardShape = args[0];
    int size = -1;
    int holeRow = -1;
    int holeCol = -1;

    // Process command-line arguments
    for (int i = 1; i < args.length; i++) {
      if ("-size".equals(args[i]) && i + 1 < args.length) {
        size = Integer.parseInt(args[i + 1]);
        i++;
      } else if ("-hole".equals(args[i]) && i + 2 < args.length) {
        holeRow = Integer.parseInt(args[i + 1]);
        holeCol = Integer.parseInt(args[i + 2]);
        i += 2;
      }
    }

    MarbleSolitaireModel model = createModel(boardShape, size, holeRow, holeCol);
    if (model == null) {
      System.out.println("Invalid board shape: " + boardShape);
      return;
    }

    MarbleSolitaireView view = new TriangleSolitaireTextView(model);
    view.renderBoard();
  }

  private static MarbleSolitaireModel createModel(String boardShape, int size, int holeRow, int holeCol) {
    switch (boardShape) {
      case "english":
        return new EnglishSolitaireModel(size, holeRow, holeCol);
      case "european":
        return (MarbleSolitaireModel) new EuropeanSolitaireModel();
      case "triangular":
        return (MarbleSolitaireModel) new TriangleSolitaireModel(size, holeRow, holeCol);
      default:
        return null;
    }
  }
}
