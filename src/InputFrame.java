
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

class InputFrame extends JFrame implements ActionListener {
	private Container c;
	private JLabel title;
	private JLabel lableI;
	private JRadioButton inputHere;
	private JRadioButton fromFile;
	private ButtonGroup input;
	private JTextField fileName;
	private JLabel labelP;
	private JLabel labelN;
	private JComboBox<String> numberOfPoints;
	private JLabel lablePos;
	private JRadioButton rand;
	private JRadioButton circle;
	private ButtonGroup gen;
	private JLabel labelE;
	private JButton buttonAdd;
	private JComboBox<String> fromPoint;
	private JComboBox<String> toPoint;
	private JRadioButton inputLength;
	private JRadioButton calculateLength;
	private ButtonGroup length;
	private JComboBox<String> choseLength;

	private JButton Draw;
	private JButton Look;
	private JTextArea tout;
	private JLabel res;
	


	private String nArray[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
			"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45" };

	private String pointArray[] = {};

	private String outputStreamEdges;
	private String outputStreamData;
	private Set<Integer> checkEdge = new HashSet<Integer>();
	private String lengthArray[];
	private String edges;

	public InputFrame() {
		lengthArray = new String[100];
		for (int i = 0; i < 100; i++)
			lengthArray[i] = Integer.toString(i + 1);

		setTitle("Registration Form");
		setBounds(200, 40, 1200, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		title = new JLabel("Registration Form");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setSize(300, 30);
		title.setLocation(300, 30);
		c.add(title);

		lableI = new JLabel("Input");
		lableI.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
		lableI.setSize(100, 30);
		lableI.setLocation(30, 100);
		c.add(lableI);

		inputHere = new JRadioButton("Here");
		inputHere.setFont(new Font("Arial", Font.HANGING_BASELINE, 15));
		inputHere.setSelected(true);
		inputHere.setSize(70, 20);
		inputHere.setLocation(150, 105);
		inputHere.addActionListener(this);
		inputHere.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numberOfPoints.setEnabled(true);
				buttonAdd.setEnabled(true);
				fileName.setEnabled(false);
				if (pointArray.length != 0) {
					fromPoint.setEnabled(true);
					toPoint.setEnabled(true);
					calculateLength.setEnabled(true);
					inputLength.setEnabled(true);
				}
			}
		});
		c.add(inputHere);

		fromFile = new JRadioButton("From file");
		fromFile.setFont(new Font("Arial", Font.PLAIN, 15));
		fromFile.setSelected(false);
		fromFile.setSize(150, 20);
		fromFile.setLocation(245, 105);
		fromFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numberOfPoints.setEnabled(false);
				buttonAdd.setEnabled(false);
				fromPoint.setEnabled(false);
				toPoint.setEnabled(false);
				calculateLength.setEnabled(false);
				inputLength.setEnabled(false);
				fileName.setEnabled(true);
			}
		});
		c.add(fromFile);

		input = new ButtonGroup();
		input.add(inputHere);
		input.add(fromFile);
		
		fileName = new JTextField();
		fileName.setText("DijkstrasAlgorithmInput.txt");
		fileName.setFont(new Font("Arial", Font.PLAIN, 15));
		fileName.setSize(190, 20);
		fileName.setEnabled(false);
		fileName.setLocation(180, 135);
        c.add(fileName);

		labelP = new JLabel("Points");
		labelP.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
		labelP.setSize(100, 30);
		labelP.setLocation(30, 200);
		c.add(labelP);

		labelN = new JLabel("Number");
		labelN.setFont(new Font("Arial", Font.HANGING_BASELINE, 20));
		labelN.setSize(200, 20);
		labelN.setLocation(150, 180);
		c.add(labelN);

		numberOfPoints = new JComboBox<String>(nArray);
		numberOfPoints.setFont(new Font("Arial", Font.PLAIN, 15));
		numberOfPoints.setSize(50, 20);
		numberOfPoints.setLocation(250, 180);
		numberOfPoints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = Integer.valueOf((String) numberOfPoints.getSelectedItem());
				pointArray = new String[i];
				for (int j = 0; j < i; j++)
					pointArray[j] = Integer.toString(j + 1);
				remove(fromPoint);
				fromPoint = new JComboBox<String>(pointArray);
				fromPoint.setFont(new Font("Arial", Font.PLAIN, 15));
				fromPoint.setSize(50, 20);
				fromPoint.setLocation(260, 315);
				c.add(fromPoint);
				remove(toPoint);
				toPoint = new JComboBox<String>(pointArray);
				toPoint.setFont(new Font("Arial", Font.PLAIN, 15));
				toPoint.setSize(50, 20);
				toPoint.setLocation(320, 315);
				c.add(toPoint);
				calculateLength.setEnabled(true);
				inputLength.setEnabled(true);
			}
		});
		c.add(numberOfPoints);

		lablePos = new JLabel("Position");
		lablePos.setFont(new Font("Arial", Font.HANGING_BASELINE, 20));
		lablePos.setSize(200, 20);
		lablePos.setLocation(150, 230);
		c.add(lablePos);

		circle = new JRadioButton("Circle");
		circle.setFont(new Font("Arial", Font.PLAIN, 15));
		circle.setSelected(true);
		circle.setSize(70, 20);
		circle.setLocation(250, 230);
		c.add(circle);

		rand = new JRadioButton("Random");
		rand.setFont(new Font("Arial", Font.PLAIN, 15));
		rand.setSelected(false);
		rand.setSize(100, 20);
		rand.setLocation(320, 230);
		rand.addActionListener(this);
		c.add(rand);

		gen = new ButtonGroup();
		gen.add(circle);
		gen.add(rand);

		labelE = new JLabel("Edges");
		labelE.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
		labelE.setSize(100, 31);
		labelE.setLocation(30, 310);
		c.add(labelE);

		buttonAdd = new JButton("Add edge");
		buttonAdd.setFont(new Font("Arial", Font.ITALIC, 12));
		buttonAdd.setSize(90, 20);
		buttonAdd.setLocation(150, 315);
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((String)fromPoint.getSelectedItem()).equals((String) toPoint.getSelectedItem())) {
					res.setText("Please select correct points of the edge!!!"); 
				}else if(checkEdge.contains(Integer.valueOf((String)fromPoint.getSelectedItem() + (String) toPoint.getSelectedItem())) ||
						checkEdge.contains(Integer.valueOf((String)toPoint.getSelectedItem() + (String) fromPoint.getSelectedItem()))) 
					{
						res.setText("This edge is already exist!!!"); 
					} else if (fromPoint.isEnabled() && toPoint.isEnabled()) {
							res.setText("");
							if (edges == null) {
								edges = "\n" + (String) fromPoint.getSelectedItem() + " " + (String) toPoint.getSelectedItem();
								outputStreamEdges = " " + Integer.toString((Integer.valueOf((String) fromPoint.getSelectedItem()) - 1)) + 
													" " + Integer.toString((Integer.valueOf((String) toPoint.getSelectedItem()) - 1)) + " ";
							}
							else { 
								edges += (String) fromPoint.getSelectedItem() + " " + (String) toPoint.getSelectedItem();
								outputStreamEdges += " " + Integer.toString((Integer.valueOf((String) fromPoint.getSelectedItem()) - 1)) + 
										" " + Integer.toString((Integer.valueOf((String) toPoint.getSelectedItem()) - 1)) + " ";
							}
							if (choseLength.isEnabled()) {
								edges += " " + (String) choseLength.getSelectedItem() + " \n";
								outputStreamEdges += (String) choseLength.getSelectedItem() + " ";
							}
							else {
								outputStreamEdges += "0 ";
								edges += " 0 " + " \n";
							}
							
							checkEdge.add(Integer.valueOf((String)fromPoint.getSelectedItem() + (String) toPoint.getSelectedItem()));
						}
				}
		});
		c.add(buttonAdd);

		fromPoint = new JComboBox<String>(pointArray);
		fromPoint.setFont(new Font("Arial", Font.PLAIN, 15));
		fromPoint.setSize(50, 20);
		fromPoint.setEnabled(false);
		fromPoint.setLocation(260, 315);
		c.add(fromPoint);

		toPoint = new JComboBox<String>(pointArray);
		toPoint.setFont(new Font("Arial", Font.PLAIN, 15));
		toPoint.setSize(50, 20);
		toPoint.setEnabled(false);
		toPoint.setLocation(320, 315);
		c.add(toPoint);

		choseLength = new JComboBox<String>(lengthArray);
		choseLength.setFont(new Font("Arial", Font.PLAIN, 15));
		choseLength.setSize(50, 20);
		choseLength.setEnabled(false);
		choseLength.setLocation(180, 345);
		c.add(choseLength);

		inputLength = new JRadioButton("Input");
		inputLength.setFont(new Font("Arial", Font.PLAIN, 15));
		inputLength.setSelected(false);
		inputLength.setEnabled(false);
		inputLength.setSize(70, 20);
		inputLength.setLocation(250, 345);
		inputLength.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choseLength.setEnabled(true);
			}
		});
		c.add(inputLength);

		calculateLength = new JRadioButton("Calculate");
		calculateLength.setFont(new Font("Arial", Font.PLAIN, 15));
		calculateLength.setSelected(true);
		calculateLength.setEnabled(false);
		calculateLength.setSize(100, 20);
		calculateLength.setLocation(320, 345);
		calculateLength.addActionListener(this);
		calculateLength.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choseLength.setEnabled(false);
			}
		});
		c.add(calculateLength);

		length = new ButtonGroup();
		length.add(inputLength);
		length.add(calculateLength);

		Draw = new JButton("Draw");
		Draw.setFont(new Font("Arial", Font.PLAIN, 15));
		Draw.setSize(100, 20);
		Draw.setLocation(150, 450);
		Draw.addActionListener(this);
		c.add(Draw);

		Look = new JButton("Look");
		Look.setFont(new Font("Arial", Font.PLAIN, 15));
		Look.setSize(100, 20);
		Look.setLocation(270, 450);
		Look.addActionListener(this);
		c.add(Look);

		tout = new JTextArea();
		tout.setFont(new Font("Arial", Font.PLAIN, 15));
		tout.setSize(300, 400);
		tout.setLocation(500, 100);
		tout.setLineWrap(true);
		tout.setEditable(false);
		c.add(tout);

		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setForeground(Color.RED);
		res.setSize(500, 25);
		res.setLocation(100, 500);
		c.add(res);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Look) {
			String data = "";
			if(fileName.isEnabled()) {		        
		        String strPattern = "^[a-zA-Z0-9._ -]+\\.(txt)$";
		        
		        Pattern pattern = Pattern.compile(strPattern);
		        Matcher matcher = pattern.matcher(fileName.getText());
		        if(matcher.matches())
	            {
		   		 try {
		 			FileReader fl = new FileReader(fileName.getText());
		 			Scanner sc = new Scanner(fl);
		 			if(sc.hasNextInt()) {
		 				int n = sc.nextInt();
		 				data = "Number of points: " + Integer.toString(n) + "\n";
		 				outputStreamData = Integer.toString(n);
		 			}
		 			if(!sc.hasNextInt())
		 				data += "There are no edges\n";
		 			else
		 				data += "Edges: \n"; 
		 			while(sc.hasNextInt()) {
		 				int a = sc.nextInt();
		 				int b = sc.nextInt();
		 				int length = sc.nextInt();
		 				data += Integer.toString(a) + " " + Integer.toString(b) + " " + Integer.toString(length) + " \n";
		 				outputStreamData += " " + Integer.toString((a - 1)) + " " + Integer.toString((b - 1)) + " " + Integer.toString(length);
		 			}
		 			sc.close();
		 		 } catch (FileNotFoundException ef) {
		 		    ef.printStackTrace();
		 		 }
	            }else
	            	res.setText("Please enter correct file name"); 
			}else {
				data = "Number of points: " + (String) numberOfPoints.getSelectedItem() + "\n";
				if (edges == null)
					data += "You don't add any edges\n";
				else {
					if(rand.isSelected())
						data += "Type of generation: random \n" ;
					else
						data += "Type of generation: circle \n";
					data += "Edges: " + edges;
					outputStreamData = (String)numberOfPoints.getSelectedItem() + outputStreamEdges;
				}
			}
			tout.setText(data);
			tout.setEditable(false);
		} else if(e.getSource() == Draw) {
			if(fileName.isEnabled()) {		        
		        String strPattern = "^[a-zA-Z0-9._ -]+\\.(txt)$";
		        
		        Pattern pattern = Pattern.compile(strPattern);
		        Matcher matcher = pattern.matcher(fileName.getText());
		        if(matcher.matches())
	            {
		   		 try {
		 			FileReader fl = new FileReader(fileName.getText());
		 			Scanner sc = new Scanner(fl);
		 			if(sc.hasNextInt()) {
		 				int n = sc.nextInt();
		 				outputStreamData = Integer.toString(n);
		 			}
		 			if(rand.isSelected())
						outputStreamData += " Random ";
					else
						outputStreamData += " Circle ";
		 			while(sc.hasNextInt()) {
		 				int a = sc.nextInt();
		 				int b = sc.nextInt();
		 				int length = sc.nextInt();
		 				outputStreamData += " " + Integer.toString((a - 1)) + " " + Integer.toString((b - 1)) + " " + Integer.toString(length);
		 			}
		 			sc.close();
		 		 } catch (FileNotFoundException ef) {
		 		    ef.printStackTrace();
		 		 }
	            }else
	            	res.setText("Please enter correct file name"); 
			}else {
				if(rand.isSelected())
					outputStreamData = (String)numberOfPoints.getSelectedItem() + " Random " + outputStreamEdges;
				else
					outputStreamData = (String)numberOfPoints.getSelectedItem() + " Circle " + outputStreamEdges;
			}
				
			//MainFrame frame = new MainFrame(outputStreamData);
		}
	}
}