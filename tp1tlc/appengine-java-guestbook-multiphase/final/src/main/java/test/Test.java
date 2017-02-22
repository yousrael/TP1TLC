package test;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.*;

public class Test {
	static boolean debug = false;
	private static List<Long> search(int nb)throws Exception {
		List<Long> time = new ArrayList<Long>();
		long timeStart;
		long timeStop;
		Date ndate = new Date(System.currentTimeMillis());
		for (int i = 0; i < nb; i++) {
			timeStart = System.currentTimeMillis();
			ndate.setYear(ndate.getYear());
			Runtime r = Runtime.getRuntime();
			String curl = "curl -H 'Content-Type: application/json' -X GET";
			String cmd = "http://127.0.0.1:8080/board.jsp?boardName=" + "null"+"&filter="+""+"&priceMin="+0+"&priceMax="+4000+"&dateMin="+URLEncoder.encode("01-01-2005", "UTF-8")+"&dateMax="+URLEncoder.encode("01-01-2025", "UTF-8");
			if(debug)
				System.out.println("sending url: "+cmd);
			Process p = r.exec(curl+" "+cmd);p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = b.readLine()) != null) {
				if(debug)
					System.out.print(line);
			}
			if(debug)
				System.out.println("");
			b.close();


			timeStop = System.currentTimeMillis();
			time.add(timeStop-timeStart);
		}
		return time;
	}
	private static List<Long> delete(int nb)throws Exception {
		List<Long> time = new ArrayList<Long>();
		long timeStart;
		long timeStop;
		Date ndate = new Date(System.currentTimeMillis());
		for (int i = 0; i < nb; i++) {
			timeStart = System.currentTimeMillis();
			ndate.setYear(ndate.getYear());
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("curl -H 'Content-Type: application/json' -X GET "+"http://127.0.0.1:8080/delete?boardName=null&filter=null");p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = b.readLine()) != null) {
				if(debug)
					System.out.println(line);
			}

			b.close();


			timeStop = System.currentTimeMillis();
			time.add(timeStop-timeStart);
		}
		if(debug)
			for (int i = 0; i < time.size(); i++) {
				System.out.println(" "+time.get(i)+"ms");
			}
		return time;
	}
	private static List<Long> insert(int nb)throws Exception {
		List<Long> time = new ArrayList<Long>();
		long timeStart;
		long timeStop;
		Date ndate = new Date(System.currentTimeMillis());
		for (int i = 0; i < nb; i++) {
			timeStart = System.currentTimeMillis();
			ndate.setYear(ndate.getYear());
			Runtime r = Runtime.getRuntime();
			String curl = "curl -H 'Content-Type: application/json' -X GET";
			String cmd = "http://127.0.0.1:8080/sign?title=PetiteAnnonce"+i+"&price="+i+"&date="+URLEncoder.encode("01-01-2005", "UTF-8");
			if(debug)
				System.out.println("sending url: "+cmd);
			Process p = r.exec(curl+" "+cmd);p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = b.readLine()) != null) {
				if(debug)
					System.out.println(line);
			}

			b.close();


			timeStop = System.currentTimeMillis();
			time.add(timeStop-timeStart);
		}
		return time;

	}
	private static void minMax(List<Long> tab)throws Exception {
		long min = 9999L;
		long max = 0L;
		long moyenne = 0L;
		for (int i = 0; i < tab.size(); i++) {
			if(tab.get(i) > max) max = tab.get(i);
			if(tab.get(i) < min) min = tab.get(i);
			moyenne += tab.get(i);
		}
		System.out.println("  min: "+min+"ms      max: "+max+"ms      moyenne: "+moyenne/tab.size()+"ms      total: "+moyenne+"ms");

	}
	public static void main(String[] args) throws Exception {
//		List<Long> time = new ArrayList<Long>();
//		long timeStart;
//		long timeStop;
		//lecture simple
//		for (int i = 0; i < 10; i++) {
//			timeStart = System.currentTimeMillis();
//			URL url = new URL("http://127.0.0.1:8080/");
//			System.out.println("sending url: "+url);
//			BufferedReader in = new BufferedReader(
//					new InputStreamReader(url.openStream()));
//
//			String inputLine;
//			while ((inputLine = in.readLine()) != null){
//				//				System.out.println(inputLine);
//				inputLine.length();
//			}
//			in.close();
//			timeStop = System.currentTimeMillis();
//			time.add(timeStop-timeStart);
//		}
//		for (int i = 0; i < time.size(); i++) {
//			System.out.println(" "+time.get(i)+"ms");
//		}
//
//		time.clear();
//		Date ndate = new Date(System.currentTimeMillis());
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//ecriture
		
		minMax(insert(1000));
		////////////////////////////////////////////////////////////////////////////////
		//find
		
		minMax(search(100));
		////////////////////////////////////////////////////////////////////////////////
		//delete

		minMax(delete(10));
		minMax(insert(100));
		minMax(delete(10));
		minMax(insert(100));
		minMax(delete(10));
		minMax(insert(100));
		minMax(delete(10));
	}
}
