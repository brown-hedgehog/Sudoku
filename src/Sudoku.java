import java.util.LinkedList;
import java.util.List;

public class Sudoku {
	private static final int[] answers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private static int[][] board = { { 0, 0, 0, 0, 0, 3, 2, 8, 0 },
			{ 6, 0, 0, 1, 0, 0, 4, 0, 0 }, { 0, 1, 3, 0, 8, 0, 0, 0, 0 },
			{ 0, 3, 0, 0, 0, 9, 0, 0, 0 }, { 8, 0, 1, 2, 3, 4, 5, 0, 9, },
			{ 0, 0, 0, 5, 0, 0, 0, 6, 0 }, { 0, 2, 0, 0, 1, 0, 6, 5, 0 },
			{ 5, 0, 8, 0, 0, 6, 0, 0, 2 }, { 0, 7, 6, 3, 0, 0, 0, 0, 0 } };

	private static int counter;

	private int stopCondition = board.length * board.length;

	private static int innerBoardSize = board.length / 3;

	private void printSolution() {
		for (int[] row : board) {
			for (int col : row) {
				System.out.print(col + ",");
			}
			System.out.println();
		}
	}

	private boolean isEmpty(int row, int col) {
		return (board[row][col]) == 0 ? true : false;
	}

	private void setNumber(int row, int col, int number) {
		board[row][col] = number;
	}

	private void findSolution() {
		counter += 1;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (isEmpty(row, col)) {
					if (getAnswer(row, col) != 0) {
						setNumber(row, col, getAnswer(row, col));
						break;
					}
				}
			}
		}
		if (stopCondition == counter) {
			printSolution();
			return;
		}
		if (!isCompleted()) {
			findSolution();
		} else {
			printSolution();
		}
	}

	private boolean isCompleted() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (isEmpty(row, col)) {
					return false;
				}
			}
		}
		return true;
	}

	private int getAnswer(int row, int col) {
		for (int answer : answers) {
			if (isDuplicate(row, col, answer)) {
				continue;
			}
			if (isOnlyPossibleAnswer(row, col, answer)) {
				return answer;
			}
		}
		return 0;
	}

	private boolean isDuplicate(int row, int col, int answer) {
		for (int i = 0; i < board[row].length; i++) {
			if (answer == board[row][i]) {
				return true;
			}
		}
		for (int i = 0; i < board.length; i++) {
			if (answer == board[i][col]) {
				return true;
			}
		}
		int rowRange = row / innerBoardSize * innerBoardSize;
		int colRange = col / innerBoardSize * innerBoardSize;
		for (int i = rowRange; i < rowRange + innerBoardSize; i++) {
			for (int j = colRange; j < colRange + innerBoardSize; j++) {
				if (answer == board[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isOnlyPossibleAnswer(int row, int col, int possibleAnswer) {
		List<Integer> numbers = new LinkedList<Integer>();
		for (int i = 0; i < board[row].length; i++) {
			if (board[row][i] != 0 && !numbers.contains(board[row][i])) {
				numbers.add(board[row][i]);
			}
		}
		for (int i = 0; i < board.length; i++) {
			if (board[i][col] != 0 && !numbers.contains(board[i][col])) {
				numbers.add(board[i][col]);
			}
		}
		int rowRange = row / innerBoardSize * innerBoardSize;
		int colRange = col / innerBoardSize * innerBoardSize;
		for (int i = rowRange; i < rowRange + innerBoardSize; i++) {
			for (int j = colRange; j < colRange + innerBoardSize; j++) {
				if (board[i][j] != 0 && !numbers.contains(board[i][j])) {
					numbers.add(board[i][j]);
				}
			}
		}
		int answerSum = 0;
		int numberSum = 0;
		for (int answer : answers) {
			answerSum += answer;
		}
		for (Integer number : numbers) {
			numberSum += number;
		}
		if ((answerSum - possibleAnswer) == numberSum
				&& numbers.size() == (answers.length - 1)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new Sudoku().findSolution();
	}
}