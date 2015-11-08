package com.GL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;






public class DBconnect{
	public static int yearNum ;
	private static float[] summs;
	private static List<Weather> weatherList;
	
	public List<Weather> QueryList(int year) throws SQLException{
		
		weatherList =new ArrayList<Weather>();
		int i = 0,n=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM [aaaa].[dbo].[51628] " +
	"WHERE ([年] = '" +year+"' or([年]='" +(year-1)+"' AND [月]= '12' AND [日] = '31'AND[时]>='20'))"+
	"AND NOT([年]='" +year+"' AND [月]= '12' AND [日] = '31'AND[时]>='20')" +
	" ORDER BY [年], [月],[日],[时]";

		try {
			conn = DBconnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			}
		
		while(rs.next()){
			n++;	
		}
		yearNum = n;
		
		rs = pstmt.executeQuery();
		
		for (int i2 = 0; rs.next(); i2++) {
			Weather weather=new Weather();
			List<String> minutes=new ArrayList<String>();
			for (int j = 0; j < 65; j++) {
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
				minutes.add(rs.getString(j+1));
			}
			weather.setMinutes(minutes);
			
			weatherList.add(weather);
		}
		//System.out.println(weatherList.toString());
		
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
		
		

	public static String[][] Query(int year) throws SQLException{
		int i = 0,n=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM [aaaa].[dbo].[51628] " +
"WHERE ([年] = '" +year+"' or([年]='" +(year-1)+"' AND [月]= '12' AND [日] = '31'AND[时]>='20'))"+
"AND NOT([年]='" +year+"' AND [月]= '12' AND [日] = '31'AND[时]>='20')" +
" ORDER BY [年], [月],[日],[时]";

		try {
			conn = DBconnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			}
		
		while(rs.next()){
			n++;	
		}
		yearNum = n;
		String[][] arr = new String[n][65];
		
		rs = pstmt.executeQuery();
		
		for (int i2 = 0; rs.next(); i2++) {
			for (int j = 0; j < 65; j++) {
				arr[i2][j] = rs.getString(j+1);
			}
		}
		
		for (int i1 = 0; i1 < arr.length; i1++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i1][j] + "\t");
			}
			System.out.println();
			}	
		return arr;
	}
	public static float[] getsumm(List<Weather> weatherList1, int i,int j){
		float [] getsumm = new float[5];
		float tmp = 0;
		int index = 0;
		if (j <= 61){
		for(int k = j;k<j+5;k++,index++){
			if(k<=64)tmp = Float.parseFloat(weatherList.get(i).getMinutes().get(k));
			getsumm[index] = tmp;
		}
		
		}
		return getsumm;
		
	}	
	
//	public static float[] getsumm(String[][]arrs, int i,int j)
//	{
//		float [] getsumm = new float[5];
//		float tmp = 0;
//		int index = 0;
//		if (j <= 61){
//		for(int k = j;k<j+5;k++,index++){
//			if(k<=64)tmp = Float.parseFloat(arrs[i][k]);
//			getsumm[index] = tmp;
//		}
//		}else
//		{
//			if (j==61){
//				
//				
//			}
//		}
//		return getsumm;
//		
//	}

	public static float sum(float[]summ){
		float sum = 0;
		for (int i=0; i<summ.length; i++){
			
			sum+= summ[i];
			
		}
		return sum;	
	}

	public static void main (String[] args) {
		int year = 1971;
		float sum = 0,tmp = 0;
		
		float[]summs = new float[5];
	
		for (year =2008; year <= 2010 ;year ++){			
			try{
				
				List<weather>weatherList=QueryList(year);
				
				
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		if (weatherList.size()!= 0){
			for (int i=0;i < yearNum; i++){
				for(int j=6; j<= 65; j++){
						summs =getsumm(weatherList,i,j);
						sum = sum(summs);
						System.out.println(sum);
						
	
						}
					}
						
					
				}else{
		continue;
		}
		
	}
	
	}


}

