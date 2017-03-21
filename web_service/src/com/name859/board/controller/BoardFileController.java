package com.name859.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.name859.board.dao.BoardDao;
import com.name859.board.model.Board;
import com.name859.dao.DaoFactory;
import com.name859.util.Common;

public class BoardFileController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = URLEncoder.encode(request.getParameter("fn"), "UTF-8");
		
		String filePath = request.getSession().getServletContext().getRealPath("") +"\\board\\upload\\";
		String fileRename = request.getParameter("fr");
		File file = new File(filePath + fileRename);
		
		if (!file.exists()) throw new FileNotFoundException();
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="+ fileName +";");
			response.setContentLength((int)file.length());
			
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			
			byte[] data = new byte[1024 * 100];
			
			int read = 0;
			while ((read = bis.read(data)) != -1) {
				bos.write(data, 0, read);
			}
			bos.flush();
		} catch (Exception e) {
			System.out.println("@ Download Error : "+ e.getMessage());
		} finally {
			bos.close();
			bis.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		int uploadCount = 1;
		int fsm = 5;
		long fileSizeMax = 1024 * 1024 * fsm;
		int usm = fsm * uploadCount;
		long uploadSizeMax = 1024 * 1024 * usm;
		
		String uploadDir = request.getSession().getServletContext().getRealPath("") +"\\board\\upload\\";
		File tempDir = new File(uploadDir +"temp\\");
		if (!tempDir.exists()) tempDir.mkdirs();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(tempDir);
		factory.setSizeThreshold(1024 * 100);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setFileSizeMax(fileSizeMax);
		upload.setSizeMax(uploadSizeMax);
		
		Map<String, String> param = new HashMap<String, String>();
		boolean fileUpload = false;
		Common comm = new Common();
		PrintWriter pw = response.getWriter();
		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem item = (FileItem)iterator.next();
				if (item.isFormField()) {
					param.put(item.getFieldName(), item.getString("UTF-8"));
				} else if (item.getSize() > 0) {
					fileUpload = true;
					
					String boardFileRename = new Common().currentTime("yyMMddHHmmss") + (int)(Math.random() * 10000);
					
					param.put(item.getFieldName(), item.getName());
					param.put("boardFileRename", boardFileRename);
					param.put("boardFileSize", Long.toString(item.getSize()));
					
					item.write(new File(uploadDir + boardFileRename));
				}
			}
		} catch (Exception e) {
			System.out.println("@ Upload Error : "+ e.getMessage());
			
			if (e.toString().substring(e.toString().lastIndexOf("$") + 1, e.toString().lastIndexOf("$") + 1 + 30).equals("FileSizeLimitExceededException")) {
				comm.alert(pw, "파일은 "+ fsm +"MB 이하!", "javascript:history.go(-1)");
			} else if (e.toString().substring(e.toString().lastIndexOf("$") + 1, e.toString().lastIndexOf("$") + 1 + 26).equals("SizeLimitExceededException")) {
				comm.alert(pw, "파일은 총 "+ usm +"MB 이하!", "javascript:history.go(-1)");
			}
			pw.close();
			
			return;
		}
		
		String act = param.get("act");
		int boardId = Integer.parseInt(param.get("bid"));
		
		Board board = new Board();
		BoardDao boardDao = new DaoFactory().getBoardDao();
		board.setBoardNo(param.get("bno") == null ? 0 : Integer.parseInt(param.get("bno")));
		
		int searchPage = param.get("sp") == null ? 0 : Integer.parseInt(param.get("sp"));
		
		board.setBoardWriter(param.get("boardWriter"));
		board.setBoardPw(param.get("boardPw"));
		board.setBoardSubject(param.get("boardSubject"));
		board.setBoardContent(param.get("boardContent"));
		if (fileUpload == true) {
			board.setBoardFileName(param.get("boardFileName"));
			board.setBoardFileRename(param.get("boardFileRename"));
			board.setBoardFileSize(Long.parseLong(param.get("boardFileSize")));
		} else if (act.equals("mod")) {
			board.setBoardFileName(null);
			board.setBoardFileRename(null);
			board.setBoardFileSize(0);
			if (param.get("oldBoardFileName") != null && param.get("opt").equals("fv")) {
				board.setBoardFileName(param.get("oldBoardFileName"));
				board.setBoardFileRename(param.get("oldBoardFileRename"));
				board.setBoardFileSize(Long.parseLong(param.get("oldBoardFileSize")));
			}
		}
		board.setBoardIp(request.getRemoteAddr());
		
		if (act.equals("add")) {
			board.setBoardId(boardId);
			board.setUserNo(Integer.parseInt(param.get("uno")));
			
			boardDao.boardAdd(board);
			
			comm.alert(pw, "게시판 글 등록!", "board.do?act=list&bid="+ boardId +"&sopt=bs&sp=1");
		} else if (act.equals("mod")) {
			if (param.get("oldBoardFileName") != null && param.get("opt").equals("fu")) new File(request.getSession().getServletContext().getRealPath("") +"\\board\\upload\\"+ param.get("oldBoardFileRename")).delete();
			
			boardDao.boardModify(board);
			
			comm.alert(pw, "게시판 글 수정!", "board.do?act=read&opt=read&bid="+ boardId +"&bno="+ board.getBoardNo() +"&sp="+ searchPage);
		}
		
		pw.close();
	}
	
}
