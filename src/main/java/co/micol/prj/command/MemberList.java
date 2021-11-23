package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.comm.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberList implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//멤버 리스트
		MemberService memberDao = new MemberServiceImpl();
		request.setAttribute("members", memberDao.memberSelectList());	//지가 알아서 형변환 되서 들어간다. //배열도 형변환됨
		//request에 있는 "members"는 memberList.jsp에 가면 members로 사용할 수 있다.
		
		return "member/memberList";
	}

}
