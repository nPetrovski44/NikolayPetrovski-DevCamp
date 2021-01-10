/**
 * This is my own original work.
 * I have decided to use matrices as I find it to be the easiest way to solve the problem.
 * Signed by Nikolay Petrovski
 */
import java.util.Scanner;

public class BrickWork
{
	public static void main(String[] args)
	{
		/* 
		 * areaScanner We add a scanner for user input to enter the area of the layer.
		 * N The number of lines for the area (even number between 0-100)
		 * M The number of columns for the area (even number between 0-100)
		 * coordinatesEntered We use this variable to keep track of how many values we have entered.
		 * isInteger We add this variable to check whether the user's input is an integer
		 * validInput We add this variable to check whether the integer entered is in range
		 */
		
		System.out.println("Enter the size of the area: N Lines /M Columns (Should be even numbers between 0 and 100)");
		Scanner areaScanner = new Scanner(System.in);
		int M = 0, N = 0, coordinatesEntered = 0;
		boolean isInteger = false, validInput = false;
		
		 //Validating the lines and columns of area
		 do 
		{
			//First we assign the value of the lines - N
			if(coordinatesEntered == 0)
			{
				if(areaScanner.hasNextInt())
				{
					isInteger = true;
					N = areaScanner.nextInt();
					if((N > 0 || N <= 100) && (N % 2 == 0))
					{
						validInput = true;
						coordinatesEntered++;
					}
					else System.out.println("Try to input an even number for N within the range (0-100).");
				}
				else 
					{
						System.out.println("Try to input a correct number.");
						areaScanner.next();
					}
			}
			//Secondly, we assign the value of the columns - M
			else if(coordinatesEntered == 1)
			{
				if(areaScanner.hasNextInt())
				{
					isInteger = true;
					M = areaScanner.nextInt();
					if((M > 0 || M <= 100) && (M % 2 == 0))
					{
						validInput = true;
						coordinatesEntered++;
					}
					else System.out.println("Try to input an even number for M within the range (0-100).");
				}
				else 
					{
						System.out.println("Try to input a correct number.");
						areaScanner.next();
					}
			}
		}while(isInteger == false || validInput == false || coordinatesEntered < 2);
		 
		//Input instructions
		System.out.println("Enter " + (N*M)/2 + " numbers separated by spaces. Every number should be included twice.");
		System.out.println("When adding the number for the second time make sure its position is either next to or below its previous position.");
		System.out.println("For visual ease, press enter when you reach the end of each line. ");
		
		
		/*
		 * The code below validates the user's input so that:
		 *  - the input is an integer
		 * 	- the sizes of the bricks are either 2x1 or 1x2 depending on the orientation
		 *  - the numbers corresponding to a brick are entered in the correct positions;
		 * 
		 * brickScanner A new scanner for the user's input 
		 * grid A matrix to store the user's input 
		 * temporaryVar A variable to store the current number from the user's input
		 * entriesArray An array to store the numbers that have been assigned to bricks
		 * arrayPosition A variable to navigate through the array
		 * entries A variable to keep track of the unique numbers that have been entered so far
		 * incorrectlyUsed A boolean to check if a number has been used correctly
		 * integer A boolean to check if the input is an integer
		 */
		Scanner brickScanner = new Scanner(System.in);
		
		int grid[][] = new int[N][M];
		int temporaryVar = 0, arrayPosition = 0, entries = 0;
		int entriesArray[] = new int[N*M];
		boolean incorrectlyUsed = false, integer = true;
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < M; j++)
			{
				//We check if the user's input is an integer
				do {
					
					if (brickScanner.hasNextInt())
					{
						integer = true;
						//We assign the value of the input to temporaryVar
						temporaryVar = brickScanner.nextInt();
						//We check if a value has been used less than 2 times and if not we need to enter a different value
						if(timesUsed(temporaryVar, entriesArray) < 2)
						{
							try {
								//We check to see if a value from the input has been used only once and if so,
								//we validate where in the matrix to put the same value again
								if(i > 0 && timesUsed(temporaryVar, entriesArray) == 1)
								{
									if((grid[i - 1][j] == temporaryVar || grid[i][j - 1] == temporaryVar))
									{
										//If valid, we store the value in grid, we add the value to the array of numbers,
										//we increment arrayPosition and we set incorrectlyUsed to false.
										grid[i][j] = temporaryVar;
										entriesArray[arrayPosition] = temporaryVar;
										arrayPosition++;
										incorrectlyUsed = false;
									}
									else
									{
										//If the value entered doesn't belong we prompt the user to enter a different value.
										System.out.println("Enter a valid brick number at line " + (i + 1)+ " and column " + (j + 1));
										incorrectlyUsed = true;
									}
								}
								else
								{
									//We check if the user's value has not been entered so far
									//and if there aren't too many unique entries
									if(timesUsed(temporaryVar, entriesArray) == 0 && entries < (N*M)/2)
									{
										//If we are on the first line there is definitely enough space for a new unique value
										if(i > 0)
										{
											//We check the number 'above' and 'next to' the user's value
											if(timesUsed(grid[i-1][j], entriesArray) == 1 && temporaryVar == grid[i-1][j])
											{
												//We check if the number has been used only once and the current value is equal to that number
												grid[i][j] = temporaryVar;
												entriesArray[arrayPosition] = temporaryVar;
												arrayPosition++;
												incorrectlyUsed = false;
											}
											else if(timesUsed(grid[i-1][j], entriesArray) == 2)
											{
												//If not, we have a new unique entry so we increment entries
												grid[i][j] = temporaryVar;
												entriesArray[arrayPosition] = temporaryVar;
												arrayPosition++;
												entries++;
												incorrectlyUsed = false;
											}
											else 
											{
												//If none of the above are true, we need a different brick number
												System.out.println("Enter a valid brick number at line "+ (i + 1)+ " and column " + (j + 1));
												incorrectlyUsed = true;
											}
										}
										else 
										{
											grid[i][j] = temporaryVar;
											entriesArray[arrayPosition] = temporaryVar;
											arrayPosition++;
											entries++;
											incorrectlyUsed = false;
										}
									}
									//We check if the user's value has been entered
									//and if there aren't too many unique entries
									else if(timesUsed(temporaryVar, entriesArray) != 0 && entries < (N*M)/2)
									{
										if(i > 0)
										{
											//We check to see when we are not on the first line if the number 'above' equals to the user's input
											if(grid[i - 1][j] == temporaryVar)
											{
												grid[i][j] = temporaryVar;
												entriesArray[arrayPosition] = temporaryVar;
												arrayPosition++;
												incorrectlyUsed = false;
											}
										}
										else if(grid[i][j - 1] == temporaryVar)
										{
											//If we are on the first line we check if the value 'next to' equals the user's input
											grid[i][j] = temporaryVar;
											entriesArray[arrayPosition] = temporaryVar;
											arrayPosition++;
											incorrectlyUsed = false;
										}
										else
										{
											//If none of the above are correct, we need a different number
											System.out.println("This number does not belong here. Error at line "+ (i + 1)+ " and column " + (j + 1));
											incorrectlyUsed = true;
										}
									}
									//Any other case we need a different number
									else
									{
										System.out.println("Enter a valid brick number at line "+ (i + 1)+ " and column " + (j + 1));
										incorrectlyUsed = true;
									}
								}
						
							} 
							catch (Exception e)
							{
								System.out.println("Enter a vaid brick number at line " + (i + 1)+ " and column " + (j + 1));
								incorrectlyUsed = true;
							}
						}
						else 
						{
							System.out.println("Enter a different digit at line " + (i + 1)+ " and column " + (j + 1));
							incorrectlyUsed = true;
						}
					}
					else
					{
						integer = false;
						System.out.println("Make sure you enter digits. Error at line "+ (i + 1)+ " and column " + (j + 1));
						brickScanner.next();
					}
			}while(incorrectlyUsed == true || integer == false);
			}
			System.out.println();
		}
		
		/*
		 * Sorting the 'entriesArray' array using Bubble sort,
		 * because generally the size of the array isn't expected to be that long,
		 * however a different method could be used.
 		 */
		int temp = 0;
		for(int i = 0; i < entriesArray.length; i++)
		{
			for(int j = 0; j < entriesArray.length - 1; j++)
			{
				if(entriesArray[j] > entriesArray[j + 1])
				{
					temp = entriesArray[j];
					entriesArray[j] = entriesArray[j + 1];
					entriesArray[j + 1] = temp; 
				}
			}
		}

		printingGrid(grid, N, M);
		System.out.println();
		System.out.println();
		areaScanner.close();
		brickScanner.close();
		
		//Calling the method to create the second layer.
		secondLayer(grid, N, M, entriesArray);
	}
	/**
	 * This method creates the second Layer of bricks.
	 * 
	 * @param grid The matrix with the user inputs
	 * @param lines The number of lines in the matrix
	 * @param columns The number of columns in the matrix
	 * @param countArray The array storing all the brick numbers
	 */
	public static void secondLayer(int grid[][], int lines, int columns, int countArray[])
	{
		int copyGrid [][] = new int [lines][columns];
		copyGrid = returnFilled(lines, columns);
		copyGrid = formLayer(grid, lines, columns, countArray);
		//If the result after calling formLayer is null, we print '-1'.
		if(copyGrid == null)System.out.println(-1);
		printingGrid(copyGrid, lines, columns);
	}
	
	/**
	 * This method rearranges the matrix with the user inputs,
	 * so that no brick lies exactly on a brick from the original layer.
	 * 
	 * @param grid The matrix with the user inputs
	 * @param lines The number of lines in the matrix
	 * @param columns The number of columns in the matrix
	 * @param countArray The array storing all the brick numbers
	 * @return Returning a rearranged matrix
	 */
	public static int[][] formLayer(int[][] grid, int lines, int columns, int countArray[])
	{
		//We create a new matrix in which we will store the rearranged values
		int copyGrid [][] = new int [lines][columns];
		
		/*
		 * temporaryVar This variable stores the number of the current brick
		 * oldVar This variable stores the number of the last brick entered in the new matrix
		 * columnCounter This variable is used to navigate through the array and its numbers
		 * index This variable is used to go through the array with the brick numbers
		 * oldValueEqualsTemp This boolean variable is used when navigating through the array to only add unique numbers in order.
		 */
		int temporaryVar, oldVar = 0, columnCounter = 0, index = 0;
		boolean oldValueEqualsTemp = false;
		for(int i = 0; i < lines; i++)
		{
			for(int j = 0; j< columns; j++)
			{	
				index = j;
				temporaryVar = countArray[index + columnCounter];
				
				/*
				 * Since every value in the array is repeated(at least it should be)
				 *we enter the loop to check if the previous entered value is equal to or less than the current one
				 *and only leave when it is bigger.
				 */
				do
				{
					if(oldVar == temporaryVar || temporaryVar < oldVar)
					{
						//We try and catch an exception when the array goes out of bounds
						try
						{
							temporaryVar = countArray[index + columnCounter];
							index++;
							oldValueEqualsTemp = true;
						}
							catch(Exception e) 
						{
							oldValueEqualsTemp = false;
						}
					}
					else oldValueEqualsTemp = false;
				}while(oldValueEqualsTemp == true);
				 
				/*
				 * We try to find a solution and if none is found, we return a null value.
				 * The algorithm works by assigning 2 values at the same time, only when
				 * the corresponding position in the new matrix is equal to 0. This way we avoid overlapping.
				 * (Even though, I am fairly certain that there should always be a solution
				 * and if one isn't found it is because of my code)
				 */
				try
				{
					//We have several tests to determine the position of the next 2 numbers(which are equal)
					if(copyGrid[i][j] == 0)
					{
						if(i + 1 < lines && j + 2 == columns && grid[i][j+1] == grid[i+1][j+1])
						{
							copyGrid[i][j] = temporaryVar;
							copyGrid[i][j+1] = temporaryVar;
							oldVar = temporaryVar;
						}
						else if(temporaryVar != grid[i][j] && i + 1 < lines)
						{
							if(i % 2 == 0)
							{
								copyGrid[i][j] = temporaryVar;
								copyGrid[i+1][j] = temporaryVar;
								oldVar = temporaryVar;
							}
							else 
							{
								copyGrid[i][j] = temporaryVar;
								copyGrid[i][j+1] = temporaryVar;
								oldVar = temporaryVar;
							}
						}
						else if(i + 1 == lines)
						{ 
							copyGrid[i][j] = temporaryVar;
							copyGrid[i][j+1] = temporaryVar;
							oldVar = temporaryVar;
						}
						else if(temporaryVar == grid[i][j] && i + 1 < lines)
						{
							if(temporaryVar != grid[i+1][j] && i % 2 == 0)
							{ 
								copyGrid[i][j] = temporaryVar;
								copyGrid[i+1][j] = temporaryVar;
								oldVar = temporaryVar;
							}
							else if(temporaryVar == grid[i + 1][j] && j + 1 < columns)
							{ 
								copyGrid[i][j] = temporaryVar;
								copyGrid[i][j+1] = temporaryVar;
								oldVar = temporaryVar;
							}
							
							/*
							 * In this case we skip the current brick number and we use the next one,
							 * consequently we don't alter the oldVar and we handle the repetition
							 * in the beginning.
							 */
							
							else 
							{
								temporaryVar = countArray[index + 1 + columnCounter];
								copyGrid[i][j] = temporaryVar;
								copyGrid[i+1][j] = temporaryVar;
							}
						}
					}	
				} 
				catch(Exception e)
				{
					System.out.println("A solution could not be found.");
					return null;
				}
			}
			//We add the the number of columns to the columCounter to expand the reach of the index in the array.
			columnCounter = columnCounter + columns;
		}
		return copyGrid;
	}
	
	/**
	 * This method fills a matrix with '0' values
	 * @param lines The number of lines entered
	 * @param columns The number of columns entered
	 * @return returning the matrix 
	 */
	public static int[][]returnFilled(int lines, int columns)
	{
		//A temporary matrix to be filled
		int filledGrid[][] = new int [lines][columns];
		for(int i = 0; i < lines; i++)
		{
			for(int j = 0; j < columns; j++)
				filledGrid[i][j] = 0;
		}
		return filledGrid;
	}
	
	/**
	 * This method counts the amount of brick numbers we enter.
	 * (Ideally every brick's number should be entered twice)
	 * 
	 * @param temp The brick number we are checking
	 * @param array The array of numbers already entered
	 * @return Returning the amount of times it has been found in the array
	 */
	public static int timesUsed(int currentBrick, int[]array)
	{ 
		int tempArray = 0;
		for(int i = 0; i < array.length; i++)
		{
			if(currentBrick == array[i]) tempArray++;
		}
		return tempArray;
	}
	
	/**
	 * This method prints the filled matrices.
	 * 
	 * @param grid The filled matrix we receive 
	 * @param lines The number of lines
	 * @param columns The number of columns
	 */
	public static void printingGrid(int[][] grid, int lines, int columns)
	{
		for(int i = 0; i < lines; i++)
		{
			
			for(int k = 0; k < columns; k++)
			{
				if(i == 0)
				{
					System.out.print("******");
				}
				if(i > 0)
				{	if(grid[i][k] == grid[i - 1][k]) System.out.print("------");
					else  System.out.print("******");
				}

			}
			System.out.println();
			for(int j = 0; j < columns; j++)
			{	
				int digitCount = 0;
				int currentDigit = grid[i][j];
				while(currentDigit != 0)
				{
					currentDigit /= 10;
					digitCount++;
				}
				
				/*
				 * Since our limits for lines and columns are 100 we can get a number of a brick with up to a 4-digit-number,
				 * therefore, we have slightly different printing methods depending on the number of digits in a number.
				 */
				switch(digitCount)
				{
				case 1:
					if(j == 0) System.out.print("|  " + grid[i][j] + "  ");
					else if(grid[i][j-1] == grid[i][j])System.out.print(":  " + grid[i][j] + "  ");
					else System.out.print("|  " + grid[i][j] + "  ");
					break;
				case 2:
					if(j == 0) System.out.print("|  " + grid[i][j] + " ");
					else if(grid[i][j-1] == grid[i][j])System.out.print(":  " + grid[i][j] + " ");
					else System.out.print("|  " + grid[i][j] + " ");
					break;
				case 3:
					if(j == 0) System.out.print("| " + grid[i][j] + " ");
					else if(grid[i][j-1] == grid[i][j])System.out.print(": " + grid[i][j] + " ");
					else System.out.print("| " + grid[i][j] + " ");
					break;
				case 4:
					if(j == 0) System.out.print("| " + grid[i][j] + "");
					else if(grid[i][j-1] == grid[i][j])System.out.print(": " + grid[i][j]);
					else System.out.print("| " + grid[i][j] + "");
					break;
				}
			}
			System.out.print("|");
			System.out.println();
		}
		for(int k = 0; k < columns; k++)
		{
			System.out.print("******");
		}
	}
}	