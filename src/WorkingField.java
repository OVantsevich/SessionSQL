import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

enum TableType {
	EXAM, STUDENT, RECORDBOOK, SESSION;
}

public class WorkingField extends JFrame {
	private Container c;
	private ArrayList<Integer> forUpdate = new ArrayList<Integer>();
	private JPanel tablePanel;
	private JButton addStudents;
	private JButton addRecordBook;
	private JButton addSession;
	private JButton addExam;
	private JButton push;
	public TableType tableType = null;
	JTable table1;
	SQLTest sqltest = new SQLTest();

	class DBtableEdit implements TableModelListener {
		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
			// int column = e.getColumn();
			DefaultTableModel model = (DefaultTableModel) e.getSource();

			if (model.getValueAt(row, 0).equals((Object) "") && row == model.getRowCount() - 1
					&& (!model.getValueAt(row, 1).equals((Object) "Add...")
							&& !model.getValueAt(row, 1).equals((Object) ""))) {
				model.addRow(new String[] { "", "Add..." });
				model.fireTableDataChanged();
			}

			if (!model.getValueAt(row, 0).equals((Object) "")) {
				forUpdate.add(row);
			}
//			else {
//				if (!model.getValueAt(row, 0).equals((Object) "")) {
//					if (!forpush.contains(Integer.parseInt(((String) model.getValueAt(row, 0))))) {
//						forpush.add(Integer.parseInt(((String) model.getValueAt(row, 0))));
//					}
//				}
//			}
		}
	}

	@SuppressWarnings("serial")
	public WorkingField() {
		setTitle("Working field");
		setBounds(200, 40, 1200, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		tablePanel = new JPanel();
		tablePanel.setSize(500, 400);
		tablePanel.setLocation(500, 100);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBackground(Color.WHITE);

		table1 = new JTable();
		table1.getModel().addTableModelListener(new DBtableEdit());
		table1 = new JTable() {
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(500, 400);
			}
		};
		table1.setAutoCreateRowSorter(true);
		table1.setSize(500, 400);
		table1.setVisible(true);
		table1.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(table1);
//		table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent event) {
//				if (table1.getModel().getValueAt(table1.getSelectedRow(), 1).equals((Object) "Add...")) {
//					table1.getModel().setValueAt((Object) "", table1.getSelectedRow(), 1);
//				}
//
//			}
//		});
		tablePanel.add(pane);

		c.add(tablePanel);

		addStudents = new JButton("Show students");
		addStudents.setFont(new Font("Arial", Font.BOLD, 12));
		addStudents.setSize(150, 30);
		addStudents.setLocation(150, 100);
		addStudents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] data = new String[0][0];
				Object[] columnsHeader = new String[] { "Id", "Name" };
				try {
					data = sqltest.get("SELECT * FROM students", 2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				DefaultTableModel model = new DefaultTableModel(data, columnsHeader) {
					@Override
					public boolean isCellEditable(int row, int column) {
						if (column != 0) {
							return true;
						}
						return false;
					}
				};
				model.addRow(new String[] { "", "Add..." });
				model.addTableModelListener(new DBtableEdit());
				table1.setModel(model);
				tableType = TableType.STUDENT;
			}
		});
		c.add(addStudents);

		addRecordBook = new JButton("Show record-books");
		addRecordBook.setFont(new Font("Arial", Font.BOLD, 12));
		addRecordBook.setSize(150, 30);
		addRecordBook.setLocation(150, 150);
		addRecordBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] data = new String[0][0];
				Object[] columnsHeader = new String[] { "Id", "StudentID" };
				try {
					data = sqltest.get("SELECT * FROM recordbook", 2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				DefaultTableModel model = new DefaultTableModel(data, columnsHeader) {
					@Override
					public boolean isCellEditable(int row, int column) {
						if (column != 0) {
							return true;
						}
						return false;
					}
				};
				model.addRow(new String[] { "", "Add..." });
				model.addTableModelListener(new DBtableEdit());
				table1.setModel(model);
				tableType = TableType.RECORDBOOK;
			}

		});
		c.add(addRecordBook);

		addSession = new JButton("Show sessions");
		addSession.setFont(new Font("Arial", Font.BOLD, 12));
		addSession.setSize(150, 30);
		addSession.setLocation(150, 200);
		addSession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] data = new String[0][0];
				Object[] columnsHeader = new String[] { "Id", "year", "IdRecordBook" };
				try {
					data = sqltest.get("SELECT * FROM session", 3);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				DefaultTableModel model = new DefaultTableModel(data, columnsHeader) {
					@Override
					public boolean isCellEditable(int row, int column) {
						if (column != 0) {
							return true;
						}
						return false;
					}
				};
				model.addRow(new String[] { "", "Add..." });
				model.addTableModelListener(new DBtableEdit());
				table1.setModel(model);
				tableType = TableType.SESSION;
			}

		});
		c.add(addSession);

		addExam = new JButton("Show exam");
		addExam.setFont(new Font("Arial", Font.BOLD, 12));
		addExam.setSize(150, 30);
		addExam.setLocation(150, 250);
		addExam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] data = new String[0][0];
				Object[] columnsHeader = new String[] { "Id", "Teacher Name", "Lesson Name", "Mark", "IdSession" };
				try {
					data = sqltest.get("SELECT * FROM exam", 5);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				DefaultTableModel model = new DefaultTableModel(data, columnsHeader) {
					@Override
					public boolean isCellEditable(int row, int column) {
						if (column != 0) {
							return true;
						}
						return false;
					}
				};
				model.addRow(new String[] { "", "Add..." });
				model.addTableModelListener(new DBtableEdit());
				table1.setModel(model);
				tableType = TableType.EXAM;
			}
		});
		c.add(addExam);

		push = new JButton("Push");
		push.setFont(new Font("Arial", Font.BOLD, 12));
		push.setSize(100, 30);
		push.setLocation(900, 520);
		push.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sqlCommandInsert = "INSERT INTO";
				String sqlCommandUpdate = "UPDATE";
				switch (tableType) {
				case EXAM:
					sqlCommandInsert += " exam(teacher_name, lesson_name, mark, idsession) VALUES";
					sqlCommandUpdate += " exam SET ";
					break;
				case STUDENT:
					sqlCommandInsert += " students(name) VALUES";
					sqlCommandUpdate += " students SET ";
					break;
				case RECORDBOOK:
					sqlCommandInsert += " recordbook(idstudents) VALUES";
					sqlCommandUpdate += " recordbook SET ";
					break;
				case SESSION:
					sqlCommandInsert += " session(idrecordbook) VALUES";
					sqlCommandUpdate += " session SET ";
					break;
				}

				ArrayList<DBUpdatable> forInsert = new ArrayList<DBUpdatable>();
				DefaultTableModel model = (DefaultTableModel) table1.getModel();
				for (int i = 0; i < model.getRowCount() - 1; i++) {
					if (model.getValueAt(i, 0).equals((Object) "")) {
						switch (tableType) {
						case EXAM:
							forInsert.add(new exam((String) model.getValueAt(i, 1), (String) model.getValueAt(i, 2),
									Integer.parseInt((String) model.getValueAt(i, 3)),
									Integer.parseInt((String) model.getValueAt(i, 4))));
							break;
						case STUDENT:
							forInsert.add(new students((String) model.getValueAt(i, 1)));
							break;
						case RECORDBOOK:
							forInsert.add(new recordbook(Integer.parseInt((String) model.getValueAt(i, 1))));
							break;
						case SESSION:
							forInsert.add(new session(Integer.parseInt((String) model.getValueAt(i, 1))));
							break;
						}
					}
				}

				String[] updateArray = new String[forUpdate.size()];

				for (Integer i : forUpdate) {
					switch (tableType) {
					case EXAM:
						updateArray[i] = sqlCommandUpdate + "teacher_name = " + (String) model.getValueAt(i, 1)
								+ "lesson_name = " + (String) model.getValueAt(i, 2) + "mark = "
								+ Integer.parseInt((String) model.getValueAt(i, 1)) + "idsession = "
								+ Integer.parseInt((String) model.getValueAt(i, 1));
						break;
					case STUDENT:
						forInsert.add(new students((String) model.getValueAt(i, 1)));
						break;
					case RECORDBOOK:
						forInsert.add(new recordbook(Integer.parseInt((String) model.getValueAt(i, 1))));
						break;
					case SESSION:
						forInsert.add(new session(Integer.parseInt((String) model.getValueAt(i, 1))));
						break;
					}
				}

				for (DBUpdatable i : forInsert) {
					sqlCommandInsert += "(" + i.toString() + "),";
				}
				if (forInsert.size() != 0) {
					sqlCommandInsert = sqlCommandInsert.substring(0, sqlCommandInsert.length() - 1) + ";";
					try {
						sqltest.set(sqlCommandInsert);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		c.add(push);

		setVisible(true);
	}
}

