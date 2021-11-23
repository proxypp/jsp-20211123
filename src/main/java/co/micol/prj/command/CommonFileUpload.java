package co.micol.prj.command;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import co.micol.prj.comm.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class CommonFileUpload implements Command {
	//Apache common-fileUpload 라이브러리 사용.
	private String fileSave="c:\\FileTest"; //파일이 저장되는 절대 경로(개발용.)
	//private String fileSave="fileUpload"; //운영서버에서 실제 동작환경을 꾸밀 때.
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//공지사항저장
		HashMap<String, String> map = new HashMap<String, String>(); //데이터 저장 할 공간.
		NoticeService noticeDao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		HttpSession session = request.getSession();
		vo.setId((String) session.getAttribute("id")); //세션에 저장된 아이디 값.
		vo.setName((String) session.getAttribute("name")); //세션에 저장된 이름 값.
		String fileName = null;  //파일명.
		String pfileName = null; //물리 파일명.
		
		//File attechDir = new File(fileSave); //파일 객체 생성
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //파일 저장소 관련 정보.
		//fileItemFactory.setRepository(attechDir);//저장공간
		//fileItemFactory.setSizeThreshold(fileSize);//파일 최대 사이즈
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory); //request 객체 parse
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			//FileItem 객체는 폼에서 넘어온 multipart 객체 형식을 다루는 객체.
			for (FileItem item : items) {
				if (item.isFormField()) {
					map.put(item.getFieldName(),item.getString("utf-8")); //필드명과 데이터를 저장.
				}else if(!item.isFormField() && item.getSize()>0) {
					//폼에서 넘어온 타입이 일반 필드가 아니고 file 타입이고 사이즈가 0 보다 크면
					int index = item.getName().lastIndexOf(File.separator); //마지막 \의 위치.
					fileName = item.getName().substring(index+1); //실 파일명만 추출.
					String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length()); //파일 확인
					UUID uuid = UUID.randomUUID();			//고유한 UUID 생성.
					String newFileName = uuid.toString()+extension; //UUID를 통한 새로운 파일명으로 변환.
					pfileName = fileSave+File.separator+newFileName; // C:\\FileTest\파일명
					File uploadFile = new File(pfileName); // 파일을 열어서 읽고
					item.write(uploadFile);  //파일 업로드가 일어남.
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//이곳에 DB처리할 값을 넣어주는 곳
		vo.setFileName(fileName);  //원본 파일 넣기.
		vo.setPfileName(pfileName); //물리 파일명 넣기.
		vo.setWdate(Date.valueOf(map.get("wdate")));  //문자를 날짜로 변경시켜줌. 왜냐하면 form에서 넘어오는 것은 다 문자형으로 넘어온다. 그걸 날짜 형태로 바꿔줘야함.
		vo.setTitle(map.get("title"));
		vo.setSubject(map.get("subject"));
		noticeDao.noticeInsert(vo);
		
		return  "noticeList.do";
	}

}
