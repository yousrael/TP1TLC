import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.*;

public class Test {
	public static void main(String[] args) throws Exception {
		List<Long> time = new ArrayList<Long>();
		long timeStart;
		long timeStop;
		//lecture simple
		for (int i = 0; i < 10; i++) {
			timeStart = System.currentTimeMillis();
			URL url = new URL("http://127.0.0.1:8080/");
			System.out.println("sending url: "+url);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null){
//				System.out.println(inputLine);
				inputLine.length();
			}
			in.close();
			timeStop = System.currentTimeMillis();
			time.add(timeStart-timeStop);
		}
		for (int i = 0; i < time.size(); i++) {
			System.out.println(" "+time.get(i)+"ms");
		}
		
		time.clear();
		Date ndate = new Date(System.currentTimeMillis());
		
		//ecriture
		for (int i = 0; i < 10; i++) {
			timeStart = System.currentTimeMillis();
			ndate.setYear(ndate.getYear());
			URL url = new URL("http://127.0.0.1:8080/board.jsp?title=PetiteAnnonce"+i+"&price="+i+"&date="+URLEncoder.encode(ndate.toString(), "UTF-8"));
			System.out.println("sending url: "+url);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null){
//				System.out.println(inputLine);
				inputLine.length();
			}
			in.close();
			timeStop = System.currentTimeMillis();
			time.add(timeStart-timeStop);
		}
		for (int i = 0; i < time.size(); i++) {
			System.out.println(" "+time.get(i)+"ms");
		}
	}
}
