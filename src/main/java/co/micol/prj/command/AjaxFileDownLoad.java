package co.micol.prj.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.comm.Command;

public class AjaxFileDownLoad implements Command {		//지금 이방식 안씀... OUTPUTSTREAM과 GET WRITER의 충돌 때문에

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//파일 다운로드
		String fileName = request.getParameter("fileName");
		String pfileName = request.getParameter("pfileName");

		InputStream in = null;
		OutputStream out = null;
		
		
		File file = null;
		try {
			file = new File(pfileName);  //물리위치에서 파일을 선택하고
			in = new FileInputStream(file);	// 읽어 들임.
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");  //한글 파일명 처리.
			
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			out = response.getOutputStream(); //response 객체로 초기화
			byte b[] = new byte[(int)file.length()]; //메모리에 파일을 담음
			int leng = 0;
			while((leng = in.read(b))>0) {   //실제 다운로드함.
				out.write(b, 0, leng);
			}
			in.close();		//반드시 닫아 줘야 한다.
			out.flush();
			out.close();	//반드시 닫아줘야 한다.	
			request.setAttribute("message", "파일이 성공적으로 다운로드 되었다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice/noticeFileDownLoad";
	}
}