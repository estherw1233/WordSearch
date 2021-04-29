/* 
Esther Wu 
4/5/2021

*/

// create a class that holds all the information of each player
// stores the words they guessed and for each player
// gotta create an object for each player

import java.util.Scanner;
import java.util.Arrays;

public class Player{

	// player number
	public int number;
	// holds the player's correct guesses
	public String [] guess;
	// number of points/ correct guesses
	public int point;
	// number of possible guesses
	public int length;

	public Player(int num, int length){

		this.number = num;
		this.length = length;
		this.point = 0;
		this.guess = new String[length];

		// filling up the guess array
		for (int i = 0; i < this.length; i++){
			guess[i] = "0";}
	}

	// if word was guessed correctly/ is in the key array, put it through this method
	public void guessMade(String word){
		// keeping track of how many points this player has
		this.guess[this.point] = word;
		this.point ++;
	}

	// printing out guesses in each round
	public void printGuess(){
		System.out.print("Player " + this.number + ": " + point + " ");
		System.out.print("[");
		for (int i = 0; i < guess.length; i++){
			if (guess[i].equals("0") == false){
				System.out.print("'");
				System.out.print(guess[i]);
				System.out.print("',");
			}
		}
		System.out.print("]");
		System.out.println();
	}
}




