package com.name859.util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

	public void alert(PrintWriter pw, String msg, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>")
		.append("alert(\""+ msg +"\");")
		.append("location.href=\""+ url +"\";")
		.append("</script>");
		
		pw.print(sb.toString());
		pw.close();
	}
	
	public String currentTime(String timeConfig) {
		return new SimpleDateFormat(timeConfig).format(new Date(System.currentTimeMillis()));
	}
	
}
