package project;

import java.util.*;

public class acceptor extends person 
{
	//Constructor
	public acceptor(String personName, List<String> preferences)
	{
		super(personName, preferences);
	}
	
	//Method to return position/index of proposer in acceptors's preference list
	public int getProposerPosition(String proposerName)
	{
		for(String eachProposer : prefList)
		{
			if(eachProposer.equalsIgnoreCase(proposerName))
				return prefList.indexOf(eachProposer);
		}
		//if acceptor not found in  preference list, return -1
		return -1;
	}	
}
