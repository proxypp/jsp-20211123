package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.comm.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberIdCheck implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//아이디 중복 체크(ajax 이용)
		MemberService memberDao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		
		vo.setId(request.getParameter("chkid"));
		System.out.println(request.getParameter("chkid"));
		boolean b = memberDao.memberIdCheck(vo);  //true 존재, false 사용가능. 반대가 되야함.
		String chk = "0";
		if(b) { //true존재하면 0, false존재하지않으면 1
			chk= "1";
		}
		return "ajax:"+chk;
	}

}
