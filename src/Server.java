import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

class Jumun extends JFrame {
	String[] Menu = { "�����", "«��", "������", "������", "��¥��", "��ǳ��"}; //Menu�� ����, ���ۺ�뿡 ���� �� ����
	int[] price = {4000, 4000, 8000, 5000, 5000, 10000};
	int[] cost = {1500, 1500, 3500, 2000, 2500, 4000};
	private Container con;
	private ServerSocket ss; //�������� ����
	private Socket socket; //���ϻ�� ����
	private PrintWriter pw; //������� ����
	private BufferedReader buffer; //ȿ������ �޸𸮻���� ���� buffer ����
	private Scanner scan;
	private ArrayList array = new ArrayList();
	private TextArea ta1 = new TextArea();
	private TextArea ta2 = new TextArea(5, 30);
	int sum1 = 0;
	int sum2 = 0;

	public void main() {
		con = this.getContentPane(); // JFrame ���� Container ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ȭ���� x�� ������ Ŭ����
		con.setLayout(new BorderLayout());
		con.add("Center", ta1); //���Ϳ� ta ����
		con.add("South", ta2); //�ϴܿ� ta2����
		setTitle("�ֹ�Ȯ�μ�");
	}

	public Jumun() throws Exception { //try catch ��ſ� �ڵ���ü�� Exception �� �� �ִ� throws ���
		this.setSize(400, 300); //gui ������
		main();
		this.setResizable(false);
		this.setVisible(true);
		ss = new ServerSocket(12345); //�������� ��ȣ
		scan = new Scanner(System.in);
		while (true) {
			socket = ss.accept(); //���ϰ� �������Ͽ� ���� ����
			JCustomer cc = new JCustomer(socket);
			cc.start();
		}
	}

	class JCustomer extends Thread { //thread��ӹ޾Ƽ� ���ӽ����� �����ϰ� �����.
		Socket socket;
		JCustomer(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			while (true) {
				try {
					buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); //�Է¹��� stream���� buffer�� �ҷ�����
					String msg1 = buffer.readLine(); //���� read
					String son = "" + msg1.charAt(0);
					String msg = msg1.substring(1); //msg1�� ���� String���� ù��°������ ������ ����
					String temp1 = "";
					String temp2 = "";
					array.add(msg1);

					for (int j = 0; j < 6; j++) { //�� �޴��� ���� ���ݰ� ������ ta1�� �ʵ忡 ����ϰ� ta2�� �ʵ忡 �ֹ��޴��� �� ���ݰ� �������� ���
						if (msg.equals(Menu[j])) {
							temp1 += price[j];
							temp2 += cost[j];
							sum1 += price[j];
							sum2 += price[j] - cost[j];
						}
					}
					ta1.append(son + "�� �մ� " + msg + " : " + temp1 + "\t" + "���� :" + temp2 +"\n"); //String���ڿ� �����ϱ����� append���
					temp1 = "";
					ta2.setText("����: " + sum1 + "\t" + "������: " + sum2);
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