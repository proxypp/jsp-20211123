package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.comm.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberDelete implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//회원 탈퇴.
		HttpSession session = request.getSession();
		MemberService memberDao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		vo.setId(request.getParameter("id"));
		int n = memberDao.memberDelete(vo);
		if(n == 1) {
			session.invalidate(); //회원정보 삭제 후 세션 값도 삭제한다.
		}
		return "home.do";
	}

}
