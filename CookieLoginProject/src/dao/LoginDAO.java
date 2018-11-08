package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vo.Member;
import static db.JdbcUtil.*;

//데이터 베이스에 로그인관련 SQL구문 전송하는 파일
public class LoginDAO {
	private static LoginDAO loginDAO; //LoginDAO 타입의 레퍼런스 변수 선언
	private Connection con;
	
	private LoginDAO(){
		
	}//constructer
	
	public static LoginDAO getInstance(){
		if(loginDAO==null){//외부클래스에서 getInstance메소드를 처음 호출할 때 loginDAO 값이 널일 때 LoginDAO 객체생성, 두 번째 호출부터는 처음 생성된 객체의 레퍼런스 값 반환
			loginDAO=new LoginDAO();//객체를 힙 영역에 하나 생성 후 공유하는 싱글톤 패턴. 주로 클래스에 메소드만 정의되어 있고 객체마다 다른 속성 값을 유지할 필요가 없는 클래스에 사용
		}
		return loginDAO;
	}
	
	public void setConnection(Connection con){
		this.con=con;
	}
	
	//사용자가 입력한 아이디와 비밀번호를 사용하여 로그인 처리를 수행, 로그인 성공하면 사용자의 정보를 Member 객체에 저장하여 반환, 로그인 실패하면 null 반환하는 메소드
	public Member selectLoginMember(String id, String passwd){
		Member loginMember=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
			pstmt=con.prepareStatement("SELECT * FROM users WHERE id=? AND passwd=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs=pstmt.executeQuery();
			if(rs.next()){//위에서 조회하는 회원 정보를 Member 객체의 속성 값으로 설정. 로그인 정보가 동일한 회원은 존재하거나 아니거나 둘 중 하나기에 if문 사용하여 반복 while로 여러번 반복 필요 없다
				loginMember=new Member();
				loginMember.setAddr(rs.getString("addr"));
				loginMember.setAge(rs.getInt("age"));
				loginMember.setEmail(rs.getString("email"));
				loginMember.setGender(rs.getString("gender"));
				loginMember.setId(rs.getString("id"));
				loginMember.setName(rs.getString("name"));
				loginMember.setNation(rs.getString("nation"));
				loginMember.setPasswd(rs.getString("passwd"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				close(rs);
				close(pstmt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return loginMember;
	}//selectLoginMember
}//LoginDAO
