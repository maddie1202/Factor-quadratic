package codes;

import java.util.Scanner;

public class Factors 
{
	public static final int FMSIZE = 12; //FMSIZE = Factor Method Size
	//This integer indicates the number of factors a*c can have
	//It's a class variable because it is used my multiple methods
	//No numbers that fit in the parameters below (nothing can be higher than 100 or lower than -100) has more than 12 factors
	
	
	public static int errorTrap (int min, int max)
	{
		Scanner input = new Scanner(System.in); // Create scanner
		
		boolean inputSucess; // Boolean for error trap
		int number = 0;
	
		do
		{
			inputSucess = true; //Reset the boolean to prepare for correct input
				
			try
			{
				number = input.nextInt();
			}
				
			catch (Exception e) //If an error occurs in the try statement, catch all exceptions
			{
				input.nextLine(); //Clear the stream of the user entered data
				inputSucess = false; //Set 'inputSucess' to false so the program will loop back to the prompt
			}
				
			if (number < min || number > max || inputSucess == false) //Test if the data is in the correct range
			{
				System.out.println("Invalid data, try agin."); //Informs user that they entered the wrong data
			}
				
		}while (number < min || number > max || inputSucess == false); //Loop back to the prompt if the input is in the incorrect range
		
		return number; //return input
	}
	
	public static boolean duplicateFactor (int factor,int[][]factors) //checks for factors already present in factors array
	{
		for (int y = 0; y < FMSIZE; y++) //loops through all integers in the second 'row' of the array
			if(factors[1][y] == factor) //if the factor is already present in the factors array, return that there IS(true) a duplicate factor
				return true; //doesn't execute the rest of the code in the method
		
		return false; //If the loops is completed without finding any duplicates, return that there ISN'T(false) a duplicate factor
	}
	
	public static int [][] factors(int product) //creates and returns a two dimensional array of the factors of the given product
	{
		int index = 0; //Initialize this variable to start filling the array at the first position([0][0])
		int [][] factors = new int [2][FMSIZE]; //Creates a two dimensional array with two 'rows' to match up factors that multiply to equal the given product
		//Therefore, the each column would have two matching(two factors that multiply to equal the given product)
		//For example, if the product was 12, the first column would have 12 and 1 and the second column would have 6 and 2
		
		for(int x = (0 - Math.abs(product)); x < (Math.abs(product) + 1); x++) //Searches through numbers from the negative value of the given product to the positive value of the given product
			//For example, if the product was 12, it would search through numbers ranging from -12 to 12
		{
			if(x == 0)//zero is skipped because it can't be a factor of any number other than zero
				//also, this value appears in the denominator of a division equation later in the method and would produce a math error if it isn't be skipped
				continue; //when the condition is met, the rest of the code for this loop is skipped and it moves on to the next iteration
			
			else if (product % x == 0 && duplicateFactor(x,factors) == false) //checks if the iteration variable is a factor and if said factor is already present in the factos array
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
		
		for (int y = 0; y < FMSIZE; y++)
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
				coefficients[0] = aFactors[0][x];
				coefficients[2] = aFactors[1][x];
				
				if(sn[0] % coefficients[0] == 0 && sn[1] % coefficients[2] == 0)
				{
					coefficients[3] = sn[0]/coefficients[0];
					coefficients[1] = sn[1]/coefficients[2];
				}
				
				else if(sn[1] % coefficients[0] == 0 && sn[0] % coefficients[2] == 0)
				{
					coefficients[3] = sn[1]/coefficients[0];
					coefficients[1] = sn[0]/coefficients[2];
				}
				
				if (coefficients[0] > 0 && coefficients[1] != 0 && coefficients[0] > 0 && coefficients[0] != 0)
					if((coefficients[0] * coefficients[3] == sn[0] && coefficients[1] * coefficients[2] == sn[1]) || (coefficients[0] * coefficients[3] == sn[1] && coefficients[1] * coefficients[2] == sn[0]))
						break;
			}
		}
	
		
		return coefficients;
	}
	
	public static int greatestCommonFactor (int a, int b, int c)
	{
		int gcf = 0;
		
		for(int x = 2; x < 100; x++)
			if(a % x == 0 && b % x == 0 && c % x == 0)
				gcf = x;
		
		return gcf;
	}

	public static void main(String[] args) 
	{
		
		System.out.println("Ax\u00B2 + Bx + C = 0");
		System.out.println("0 < A < 100; -100 < B < 100; -100 < C < 100; -100 < A*C < 100");
		System.out.println();
		
		int a = 0;
		int b = 0;
		int c = 0;
		
		do
		{
			System.out.println("Enter values for A, B and C: ");
			System.out.print("A: ");
			a = errorTrap(1,99);
			System.out.print("B: ");
			b = errorTrap(-99,99);
			System.out.print("C: ");
			c = errorTrap(-99,99);
			
			if (Math.abs(a*c) > 99)
				System.out.println("A*C has to be smaller than 100 and greater than -100.");
			
		}while (Math.abs(a*c) > 99);

		int gcf = greatestCommonFactor(a,b,c);
		
		if(gcf != 0)
		{
			a = a/gcf;
			b = b/gcf;
			c = c/gcf;
		}
		
		int [] sn = specialNumbers(a,b,c); //sn = special numbers
		
		//System.out.println(sn[0] + " " + sn[1]);
		
		if (sn[0] == 0 && sn[1] == 0)
		{
			System.out.println("Cannot be factorized.");
			System.exit(0);
		}
		
		int[] coefficients = coefficients(sn,a);
		
		/*
		for (int x = 0; x < 4; x++)
			System.out.print(coefficients[x] + " ");
		*/
		
		
		
		char opperator1;
		char opperator2;
		
		if(coefficients[1] > 0)
			opperator1 = '+';
		else
			opperator1 = '-';
		
		if(coefficients[3] > 0)
			opperator2 = '+';
		else
			opperator2 = '-';
		
		System.out.println();
		
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
