/* 
Esther Wu 
4/5/2021

*/

import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class Board{

	String [][] table;
	String file;
	String [] key;
	int dimension;

	// constructor
	public Board(){}
	
	// getting the value that tell us dimension of chart row number
	// only returns new array, if we need the key we have to scan by ourselves

	// getting dimensions
	public int getDimension(String fileName){
		this.file = fileName;

		try{
			// scanner object for reading in file
			Scanner sc = new Scanner(new File(file));

			sc.useDelimiter(",");

			// saving the information of the board from the csv
			String headerLine = sc.nextLine();
			// answer key --> the words
			String [] answer = headerLine.split(",");

			// key: contains all the words
			int dimension = Integer.valueOf(answer[0]);

			this.dimension = dimension;

		}
		// avoiding errors with the file name, just in case file isn't found in folder
		catch (IOException e){
			System.out.println("Invalid file name.");
		}
		// array with values of csv file
		return dimension;
	}

	public String [] getKey(String fileName){
		this.file = fileName;

		try{
			// scanner object for reading in file
			Scanner sc = new Scanner(new File(file));

			sc.useDelimiter(",");

			// saving the information of the board from the csv
			String headerLine = sc.nextLine();
			// answer key --> the words
			String [] answer = headerLine.split(",");

			// key: contains all the words
			String[] key = Arrays.copyOfRange(answer, 3, (answer.length - 1));
			
			// getting the value that tell us dimension of chart row number
			int counter_row = 0;

			this.key = key;

		}
		// avoiding errors with the file name, just in case file isn't found in folder
		catch (IOException e){
			System.out.println("Invalid file name.");
		}
		// array with values of csv file
		return key;

	}

	public String [][] readFile(String fileName){

		this.file = fileName;

		try{
			// scanner object for reading in file
			Scanner sc = new Scanner(new File(file));

			sc.useDelimiter(",");

			// saving the information of the board from the csv
			String headerLine = sc.nextLine();
			// answer key --> the words
			String [] answer = headerLine.split(",");

			// dimensions of array that will hold board
			int x = Integer.parseInt(answer[0]); //Character.getNumericValue(headerLine.charAt(0));
			int y = Integer.parseInt(answer[1]); //Character.getNumericValue(headerLine.charAt(2));
			
			// array with csv info
			String [][] board = new String[x][y];

			// key: contains all the words
			//String[] key = Arrays.copyOfRange(answer, 3, (answer.length - 1));
			
			// getting the value that tell us dimension of chart row number
			int counter_row = 0;

			while (sc.hasNext()){
				// reading each line
				String newLine = sc.nextLine();
				// making line into array
				String [] line = newLine.split(",");

				for (int i = 0; i < line.length; i++){
					board[counter_row][i] = line[i];
				}
				counter_row++;
			}
			this.table = board;
		}
		// avoiding errors with the file name, just in case file isn't found in folder
		catch (IOException e){
			System.out.println("Invalid file name.");
		}
		// array with values of csv file
		return table;
	}

	// printing out board
	public void printBoard(String [][] board){
	
		// print top border (horizental line of +---+---+...)
		for(int border = 0; border < board[0].length; border++){
			System.out.print("+---");
		}

		System.out.print("+");
		System.out.println();

		// print the starting number of the first column
		for(int r = 0; r < board.length; r++){
			
			System.out.print("|");

			// print the values of the array with the barriers between
			for(int c = 0; c < board[r].length; c++){
				System.out.print(" ");
				System.out.print(board[r][c]);
				System.out.print(" |");
			}
			System.out.println();

		System.out.print("+");

		// print the borders between columns
		for (int mid = 0; mid < board.length; mid++){

			System.out.print("---+");
		}
		System.out.println();
		}
	}

} 