interface DBUpdatable {
	public String sqlInsert();
	public String sqlUpdate();
	
	public void convert(JTable table, int row);
}

class exam implements DBUpdatable {
	public int ID;
	public String teacher_name;
	public String lesson_name;
	public int mark;
	public int idsession;

	exam() {
		ID = 0;
		teacher_name = null;
		lesson_name = null;
		mark = 0;
		idsession = 0;
	}

	exam(String teacher_name, String lesson_name, int mark, int idsession) {
		ID = 0;
		this.teacher_name = teacher_name;
		this.lesson_name = lesson_name;
		this.mark = mark;
		this.idsession = idsession;
	}
	
	@Override
	public void convert(JTable table, int row) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		ID = 0;
		this.teacher_name = (String) model.getValueAt(row, 1);
		this.lesson_name = (String) model.getValueAt(row, 2);
		this.mark = Integer.parseInt((String) model.getValueAt(row, 3));
		this.idsession = Integer.parseInt((String) model.getValueAt(row, 4));
	}

	@Override
	public String sqlInsert() {
		return "INSET INTO exam(teacher_name, lesson_name, mark, idsession) VALUES(" + 
				"\"" + teacher_name + "\", \"" + lesson_name + "\", " + Integer.toString(mark) + ", "
				+ Integer.toString(idsession) + ");";
	}

	@Override
	public String sqlUpdate() {
		return "UPDATE exam SET " +
				"teacher_name = \"" + teacher_name + "\", lesson_name =  \"" + lesson_name + "\", mark = "
				+ Integer.toString(mark) + ", idsession = " + Integer.toString(idsession) + ");";
	}

}

