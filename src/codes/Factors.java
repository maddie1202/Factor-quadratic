package codes;

import java.util.Scanner;

public class Factors 
{
	public static int FMSIZE; //FMSIZE = Factor Method Size
	//This integer indicates the number of positive factors a*c can have
	//It's a class variable because it is used my multiple methods
	
	
	public static int errorTrap (int min, int max)
	{
		Scanner input = new Scanner(System.in); // Create a new Scanner called input that takes input from the keyboard
		
		boolean inputSucess;
		int number = 0;
	
		do
		{
			inputSucess = true; //Reset the boolean to prepare for correct input
				
			try
			{
				number = input.nextInt();//Value to be returned if it fits in the correct range
			}
				
			catch (Exception e) //If an error occurs in the try statement, catch all exceptions
			{
				input.nextLine(); //Clear the stream of the user entered data
				inputSucess = false; //Set 'inputSucess' to false so the program will loop back to the prompt
			}
				
			if (number < min || number > max || inputSucess == false) //Test if the data is in the correct range
			{
				System.out.println("Invalid data, try again."); //Informs user that they entered the wrong data
			}
				
		}while (number < min || number > max || inputSucess == false); //Loop back to the prompt if the input is in the incorrect range
		
		return number; //return what the user entered in the try statement
	}
	
	public static boolean duplicateFactor (int factor,int[][]factors) //checks for factors already present in factors array
	{
		for (int y = 0; y < FMSIZE; y++) //loops through all integers in the second 'row' of the array
			if(factors[1][y] == factor) //if the factor is already present in the factors array, return that there IS(true) a duplicate factor
				return true; //doesn't execute the rest of the code in the method
		
		return false; //If the loops is completed without finding any duplicates, return that there ISN'T(false) a duplicate factor
	}
	
	public static int howManyFactors(int product)//This method calculates how many positive factors a given number has
	{
		int numberOfFactors = 0;
		
		for (int x = 1; x < (Math.abs(product) + 1);x++)
			if(product % x == 0)//Every time it finds a number that's evenly divisible by the product, then it increments the numberOfFactors variable
				numberOfFactors++;
		
		return numberOfFactors;
	}
	
	public static int [][] factors(int product) //creates and returns a two dimensional array of the factors of the given product
	{
		int index = 0; //Initialize this variable to start filling the array at the first position([0][0])
		FMSIZE = howManyFactors(product);
		//System.out.println(product + " has " + FMSIZE + " factor(s).");
		int [][] factors = new int [2][FMSIZE]; //Creates a two dimensional array with two 'rows' to match up factors that multiply to equal the given product
		//Therefore, the each column would have two matching(two factors that multiply to equal the given product) factors
		//For example, if the product was 12, the first column would have -12 and -1 and the second column would have -6 and -2
		
		for(int x = (0 - Math.abs(product)); x < (Math.abs(product) + 1); x++) //Searches through numbers from the negative value of the given product to the positive value of the given product
			//For example, if the product was 12, it would search through numbers ranging from -12 to 12
		{
			if(x == 0)//zero is skipped because it can't be a factor of any number other than zero
				//also, this value appears in the denominator of a division equation later in the method and would produce a math error if it isn't be skipped
				continue; //when the condition is met, the rest of the code for this loop is skipped and it moves on to the next iteration
			
			else if (product % x == 0 && duplicateFactor(x,factors) == false && index < FMSIZE) //checks if the iteration variable is a factor and if it's is already present in the factors array
			{
				
				factors[0][index] = x; //if the conditions are met, this puts the iteration variable into the first row of the factors array
				factors[1][index] = product/x; //puts the matching(the other factor that multiples with the iteration variable to equal the given product)
											   //into the second 'row' and the same 'column' as the previous factor
				index++; //adds one to the 'column' variable the next factors will be put into
			}
		
		}	
		
		return factors;
	
	}
	
	public static int[] specialNumbers (int a, int b, int c) //Finds two number that multiply to equal a*c and add to equal b
	{	
		int product = a*c; 
		int [] sn = new int[2]; //This array will hold the tow "special numbers"
		//sn = special numbers
		
		int [][] factors = factors(product); //This creates an array of factors of a*c 
		//See above for more information on how this array is sorted
		
		/*
		for (int x = 0; x < 2; x++) //This prints the array of factors into 'rows' and 'columns' mentioned above
		//It is commented out because it is for testing purposes
		{
			for (int y = 0; y < FMSIZE; y++)
				System.out.print(factors[x][y] + " ");
			
			System.out.println();
		}
		*/
		
		for (int y = 0; y < FMSIZE; y++)//This checks for factors that add to equal b
		{	
			if(factors[0][y] == 0 || factors[1][y] == 0)
				break; //This stops the loop once it has gone through all of the factors
			//Because each product a different number of factors, there will be blank spaces(0's) in the array
			
			if(factors[0][y] + factors[1][y] == b) //This checks to see if the two "matching" factors add to equal b
			{
				sn[0] = factors[0][y]; //If the conditions above are met, this sets the "matching" factors as the "special numbers"
				sn[1] = factors[1][y];
			}
		}
			
			
		
		return sn; //returns the tow factors that multiply to equal a*c and add to equal b
	}
	
