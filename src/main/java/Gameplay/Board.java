package Gameplay;

import java.util.List;

public class Board {
	int[][] playerBoard; // 0 = empty, 1 = activated, -1 = crossed off
	int[][] completeBoard;

	public Board init(int width, int height, int boardId){
		int[][] board = new int[height][width];

		return this;
	}

	public Board init(int width, int height){
		int[][] board = new int[height][width];

		return this;
	}

	// if the player board is equal to the completed board, return true
	public boolean checkComplete(){
		for (int boardRow = 0; boardRow < playerBoard.length; boardRow++) {
			for (int val = 0; val < playerBoard[boardRow].length; val++) {
				if (this.playerBoard[boardRow][val] != this.completeBoard[boardRow][val]) {
					return false;
				}
			}
		}
		return true;
	}
}
