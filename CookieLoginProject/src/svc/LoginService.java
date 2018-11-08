package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import dao.LoginDAO;
import vo.Member;

//로그인 비즈니스 로직을 처리
public class LoginService {
	public Member getLoginMember(String id, String passwd){
		LoginDAO loginDAO=LoginDAO.getInstance();//LoginDAO 클래스에 정의되어 있는 getInstance()메소드를 호출하여 LoginDAO객체를 참조하는 레퍼런스 값 얻어옴
		//LoginDAO 클래스 객체는 getInstance() 메소드가 처음 호출할 때만 생성하고 두 번째 호출할 때 부터는 힙 영역에 이미 생성되어있는 객체의 레퍼런스 값을 반환, singleton pattern
		Connection con=getConnection(); 
		loginDAO.setConnection(con);//getConnection 주입
		Member loginMember=loginDAO.selectLoginMember(id, passwd);//LoginDAO 객체로 로그인한 사용자의 정보를 Member객체로 반환하는 메소드(selectLoginmember) 호출
		close(con);
		return loginMember;//로그인된 사용자의 정보를 반환
	}
}//LoginService
