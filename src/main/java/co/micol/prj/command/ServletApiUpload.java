/*
 * File UpLoad
 * Servlet 3.0 이상에서 사용가능.
 * Servlet 개체가 제공하는 Part Class를 사용함
 *  
 */
package co.micol.prj.command;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import javax.servlet.http.Part;
import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.comm.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;
//@MultipartConfig(			//선언하지 않아도 무방함.
//		location ="c:\\FileTest",  //임시 저장소.
//		maxFileSize = -1,			//업로드 파일사이즈 제한을 두지 않음.
//		maxRequestSize= -1,			//전체 multipart 사이즈 제한 두지 않음.
//		fileSizeThreshold = 1024)	//임시 저장시 byte 단위로 저장.
		
public class ServletApiUpload implements Command {
	private String attechDir = "c:\\FileTest"; //실제 저장할 장소
	private String fileName;
	
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//공지사항저장
		NoticeService noticeDao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		HttpSession session = request.getSession();
		vo.setId((String) session.getAttribute("id")); //세션에 저장된 아이디 값.
		vo.setName((String) session.getAttribute("name")); //세션에 저장된 이름 값.
		
		String contentType = request.getContentType(); // 콘텐츠 타입을 가져옴.
		if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			try {
				Collection<Part> parts = request.getParts(); // 폼에서 넘어온 데이터를 각각 Part 객체에 담음.
				for (Part part : parts) {
					if (part.getHeader("Content-Disposition").contains("filename=")) {
						String partHeader = part.getHeader("Content-Disposition");
						for (String str : partHeader.split(";")) {
							if (str.trim().startsWith("filename")) {  //파일명찾음
								fileName = str.substring(str.indexOf("=")+1).trim().replace("\"", "");
								int index = fileName.indexOf(File.separator);
								fileName = fileName.substring(index+1);
								System.out.println(fileName);
							}
						}
						if (part.getSize()>0) {
							part.write(attechDir+File.separator+fileName); // 파일저장.
							part.delete(); //임시저장된 파일 삭제.
						}
					}
				}				
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		} //파일 업로드
		//이곳에 데이터 처리.request.getParameter("id")형식으로 vo 객체에 담아서 처리.
		vo.setFileName(fileName);  //원본 파일 넣기.
		vo.setPfileName(attechDir+File.separator+fileName); //물리 파일명 넣기.
		vo.setWdate(Date.valueOf(request.getParameter("wdate")));  //문자를 날짜로 변경시켜줌. 왜냐하면 form에서 넘어오는 것은 다 문자형으로 넘어온다. 그걸 날짜 형태로 바꿔줘야함.
		vo.setTitle(request.getParameter("title"));
		vo.setSubject(request.getParameter("subject"));
		noticeDao.noticeInsert(vo);
		
		return "noticeList.do";
	}

}
