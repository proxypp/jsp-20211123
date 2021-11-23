package co.micol.prj.comm;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.command.AjaxAuthorUpdate;
//import co.micol.prj.command.AjaxFileDownLoad;
import co.micol.prj.command.CommonFileUpload;
import co.micol.prj.command.HomeCommand;
import co.micol.prj.command.Logout;
import co.micol.prj.command.MemberDelete;
import co.micol.prj.command.MemberEditSave;
import co.micol.prj.command.MemberIdCheck;
import co.micol.prj.command.MemberInfo;
import co.micol.prj.command.MemberJoin;
import co.micol.prj.command.MemberJoinForm;
import co.micol.prj.command.MemberList;
import co.micol.prj.command.MemberLogin;
import co.micol.prj.command.MemberLoginForm;
import co.micol.prj.command.MemberUpdate;
import co.micol.prj.command.NoticeForm;
import co.micol.prj.command.NoticeList;
import co.micol.prj.command.NoticeRead;
//import co.micol.prj.command.NoticeResister;
//import co.micol.prj.command.ServletApiUpload;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Command> map = new HashMap<String, Command>();

	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		// Command들을 요청에 따라 처리할 수 있도록 메모리에 구성한다.
		map.put("/home.do", new HomeCommand()); // 홈 페이지
		map.put("/memberLoginForm.do", new MemberLoginForm()); // 로그인 폼 호출
		map.put("/memberLogin.do", new MemberLogin()); // 로그인 처리.
		map.put("/logout.do", new Logout()); // 로그아웃.
		map.put("/memberList.do", new MemberList()); // 멤버 목록.
		map.put("/memberJoinForm.do", new MemberJoinForm()); // 회원가입 폼 호출.
		map.put("/ajaxIdCheck.do", new MemberIdCheck()); // 아이디 중복 체크.
		map.put("/memberJoin.do", new MemberJoin()); //회원가입 처리.
		map.put("/memberInfo.do", new MemberInfo()); //회원 정보 처리.
		map.put("/memberUpdate.do", new MemberUpdate()); //회원 정보 수정
		map.put("/memberEditSave.do", new MemberEditSave()); //회원 정보 저장.
		map.put("/memberDelete.do", new MemberDelete()); // 회원 정보 삭제.
		map.put("/ajaxAuthorUpdate.do", new AjaxAuthorUpdate());	 //회원권한 변경
		map.put("/noticeForm.do", new NoticeForm()); //공지사항 폼 호출.
		map.put("/noticeList.do", new NoticeList()); //공지사항 목록보기.
		//map.put("/noticeResister.do", new ServletApiUpload()); //공지사항 저장.  servlet Part
		//map.put("/noticeResister.do", new NoticeResister()); //공지사항 저장. cos.jar
		map.put("/noticeResister.do", new CommonFileUpload()); //공지사항 저장. common-fileupload
		map.put("/noticeRead.do", new NoticeRead());
		//map.put("/ajaxFileDownLoad.do", new AjaxFileDownLoad()); //파일 다운로드
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청을 분석 실행할 명령을 찾아 수행하고 결과를 돌려주는 메소드.
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring(contextPath.length());

		Command command = map.get(page); // new HomeCommand();
		String viewPage = command.run(request, response);

		if (!viewPage.endsWith(".do")) {
			if (viewPage.startsWith("ajax:")) { //ajax 처리
				response.setContentType("text/html; charset=UTF-8");
//				response.getWriter().append(viewPage.substring(5)); //호출한 페이지로 돌려보내라.
				response.getOutputStream().print(viewPage.substring(5));
				response.getOutputStream().flush();
				response.getOutputStream().close();
				return; //리턴하면 밑으로 안내려간다.
			} else {
				viewPage = "WEB-INF/views/" + viewPage + ".jsp";
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);

	}
}
