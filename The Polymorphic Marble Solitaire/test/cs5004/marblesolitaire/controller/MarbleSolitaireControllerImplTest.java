package cs5004.marblesolitaire.controller;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class MarbleSolitaireControllerImplTest {

  MarbleSolitaireModel mockModel;
  MarbleSolitaireView mockView;
  MarbleSolitaireController controller;

  @Before
  public void setup() {
    mockModel = Mockito.mock(MarbleSolitaireModel.class);
    mockView = Mockito.mock(MarbleSolitaireView.class);
    controller = new MarbleSolitaireControllerImpl(mockModel, mockView, new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new MarbleSolitaireControllerImpl(null, mockView, new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new MarbleSolitaireControllerImpl(mockModel, null, new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    new MarbleSolitaireControllerImpl(mockModel, mockView, null);
  }

  @Test
  public void testPlayGame() throws IOException {
    String input = "2 4 4 4\n2 2 2 4\nq\n";
    controller = new MarbleSolitaireControllerImpl(mockModel, mockView, new StringReader(input));
    Mockito.when(mockModel.isGameOver()).thenReturn(false, false, false, true);

    controller.playGame();

    Mockito.verify(mockModel, Mockito.times(3)).isGameOver();
    Mockito.verify(mockView, Mockito.times(3)).renderBoard();
    Mockito.verify(mockView, Mockito.times(3)).renderMessage(Mockito.anyString());
    Mockito.verify(mockModel).move(1, 3, 3, 3);
    Mockito.verify(mockModel).move(1, 1, 1, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameWithoutEnoughInputs() throws IOException {
    String input = "2 4 4";
    controller = new MarbleSolitaireControllerImpl(mockModel, mockView, new StringReader(input));
    Mockito.when(mockModel.isGameOver()).thenReturn(false);

    controller.playGame();
  }
}
