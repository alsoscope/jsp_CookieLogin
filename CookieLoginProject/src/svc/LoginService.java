package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import dao.LoginDAO;
import vo.Member;

//�α��� ����Ͻ� ������ ó��
public class LoginService {
	public Member getLoginMember(String id, String passwd){
		LoginDAO loginDAO=LoginDAO.getInstance();//LoginDAO Ŭ������ ���ǵǾ� �ִ� getInstance()�޼ҵ带 ȣ���Ͽ� LoginDAO��ü�� �����ϴ� ���۷��� �� ����
		//LoginDAO Ŭ���� ��ü�� getInstance() �޼ҵ尡 ó�� ȣ���� ���� �����ϰ� �� ��° ȣ���� �� ���ʹ� �� ������ �̹� �����Ǿ��ִ� ��ü�� ���۷��� ���� ��ȯ, singleton pattern
		Connection con=getConnection(); 
		loginDAO.setConnection(con);//getConnection ����
		Member loginMember=loginDAO.selectLoginMember(id, passwd);//LoginDAO ��ü�� �α����� ������� ������ Member��ü�� ��ȯ�ϴ� �޼ҵ�(selectLoginmember) ȣ��
		close(con);
		return loginMember;//�α��ε� ������� ������ ��ȯ
	}
}//LoginService
