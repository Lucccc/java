package com.GL;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DBconnect1{

	public static int yearNum ;
	
	
	public static List<Weather> QueryList(int year) throws SQLException{
		List<Weather> weatherList =new ArrayList<Weather>();
		int n=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM [aaaa].[dbo].[51628] " +
	"WHERE ([年] = '" +year+"' or([年]='" +(year-1)+"' AND [月]= '12' AND [日] = '31'AND[时]>='20'))"+
	"AND NOT([年]='" +year+"' AND [月]= '12' AND [日] = '31'AND[时]>='20')" +
	" ORDER BY [年],[月],[日],[时]";
		try {
			conn = DBconnect1.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			}
		
		
		
		
		
		rs = pstmt.executeQuery();
		
		
		for (int i2 = 0; rs.next(); i2++) {
			Weather weather=new Weather();
			List<String> minutes=new ArrayList<String>();
			for (int j = 0; j <= 65; j++) {
				
				switch(j){
				case 0: 		
					weather.setZhanhao(rs.getInt(j+1));
					continue;
					
				case 1:
					weather.setYear(rs.getInt(j+1));
					continue;
					
				case 2:
					weather.setMonth(rs.getInt(j+1));
					continue;
						
				case 3:
					weather.setDay(rs.getInt(j+1));
					continue;
					
				case 4:
					weather.setHour(rs.getInt(j+1));
					continue;
						
				case 5:
					weather.setR(rs.getString(j+1));
					continue;
			}
				if(rs.getString(j+1)==null){
					minutes.add("///");
				}else
				{
					minutes.add(rs.getString(j+1));
				}
				
		}
			
			weather.setMinutes(minutes);
			weatherList.add(weather);
		}
		yearNum = weatherList.size();
		return weatherList;
	}
	public static Connection getConnection(){
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://127.0.0.1 ;DatabaseName=aaaa";
		String user="sa";
		String password="123654789";
		Connection conn = null;
		try{
			
			Class.forName(driverName);
			System.out.println("数据库驱动程序注册成功"); 
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Successful!");   //如果连接成功控制台输出Connection Successful! 		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		return conn;	
	}
	
	public static boolean isrun(int i){
		
		if (i % 4 == 0){
			return true;
		}else{
			return false;
			
		}
		
		
	
		
	}
	
	public static List<String> getFinalList(List<Weather> weatherList,List<String>nullList){
		int n =0, days=0;
		int size =weatherList.size();
		
		
		List <String> finalList = new ArrayList<String>();
		Calendar cal =Calendar.getInstance();
		Calendar calone = Calendar.getInstance();
		 
		cal.clear();
		calone.clear();
		
		String calStr =new String();
		String firstDay =new String();
		
		SimpleDateFormat formater= new SimpleDateFormat("yyyy-MM-dd HH");
		if(weatherList.get(0).getMonth()==12){
			calone.set(weatherList.get(0).getYear(),11,31,20,0,0);
		}else{
			calone.set(weatherList.get(0).getYear()-1,11,31,20,0,0);
		}
		
	
		if(weatherList.get(0).getMonth()==12){
			if(isrun(weatherList.get(0).getYear()+1)){
				days = 8784;
			}else{
				days = 8760;
			}
		}else{
			if(isrun(weatherList.get(0).getYear())){
				days = 8784;
			}else{
				days = 8760;
		}
		}
		
		
		
		for (int i = 0;i < days;i++ ){
			if(n<size){
			cal.set(weatherList.get(n).getYear(),weatherList.get(n).getMonth()-1,weatherList.get(n).getDay(),weatherList.get(n).getHour(),0,0);
			}
			
			
			
			calStr = formater.format(cal.getTime()).toString();	
			firstDay = formater.format(calone.getTime()).toString();
			
			
			if(calStr.equals(firstDay)){
				finalList.addAll(weatherList.get(n).getMinutes());
				n++;
				calone.add(Calendar.HOUR, +1);
			}else{
				finalList.addAll(nullList);
				calone.add(Calendar.HOUR, +1);
			}
			
		}
			
		
		return finalList;
	}
	
	public static float sum(String[] summList){
		float sum = 0;
	
		for (String str:summList){
			
			sum+= Float.valueOf(str);
		}
		return sum;
		
	}
	
	
	public static List<String> getsumList(List<String> finalList,int h){
		String[] sumList1=new String[h];
		List<String> sumList=new ArrayList<String>();
		int aaa =finalList.size()-h+1;
		float summ  = 0;
		String sum = "";
		for(int i=0; i<aaa;i++){
			for(int j = i; j<i+h;j++){
				if((finalList.get(j).equals("///"))==false ){
					sumList1[j-i]=finalList.get(j);
				}else{
					sumList.add("///");
					break;
				}
				
			}
			if((finalList.get(i).equals("///"))==false ){
				summ = sum(sumList1);
				sum= String.valueOf(summ);
				sumList.add(sum);
			}
				
		}
		
		
		
		return sumList;
		
	}
	
	public static List<String> nullList(){
		List<String> nulllist=new ArrayList<String>();
		for(int i = 0;i< 60;i++){
			nulllist.add("///");
		}
		
		return nulllist;
		
	}
	
	
	 
	public static void main (String[] args)throws IOException  {
		
		int year =0,size = 0;
		String firstDay = ""; 
		Calendar calone = Calendar.getInstance();
		SimpleDateFormat formater= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		
		List<String> finalList = new ArrayList<String>();
		List<String> sumList5= new ArrayList<String>();
		List<String> sumList10= new ArrayList<String>();
		List<String> sumList15= new ArrayList<String>();
		List<String> sumList20= new ArrayList<String>();
		List<String> sumList30= new ArrayList<String>();
		List<String> sumList45= new ArrayList<String>();
		List<String> sumList60= new ArrayList<String>();
		List<String> sumList90= new ArrayList<String>();
		List<String> sumList120= new ArrayList<String>();
		List<String> sumList150= new ArrayList<String>();
		List<String> sumList180= new ArrayList<String>();
		List<Weather>  weatherList =new ArrayList<Weather>();
		
		
		
		for (year =2005; year <= 2009 ;year ++){
			try{
				weatherList=QueryList(year);
				System.out.println(weatherList.get(1730));
                
			}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (weatherList.size()!= 0){
				
				finalList = getFinalList(weatherList,nullList());
				

				sumList5 = getsumList(finalList,5);
				sumList10= getsumList(finalList,10);
				sumList15=getsumList(finalList,15);
				sumList20= getsumList(finalList,20);
				sumList30= getsumList(finalList,30);
				sumList45= getsumList(finalList,45);
				sumList60=getsumList(finalList,60);
				sumList90= getsumList(finalList,90);
				sumList120= getsumList(finalList,120);
				sumList150= getsumList(finalList,150);
				sumList180= getsumList(finalList,180);
				List<Integer> dlist = new ArrayList<Integer>();
				dlist.add(sumList5.size());
				dlist.add(sumList10.size());
				dlist.add(sumList15.size());
				dlist.add(sumList20.size());
				dlist.add(sumList30.size());
				dlist.add(sumList45.size());
				dlist.add(sumList60.size());
				dlist.add(sumList90.size());
				dlist.add(sumList120.size());
				dlist.add(sumList150.size());
				dlist.add(sumList180.size());
				size = Collections.min(dlist);
				
				
				if(weatherList.get(0).getMonth()==12){
					calone.set(weatherList.get(0).getYear(),11,31,20,1,0);
				}else{
					calone.set(weatherList.get(0).getYear()-1,11,31,20,1,0);
				}
				
				
				
			
				FileWriter fileWriter=new FileWriter("d:\\Result"+year+".txt");
				  for (int i = 0; i < size ; i++) {
				firstDay = formater.format(calone.getTime()).toString();
				   fileWriter.write(firstDay+"\t"+sumList5.get(i)+"\t"+sumList10.get(i)+"\t"+sumList15.get(i)+"\t"+sumList20.get(i)+"\t"+
				  sumList30.get(i)+"\t"+sumList45.get(i)+"\t"+sumList60.get(i)+"\t"+sumList90.get(i)+"\t"+sumList120.get(i)+"\t"+
				  sumList150.get(i)+"\t"+sumList180.get(i)+"\r\n");
				   
				   calone.add(Calendar.MINUTE, +1); 
				
				  }
				  fileWriter.flush();
				  fileWriter.close();
//				int aaa = finalList.size();
//				for(int k = 0; k<aaa-5;k++){
//					if(k%60==0){
//						System.out.println("");
//						System.out.print(sumList.get(k)+"\t");
//					}else{
//						System.out.print(sumList.get(k)+"\t");
//					}
//						
//				}
				weatherList.clear();
				finalList.clear();
				sumList5.clear();
				sumList10.clear();
				sumList15.clear();
				sumList20.clear();
				sumList30.clear();
				sumList45.clear();
				sumList60.clear();
				sumList90.clear();
				sumList120.clear();
				sumList150.clear();
				sumList180.clear();
				
				}else{
					continue;
				}
			
			 
		}
		
		
		
	}
	
	
}
