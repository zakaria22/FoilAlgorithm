package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.DataSet;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class View {

	private JFrame frame;
	
	private JLabel labelInstances;
	private JLabel labelAttribute;
	private JLabel labelPositive;
	private JLabel labelNegative;
	private JTextArea dataArea;
	private JTextArea ruleArea;
	JLabel label;
	JButton btnNew, btnQuit;
	private static String filePath; 
	


	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		View.filePath = filePath;
	}
	

	public void setSummaryValues(int s,int st,int str,int strg){
		labelInstances.setText(Integer.toString(s));
		labelAttribute.setText(Integer.toString(st));
		labelPositive.setText(Integer.toString(str));
		labelNegative.setText(Integer.toString(strg));
	}
	
	public void setData(String str){
		dataArea.setText(str);
	}

	public void setRules(String str){
		ruleArea.setText(str);
	}

	
	
	 /**
	  * Methods needed
	  * */
	 public void openFile(){
		 	JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File file = fileChooser.getSelectedFile(); 
			filePath = file.getAbsolutePath();
			String filename = file.getName();
			label.setText(filename);
	 }
	 
	 public String askForPositive(String j){
		 int g = -1;
		 String input = "";
		 while(g <0){
			  input = JOptionPane.showInputDialog(j);
			  g++;
		 }
		 return input;
	 }
	 
	 public void append(String str){
		 dataArea.setText(dataArea.getText() + "\n\n" + str);
	 }
	 
	 public void startListner(ActionListener listenForStartButton){
			btnNew.addActionListener(listenForStartButton);
	 }
	 
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					DataSet model = new DataSet();
					Controller cont = new Controller(window, model);
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Summary", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(33, 155, 417, 158);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInstances = new JLabel("Instances =");
		lblInstances.setBounds(36, 25, 114, 22);
		panel.add(lblInstances);
		
		JLabel lblAttributes = new JLabel("Attributes =");
		lblAttributes.setBounds(36, 59, 103, 15);
		panel.add(lblAttributes);
		
		JLabel lblPositiveExamples = new JLabel("Positive Examples  =");
		lblPositiveExamples.setBounds(36, 86, 167, 28);
		panel.add(lblPositiveExamples);
		
		JLabel lblNegativeExamples = new JLabel("Negative Examples =");
		lblNegativeExamples.setBounds(36, 113, 167, 28);
		panel.add(lblNegativeExamples);
		
		labelInstances = new JLabel("");
		labelInstances.setBounds(194, 19, 130, 28);
		panel.add(labelInstances);
		
		 labelAttribute = new JLabel("");
		labelAttribute.setBounds(194, 52, 130, 22);
		panel.add(labelAttribute);
		
		 labelPositive = new JLabel("");
		labelPositive.setBounds(194, 86, 130, 22);
		panel.add(labelPositive);
		
		 labelNegative = new JLabel("");
		labelNegative.setBounds(194, 113, 130, 22);
		panel.add(labelNegative);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Rules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(33, 347, 417, 203);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 405, 179);
		panel_1.add(scrollPane);
		
		ruleArea = new JTextArea();
		scrollPane.setViewportView(ruleArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(471, 65, 417, 510);
		frame.getContentPane().add(panel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 12, 405, 498);
		panel_2.add(scrollPane_1);
		
		 dataArea = new JTextArea();
		scrollPane_1.setViewportView(dataArea);
		
		btnNew = new JButton("new");
		btnNew.setBounds(33, 77, 117, 25);
		frame.getContentPane().add(btnNew);
		
		 btnQuit = new JButton("quit");
		btnQuit.setBounds(33, 114, 117, 25);
		frame.getContentPane().add(btnQuit);
		
		 label = new JLabel("");
		label.setBounds(168, 77, 282, 25);
		frame.getContentPane().add(label);
		
		
	}
}
