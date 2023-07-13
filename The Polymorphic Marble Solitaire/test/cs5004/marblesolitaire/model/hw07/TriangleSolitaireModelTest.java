package cs5004.marblesolitaire.model.hw07;

import org.junit.Before;
import org.junit.Test;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState.SlotState;
import cs5004.marblesolitaire.view.TriangleSolitaireTextView;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for invalid getSlotAt method in TriangleSolitaireModel.
 */
public class TriangleSolitaireModelTest {
  private TriangleSolitaireModel model;
  private TriangleSolitaireModel custom;
  private TriangleSolitaireTextView view;

  @Before
  public void setup() {
    model = new TriangleSolitaireModel();
    custom = new TriangleSolitaireModel(7,3,3);
    view = new TriangleSolitaireTextView(model);
  }

  @Test
  public void testGetSlotAt() {
    assertEquals(SlotState.Marble, model.getSlotAt(1, 0));
    assertEquals(SlotState.Empty, model.getSlotAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetSlotAt() {
    model = new TriangleSolitaireModel(5);
    model.getSlotAt(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSlotAtInvalidPosition() {
    model.getSlotAt(5, 5);
  }

  @Test
  public void testDefaultConstructor() {
    assertNotNull(model);
    assertEquals(5, model.getBoardSize());
    assertEquals(14, model.getScore());
  }

  @Test
  public void testCustomConstructor() {
    assertNotNull(custom);
    assertEquals(7, model.getBoardSize());
    assertEquals(27, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor() {
    new TriangleSolitaireModel(-1, 0, 0);
  }

  @Test
  public void testMove() {
    model.move(2, 0, 0, 0);
    assertEquals(13, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove1() {
    model.move(0, 0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove2() {
    TriangleSolitaireModel model = new TriangleSolitaireModel(5);
    model.move(0, 1, 2, 1);
  }

  @Test
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
  }

  @Test
  public void testGetBoardSize() {
    assertEquals(5, model.getBoardSize());
    assertEquals(7, custom.getBoardSize());
  }

  @Test
  public void testGetScore() {
    assertEquals(14, model.getScore());
    assertEquals(27, custom.getScore());
  }

  @Test
  public void testTriangleMoveToInvalidPosition() {
    TriangleSolitaireModel model = new TriangleSolitaireModel();
    try {
      model.move(0, 0, 5, 5); // An invalid position
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid move 3");
    }
  }

  @Test
  public void testTriangleMovesNotTwoApartSameColumn() {
    TriangleSolitaireModel model = new TriangleSolitaireModel();
    try {
      model.move(2, 0, 3, 0); // Not two apart in the same column
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid move 3");
    }
  }

  @Test
  public void testNullAppendable() {
    TriangleSolitaireModel model = new TriangleSolitaireModel();
    try {
      TriangleSolitaireTextView view = new TriangleSolitaireTextView(model, null);
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Neither Model nor Appendable can be null");
    }
  }

  @Test
  public void testNullModelStateDefaultAppendable() {
    try {
      TriangleSolitaireTextView view = new TriangleSolitaireTextView(null);
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Neither Model nor Appendable can be null");
    }
  }

  @Test
  public void testBoardRepresentation() {
    String expectedBoard = "    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O";

    assertEquals(expectedBoard, view.toString());
  }

  @Test
  public void testBoardMove() {
    model.move(2, 0, 0, 0);
    String expectedBoard = "    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O";

    assertEquals(expectedBoard, view.toString());
  }

  @Test
  public void testBoardGameOver() {
    model.move(2, 0, 0, 0);
    model.move(2, 2, 2, 0);
    model.move(3, 0, 1, 0);
    model.move(4, 1, 2, 1);
    model.move(4, 4, 2, 2);
    model.move(4, 2, 4, 4);
    model.move(2, 1, 4, 3);
    model.move(4, 4, 4, 2);
    model.move(0, 0, 2, 0);
    model.move(2, 2, 0, 0);

    String expectedBoard = "    O\n" +
              "   _ _\n" +
              "  O _ _\n" +
              " _ _ _ _\n" +
              "O _ O _ _";

    assertEquals(expectedBoard, view.toString());
    assertEquals(4, model.getScore());
    assertTrue(model.isGameOver());
  }

}