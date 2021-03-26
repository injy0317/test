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
	
	
	
	private Container con; //Container�� �����Ѵ�.
	private JButton[] bt = new JButton[6]; //JButton �޴� 6�� �����Ѵ�.
	private JPanel p1 = new JPanel(); //J�г��� �����Ѵ�.
	private JPanel p2 = new JPanel();	
	private JPanel p3 = new JPanel();
	private TextArea ta1 = new TextArea(20, 15); //�ؽ�Ʈ ũ�⸦ �����Ѵ�.
	private TextArea ta2 = new TextArea(20, 15);
	private TextArea ta3 = new TextArea(20, 15);
	
	private InetAddress address; //�ڹ� IP�ּ� ����
	private Socket socket; //Java ����
	private PrintWriter pw; //��¸��� ���� outputstream, bufferedreader, printwriter ����Ѵ�.
	private BufferedReader buffer; //���� �����Է�

	String msg; 
	int total = 0;
	int[] price = { 4000, 4000, 8000, 5000, 5000, 10000 };
	String table;
	String num;
	
	public void main() {
		con = this.getContentPane(); // JFrame ���� Container ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ȭ���� x�� ������ Ŭ���� 
		con.setLayout(new BorderLayout()); //��ư�� ��ġ�� �����Ѵ�.
		con.add("Center", p1); //���Ϳ� ��ġ
		p1.setLayout(new GridLayout(2, 3)); //���� 2�� ���� 3��
		
		for (int i = 0; i < 6; i++) {  //�޴��� ��ư�� ����
			bt[i] = new JButton("====");
			bt[i].addActionListener(this);
			p1.add(bt[i]); //p1�гο� ��ưi���� ���Ѵ�.
		}

		bt[0].setText("�����"); //��ưi�� �ش��ϴ� �޴�
		bt[1].setText("«��");
		bt[2].setText("������");
		bt[3].setText("������");
		bt[4].setText("��¥��");
		bt[5].setText("��ǳ��");		
		

		con.add("East", p2); //p2�� ��ġ�� East�� �����Ѵ�.
		p2.setLayout(new GridLayout(2, 1)); //p2�� �׸��带 ���� 1�� ���� 2��
		p2.add(ta1); //p2�� ta1�� ta2�� �ؽ�Ʈũ�⸦ ����
		p2.add(ta2);
		ta1.setText("---�ֹ� ����---\n");
		ta2.setText("�հ� : ");
	}

	public Menu(String title) throws Exception { //try catch ��ſ� �ڵ���ü�� Exception �� �� �ִ� throws ���
		super(title); //menu�� title ���
		main();
		setSize(700, 600);
		setResizable(false);
		setVisible(true);
		address = InetAddress.getByName("Localhost"); //Localhost��� �̸��̷� address �޴´�.
		socket = new Socket(address, 12345); //socket���� id=address , ��й�ȣ = 12345
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		table = JOptionPane.showInputDialog(this, "���̺� �ѹ� : ", "�Է�", JOptionPane.QUESTION_MESSAGE); //ó�� ����� ���̺�ѹ��� �Է��Ѵ�.
		num = JOptionPane.showInputDialog(this, "�ο��� : ", "�Է�", JOptionPane.QUESTION_MESSAGE);
		setTitle(table + "�� ���̺�" + "\t �ο� : " + num + "��"); //��� Title�� ���̺��ȣ�� �޾Ƽ� ��µȴ�.
		Thread th = new Thread(this); //runnable �������̽��� �����޾Ƽ� ���ӽ����� �ǵ��� Thread�� ����
		th.start(); //Thread ����
	}

	public void run() {
		while (true) {
			try {
				String msg = buffer.readLine();
				ta1.append(msg + "\n"); //readLine���� �޾ƿ� msg�ڿ� ���ڿ� ��� �߰�
			} catch (Exception e) {
			}
		}
	}

	@Override

	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 6; i++) { //�޴� 6���� ���� �ֹ������� �հ� ���
			if (e.getSource() == bt[i]) {
				String temp = table +bt[i].getText();
				pw.println(temp);
				pw.flush(); //socket ���α׷��ֿ� ���� close����
				ta1.append(temp.substring(1) + " : " + price[i] + "\n");
				total += price[i];
				ta2.setText("�հ� : " + total);
			}
		}
	}
}

public class Customer {
	public static void main(String[] args) throws Exception {
		new Menu("��");
		
	}
}