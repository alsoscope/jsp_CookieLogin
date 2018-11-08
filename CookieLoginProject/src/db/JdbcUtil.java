package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//데이터베이스 작업시 반복적으로 사용하는 기능을 정의
public class JdbcUtil {
	public static Connection getConnection(){
		Connection con=null;
		try{
			Context initCtx=new InitialContext(); //현재 환경의 naming Context 획득하기
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			//InitialContext()는 웹 어플리케이션이 처음으로 배치될 때 모든 설정된 엔트리와 자원은 JNDI namespace의
			//java:comp/env 부분에 놓이게 된다
			//그래서 자원 접근을 Context ct=new InitialContext(); 이렇게 한다
			
			//Context envCt=(Context)ct.lookup("java:comp/env");
			//JDNI에 등록된 생성할 자원 이름(즉, 컨텍스트에 대한 상대적인 이름)
			
			DataSource ds=(DataSource)envCtx.lookup("jdbc/mysql");
			//server.xml 또는 context.xml, web.xml 에서 설정해놓은 이름 "jdbc/mysql"
			
			//Context ct2=new InitialContext();
			//DataSource ds2=(DataSource)ct.lookup("java:comp/env/jdbc");
			//위 줄을 이렇게 쓸 수도 있음
			
			con=ds.getConnection();//Connection 얻기
			con.setAutoCommit(false);
			System.out.println("connect success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(Connection con){
		try{
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}//close()------
	
	public static void close(Statement stmt){
		try{
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}//close()------
	
	public static void close(ResultSet rs){
		try{
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}//close()------
	
	public static void commit(Connection con){
		try{
			con.commit();
			System.out.println("commit success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}//commit()------
	
	public static void rollback(Connection con){
		try{
			con.rollback();
			System.out.println("rollback success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}//rollback()------
}//JdbcUtil