package project;

import java.util.*;

public class person
{
	//Instance variables - Each person has a name, preference list 
	//                     and a pointer array to represent deletions from their preference list
	private String name = null;
	List<String> prefList = new ArrayList<String>();
	List<Boolean> pointer = new ArrayList<Boolean>(Arrays.asList(new Boolean[GSAlgo.maxNoOfParticipants]));
	
	//Constructor
	public person(String personName, List<String> preferences)
	{
		name = personName;
		prefList = preferences;
		
		//Initially all proposers and acceptors are available - hence all pointers set to TRUE
		Collections.fill(pointer, Boolean.TRUE);
	}
	//Getters
	public String getName()
	{
		return name;
	}
	
	public List<String> getPrefList()
	{
		return prefList;
	}
	
	public List<Boolean> getPointer()
	{
		return pointer;
	}
	
	public Boolean getPointerValueAtThisPosition(int position)
	{
		return pointer.get(position);
	}
	
	//Setters
	public void setName(String Name)
	{
		name = Name;
	}
	
	public void setPrefList(List<String> PrefList)
	{
		prefList = PrefList;
	}
	
	public void setPointer(int position, Boolean value)
	{
		pointer.set(position, value);
	}

}
