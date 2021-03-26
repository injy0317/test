import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

class Jumun extends JFrame {
	String[] Menu = { "자장면", "짬뽕", "탕수육", "볶음밥", "간짜장", "깐풍기"}; //Menu와 가격, 제작비용에 대한 값 설정
	int[] price = {4000, 4000, 8000, 5000, 5000, 10000};
	int[] cost = {1500, 1500, 3500, 2000, 2500, 4000};
	private Container con;
	private ServerSocket ss; //서버소켓 정의
	private Socket socket; //소켓사용 정의
	private PrintWriter pw; //출력전용 정의
	private BufferedReader buffer; //효율적인 메모리사용을 위한 buffer 정의
	private Scanner scan;
	private ArrayList array = new ArrayList();
	private TextArea ta1 = new TextArea();
	private TextArea ta2 = new TextArea(5, 30);
	int sum1 = 0;
	int sum2 = 0;

	public void main() {
		con = this.getContentPane(); // JFrame 위에 Container 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 화면의 x를 누르면 클로즈
		con.setLayout(new BorderLayout());
		con.add("Center", ta1); //센터에 ta 정의
		con.add("South", ta2); //하단에 ta2정의
		setTitle("주문확인서");
	}

	public Jumun() throws Exception { //try catch 대신에 코드자체를 Exception 할 수 있는 throws 사용
		this.setSize(400, 300); //gui 사이즈
		main();
		this.setResizable(false);
		this.setVisible(true);
		ss = new ServerSocket(12345); //서버소켓 암호
		scan = new Scanner(System.in);
		while (true) {
			socket = ss.accept(); //소켓과 서버소켓에 대한 연결
			JCustomer cc = new JCustomer(socket);
			cc.start();
		}
	}

	class JCustomer extends Thread { //thread상속받아서 연속실행이 가능하게 만든다.
		Socket socket;
		JCustomer(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			while (true) {
				try {
					buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); //입력받은 stream값을 buffer로 불러오기
					String msg1 = buffer.readLine(); //버퍼 read
					String son = "" + msg1.charAt(0);
					String msg = msg1.substring(1); //msg1에 대한 String값을 첫번째값부터 끝까지 추출
					String temp1 = "";
					String temp2 = "";
					array.add(msg1);

					for (int j = 0; j < 6; j++) { //각 메뉴에 대한 가격과 원가를 ta1번 필드에 출력하고 ta2번 필드에 주문메뉴의 총 가격과 순수익을 출력
						if (msg.equals(Menu[j])) {
							temp1 += price[j];
							temp2 += cost[j];
							sum1 += price[j];
							sum2 += price[j] - cost[j];
						}
					}
					ta1.append(son + "번 손님 " + msg + " : " + temp1 + "\t" + "원가 :" + temp2 +"\n"); //String문자열 연결하기위한 append사용
					temp1 = "";
					ta2.setText("총합: " + sum1 + "\t" + "순수익: " + sum2);
				} catch (Exception e) {
				}
			}
		}
	}
}

public class Server {
	public static void main(String[] args) throws Exception {
		new Jumun();
	}
}