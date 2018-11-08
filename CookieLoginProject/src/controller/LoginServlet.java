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

//"/login" URL을 매핑하는 서블릿 페이지
//로그인 요청을 처리하는(요청을 받는) 서블릿 페이지
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID=1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
@Override
//<jsp:forward/>,액션 태그에 의해 요청이 들어왔을 때 GET방식으로 넘어오기 때문에 doGet메소드 실행
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	Cookie[] cookieArray=request.getCookies(); //클라이언트에서 전송된 쿠키 객체 얻어옴. 배열로 반환.
	String id="";//id, passwd 저장될 변수 선언
	String passwd="";
	
	if(cookieArray!=null){
		for(int i=0; i<cookieArray.length; i++){//쿠키배열에 쿠키 객체이름 비교
			if(cookieArray[i].getName().equals("id")){
				id=cookieArray[i].getValue();
			}else if(cookieArray[i].getName().equals("passwd")){
				passwd=cookieArray[i].getValue();
			}
		}
	
	LoginService loginService=new LoginService();//로그인 비즈니스 로직이 구현되어있는 LoginService 객체를 생성
	Member loginMember=loginService.getLoginMember(id, passwd);//LoginService클래스에 정의되어 있는 getLoginMember 메소드를 호출하여 로그인에 성공한 사용자의 정보를
	//Member 객체타입으로 얻어옴. getLoginMember 메소드에서는 사용자가 로그인에 성공하면 로그인에 성공한 사용자의 정보를 Member 객체의 속성값으로 설정하여 반환, 로그인 실패하면 null 반환
	
	//로그인 성공=사용자 시스템의 쿠키 정보에 로그인에 성공한 id, passwd 저장돼있으면 request 영역에 로그인에 성공한 사용자의 정보를 Member객체타입으로 공유 loginSuccess.jsp 포워딩
	//loginSuccess.jsp 페이지에서는 request 영역에 공유된 속성 값을 얻어서 로그인에 성공한 사용자의 정보를 화면에 출력
	if(loginMember!=null){
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginSuccess.jsp");
		request.setAttribute("loginMember", loginMember);
		dispatcher.forward(request, response); 
	}else{//로그인에 실패하면 loginForm.html로 포워딩. 사이트 접속시 사용자의 시스템 쿠키에 이전 로그인 정보가 없을 경우.
		RequestDispatcher dispatcher=request.getRequestDispatcher("loginForm.html");
		dispatcher.forward(request, response);
	}
	
	}
}//doGet

//loginForm.html에서 로그인 요청하면 POST방식으로 전송되기에 doPost
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	String id=request.getParameter("id");//클라이언트에서 전송된 값 얻어와 각 변수에 할당
	String passwd=request.getParameter("passwd");
	String useCookie=request.getParameter("useCookie");//loginForm.html에서 쿠키 사용 체크박스 사용여부 판단. 체크했을시 on문자열 전송, 아니면 null값 전송
	LoginService loginService=new LoginService();
	Member loginMember=loginService.getLoginMember(id, passwd);//사용자가 입력한 정보를 이용해 로그인된 사용자 정보를 요청하는 부분
	
	if(useCookie!=null){//쿠키사용체크박스 체크한 경우 클라이언트에서 전송된 정보를 쿠키 정보로 저장
		Cookie idCookie=new Cookie("id", id);
		//쿠키를 생성하면 기본 생존기간이 -1이다.
		//브라우저가 실행중일 때는 쿠키가 생존하지만 브라우저를 닫으면
		//쿠키가 사라짐
		idCookie.setMaxAge(60*60*24);//단위는 초
		Cookie passwdCookie=new Cookie("passwd", passwd);
		passwdCookie.setMaxAge(60*60*24);
		response.addCookie(idCookie);//응답에 쿠키 추가
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