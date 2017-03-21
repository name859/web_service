package com.name859.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.name859.board.dao.BoardDao;
import com.name859.board.dao.CommentDao;
import com.name859.board.model.Board;
import com.name859.board.model.BoardConfig;
import com.name859.board.model.Comment;
import com.name859.dao.DaoFactory;
import com.name859.util.Common;

public class BoardController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		String act = request.getParameter("act");
		int boardId = Integer.parseInt(request.getParameter("bid"));
		
		Board board = new Board();
		BoardDao boardDao = new DaoFactory().getBoardDao();
		board.setBoardNo(request.getParameter("bno") == null ? 0 : Integer.parseInt(request.getParameter("bno")));
		
		int searchPage = request.getParameter("sp") == null ? 0 : Integer.parseInt(request.getParameter("sp"));
		
		BoardConfig boardConfig = new BoardConfig();
		boardConfig.setBoardId(boardId);
		boardConfig = new DaoFactory().getBoardConfigDao().boardConfigSearch(boardConfig);
		
		request.setAttribute("boardId", boardId);
		request.setAttribute("boardConfig", boardConfig);
		
		Comment comment = new Comment();
		CommentDao commentDao = new DaoFactory().getCommentDao();
		Common comm = new Common();
		RequestDispatcher rd = null;
		PrintWriter pw = response.getWriter();
		if (act.equals("addfm")) {
			rd = request.getRequestDispatcher("/board/board_add_form.jsp");
			rd.forward(request, response);
		} else if (act.equals("read")) {
			String opt = request.getParameter("opt");
			request.setAttribute("opt", opt);
			
			if (opt.equals("read")) boardDao.boardHitPlus(board);
			
			board = boardDao.boardSearch(board);
			
			int commentNo = request.getParameter("cno") == null ? 0 : Integer.parseInt(request.getParameter("cno"));
			
			comment.setBoardNo(board.getBoardNo());
			List<Comment> searchList = commentDao.commentSearchList(comment);
			
			rd = request.getRequestDispatcher("/board/board_read.jsp");
			request.setAttribute("board", board);
			request.setAttribute("searchPage", searchPage);
			request.setAttribute("commentNo", commentNo);
			request.setAttribute("searchList", searchList);
			rd.forward(request, response);
		} else if (act.equals("modfm") || act.equals("del")) {
			board = boardDao.boardSearch(board);
			
			String boardPw = request.getParameter("boardPw");
			if (boardPw.equals(board.getBoardPw())) {
				if (act.equals("modfm")) {
					String opt = request.getParameter("opt");
					request.setAttribute("opt", opt);
					
					rd = request.getRequestDispatcher("/board/board_modify_form.jsp");
					request.setAttribute("board", board);
					request.setAttribute("searchPage", searchPage);
					rd.forward(request, response);
				} else if (act.equals("del")) {
					if (boardDao.boardDelete(board)) new File(request.getSession().getServletContext().getRealPath("") +"\\board\\upload\\"+ board.getBoardFileRename()).delete();
					
					comm.alert(pw, "게시판 글 삭제!", "board.do?act=list&bid="+ boardId +"&sopt=bs&sp="+ searchPage);
				}
			} else {
				comm.alert(pw, "비번 다시 입력!", "javascript:history.go(-1)");
			}
		} else if (act.equals("list")) {
			board.setBoardId(boardId);
			
			String searchOpt = request.getParameter("sopt");
			String searchVal = request.getParameter("sval") == null ? "" : request.getParameter("sval");
			
			int viewRecord = 5;
			int viewPage = 5;
			int searchListCount = boardDao.boardSearchListCount(board, searchOpt, searchVal);
			List<Board> searchList = boardDao.boardSearchList(board, searchOpt, searchVal, searchPage, viewRecord);
			
			int totalPage = (searchListCount - 1) / viewRecord + 1;
			int startPage = (searchPage - 1) / viewPage * viewPage + 1;
			int endPage = startPage + viewPage - 1;
			if (endPage > totalPage) endPage = totalPage;
			
			rd = request.getRequestDispatcher("/board/board_list.jsp");
			request.setAttribute("searchOpt", searchOpt);
			request.setAttribute("searchVal", searchVal);
			request.setAttribute("searchPage", searchPage);
			request.setAttribute("searchList", searchList);
			request.setAttribute("viewPage", viewPage);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			rd.forward(request, response);
		}
		
		if (act.equals("coadd") || act.equals("comod")) {
			comment.setCommentWriter(request.getParameter("commentWriter"));
			comment.setCommentContent(request.getParameter("commentContent"));
			comment.setCommentIp(request.getRemoteAddr());
			
			if (act.equals("coadd")) {
				comment.setBoardNo(board.getBoardNo());
				comment.setUserNo(Integer.parseInt(request.getParameter("uno")));
				
				commentDao.commentAdd(comment);
				
				comm.alert(pw, "게시판 덧글 등록!", "board.do?act=read&opt=read&bid="+ boardId +"&bno="+ board.getBoardNo() +"&sp="+ searchPage);
			} else if (act.equals("comod")) {
				comment.setCommentNo(Integer.parseInt(request.getParameter("cno")));
				
				commentDao.commentModify(comment);
				
				comm.alert(pw, "게시판 덧글 수정!", "board.do?act=read&opt=read&bid="+ boardId +"&bno="+ board.getBoardNo() +"&sp="+ searchPage);
			}
		} else if (act.equals("codel")) {
			comment.setCommentNo(Integer.parseInt(request.getParameter("cno")));
			
			commentDao.commentDelete(comment);
			
			comm.alert(pw, "게시판 덧글 삭제!", "board.do?act=read&opt=read&bid="+ boardId +"&bno="+ board.getBoardNo() +"&sp="+ searchPage);
		}
		
		if (pw != null) pw.close();
	}
	
}
