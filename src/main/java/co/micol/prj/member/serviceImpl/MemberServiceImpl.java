package co.micol.prj.member.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.micol.prj.comm.DataSource;
import co.micol.prj.member.service.MemberMapper;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;

public class MemberServiceImpl implements MemberService {
	private SqlSession sqlSession = DataSource.getDataSource().openSession(true);
	private MemberMapper map = sqlSession.getMapper(MemberMapper.class);
	
	@Override
	public List<MemberVO> memberSelectList() {
		//전체 리스트 보기.
		
		return map.memberSelectList();
	}

	@Override
	public MemberVO memberSelect(MemberVO vo) {
		//로그인 처리 또는 한명의 데이터 서치.
		return map.memberSelect(vo);
	}

	@Override
	public int memberInsert(MemberVO vo) {
		
		return map.memberInsert(vo);
	}

	@Override
	public int memberUpdate(MemberVO vo) {

		return map.memberUpdate(vo);
	}

	@Override
	public int memberDelete(MemberVO vo) {
		return map.memberDelete(vo);
	}

	@Override
	public boolean memberIdCheck(MemberVO vo) {
		return map.memberIdCheck(vo);
	}

	@Override
	public int memberAuthorUpdate(MemberVO vo) {
		// TODO Auto-generated method stub
		return map.memberAuthorUpdate(vo);
	}
}
