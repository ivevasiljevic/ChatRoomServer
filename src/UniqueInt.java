import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueInt {
	private static List<Integer> ID =  new ArrayList<Integer>();
	
	private static int index = 0;
	
	private static final int RANGE = 100;
	
	static
	{
		for(int i = 0;i<RANGE;i++)
		{
			ID.add(i);
		}
		Collections.shuffle(ID);
	}
	
	private UniqueInt()
	{
		
	}
	
	public static int getID()
	{
		if(index > 100) index = 0;
		
		return ID.get(index++); 
	}
	
	
}