class recordbook implements DBUpdatable {
	public int ID;
	public int idstudents;

	recordbook(int idstudents) {
		ID = 0;
		this.idstudents = idstudents;
	}

	recordbook() {
		ID = 0;
		idstudents = 0;
	}

	@Override
	public void convert(JTable table, int row) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		ID = 0;
		this.idstudents = Integer.parseInt((String) model.getValueAt(row, 1));
	}
	
	@Override
	public String sqlInsert() {
		return "INSERT INTO students(name) VALUES(" + 
				Integer.toString(idstudents) + ");";
	}

	@Override
	public String sqlUpdate() {
		return "idstudents = " + Integer.toString(idstudents);
	}
}

class session implements DBUpdatable {
	public int ID;
	public int idrecordbook;

	session(int idrecordbook) {
		ID = 0;
		this.idrecordbook = idrecordbook;
	}

	session() {
		ID = 0;
		idrecordbook = 0;
	}

	@Override
	public void convert(JTable table, int row) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		ID = 0;
		this.idrecordbook = Integer.parseInt((String) model.getValueAt(row, 1));
	}
	
	@Override
	public String sqlInsert() {
		return Integer.toString(idrecordbook);
	}

	@Override
	public String sqlUpdate() {
		return "idrecordbook = " + Integer.toString(idrecordbook);
	}
}

class students implements DBUpdatable {
	public int ID;
	public String name;

	students(String name) {
		ID = 0;
		this.name = name;
	}

	students() {
		ID = 0;
		name = null;
	}

	@Override
	public void convert(JTable table, int row) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		ID = 0;
		this.name = (String) model.getValueAt(row, 1);
	}
	
	@Override
	public String sqlInsert() {
		return "\"" + name + "\"";
	}

	@Override
	public String sqlUpdate() {
		return "name = \"" + name + "\"";
	}
}
