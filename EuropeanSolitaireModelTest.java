package cs5004.marblesolitaire.model.hw07;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class EuropeanSolitaireModelTest {

  private EuropeanSolitaireModel model;

  @Before
  public void setup() {
    model = new EuropeanSolitaireModel();
    try {
      model.move(4323);
      model.move(3533);
      model.move(4543);
      model.move(6544);
      model.move(4345);
      model.move(5535);
      model.move(3234);
      model.move(4143);
      model.move(5333);
      model.move(3432);
      model.move(3133);
      model.move(3634);
      model.move(3335);
      model.move(6242);
      model.move(1131);
      model.move(1232);
      model.move(1333);
      model.move(1434);
      model.move(2545);
      model.move(3335);
      model.move(4525);
      model.move(1535);
      model.move(3133);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }
  @Test
  public void testGameStateWithFourMarbles() {
    // Assert that the game is not over yet
    assertFalse(model.isGameOver());

    // Assert that there are only four marbles left on the board
    assertEquals(4, model.getScore());
  }
}