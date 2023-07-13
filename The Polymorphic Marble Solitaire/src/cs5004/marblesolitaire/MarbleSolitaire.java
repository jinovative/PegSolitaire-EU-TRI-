package cs5004.marblesolitaire;

import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.model.hw07.EuropeanSolitaireModel;
import cs5004.marblesolitaire.model.hw07.TriangleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireTextView;
import cs5004.marblesolitaire.view.MarbleSolitaireView;
import cs5004.marblesolitaire.view.TriangleSolitaireTextView;

/**
 * These arguments will appear in the String[] args parameter to your main method.
 */
public final class MarbleSolitaire {
  /**
   *  This argument will decide which board shape (and hence which model and view) you should use.
   * @param args decide which board shape
   */
  public static void main(String[] args) {
    for (int i = 0; i < args.length; i += 3) {
      MarbleSolitaireModel game;
      MarbleSolitaireView view;
      String gameType = args[i];

      // Parse the size of the game board
      int size = Integer.parseInt(args[i + 2]);

      // Check the type of game and create the corresponding model
      switch (gameType.toLowerCase()) {
        case "english":
          game = new EnglishSolitaireModel(size);
          view = new MarbleSolitaireTextView(game);
          break;
        case "triangle":
          game = new TriangleSolitaireModel(size);
          view = new TriangleSolitaireTextView(game);
          break;
        case "european":
          game = new EuropeanSolitaireModel(size);
          view = new MarbleSolitaireTextView(game);
          break;
        default:
          throw new IllegalArgumentException("Invalid game type: " + gameType);
      }

      // Display the game board
      try {
        view.renderBoard();
        System.out.println("\n-----------------------");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

