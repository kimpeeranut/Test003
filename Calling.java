import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calling {

	List<Double> Total_Time = new ArrayList<Double>();
	List<String> Price = new ArrayList<String>();
	LocalTime  From = LocalTime.parse("00:00");
 
	public double getMinute(LocalTime To)
	{
		double minutes = 0.0;
		minutes = Duration.between(From, To).toMillis()/60000.0;
		return minutes;
	}
	
	
	public void getPrice(List<Double> minute_begin,List<Double> minute_end)
	{
		DecimalFormat df = new DecimalFormat("####.##");
	    int loop = 0;
	    double total_price = 0;
	    while(loop<minute_begin.size())
	    {
	    	Total_Time.add(Math.abs( minute_end.get(loop)-minute_begin.get(loop))) ;
	    	if(Total_Time.get(loop)>1)
	    	{
	    		total_price = (Total_Time.get(loop)-1)+3;
	    		Price.add(df.format(total_price));
	    	}
	    	
	    	else if (Total_Time.get(loop)<1 ||Total_Time.get(loop)== 1 )
	    	{
	    		total_price = 3;
	    		Price.add(df.format(total_price));
	    	}
	    	loop++;
	    }
	    
		
	}

	public List<String> getPrice()
	{
		return Price;
	}
	
	
}
