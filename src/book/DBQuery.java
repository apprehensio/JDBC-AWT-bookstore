package book;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private JScrollPane scrollPane;
	private boolean scroll_exist=false;
	
	private Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBQuery window = new DBQuery();
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
	public DBQuery() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try{
			conn=DriverManager.getConnection("jdbc:mysql://localhost/chenglong?" +
				                                   "user=root&password=1a2b3c4d");
			}
		catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
				
			}
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] columnNames = {"ISBN",
                "Title",
                "Author",
                "Year",
                "Edition",
                "Publisher"};
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblEnterIsbn = new JLabel("Enter ISBN:");
		panel_1.add(lblEnterIsbn);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		
		JLabel lblEnterName = new JLabel("Enter Name:");
		panel_3.add(lblEnterName);
		
		textField_1 = new JTextField();
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		
		
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					String isbn=textField.getText();
					String name=textField_1.getText();
					String sql="SELECT * FROM book WHERE title LIKE"+ "'%"+name+"%' AND isbn LIKE"+"'%"+isbn+"%'";
					int i=0;
					
					Statement stmt=conn.createStatement();					
					ResultSet rs=stmt.executeQuery(sql);
					int rowcount = 0;
					
					
					while(rs.next()) {
					  rowcount+=1;
					}
					
					Statement stmt2=conn.createStatement();
					ResultSet rs2=stmt2.executeQuery(sql);
					
					Object [][] data = new Object[rowcount][6];
					while (rs2.next()) {
						for(int j=0;j<6;j++){
							data[i][j]=rs2.getObject(j+1);
						}
						i+=1;					  
					}
					
					if(scroll_exist==true){
						frame.getContentPane().remove(scrollPane);
					}
					table = new JTable(data, columnNames);
					scrollPane = new JScrollPane(table);
					frame.getContentPane().add(scrollPane);
					frame.revalidate();
				    frame.repaint();
				    scroll_exist=true;
				}catch (SQLException ex) {
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}		
				}
		});
		panel_2.add(btnNewButton);
	}

}
