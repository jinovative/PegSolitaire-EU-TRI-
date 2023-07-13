package cs5004.marblesolitaire.model.hw07;

import org.junit.Before;
import org.junit.Test;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState.SlotState;
import cs5004.marblesolitaire.view.MarbleSolitaireTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for invalid getSlotAt method in EuropeanSolitaireModel.
 */
public class EuropeanSolitaireModelTest {

  private MarbleSolitaireTextView view;
  private EuropeanSolitaireModel model;

  @Before
  public void setup() {
    model = new EuropeanSolitaireModel();
    this.view = new MarbleSolitaireTextView(model);

  }

  @Test
  public void testInitialGameState() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel();
    String expectedState =
            "    O O O\n" +
                    "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O";

    String actualState = view.toString();
    assertEquals(expectedState,actualState);
  }

  @Test
  public void testEuropeanEmptyModelCustomInvalidEmptyCell() {

    try {
      model = new EuropeanSolitaireModel(4, -1, 2);
      fail("Expected IllegalArgumentException to be thrown");
    } catch (IllegalArgumentException ignored) { }
  }

  @Test
  public void testGetBoardSizeDefaultConstructor() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel();
    assertEquals(7, model.getBoardSize());
  }

  @Test
  public void testInitialBoardCelSize3() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel(3);
    assertEquals(SlotState.Empty, model.getSlotAt(3, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 4));
  }

  @Test
  public void testInitialBoardCellSize5() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel(5);
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 4));
  }

  @Test
  public void testMoveValid() {
    model.move(5, 3, 3, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(5, 3));
    assertEquals(SlotState.Empty, model.getSlotAt(4, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalid() {
    model.move(3, 3, 3, 6);
  }

  @Test
  public void testScore() {
    assertEquals(36, model.getScore());
  }

  @Test
  public void testGameOver() {
    try {
      model.move(5,3,3,3);
      model.move(4,5,4,3);
      model.move(6,4,4,4);
      model.move(6,2,6,4);
      model.move(4,2,6,2);
      model.move(4,0,4,2);
      model.move(3,2,5,2);
      model.move(3,0,3,2);
      model.move(2,2,4,2);
      model.move(4,3,4,1);
      model.move(5,1,3,1);
      model.move(2,0,2,2);
      model.move(2,3,2,1);
      model.move(2,1,4,1);
      model.move(6,2,4,2);
      model.move(4,1,4,3);
      model.move(4,3,4,5);
      model.move(4,6,4,4);
      model.move(3,4,5,4);
      model.move(6,4,4,4);
      model.move(3,6,3,4);
      model.move(3,4,5,4);
      model.move(5,5,5,3);
      model.move(2,5,2,3);
      model.move(2,3,4,3);
      model.move(5,3,3,3);
      model.move(0,4,2,4);
      model.move(0,3,2,3);
      model.move(0,2,2,2);
      model.move(2,3,2,5);
      model.move(2,6,2,4);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    assertTrue(model.isGameOver());
    assertEquals(5, model.getScore());
  }

  @Test
  public void testInvalidGameOver() {
    assertFalse(model.isGameOver());
  }

  @Test
  public void testGameOverBoard() {
    // Move a marble
    try {
      model.move(5,3,3,3);
      model.move(4,5,4,3);
      model.move(6,4,4,4);
      model.move(6,2,6,4);
      model.move(4,2,6,2);
      model.move(4,0,4,2);
      model.move(3,2,5,2);
      model.move(3,0,3,2);
      model.move(2,2,4,2);
      model.move(4,3,4,1);
      model.move(5,1,3,1);
      model.move(2,0,2,2);
      model.move(2,3,2,1);
      model.move(2,1,4,1);
      model.move(6,2,4,2);
      model.move(4,1,4,3);
      model.move(4,3,4,5);
      model.move(4,6,4,4);
      model.move(3,4,5,4);
      model.move(6,4,4,4);
      model.move(3,6,3,4);
      model.move(3,4,5,4);
      model.move(5,5,5,3);
      model.move(2,5,2,3);
      model.move(2,3,4,3);
      model.move(5,3,3,3);
      model.move(0,4,2,4);
      model.move(0,3,2,3);
      model.move(0,2,2,2);
      model.move(2,3,2,5);
      model.move(2,6,2,4);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    String expectedState =
                    "    _ _ _\n" +
                    "  O _ _ _ O\n" +
                    "_ _ O _ O _ _\n" +
                    "_ _ _ O _ _ _\n" +
                    "_ _ _ _ _ _ _\n" +
                    "  _ _ _ _ _\n" +
                    "    _ _ _";
    String actualState = view.toString();
    assertEquals(expectedState, actualState);
  }

  @Test
  public void testGameOverMarble() {
    // Move a marble
    try {
      model.move(5,3,3,3);
      model.move(4,5,4,3);
      model.move(6,4,4,4);
      model.move(6,2,6,4);
      model.move(4,2,6,2);
      model.move(4,0,4,2);
      model.move(3,2,5,2);
      model.move(3,0,3,2);
      model.move(2,2,4,2);
      model.move(4,3,4,1);
      model.move(5,1,3,1);
      model.move(2,0,2,2);
      model.move(2,3,2,1);
      model.move(2,1,4,1);
      model.move(6,2,4,2);
      model.move(4,1,4,3);
      model.move(4,3,4,5);
      model.move(4,6,4,4);
      model.move(3,4,5,4);
      model.move(6,4,4,4);
      model.move(3,6,3,4);
      model.move(3,4,5,4);
      model.move(5,5,5,3);
      model.move(2,5,2,3);
      model.move(2,3,4,3);
      model.move(5,3,3,3);
      model.move(0,4,2,4);
      model.move(0,3,2,3);
      model.move(0,2,2,2);
      model.move(2,3,2,5);
      model.move(2,6,2,4);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    assertEquals(5, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewEuropeanEmptyModelDefault() {
    model = new EuropeanSolitaireModel(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanEmptyModelInvalidEmptyCell() {
    model = new EuropeanSolitaireModel(3, -1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMoveToInvalidPosition() {
    model.move(3, 3, 3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMovesNotTwoApartSameRow() {
    model.move(3, 3, 3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMovesNotTwoApartSameColumn() {
    model.move(3, 3, 2, 3);
  }

  @Test
  public void testEuropeanSingleLeftMoveCustomBoard() {
    model = new EuropeanSolitaireModel(3, 4, 3);
    model.move(4, 5, 4, 3);
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 1));
  }

  @Test
  public void testRandomPlay() {
    model.move(5,3,3,3);
    model.move(4,5,4,3);
    model.move(6,4,4,4);
    model.move(6,2,6,4);
    model.move(4,2,6,2);
    model.move(4,0,4,2);
    model.move(3,2,5,2);
    model.move(3,0,3,2);
    model.move(2,2,4,2);
    String expectedState =
            "    O O O\n" +
                    "  O O O O O\n" +
                    "O O _ O O O O\n" +
                    "_ _ _ O O O O\n" +
                    "_ _ O O O _ O\n" +
                    "  O O _ _ O\n" +
                    "    O _ O";
    String actualState = view.toString();
    assertEquals(expectedState, actualState);
  }

  @Test
  public void testEuropeanSingleUpMoveDefaultBoard() {
    model.move(5, 3, 3, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(5, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMoveFromEmptyPosition() {
    model.move(3, 3, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMoveAcrossEmptyPosition() {
    model.move(3, 1, 3, 5);
  }

  @Test
  public void testEuropeanSingleRightMoveCustomBoard() {
    model = new EuropeanSolitaireModel(3, 2, 3);
    model.move(2, 1, 2, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(2, 2));
    assertEquals(SlotState.Marble, model.getSlotAt(2, 3));
  }

  @Test
  public void testEuropeanSingleDownMoveCustomBoard() {
    model = new EuropeanSolitaireModel(3, 3, 2);
    model.move(1, 2, 3, 2);
    assertEquals(SlotState.Empty, model.getSlotAt(2, 2));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 2));
  }

  @Test
  public void testEuropeanSingleDownMoveDefaultBoard() {
    model.move(1, 3, 3, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(1, 3));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMoveFromInvalidPosition() {
    model.move(-1, 3, 3, 3);
  }

  @Test
  public void testEuropeanSingleLeftMoveDefaultBoard() {
    model.move(3, 5, 3, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(3, 5));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test
  public void testEuropeanSingleRightMoveDefaultBoard() {
    model.move(3, 1, 3, 3);
    assertEquals(SlotState.Empty, model.getSlotAt(3, 1));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMovesNotSameRowNorColumn() {
    model.move(3, 3, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEuropeanMoveToOccupiedPosition() {
    model.move(3, 3, 3, 5);
  }

  @Test
  public void testEuropeanSingleUpMoveCustomBoard() {
    model = new EuropeanSolitaireModel(3, 3, 5);
    model.move(5, 5, 3, 5);
    assertEquals(SlotState.Empty, model.getSlotAt(5, 5));
    assertEquals(SlotState.Marble, model.getSlotAt(3, 5));
  }

}