	public static int[] coefficients (int[] sn, int a) //This method takes the special numbers and converts them to an array(of 4) coefficients for the final factored equation
	{													//This method also takes a because it needs to find the factors of a
		int [] coefficients = new int[4]; //Creates an array of the four coefficients 
											// For example, if the coefficients were 1,3,2 and -4, the equation would look like (x + 3)(2x - 4)
		
		int[][] aFactors = factors(a); //Finds the factors of a
		
		//This prints the factors of a
		//It is commented out because it is for testing purposes
		
		/*
		for (int x = 0; x < 2; x++) 
		{
			for (int y = 0; y < FMSIZE; y++)
				System.out.print(aFactors[x][y] + " ");
			
			System.out.println();
		}
		*/

		if(a == 1) //This sets the coefficients if a is equal to 1
		{
			coefficients[0] = 1; //There are only two possibilities for the coefficients if a is equal to one
			coefficients[1] = sn[0];//and, they are both correct, so they are just set here
			coefficients[2] = 1;
			coefficients[3] = sn[1];
		}
		
		else if (aFactors[0][2] == 0)  //This sets the coefficients if a is a prime number
										// This checks the third column of the factors array because, if a is a prime number, it will only have four factors 
										//(the positive and negative value of 1 and the positive and negative value for the numbers itself)
										//For example, if a was 3, the factors array would look like: -1,3,0,0,0,0,0,0,0,0,0,0
																								    //-3,1,0,0,0,0,0,0,0,0,0,0
		{
			coefficients[0] = aFactors[0][1]; //Since there are only two (correct) possibilities for coefficients 1 and 3, they are set here
			coefficients[2] = aFactors[1][1];
			
			if(sn[0] % coefficients[0] == 0 && sn[1] % coefficients[2] == 0) //these if/else if statements check to see which of the two remaining possibilities for the coefficients are correct
			//the condition checks to see if the constant coefficients are evenly divisible by the set coefficients
			{
				coefficients[3] = sn[0]/coefficients[0]; //These set the constant coefficients to match the already set coefficients
				coefficients[1] = sn[1]/coefficients[2];
			}
			
			else if(sn[1] % coefficients[0] == 0 && sn[0] % coefficients[2] == 0) //This statement repeats what the last one does with one difference
																					//That difference is switching the special numbers
			{
				coefficients[3] = sn[1]/coefficients[0];//These set the constant coefficients to match the already set coefficients
				coefficients[1] = sn[0]/coefficients[2];
			}
			
			
		}
		
		else //This sets the coefficients if a isn't a prime number
		{
			for (int x = 0; x < FMSIZE; x++)
			{
				coefficients[0] = aFactors[0][x];//This sets coefficients 1 and 3 sequentially from the array containing the factors of a
				coefficients[2] = aFactors[1][x];//For example, if a was 12, on the first iteration, coefficient #1 would be -12 and coefficient #3 would be -1
				
				if(sn[0] % coefficients[0] == 0 && sn[1] % coefficients[2] == 0) //These if/else if statements do the same thing as the ones for the prime numbers
				{
					coefficients[3] = sn[0]/coefficients[0];
					coefficients[1] = sn[1]/coefficients[2];
				}
				
				else if(sn[1] % coefficients[0] == 0 && sn[0] % coefficients[2] == 0)
				{
					coefficients[3] = sn[1]/coefficients[0];
					coefficients[1] = sn[0]/coefficients[2];
				}
				
				if (coefficients[0] > 0 && coefficients[1] != 0 && coefficients[0] > 0 && coefficients[0] != 0) //These statements invalidate coefficient array that won't multiply to equal the "special numbers"
					if((coefficients[0] * coefficients[3] == sn[0] && coefficients[1] * coefficients[2] == sn[1]) || (coefficients[0] * coefficients[3] == sn[1] && coefficients[1] * coefficients[2] == sn[0]))
						break; //If the conditions are met, the coefficients array stays unchanged and gets returned
			}
		}
	
		
		return coefficients; //returns an array of 4 coefficients to put into the final equation
	}
	
