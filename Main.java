

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;




public class Main {


	public static void main(String[] args) throws ParseException, JSONException {
		DecimalFormat df = new DecimalFormat("####.##");
		JSONObject obj = new JSONObject();
		

		String readfile ="";
		
		List<String> Date = new ArrayList<String>();
		List<String> Promotion = new ArrayList<String>();
		List<String> MobileNumber = new ArrayList<String>();
		List<Double> Minute_begin = new ArrayList<Double>();
		List<Double> Minute_end = new ArrayList<Double>();
		List<Double> Total_Time = new ArrayList<Double>();
		List<String> Price = new ArrayList<String>();


		 try
	        {
	            BufferedReader in = new BufferedReader(new FileReader(new File("D:\\Java\\promotion1.log")));
	            System.out.println("File open successful!");

	           // int line = 0;
	            for (String x = in.readLine(); x != null; x = in.readLine())
	            {
	               // line++;
	                readfile = readfile+"|"+x;

	                
	            }
	            in.close();
	        } catch (IOException e)
	        {
	            System.out.println("File I/O error!");
	        }
		 
			


		    int loop = 0;
		
		    int remainder = 0;
		    String [] cut  = readfile.split("\\|");
		    String [] customer = new String [cut.length-1];
		    System.arraycopy( cut, 1, customer, 0, cut.length-1 );
		    

			LocalTime  From = LocalTime.parse("00:00");
		    LocalTime To ;
		    double minutes = 0;

			
		    while(loop<customer.length)
		    {
		    	remainder = loop%5;
		    	switch(remainder)
		    	{
		    		case 1 :
		    		{
		    			To =  LocalTime.parse(customer[loop], DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH));
		    			minutes = Duration.between(From,To).toMillis()/60000.0;
		    			Minute_begin.add(minutes);
		    			break;
		    		}
		    		case 2 :
		    		{
		    			To =  LocalTime.parse(customer[loop], DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH));
		    			minutes = Duration.between(From,To).toMillis()/60000.0;
		    			Minute_end.add(minutes);
		    			break;
		    		}
		    		
		    		case 3 :
		    		{
		    			MobileNumber.add(customer[loop]);
		    			break;
		    		}
		    		case 4 :
		    		{
		    			Promotion.add(customer[loop]);
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
		    
		    loop = 0;
		    double total_price = 0;
		    while(loop<Minute_begin.size())
		    {
		    	Total_Time.add(Math.abs( Minute_end.get(loop)-Minute_begin.get(loop))) ;
		    	if(Total_Time.get(loop)>1)
		    	{
		    		total_price = (Total_Time.get(loop)-1)+3;
		    		Price.add(df.format(total_price));
		    		System.out.println("Price "+Price.get(loop)+" Phone No. "+MobileNumber.get(loop));

		    	}
		    	
		    	else if (Total_Time.get(loop)<1 ||Total_Time.get(loop)== 1 )
		    	{
		    		total_price = 3;
		    		Price.add(df.format(total_price));
		    		System.out.println("Price "+Price.get(loop)+" Phone No. "+MobileNumber.get(loop));

		    	}
		    	loop++;
		    }
		    
		    loop = 0;
		    String concat = "";
		    while(loop<MobileNumber.size())
		    {
		    	  obj.put("person"+loop," Phone num "+MobileNumber.get(loop)+" Price: "+Price.get(loop));
		    	loop++;
		    }
		    obj.put("22/06/2013",concat);
		    System.out.println(obj);	    
		    try {
		    	FileWriter file = new FileWriter("C:\\xampp\\htdocs\\promotion_output.json");
		    	file.write(obj.toString());
		    	file.flush();
		    	file.close();
		    	
		    }
		    catch(IOException ex)
		    {
		    	ex.printStackTrace();
		    }


	}

}
