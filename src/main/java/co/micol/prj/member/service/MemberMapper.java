package co.micol.prj.member.service;

import java.util.List;

public interface MemberMapper {
	List<MemberVO> memberSelectList();
	MemberVO memberSelect(MemberVO vo);
	int memberInsert(MemberVO vo);
	int memberUpdate(MemberVO vo);
	int memberDelete(MemberVO vo);
	boolean memberIdCheck(MemberVO vo);
	int memberAuthorUpdate(MemberVO vo); //멤버 권한 변경.
}
