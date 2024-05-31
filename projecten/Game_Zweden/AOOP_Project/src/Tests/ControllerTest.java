package Tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domain.Controller;
import domain.GameBoard;
import domain.MoveDirection;
import domain.Tile;
import util.InvalidTileConfigException;
import util.Tuple;


class ControllerTest {
	
	boolean pathPrefix = true;
	
	@Test
	void loadBoardTestTrue() {
		Controller controller = new Controller();
		controller.setSelectedLevel(1);
		controller.loadBoard(pathPrefix);
		
		assertTrue(controller.getGameBoard() instanceof GameBoard);
	}
	
	@Test 
	void loadBoardTestFail() {
		Controller controller = new Controller();
		controller.setSelectedLevel(2);
		
		assertFalse(controller.getGameBoard() instanceof GameBoard);
	}
	
	@Nested
	class PlayerMovementAndResetTests{
	
		Controller controller;
		
		@BeforeEach
		void setUp() {
			controller = new Controller();
			controller.setSelectedLevel(1);
			controller.loadBoard(pathPrefix);
		}
		
		@Test
		void movePlayerRightTest() throws InvalidTileConfigException {
			
			controller.movePlayer(MoveDirection.RIGHT);
			
			assertTrue(controller.getGameBoard().getBoard()[2][3].is_ppos());
			assertTrue(controller.getGameBoard().getBoard()[2][4].is_cpos());
		}
		
		@Test
		void movePlayerUpTest() throws InvalidTileConfigException {
			
			//We assume this works because it is tested in the test above
			controller.movePlayer(MoveDirection.RIGHT);
			
			controller.movePlayer(MoveDirection.UP);
			
			assertTrue(controller.getGameBoard().getBoard()[1][3].is_ppos());
		}
		
		@Test
		void movePlayerDownTest() throws InvalidTileConfigException {
			
			//We assume this works because it is tested in the test above
			controller.movePlayer(MoveDirection.RIGHT);
			
			controller.movePlayer(MoveDirection.DOWN);
			
			assertTrue(controller.getGameBoard().getBoard()[3][3].is_ppos());
		}
		
		@Test
		void movePlayerLeftTest() throws InvalidTileConfigException {
			
			controller.movePlayer(MoveDirection.LEFT);
			
			assertTrue(controller.getGameBoard().getBoard()[2][1].is_ppos());
		}
		
		@Test
		void resetGameboardTest() throws InvalidTileConfigException {
			
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.RIGHT);
			
			//So we know the board changed
			assertFalse(controller.getGameBoard().getBoard()[2][2].is_ppos());
			
			controller.resetGameboard();
			
			assertTrue(controller.getGameBoard().getBoard()[2][2].is_ppos());
		}
		
		@Test 
		void checkEndConditionFalseTest() {
			
			//The board isn't initiated with the crates in the right place
			assertFalse(controller.checkEndConditions());
		}
		
		@Test
		void checkEndConditionTrueTest() {
			//execute all necessary steps to reach endConditions
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.RIGHT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.DOWN);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.LEFT);
			controller.movePlayer(MoveDirection.UP);
			controller.movePlayer(MoveDirection.RIGHT);
			
			assertTrue(controller.checkEndConditions());
	}
	}
	
}
