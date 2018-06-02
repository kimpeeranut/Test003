import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;

public class DoFile {

	JSONObject object = new JSONObject();
	List<String> Date = new ArrayList<String>();
	List<String> Promotion = new ArrayList<String>();
	List<String> MobileNumber = new ArrayList<String>();
	List<Double> Minute_begin = new ArrayList<Double>();
	List<Double> Minute_end = new ArrayList<Double>();
	List<String> Price = new ArrayList<String>();
	
	public DoFile()
	{
		
	}
	
	public DoFile(JSONObject json)
	{
		object = json;
		
	}
	
	
	public String readFile()
	{
		String read ="";

				 try
		        {
		            BufferedReader in = new BufferedReader(new FileReader(new File("D:\\Java\\promotion1.log")));
		            System.out.println("File open successful!");
		            for (String x = in.readLine(); x != null; x = in.readLine())
		            {
		            	read = read+"|"+x;     
		            }
		            in.close();
		        } catch (IOException e)
		        {
		            e.printStackTrace();
		        }
				
		return read;
	}
	
	
	public void splitContext(String text)
	{
	    int loop = 0;
	    int remainder = 0;
	    String [] cut  = text.split("\\|");
	    String [] customer = new String [cut.length-1];
	    System.arraycopy( cut, 1, customer, 0, cut.length-1 );
	    LocalTime To ;
	    Calling call = new Calling(); 
	    double minutes = 0.0;
	    while(loop<customer.length)
	    {
	    	remainder = loop%5;
	    	switch(remainder)
	    	{
	    		case 1 :  // Minute_begin
	    		{
	    			To =  LocalTime.parse(customer[loop], DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH));
	    			minutes = call.getMinute(To);
	    			Minute_begin.add(minutes);
	    			break;
	    		}
	    		case 2 : // Minute_end
	    		{
	    			To =  LocalTime.parse(customer[loop], DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH));
	    			minutes = call.getMinute(To);
	    			Minute_end.add(minutes);
	    			break;
	    		}
	    		
	    		case 3 :
	    		{
	    			MobileNumber.add(customer[loop]);
	    			break;
	    		}
	    		case 4 :
	    		{	Promotion.add(customer[loop]);
	    			break;
	    		}
	    		case 0 :  // number ended with 0 or 5
	    		{
	    			Date.add(customer[loop]);
	    			break;
	    		}
	    		
	    	}

	    	loop++;
	    }
	}
	
		
	
	public void writeFile() throws JSONException
	{
			int loop = 0;
			JSONObject obj = new JSONObject();
			 String concat = "";
			 Calling calls = new Calling();
			 calls.getPrice(Minute_begin,Minute_end);
			 Price = calls.getPrice();
			 
			
		    while(loop<MobileNumber.size())
		    {
		    	  obj.put("person"+loop," Phone num "+MobileNumber.get(loop)+" Price: "+Price.get(loop));
		    	loop++;
		    }
		    obj.put("22/06/2013",concat);    
		    try {
		    	FileWriter file = new FileWriter("C:\\xampp\\htdocs\\promotion_output.json");
		    	file.write(obj.toString());
		    	System.out.println("File Writing is successful");
		    	file.flush();
		    	file.close();
		    	
		    }
		    catch(IOException ex)
		    {
		    	ex.printStackTrace();
		    }
				
	}


}
