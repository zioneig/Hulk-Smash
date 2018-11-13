/*Zion Eig-Tassiello
 * Recursive Hulk finds the best possible path to the end of the city(integer matrix) using recursion
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ZionEigTassiello_Hulk {
	
	private int[][] city;

	public ZionEigTassiello_Hulk(String fname, int dim){

		Scanner inFile = null;
		try{
			inFile = new Scanner(new File(fname));
		}catch(FileNotFoundException e){

			System.out.println("File not found");
			System.exit(-1);
		}

		city = new int[dim][dim];

		for(int row = 0; row < dim; row++){
			for(int col = 0; col < dim; col++){
				city[row][col] = inFile.nextInt();
			}
		}

	}
	
	public int getTotal(int row, int col){
		int peopleHere = city[row][col];
		boolean canMoveRight = inBounds(row, col+1);
		boolean canMoveDown = inBounds(row+1, col);
		
		//once the hulk reaches the end, amount of people at that location are returned
		if(row == city.length-1 && col == city.length-1)
			return peopleHere;
		
		//if the hulk hits a fork in the road the two paths are compared
		else if(canMoveRight && canMoveDown){
			int rightPath = peopleHere + getTotal(row, col+1);
			int downPath = peopleHere + getTotal(row+1, col);
			//compares path 1 and 2 and decides which one is better to take
			if(rightPath > downPath)
				return rightPath;		
			else
				return downPath;
		}
		//if the hulk can only move right then he moves right
		else if(canMoveRight)
			return getTotal(row, col+1) + peopleHere;
		//if the hulk can only move down then he moves down
		else if(canMoveDown)
			return getTotal(row+1, col) + peopleHere;
		//if the hulk cannot move at all -1 is returned
		else
			return -1;
	}
	
	public boolean inBounds(int row,int col){
		if(col >= city.length || row >= city.length || col < 0 || row < 0 || city[row][col] == -1){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		ZionEigTassiello_Hulk test = new ZionEigTassiello_Hulk("city.txt", 12);
		System.out.println(test.getTotal(0, 0));
	}
}
