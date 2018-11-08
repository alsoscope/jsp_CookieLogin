package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import svc.LoginService;
import vo.Member;

//"/login" URL�� �����ϴ� ���� ������
//�α��� ��û�� ó���ϴ�(��û�� �޴�) ���� ������
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID=1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
@Override
//<jsp:forward/>,�׼� �±׿� ���� ��û�� ������ �� GET������� �Ѿ���� ������ doGet�޼ҵ� ����
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	Cookie[] cookieArray=request.getCookies(); //Ŭ���̾�Ʈ���� ���۵� ��Ű ��ü ����. �迭�� ��ȯ.
	String id="";//id, passwd ����� ���� ����
	String passwd="";
	
	if(cookieArray!=null){
		for(int i=0; i<cookieArray.length; i++){//��Ű�迭�� ��Ű ��ü�̸� ��
			if(cookieArray[i].getName().equals("id")){
				id=cookieArray[i].getValue();
			}else if(cookieArray[i].getName().equals("passwd")){
				passwd=cookieArray[i].getValue();
			}
		}
	
	LoginService loginService=new LoginService();//�α��� ����Ͻ� ������ �����Ǿ��ִ� LoginService ��ü�� ����
	Member loginMember=loginService.getLoginMember(id, passwd);//LoginServiceŬ������ ���ǵǾ� �ִ� getLoginMember �޼ҵ带 ȣ���Ͽ� �α��ο� ������ ������� ������
	//Member ��üŸ������ ����. getLoginMember �޼ҵ忡���� ����ڰ� �α��ο� �����ϸ� �α��ο� ������ ������� ������ Member ��ü�� �Ӽ������� �����Ͽ� ��ȯ, �α��� �����ϸ� null ��ȯ
	
	//�α��� ����=����� �ý����� ��Ű ������ �α��ο� ������ id, passwd ����������� request ������ �α��ο� ������ ������� ������ Member��üŸ������ ���� loginSuccess.jsp ������
	//loginSuccess.jsp ������������ request ������ ������ �Ӽ� ���� �� �α��ο� ������ ������� ������ ȭ�鿡 ���
	if(loginMember!=null){
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginSuccess.jsp");
		request.setAttribute("loginMember", loginMember);
		dispatcher.forward(request, response); 
	}else{//�α��ο� �����ϸ� loginForm.html�� ������. ����Ʈ ���ӽ� ������� �ý��� ��Ű�� ���� �α��� ������ ���� ���.
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginForm.html");
		dispatcher.forward(request, response);
	}
	
	}
}//doGet

//loginForm.html���� �α��� ��û�ϸ� POST������� ���۵Ǳ⿡ doPost
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	String id=request.getParameter("id");//Ŭ���̾�Ʈ���� ���۵� �� ���� �� ������ �Ҵ�
	String passwd=request.getParameter("passwd");
	String useCookie=request.getParameter("useCookie");//loginForm.html���� ��Ű ��� üũ�ڽ� ��뿩�� �Ǵ�. üũ������ on���ڿ� ����, �ƴϸ� null�� ����
	LoginService loginService=new LoginService();
	Member loginMember=loginService.getLoginMember(id, passwd);//����ڰ� �Է��� ������ �̿��� �α��ε� ����� ������ ��û�ϴ� �κ�
	
	if(useCookie!=null){//��Ű���üũ�ڽ� üũ�� ��� Ŭ���̾�Ʈ���� ���۵� ������ ��Ű ������ ����
		Cookie idCookie=new Cookie("id", id);
		//��Ű�� �����ϸ� �⺻ �����Ⱓ�� -1�̴�.
		//�������� �������� ���� ��Ű�� ���������� �������� ������
		//��Ű�� �����
		idCookie.setMaxAge(60*60*24);//������ ��
		Cookie passwdCookie=new Cookie("passwd", passwd);
		passwdCookie.setMaxAge(60*60*24);
		response.addCookie(idCookie);//���信 ��Ű �߰�
		response.addCookie(passwdCookie);
	}
	
	if(loginMember!=null){
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginSuccess.jsp");
		request.setAttribute("loginMember", loginMember);
		dispatcher.forward(request, response);
	}else{
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginFail.jsp");
		dispatcher.forward(request, response);
	}
}//doPost
}//LoginServlet