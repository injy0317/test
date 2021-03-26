import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.util.*;

class Menu extends JFrame implements Runnable, ActionListener {
	
	
	
	private Container con; //Container를 설정한다.
	private JButton[] bt = new JButton[6]; //JButton 메뉴 6개 설정한다.
	private JPanel p1 = new JPanel(); //J패널을 설정한다.
	private JPanel p2 = new JPanel();	
	private JPanel p3 = new JPanel();
	private TextArea ta1 = new TextArea(20, 15); //텍스트 크기를 조절한다.
	private TextArea ta2 = new TextArea(20, 15);
	private TextArea ta3 = new TextArea(20, 15);
	
	private InetAddress address; //자바 IP주소 정의
	private Socket socket; //Java 소켓
	private PrintWriter pw; //출력만을 위한 outputstream, bufferedreader, printwriter 사용한다.
	private BufferedReader buffer; //버퍼 문자입력

	String msg; 
	int total = 0;
	int[] price = { 4000, 4000, 8000, 5000, 5000, 10000 };
	String table;
	String num;
	
	public void main() {
		con = this.getContentPane(); // JFrame 위에 Container 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 화면의 x를 누르면 클로즈 
		con.setLayout(new BorderLayout()); //버튼의 위치를 조정한다.
		con.add("Center", p1); //센터에 위치
		p1.setLayout(new GridLayout(2, 3)); //세로 2개 가로 3개
		
		for (int i = 0; i < 6; i++) {  //메뉴별 버튼의 갯수
			bt[i] = new JButton("====");
			bt[i].addActionListener(this);
			p1.add(bt[i]); //p1패널에 버튼i개를 더한다.
		}

		bt[0].setText("자장면"); //버튼i에 해당하는 메뉴
		bt[1].setText("짬뽕");
		bt[2].setText("탕수육");
		bt[3].setText("볶음밥");
		bt[4].setText("간짜장");
		bt[5].setText("깐풍기");		
		

		con.add("East", p2); //p2의 위치를 East로 조정한다.
		p2.setLayout(new GridLayout(2, 1)); //p2의 그리드를 가로 1개 세로 2개
		p2.add(ta1); //p2에 ta1와 ta2의 텍스트크기를 설정
		p2.add(ta2);
		ta1.setText("---주문 내역---\n");
		ta2.setText("합계 : ");
	}

	public Menu(String title) throws Exception { //try catch 대신에 코드자체를 Exception 할 수 있는 throws 사용
		super(title); //menu의 title 상속
		main();
		setSize(700, 600);
		setResizable(false);
		setVisible(true);
		address = InetAddress.getByName("Localhost"); //Localhost라는 이름이로 address 받는다.
		socket = new Socket(address, 12345); //socket에서 id=address , 비밀번호 = 12345
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		table = JOptionPane.showInputDialog(this, "테이블 넘버 : ", "입력", JOptionPane.QUESTION_MESSAGE); //처음 실행시 테이블넘버를 입력한다.
		num = JOptionPane.showInputDialog(this, "인원수 : ", "입력", JOptionPane.QUESTION_MESSAGE);
		setTitle(table + "번 테이블" + "\t 인원 : " + num + "명"); //상단 Title에 테이블번호를 받아서 출력된다.
		Thread th = new Thread(this); //runnable 인터페이스를 구현받아서 연속실행이 되도록 Thread를 구현
		th.start(); //Thread 시작
	}

	public void run() {
		while (true) {
			try {
				String msg = buffer.readLine();
				ta1.append(msg + "\n"); //readLine으로 받아온 msg뒤에 문자열 계속 추가
			} catch (Exception e) {
			}
		}
	}

	@Override

	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 6; i++) { //메뉴 6개에 대한 주문내역과 합계 출력
			if (e.getSource() == bt[i]) {
				String temp = table +bt[i].getText();
				pw.println(temp);
				pw.flush(); //socket 프로그래밍에 대한 close역할
				ta1.append(temp.substring(1) + " : " + price[i] + "\n");
				total += price[i];
				ta2.setText("합계 : " + total);
			}
		}
	}
}

public class Customer {
	public static void main(String[] args) throws Exception {
		new Menu("고객");
		
	}
}