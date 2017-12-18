package project;

public class mainGSAlgo 
{
	//main creates 2 objects - Model object and View-Controller object
	public static void main(String[] args) 
	{
		//MVC architecture : Main creates 2 objects - Model object and View- Controller object
		//Model Object passed as parameter to View-Controller object for it to remember the Model for future use
		
		//Model object
		GSAlgo algo = new GSAlgo();
		
		//View-Controller object
		GUI mainWindow = new GUI(algo);
	}
}
