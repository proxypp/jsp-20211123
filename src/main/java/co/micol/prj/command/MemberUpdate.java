package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.comm.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberUpdate implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//회원 정보 수정.
		MemberService memberDao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		vo.setId(request.getParameter("id"));
		
		vo = memberDao.memberSelect(vo);
		request.setAttribute("member", vo);
		return "member/memberUpdate";
	}

}
