package co.micol.prj.member.service;

import java.util.List;

public interface MemberService { //Service에서 마이바티스를 상속 받아도 되지만 달라질수 있기 때문에 Mapper를 만들어준다. 
	List<MemberVO> memberSelectList();
	MemberVO memberSelect(MemberVO vo); // login, 1명의데이터 가져오기.
	int memberInsert(MemberVO vo);
	int memberUpdate(MemberVO vo);
	int memberDelete(MemberVO vo);
	boolean memberIdCheck(MemberVO vo); //아이디 중복 체크하기 위해서 만든다.
	int memberAuthorUpdate(MemberVO vo); //멤버 권한 변경.
}
