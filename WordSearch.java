/* 
Esther Wu 
4/5/2021

*/

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class WordSearch{
	public static void main(String[] args) throws Exception{

		Scanner in = new Scanner(System.in);

		// picking a random csv file
		String fileName = pickCsv();

		// initializing board object
		Board br = new Board();

		// key: contains all the words
		String[] key = br.getKey(fileName);
		Arrays.sort(key);
		System.out.println(Arrays.toString(key));

		// dimension of the board
		int dimension = br.getDimension(fileName);

		String [][] board = br.readFile(fileName);

		br.printBoard(board);

		System.out.print("How many players? ");
		int totalPlayers = in.nextInt();

		// player to start with
		int startPlayer = randomPlayer(totalPlayers);

		// instantiate array of player objects- one for each player
		Player [] players = new Player[totalPlayers];

		for (int i = 0; i < totalPlayers; i++){
			players[i] = new Player(i + 1, dimension);

		}

		// checking when the loop is completed - when all the guesses are made
		boolean complete = false;

		// storing all the words guessed
		String guesses = "";
		// storing all the correct answers
		String correct = "";

		while (complete == false){
			int skip = 0;

			System.out.print("Player " + startPlayer + ", please enter a word: ");
			String word = in.next();

			// adjusting each player object
			Player playerX = players[startPlayer - 1];

			// check if word is less than 3 letters or already mentioned
			if ((word.length() < 3) || (guesses.contains(word) == true)){
				skip ++;
				System.out.println("Your word was invalid, you lost your turn :(");
			}

			// if word is in the key list, give them points
			if ((Arrays.binarySearch(key, word) >= 0) && (skip == 0)){
					System.out.println("There is a match!");
					playerX.guessMade(word);
					correct += word + ",";
				}

			// adding guessed word into overall guesses
			guesses += word + ",";

			// checking for word on board
			if ((vertical(word, board) == true) && (Arrays.binarySearch(key, word) >= 0)){
				br.printBoard(board);
			}
			else if ((horizontal(word, board) == true) && (Arrays.binarySearch(key, word) >= 0)){
				br.printBoard(board);
			}
			else if ((negDiagonal(word, board) == true) && (Arrays.binarySearch(key, word) >= 0)){
				br.printBoard(board);
			}
			else if ((posDiagonal(word, board) == true) && (Arrays.binarySearch(key, word) >= 0)){
				br.printBoard(board);
			}
			else{
				br.printBoard(board); 
			}

			playerX.printGuess();

			// resetting count if exceeding amount of players
			if (startPlayer < totalPlayers){
				startPlayer ++;
			}
			else{
				startPlayer = 1;
			}

			System.out.println("correct: " + correct);
			System.out.println("guesses: "+ guesses);

			// checking if game is over
			if (correct.split(",").length == key.length){
				System.out.println("Game over!");
				System.out.println("Scores:");
				for (int i = 0; i < players.length; i++){
					int playnum = i + 1;
					System.out.println("Player " + playnum + "--> " + players[i].point + " points");
				}
				complete = true;
			}
			skip = 0;

		}

	}

	// method to pick a random csv file
	public static String pickCsv(){
		// picking random num 1 - 12
		int file = (int) (Math.random() * 12) + 1;
		String fileName = "board_" + file + ".csv";
		return fileName;
		
	}
	// choosing random player
	public static int randomPlayer(int total){
		return (int) (Math.random() * total) + 1;
	}

	// methods for checking directions (8)

	// checking if word has negative slope (top to bottom is left to right)
	public static boolean negDiagonal(String word, String [][] board){
		// storing the right word
		String found = "";
		int forward = 0;

		// checking col for letter
		for (int row = 0; row < board[0].length; row++){
			for (int col = 0; col < board[1].length; col++){
				// checking which coordinate has the first letter and fits in the board
				// going to the right 
				// current letter they are on
				char current = board[row][col].charAt(0);

				if ((Character.toLowerCase(current) == word.charAt(0)) && (row <= (board[0].length - word.length())) && (col <= (board[1].length - word.length()))){

					//seeing if right direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row+i][col+i];
						forward = 0;
					}
				}
				// going to the left
				else if ((Character.toLowerCase(current) == word.charAt(0)) && (row >= word.length() - 1) && (col >= word.length() - 1)){

					//seeing if left direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row-i][col-i];
						forward = 1;
					}
				}

				if (found.toLowerCase().contains(word)){
					if (forward == 0){
						for (int i = 0; i < word.length(); i++){
							board[row+i][col+i] = board[row+i][col+i].toUpperCase();
						}
					}
					else if (forward == 1){
						for (int i = 0; i < word.length(); i++){
							board[row-i][col-i] = board[row-i][col-i].toUpperCase();
						}
					}
					
					return true;
				}
				else{
					found = "";
				}
			}
		}

		return false;
	}

	// checking if word has positive slope (top to bottom is right to left)
	public static boolean posDiagonal(String word, String [][] board){
		// storing the right word
		String found = "";
		int forward = 0;

		// checking col for letter
		for (int row = 0; row < board[0].length; row++){
			for (int col = 0; col < board[1].length; col++){
				// checking which coordinate has the first letter and fits in the board
				// going to the right 
				char current = board[row][col].charAt(0);

				if ((Character.toLowerCase(current) == word.charAt(0)) && (col <= board[1].length - word.length()) && (row >= word.length())){

					//seeing if right direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row-i][col+i];
						forward = 0;
					}
				}
				// going to the left
				else if ((Character.toLowerCase(current) == word.charAt(0)) && (col >= word.length()) && (row <= (board[0].length - word.length()))){

					//seeing if left direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row+i][col-i];
						forward = 1;
					}
				}

				if (found.toLowerCase().equals(word)){
					if (forward == 0){
						for (int i = 0; i < word.length(); i++){
							board[row-i][col+i] = board[row-i][col+i].toUpperCase();
						}
					}
					else if (forward == 1){
						for (int i = 0; i < word.length(); i++){
							board[row+i][col-i] = board[row+i][col-i].toUpperCase();
						}
					}
					
					return true;
				}
				else{
					found = "";
				}
			}
		}

		return false;
	}

	public static boolean vertical(String word, String [][] board){
		// storing the right word
		String found = "";
		// whether it went forward or backwards
		int forward = 0;

		// checking col for letter
		for (int col = 0; col < board[1].length; col++){
			for (int row = 0; row < board[0].length; row++){
				// checking which coordinate has the first letter and fits in the board
				// going to the right 
				char current = board[row][col].charAt(0);

				if ((Character.toLowerCase(current) == word.charAt(0)) && (row <= board[0].length - word.length())){

					//seeing if right direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row+i][col];
						forward = 0;
					}
				}
				// going to the left
				else if ((Character.toLowerCase(current) == word.charAt(0)) && (row >= word.length() - 1)){

					//seeing if left direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row-i][col];
						forward = 1;
					}
				}

				if (found.toLowerCase().equals(word)){
					if (forward == 0){
						for (int i = 0; i < word.length(); i++){
							board[row+i][col] = board[row+i][col].toUpperCase();
						}
					}
					else if (forward == 1){
						for (int i = 0; i < word.length(); i++){
							board[row-i][col] = board[row-i][col].toUpperCase();
						}
					}
					
					return true;
				}
				else{
					found = "";
				}
			}
		} 
		return false;
	}

	public static boolean horizontal(String word, String [][] board){
		// storing the right word
		String found = "";
		// whether it went forward or backwards
		int forward = 0;

		// checking row for letter
		for (int row = 0; row < board[0].length; row++){
			for (int col = 0; col < board[1].length; col++){
				// checking which coordinate has the first letter and fits in the board
				// going to the right 
				char current = board[row][col].charAt(0);

				if ((Character.toLowerCase(current) == word.charAt(0)) && (col <= board[1].length - word.length())){

					//seeing if right direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row][col+i];
						forward = 0;
					}
				}
				// going to the left
				else if ((Character.toLowerCase(current) == word.charAt(0)) && (col >= word.length() - 1)){

					//seeing if left direction has the word
					for (int i = 0; i < word.length(); i ++){
						// checking to the right (forwards)
						found = found + board[row][col-i];
						forward = 1;
					}
				}

				if (found.toLowerCase().equals(word)){
					if (forward == 0){
						for (int i = 0; i < word.length(); i++){
							board[row][col+i] = board[row][col+i].toUpperCase();
						}
					}
					else if (forward == 1){
						for (int i = 0; i < word.length(); i++){
							board[row][col-i] = board[row][col-i].toUpperCase();
						}
					}
					
					return true;
				}
				else{
					found = "";
				}
			}
		}

		return false;
	}
}

