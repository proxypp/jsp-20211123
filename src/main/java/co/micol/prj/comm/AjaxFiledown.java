package co.micol.prj.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxFiledown")
public class AjaxFiledown extends HttpServlet {  		//파일 다운로드 할때.
	private static final long serialVersionUID = 1L;

    public AjaxFiledown() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//파일 다운로드
				request.setCharacterEncoding("utf-8");
				String fileName = request.getParameter("fileName");
				String pfileName = request.getParameter("pfileName");
				
				InputStream in = null;
				OutputStream os = null;
				
				File file = null;
				
				try {
					
					file = new File(pfileName);  //물리위치에서 파일을 선택하고
					in = new FileInputStream(file);	// 읽어 들임.
					fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");  //한글 파일명 처리.
					response.setContentType("text/html; charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment;filename="+fileName);
					os = response.getOutputStream(); //response 객체로 초기화
					byte b[] = new byte[(int)file.length()]; //메모리에 파일을 담음
					int leng = 0;
					while((leng = in.read(b))>0) {   //실제 다운로드함.
						os.write(b, 0, leng);
					}
					
					in.close();		//반드시 닫아 줘야 한다.
					os.flush();
					os.close();	//반드시 닫아줘야 한다.	
				} catch (Exception e) {
					e.printStackTrace();
			}
	}

}
