package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vo.Member;
import static db.JdbcUtil.*;

//������ ���̽��� �α��ΰ��� SQL���� �����ϴ� ����
public class LoginDAO {
	private static LoginDAO loginDAO; //LoginDAO Ÿ���� ���۷��� ���� ����
	private Connection con;
	
	private LoginDAO(){
		
	}//constructer
	
	public static LoginDAO getInstance(){
		if(loginDAO==null){//�ܺ�Ŭ�������� getInstance�޼ҵ带 ó�� ȣ���� �� loginDAO ���� ���� �� LoginDAO ��ü����, �� ��° ȣ����ʹ� ó�� ������ ��ü�� ���۷��� �� ��ȯ
			loginDAO=new LoginDAO();//��ü�� �� ������ �ϳ� ���� �� �����ϴ� �̱��� ����. �ַ� Ŭ������ �޼ҵ常 ���ǵǾ� �ְ� ��ü���� �ٸ� �Ӽ� ���� ������ �ʿ䰡 ���� Ŭ������ ���
		}
		return loginDAO;
	}
	
	public void setConnection(Connection con){
		this.con=con;
	}
	
	//����ڰ� �Է��� ���̵�� ��й�ȣ�� ����Ͽ� �α��� ó���� ����, �α��� �����ϸ� ������� ������ Member ��ü�� �����Ͽ� ��ȯ, �α��� �����ϸ� null ��ȯ�ϴ� �޼ҵ�
	public Member selectLoginMember(String id, String passwd){
		Member loginMember=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
			pstmt=con.prepareStatement("SELECT * FROM users WHERE id=? AND passwd=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs=pstmt.executeQuery();
			if(rs.next()){//������ ��ȸ�ϴ� ȸ�� ������ Member ��ü�� �Ӽ� ������ ����. �α��� ������ ������ ȸ���� �����ϰų� �ƴϰų� �� �� �ϳ��⿡ if�� ����Ͽ� �ݺ� while�� ������ �ݺ� �ʿ� ����
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
