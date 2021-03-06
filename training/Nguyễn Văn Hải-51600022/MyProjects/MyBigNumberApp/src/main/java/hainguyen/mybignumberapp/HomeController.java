package hainguyen.mybignumberapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@WebServlet("/getTwoNumberFromUser")
@Controller
public class HomeController implements IReceiver{

	protected void doHost(HttpServletRequest request, HttpServletResponse reponse) throws ServletException, IOException{
		
		//read form fields
		String str1 = request.getParameter("num1");
		String str2 = request.getParameter("num2");
		
		System.out.println(str1 + " , " + str2);
		
		//method add two string number
		//Khai báo biến
		String results = ""; //Biến dùng để lưu kết quả cuối cùng của 2 chuỗi số
		String step = ""; //biến dùng để làm tham số cho hàm send trong của interface
		String msg = ""; //biến dùng để chứa đoạn text hướng dẫn các bước cộng
		int len1 = str1.length(); //biến chứa độ dài chuỗi str1
		int len2 = str2.length(); //biến chứa độ dài chuỗi srt2
		final int maxLen = (len1 > len2) ? len1 : len2; //lấy độ dài lớn nhất của 1 trong 2 chuỗi số str1 và str2
		int index1; //xác định vị trí của kí tự đang xét của chuỗi str1
		int index2; //xác định vị trí của kí tự đang xét của chuỗi str2
		char c1; //kí tự tại vị trí đang xét index1 của chuỗi str1
		char c2; //kí tự tại vị trí đang xét index2 của chuỗi str2
		int temp1; //kí số của c1
		int temp2; //kí số của c2
		int total; //tổng tạm
		int totalNoMem; //tổng tạm không có nhớ
		int remem = 0; //biến nhớ
		final String pattern = "\\d+"; //chuỗi số đại diện cho kí tự số [0-9]
		final boolean flag1; //biến để lưu trữ kết quả xét chuỗi str1
		final boolean flag2; //biến để lưu trữ kết quả xét chuỗi str2
				
		//Kiểm tra 2 chuỗi số str1 và str2 có chứa kí tự là chữ cái hay không
		for(int i = 0; i < len1; i++) {
	        if(Character.isLetter(str1.charAt(i))) {
		        msg = ("Vị trí " + (i + 1) + " trong chuỗi " + str1 + " không phải là số.");
		    }
	    }
		    	
		for(int i = 0; i < len2; i++) {
		   if(Character.isLetter(str2.charAt(i))) {
		       msg = ("Vị trí " + (i + 1) + " trong chuỗi " + str2 + " không phải là số");
		   }
		}
		    	
		//Kiểm tra số có phải là số âm hay không
		if(str1.charAt(0) == '-') {
		    msg = ("Chưa hỗ trợ số âm " + str1);
		}
		    	
		if(str2.charAt(0) == '-') {
		   msg = ("Chưa hỗ trợ số âm " + str2);
		}
		    	
		//Kiểm tra kí tự có phải là kí tự đặc biệt hay không
		flag1 = str1.matches(pattern);
		flag2 = str2.matches(pattern);
		    	
		if(!flag1) {
		   msg = ("Trong chuỗi số " + str1 + " có chứa kí tự đặc biệt");
		}
		    	
		if(!flag2) {
		    msg = ("Trong chuỗi số " + str2 + " có chứa kí tự đặc biệt");
		}
		    	
		//Chạy vòng lập để cộng từng số trong 2 chuỗi số
		for(int i = 0; i < maxLen; i++) {
		    index1 = len1 - i - 1; //lấy ra vị trí index1 phía bên phải của chuỗi str1
		    index2 = len2 - i - 1; //lấy ra vị trí index2 phía bên phải của chuỗi str2
		    		
		    c1 = (index1 >= 0) ? str1.charAt(index1) : '0';
		    c2 = (index2 >= 0) ? str2.charAt(index2) : '0';
		    		
		    temp1 = c1 - '0'; //Số tại vị trí index1
		    temp2 = c2 - '0'; //Số tại vị trí index2
		    		
		    total = temp1 + temp2 + remem; //Tổng tạm của 2 số tại vị trí index1 + số tại vị trí index2 + số nhớ
		    totalNoMem = temp1 + temp2;
		    		
		    //Lấy số ở hàng đơn vị của total ghép vào phía trước kết quả
		    results = (total % 10) + results;
		    remem = total / 10; //số nhớ
		    		
		    if(i == 0) {
		    	msg = "Step " + i + " : " + temp1 + " + " + temp2 + " = " + totalNoMem
		    			+ " , " + " Remember " + remem + " , " + " Result " + results + "\n";
		    }else {
		    	msg = "Step " + i + " : " + temp1 + " + " + temp2 + " + " + remem + " = "
		    			+ total + " , " + " Remember " + remem + " , " + "Result " + results + "\n";
		    }
		    step = step + msg;
		}
		    	
		//Kết thúc vòng lặp
		//Nếu biến nhớ remember có giá trị thì ghép thêm remem vào phía trước kết quả
		if(remem > 0) {
		   results = remem + results;
		}
		step = "\n" + str1 + " + " + str2 + " = " + results + "\n" + " Process implementation: \n" + step;
		
		//get reponse writer
		PrintWriter writer = reponse.getWriter();
		
		//build HTML code
		String htmlResponse = "<html>";
		htmlResponse += "<pre>" + results + "</pre>";
		htmlResponse += "</html>";
		
		//return response
		writer.println(htmlResponse);
	}
	
	@RequestMapping("home")
	public String home() {
		
		return "index.jsp";
		
	}

	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub
		
	}
}
