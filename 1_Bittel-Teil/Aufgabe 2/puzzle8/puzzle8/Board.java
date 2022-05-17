package puzzle8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author Ihr Name
 */
public class Board {

	/**
	 * Problmegröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		List<Integer> array;
		do {
			array = IntStream.rangeClosed(0, N).boxed().collect(Collectors.toList());
			Collections.shuffle(array);
			this.board = array.stream().mapToInt(i -> i).toArray();
		} while (parity() != true);
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		for (int i = 0; i < board.length && i < this.board.length; i++) {
			this.board[i] = board[i];
		}
	}

	public Board solution() {
		List<Integer> array = IntStream.rangeClosed(0, N).boxed().collect(Collectors.toList());
		return new Board(array.stream().mapToInt(i -> i).toArray());
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {
		int parity = 0;
		for(int i = 0; i < board.length -1; i++) {
			if (board[i] == 0) {
				continue;
			}
			for(int j = i+1; j < board.length; j++) {
				if (board[j] == 0) {
					continue;
				}
				if (board[j] < board[i]) {
					parity++;
				}
			}
		}
		// System.out.println(parity);
		if (parity % 2 == 0) return true;
		else return false;
	}

	public boolean parity2() {
		int parity = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if (board[i] == board[j] || board[i] == 0 || board[j] == 0) {
					break;
				}
				if (board[j] < board[i]) {
					parity++;
				}
			}
		}
		// System.out.println(parity);
		if (parity % 2 == 0) return true;
		else return false;
	}
	
	/**
	 * Heurstik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int heuristik = 0;
		for(int i = 0; i < board.length; i++) {
			if (board[i] != i && board[i] != 0)
				heuristik++;
		}
		return heuristik;
	}
	
	/**
	 * Heurstik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int heuristik = 0;

		// board as multidimensional help array
		int teiler = (int) Math.sqrt(N+1);
		int[][] help = new int[teiler][teiler];
		int boardIndex = 0;
		for (int i = 0; i < help.length; i++) {
			help[i][0] = board[boardIndex++];
			help[i][1] = board[boardIndex++];
			help[i][2] = board[boardIndex++];
		}

		// Manhattan Distance
		for(int y0 = 0; y0 < help.length; y0++) {
			for(int x0 = 0; x0 < help[0].length; x0++) {
				if (help[y0][x0] != 0) {
					// correct position
					int x1 = (help[y0][x0]) % teiler;
					int y1 = (help[y0][x0]) / teiler;
					int distance = Math.abs(x1 - x0) + Math.abs(y1 - y0);
					// System.out.print(distance + ", ");
					heuristik += distance;
				}
			}
		}
		return heuristik;
	}

	private static Board exchange(Board board, int idx1, int idx2) {
		Board temp = new Board(board.board);
		int save = temp.board[idx1];
		temp.board[idx1] = temp.board[idx2];
		temp.board[idx2] = save;
		return temp;
	}

	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();

		Board tmpBoard = new Board(this.board);
		// Where is the empty Space?
		int emptyIndex = 0;
		for (int num: tmpBoard.board){
			if (num == 0) break;
			emptyIndex++;
		}
		switch (emptyIndex) {
			// Position in the Corner (2 Possibilities)
			case 0:
				boardList.add(exchange(tmpBoard, 0, 1));
				boardList.add(exchange(tmpBoard, 0, 3));
				break;
			case 2:
				boardList.add(exchange(tmpBoard, 2, 1));
				boardList.add(exchange(tmpBoard, 2, 5));
				break;
			case 6:
				boardList.add(exchange(tmpBoard, 6, 3));
				boardList.add(exchange(tmpBoard, 6, 7));
				break;
			case 8:
				boardList.add(exchange(tmpBoard, 8, 5));
				boardList.add(exchange(tmpBoard, 8, 7));
				break;
			// Position at the border (3 Possibilities)
			case 1:
				boardList.add(exchange(tmpBoard, 1, 0));
				boardList.add(exchange(tmpBoard, 1, 2));
				boardList.add(exchange(tmpBoard, 1, 4));
				break;
			case 3:
				boardList.add(exchange(tmpBoard, 3, 0));
				boardList.add(exchange(tmpBoard, 3, 4));
				boardList.add(exchange(tmpBoard, 3, 6));
				break;
			case 5:
				boardList.add(exchange(tmpBoard, 5, 2));
				boardList.add(exchange(tmpBoard, 5, 4));
				boardList.add(exchange(tmpBoard, 5, 8));
				break;
			case 7:
				boardList.add(exchange(tmpBoard, 7, 6));
				boardList.add(exchange(tmpBoard, 7, 4));
				boardList.add(exchange(tmpBoard, 7, 8));
				break;
			// Position in the middle (4 Possibilities)
			case 4:
				boardList.add(exchange(tmpBoard, 4, 1));
				boardList.add(exchange(tmpBoard, 4, 3));
				boardList.add(exchange(tmpBoard, 4, 5));
				boardList.add(exchange(tmpBoard, 4, 7));
				break;
		}

		return boardList;
	}
	
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		int[] solvedboard = IntStream.rangeClosed(0, N).toArray();
		return Arrays.equals(this.board, solvedboard);
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});
				
		System.out.println(b);
		System.out.println(b.parity());
		System.out.println(b.h1());
		System.out.println(b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		
		System.out.println(goal.isSolved());
		// System.out.println(b.isSolved());
	}
}
	