	public static int greatestCommonFactor (int a, int b, int c) //This method finds the greatest common factor between the three terms
	{
		int gcf = 1;//gcf = greatest common factor
		//This variable is initialized to 1 because all numbers can be divided by 1
		
		for(int x = 100; x > 0; x--) //This starts searching at 100 to find the GREATEST common factor
			if(a % x == 0 && b % x == 0 && c % x == 0)//This checks if all three terms can be divided by the iteration variable
			{
				gcf = x;//If the conditions above are met, the greatest common factor variable is set to be the iteration variable
				break;
			}
				
		
		return gcf; //The greatest value that all the terms can be divided by is returns
	}

	public static void main(String[] args) 
	{
		
		System.out.println("Ax\u00B2 + Bx + C = 0");//u00B2 is unicode for the superscript two
		System.out.println("0 < A < 100; -100 < B < 100; -100 < C < 100; -100 < A*C < 100; C \u2260 0");//These statements inform the user of the formula and the parameters
		System.out.println();//\u2260 is unicode for the inequation symbol
		
		int a = 0;
		int b = 0;
		int c = 0;
		
		do
		{
			System.out.println("Enter values for A, B and C: ");//User prompt
			System.out.print("A: ");//User prompt
			a = errorTrap(1,99); //Takes input from the keyboard until the input fits into the range (min 1, max 99)
			System.out.print("B: ");//User prompt
			b = errorTrap(-99,99);//Takes input from the keyboard until the input fits into the range (min -99, max 99)
			System.out.print("C: ");//User prompt
			c = errorTrap(-99,99);//Takes input from the keyboard until the input fits into the range (min -99, max 99)
			
			if (Math.abs(a*c) > 99) //Checks that the variables do not exceed the parameters
				System.out.println("A*C has to be smaller than 100 and greater than -100.");//Informs user that input doesn't fit the parameters 
			if(c == 0)
				System.out.println("C cannot be equal to 0.");
			
		}while (Math.abs(a*c) > 99 || c == 0);//Loops back to prompts if input doesn't match the parameters

		int gcf = greatestCommonFactor(a,b,c);//Calculates the greatest common factor between the three terms
		
		a = a/gcf;//Divides each term by the greatest common factor
		b = b/gcf;
		c = c/gcf;
		
		int [] sn = specialNumbers(a,b,c); //sn = special numbers
		//Finds two numbers that multiply to equal a*c and add to equal b	
		
		//System.out.println(sn[0] + " " + sn[1]);
		
		if (sn[0] == 0 && sn[1] == 0) //If "special numbers" are both zero, the equation cannot be factored
		{
			System.out.println("Cannot be factored.");//Informs user that the equation cannot be factored.
			System.exit(0);//Ends the program
		}
		
		int[] coefficients = coefficients(sn,a);//Using the special number, this calculates the four coefficients/constants needed to form the final awnser
		
		//Prints the coefficients
		
		 //Is commented out because it's for testing purposes
		/*
		for (int x = 0; x < 4; x++)
			System.out.print(coefficients[x] + " ");
		*/
		
		
		char opperator1; //Either '+' or "-" 
		char opperator2;
		
		if(coefficients[1] > 0)//If the value is positive, set operator to be '+", if it's negative, set operator to be '-';
			opperator1 = '+';
		else
			opperator1 = '-';
		
		if(coefficients[3] > 0)
			opperator2 = '+';
		else
			opperator2 = '-';
		
		System.out.println();
		
		//These if/else if statements don't print coefficients 1 and 3 if they are 1
		//For example, you don't write (1x + 1)(1x + 1), instead, you write (x + 1)(x + 1)
		
		if (coefficients[0] > 1 && coefficients[2] > 1)
			System.out.println("(" + coefficients[0] + "x " + opperator1 + " " + Math.abs(coefficients[1]) + ") (" + coefficients[2] + "x " + opperator2 + " " + Math.abs(coefficients[3]) + ")");
		
		else if (coefficients[0] > 1 && coefficients[2] == 1)
			System.out.println("(" + coefficients[0] + "x " + opperator1 + " " + Math.abs(coefficients[1]) + ") (" + "x " + opperator2 + " " + Math.abs(coefficients[3]) + ")");
		
		else if (coefficients[0] == 1 && coefficients[2] > 1)
			System.out.println("(" + "x " + opperator1 + " " + Math.abs(coefficients[1]) + ") (" + coefficients[2] + "x " + opperator2 + " " + Math.abs(coefficients[3]) + ")");
		
		else if (coefficients[0] == 1 && coefficients[2] == 1)
			System.out.println("(" + "x " + opperator1 + " " + Math.abs(coefficients[1]) + ") (" + "x " + opperator2 + " " + Math.abs(coefficients[3]) + ")");
			
	}

}
