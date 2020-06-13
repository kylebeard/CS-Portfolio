/**
 * Water Data Swing GUI for the Environmental Center.
 * Updated: 12/14/2018
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.*;
import javax.swing.table.AbstractTableModel;
import com.sun.net.httpserver.Authenticator.Success;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.*;

public class WaterGUI extends JFrame {

	////////Instance Variables////////
	private JButton invoiceButton = new JButton("Enter New Invoice");
	private JButton accountButton = new JButton("Enter New Account");
	private JButton accountInfo = new JButton("View All Accounts");
	private JButton allInvoices = new JButton("All Invoices by Account");
	private JButton fiscalYear = new JButton("Total Usage and Spending for Fiscal Year");
	private JButton byMonth = new JButton("Total Usage and Spending for Fiscal Year by Month");
	private JButton byAccount = new JButton("Total Usage and Spending for Fiscal Year by Account");
	private JButton deleteAccount = new JButton("Delete Account");
	private JButton deleteInvoice = new JButton("Delete Invoice");

	private JPanel main = new JPanel(new GridLayout(0, 1));

	//Current year
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	//SQL Query Object
	private SQLMethods query = new SQLMethods();

	//Database URL - Make sure the driver manager is in the class path when running program 
	private static final String DB_URL = "jdbc:sqlite:waterdata.db";

	//Font type
	Font fontHeading = new Font("Calibri", Font.BOLD, 20);
	Font font = new Font("Calibri", Font.PLAIN, 16);

	//Table Models
	private AccountTableModel accountTableModel;
	private JTable accountTable;
	private YearTableModel yearTableModel;
	private JTable yearTable;
	private InvoiceTableModel invoiceTableModel;
	private JTable invoiceTable;
	private Month monthInstance;
	private MonthAccount monthAccount;
	private MonthAccountTableModel monthAccountTableModel;
	private MonthTableModel monthTableModel;
	private YearAccount yearAccount;
	private YearAccountTableModel yearAccountTableModel;
	

	////////Constructors////////
	public WaterGUI() {
		super("Westminster College Environmental Center Water Data");

		//Calls methods below.
		setupLayout();
		addListeners();

		//Sets size of main screen.
		setSize(1000,600);

		//center JFrame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		//Stops the program after exiting out
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public WaterGUI(String title) {
		super(title);
		setSize(600,400);

		//Calls methods below.
		setupLayout();
		addListeners();

		//center JFrame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		//stops the program after exiting out
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	////////Methods////////
	/**
	 * Sets up the layout for the main JFrame.
	 */
	private void setupLayout() {
		main.setBorder(new EmptyBorder(10,10,10,10));
		setLayout(new BorderLayout(25, 25));
		JLabel welcome = new JLabel ("Welcome! Please enter a new account or invoice below:");
		JLabel searching = new JLabel("Search the database by the following:");
		JLabel delete = new JLabel("Delete from database:");
		welcome.setFont(fontHeading);
		searching.setFont(fontHeading);
		invoiceButton.setFont(font);
		accountButton.setFont(font);
		accountInfo.setFont(font);
		allInvoices.setFont(font);
		fiscalYear.setFont(font);
		byMonth.setFont(font);
		byAccount.setFont(font);
		deleteAccount.setFont(font);
		deleteInvoice.setFont(font);
		delete.setFont(fontHeading);
		main.add(welcome);
		main.add(invoiceButton);
		main.add(accountButton);
		main.add(searching);
		main.add(accountInfo);
		main.add(allInvoices);
		main.add(fiscalYear);
		main.add(byMonth);
		main.add(byAccount);
		main.add(delete);
		main.add(deleteAccount);
		main.add(deleteInvoice);
		welcome.setHorizontalAlignment(JLabel.CENTER);
		searching.setHorizontalAlignment(JLabel.CENTER);
		delete.setHorizontalAlignment(JLabel.CENTER);
		add(main, BorderLayout.CENTER);

	}

	/**
	 * Adds listeners to the main JFrame buttons.
	 */
	private void addListeners() {
		//If the invoice button is clicked, the addInvoice method is called.
		invoiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addInvoice();
			}
		});

		//If the account button is clicked, the addAccount method is called.
		accountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addAccount();
			}
		});

		//If the usage by fiscal year button is clicked, the addAccount method is called.
		fiscalYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				queryFiscalYear();
			}
		});

		//If the usage by month button is clicked, the usageMonth method is called.
		byMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usageMonth();
			}
		});

		//If the usage by account per year button is clicked, the usageAccount method is called.
		byAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usageAccount();
			}
		});

		//If the usage by account per year button is clicked, the addAccount method is called.
		deleteAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteAcc();
			}
		});

		//If the usage by account per month button is clicked, the addAccount method is called.
		deleteInvoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteInv();
			}
		});

		//If the usage by account per month button is clicked, the addAccount method is called.
		allInvoices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				invoices();
			}
		});

		//If the usage by account per month button is clicked, the addAccount method is called.
		accountInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accountInfo();
			}
		});
	} 

	/**
	 * Method that prompts user for data to enter a new invoice to the database.
	 */
	private void addInvoice() {
		//Creates a new window for when adding a new invoice.
		JFrame invoice = new JFrame("Add New Invoice");
		JPanel invoice2 = new JPanel(new GridLayout(0,2));
		JButton invoiceOK = new JButton("OK");
		JButton invoiceCancel = new JButton("Cancel");
		invoice2.setBorder(new EmptyBorder(10,10,10,10));
		invoiceOK.setFont(fontHeading);
		invoiceCancel.setFont(fontHeading);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		invoice.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		invoice.setVisible(true);
		invoice.setSize(675, 575);

		//Date spinners:
		JSpinner dueDate = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateFormat = new JSpinner.DateEditor(dueDate, "yyyy-MM-dd");
		dueDate.setEditor(dateFormat);
		JSpinner serviceTo = new JSpinner(new SpinnerDateModel());
		dateFormat = new JSpinner.DateEditor(serviceTo, "yyyy-MM-dd");
		serviceTo.setEditor(dateFormat);
		JSpinner serviceFrom = new JSpinner(new SpinnerDateModel());
		dateFormat = new JSpinner.DateEditor(serviceFrom, "yyyy-MM-dd");
		serviceFrom.setEditor(dateFormat);

		//Invoice attributes:
		JTextField invoiceNum = new JTextField();
		JTextField accountNum = new JTextField();
		JTextField waterCharge = new JTextField();
		JTextField sewerCharge = new JTextField();
		JTextField sanitationCharge = new JTextField();
		JTextField stormWater = new JTextField();
		JTextField streetLight = new JTextField();
		JTextField franchFee = new JTextField();
		JTextField adjustments = new JTextField();
		JTextField totalCharges = new JTextField();
		JTextField billingUnits = new JTextField();

		//Array of invoice attributes as Strings.
		String[] asString = {"Invoice Number: (ex. 12345678910) ", "Account Number: (ex. 1234567)",
				"Water Charge: (ex. XXX.XX)", "Sewer Charge: (XXX.XX)", "Sanitation Charge: (ex. XXX.XX)",
				"Storm Water: (ex. XXX.XX)", "Street Light: (ex. XXX.XX)", "Franchise Fee: (ex. XXX.XX)",
				"Adjustments: (ex. XXX.XX)", "Total Charges: (ex. XXX.XX)", "Billing Units: "};
		//"Due Date (yyyy-mm-dd): ", "Service From (yyyy-mm-dd): ", "Service To (yyyy-mm-dd): "};

		//Array of invoice attributes as JTextFields.
		JTextField[] variables = {invoiceNum, accountNum, 
				waterCharge, sewerCharge, sanitationCharge, 
				stormWater, streetLight, franchFee, 
				adjustments, totalCharges, billingUnits}; 
		//dueDate, serviceFrom, serviceTo};

		//Add the text fields for User Entry in a pop-up JFrame.
		JLabel warning = new JLabel("**Please follow example formatting.**");
		warning.setFont(font);
		invoice2.add(warning);
		invoice2.add(new JLabel());

		for(int i = 0; i < variables.length; i++) {
			//asString[i].setFont(font);
			JLabel add = new JLabel(asString[i]);
			add.setFont(font);
			invoice2.add(add);
			invoice2.add(variables[i]);
		}

		String[] dates = {"Due Date (yyyy-mm-dd): ", "Service From (yyyy-mm-dd): ", "Service To (yyyy-mm-dd): "};
		JSpinner[] dateSpinners = {dueDate, serviceFrom, serviceTo};

		//Add date spinners:
		for (int i = 0; i < dates.length; i++) {
			JLabel add = new JLabel(dates[i]);
			add.setFont(font);
			invoice2.add(add);
			invoice2.add(dateSpinners[i]);
		}

		//Adds a blank line before adding OK and Cancel buttons.
		invoice2.add(new JLabel());
		invoice2.add(new JLabel());
		invoice2.add(invoiceCancel);
		invoice2.add(invoiceOK);

		//Adds panel to the invoice pop-up JFrame. 
		invoice.add(invoice2, BorderLayout.NORTH);

		//If the invoiceOK button is clicked, the invoice data is entered into the database.
		invoiceOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String invoiceNumInput = invoiceNum.getText();
				String accountNumInput = accountNum.getText();
				String waterChargeInput = waterCharge.getText();
				String sewerChargeInput = sewerCharge.getText();
				String sanitationChargeInput = sanitationCharge.getText();
				String stormWaterInput = stormWater.getText();
				String streetLightInput = streetLight.getText();
				String franchFeeInput = franchFee.getText();
				String adjustmentsInput = adjustments.getText();
				String totalChargesInput = totalCharges.getText();
				String billingUnitsInput = billingUnits.getText();
				String dueDateInput = new SimpleDateFormat("yyyy-MM-dd").format(dueDate.getValue());
				String serviceFromInput = new SimpleDateFormat("yyyy-MM-dd").format(serviceFrom.getValue());
				String serviceToInput = new SimpleDateFormat("yyyy-MM-dd").format(serviceTo.getValue());

				boolean success = true;
				try (Connection conn = DriverManager.getConnection(DB_URL)) {

					PreparedStatement invoiceStatement = conn.prepareStatement(
							"INSERT INTO Invoice(invoiceNum, accountNum, waterCharge, sewerCharge, sanitationCharge, stormCharge, streetLight, franchFee, adjustments, totalCharges, dueDate, serviceTo, serviceFrom) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

					invoiceStatement.setString(1, invoiceNumInput);
					invoiceStatement.setString(2, accountNumInput);
					invoiceStatement.setString(3, waterChargeInput);
					invoiceStatement.setString(4, sewerChargeInput);
					invoiceStatement.setString(5, sanitationChargeInput);
					invoiceStatement.setString(6, stormWaterInput);
					invoiceStatement.setString(7, streetLightInput);
					invoiceStatement.setString(8, franchFeeInput);
					invoiceStatement.setString(9, adjustmentsInput);
					invoiceStatement.setString(10, totalChargesInput);
					invoiceStatement.setString(11, billingUnitsInput);
					invoiceStatement.setString(12, dueDateInput);
					invoiceStatement.setString(13, serviceFromInput);
					invoiceStatement.setString(14, serviceToInput);

					invoiceStatement.executeUpdate();

				}      catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Database error!", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
					success = false;
				}

				cancel(invoice);
				if (success == true) {
					JOptionPane.showMessageDialog(null, "Entry Successful!", "Invoice Added", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		//If the invoiceCancel button is clicked, the cancel method is called.
		invoiceCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel(invoice);
			}
		});

	}

	/**
	 * Method that prompts user for data to enter a new account to the database.
	 */
	private void addAccount() {
		//JWindow account = new JWindow();
		JFrame account = new JFrame();
		JPanel account2 = new JPanel(new GridLayout(0, 2));
		JButton accountOK = new JButton("OK");
		JButton accountCancel = new JButton("Cancel");
		account2.setBorder(new EmptyBorder(10,10,10,10));
		accountOK.setFont(fontHeading);
		accountCancel.setFont(fontHeading);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		account.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		account.setVisible(true);
		account.setSize(650, 350);

		//Arrays for JComboBox entries.
		String[] values = {"Y", "N"};

		JTextField accountNum = new JTextField();
		JTextField accountName = new JTextField();
		JTextField accountStreet = new JTextField();
		JTextField accountState = new JTextField();
		JTextField accountZip = new JTextField();
		JComboBox<String> current = new JComboBox<String>(values);

		String[] asString = {"Account Number: (ex. 1234567) ", "Account Name: (ex. RESIDENCE)", "Street Address: (ex. 1234 Street Ave)",
				"State: (ex. UT)", "Zip Code: (ex. 84105)"};

		JTextField[] variables = {accountNum, accountName, accountStreet,
				accountState, accountZip};

		JLabel warning = new JLabel("**Please follow example formatting.**");
		warning.setFont(font);
		account2.add(warning);
		account2.add(new JLabel());

		//Add the text fields for User Entry in a pop-up JFrame.
		for(int i = 0; i < variables.length; i++) {
			JLabel add = new JLabel(asString[i]);
			add.setFont(font);
			account2.add(add);
			account2.add(variables[i]);
		}

		//Add combo box.
		JLabel cur = new JLabel("Is the account current?");
		cur.setFont(font);
		account2.add(cur);
		current.setFont(font);
		account2.add(current);

		//Adds a blank line before adding OK and Cancel buttons.
		account2.add(new JLabel());
		account2.add(new JLabel());
		account2.add(accountCancel);
		account2.add(accountOK);

		//Adds panel to the invoice pop-up JFrame. 
		account.add(account2, BorderLayout.NORTH);

		//If the accountOK button is clicked, the account is added to the database.
		accountOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String accountNumInput = accountNum.getText();
				String accountNameInput = accountName.getText();
				String streetInput = accountStreet.getText();
				String stateInput = accountState.getText();
				String zipInput = accountZip.getText();
				String currentInput = current.getSelectedItem().toString();
				//System.out.printf("%s\n%s\n%s\n%s\n%s\n%S\n\n", accountNumInput, accountNameInput, streetInput, stateInput, zipInput, currentInput);

				boolean success = true;
				try (Connection conn = DriverManager.getConnection(DB_URL)) {
					PreparedStatement accountStatement = conn.prepareStatement(
							"INSERT INTO Account(accountNum, accountName, street, state, zip, current)" 
									+ "VALUES (?, ?, ?, ?, ?, ?);");

					accountStatement.setString(1, accountNumInput);
					accountStatement.setString(2, accountNameInput);
					accountStatement.setString(3, streetInput);
					accountStatement.setString(4, stateInput);
					accountStatement.setString(5, zipInput);
					accountStatement.setString(6, currentInput);

					accountStatement.executeUpdate();

				}      catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Unable to connect to the database. :(", "Database error!", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
					success = false;
				}     
				cancel(account);
				if (success == true) {
					JOptionPane.showMessageDialog(null, "Entry Successful! :)", "Account Added", JOptionPane.INFORMATION_MESSAGE);
				}                   
			}
		});

		// If the invoiceCancel button is clicked, the cancel method is called.
		accountCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel(account);
			}
		});

	}

	/**
	 * Queries fiscal years, user inputs the starting year.
	 */
	private void queryFiscalYear() {
		JSpinner year = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateFormat = new JSpinner.DateEditor(year, "yyyy");
		year.setEditor(dateFormat);

		JLabel yearLabel = new JLabel("For the fiscal year beginning: ");
		yearLabel.setFont(font);

		//SQLMethods query = new SQLMethods;
		Object[] message = {yearLabel, year};
		int option = JOptionPane.showConfirmDialog(null, message, "Query Fiscal Year", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String yearString = new SimpleDateFormat("yyyy-MM-dd").format(year.getValue());
			yearString = yearString.substring(0,4);
			fiscalYearTable(yearString);

			}
	} 

	/**
  * Helper method to create the fiscal year info table.
	*/
	  private void fiscalYearTable(String fiscalYear) {
		JDialog fiscalYearQuery = new JDialog();
		setLayout(new BorderLayout(5, 5));
		yearTableModel = new YearTableModel();
		List<Year> years = fetchFiscalYear(fiscalYear);
		yearTableModel.addAll(years);
		yearTable = new JTable(yearTableModel);
		fiscalYearQuery.add(new JScrollPane(yearTable), BorderLayout.CENTER);
		fiscalYearQuery.setSize(800, 575);
		fiscalYearQuery.setVisible(true);
	}

	private List<Year> fetchFiscalYear(String fiscalYear) {
		List<Year> yearList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			   PreparedStatement statement = conn.prepareStatement(
							"SELECT serviceFrom, sum(billingUnits*748) AS gallons, sum(totalCharges) FROM Invoice" 
							+ "WHERE serviceFrom >= ? AND <= ?;"
							);
			   int fy = Integer.parseInt(fiscalYear);
			   fy++;
			   String next = Integer.toString(fy);
			   next = "'" + next + "-06-30'";
			   fiscalYear = "'" + fiscalYear + "-07-01'";
			   statement.setString(1, fiscalYear);
			   statement.setString(2, next);
			   ResultSet results = statement.executeQuery();
			   while (results.next()) {
					 String fiscal = results.getString("serviceFrom");
					 Float gallons = results.getFloat("gallons"),
								   charges = results.getFloat("sum(totalCharges)");
					 Year y = new Year(fiscal, gallons, charges);
					 yearList.add(y);
			   }
		} catch (SQLException ex) {
			   JOptionPane.showMessageDialog(this,
							"Error communicating with database: " + ex.getMessage(),
							"Database error",
							JOptionPane.ERROR_MESSAGE);
		}
		return yearList;
 	}  	

	/**
	 * Queries fiscal years for a select month, user inputs months and starting year.
	 */
	private void usageMonth() {

		JSpinner year = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateFormat = new JSpinner.DateEditor(year, "yyyy");
		year.setEditor(dateFormat);

		JLabel yearLabel = new JLabel("For the fiscal year beginning: ");
		yearLabel.setFont(font);

		Object[] message = {yearLabel, year};
		int option = JOptionPane.showConfirmDialog(null, message, "Monthly Usage and Spending", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String yearString = new SimpleDateFormat("yyyy-MM-dd").format(year.getValue());
			int yearInt = Integer.parseInt(yearString);
			int end = currentYear;

			if (yearInt <= currentYear) {
				//try connection
				//perform query
				//show table
			}
		} 
	}

	/**
	 * Queries fiscal years for a single account, user inputs accountNum and starting year.
	 */
	private void usageAccount() {

		JSpinner year = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateFormat = new JSpinner.DateEditor(year, "yyyy");
		year.setEditor(dateFormat);

		JLabel yearStr = new JLabel("For the fiscal year beginning: ");

		yearStr.setFont(font);

		Object[] message = {yearStr, year};
		int option = JOptionPane.showConfirmDialog(null, message, "Monthly Usage and Spending", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String startString = year.getValue().toString();
			int start = Integer.parseInt(startString);
			int end = currentYear;

			if (start <= currentYear) {
				//try connection
				//perform query
				//show table
			}
		} 
	}

	/**
	 * Deletes an invoice.
	 */
	private void deleteInv() {

		JTextField invoice = new JTextField();
		JLabel info = new JLabel("Delete the following invoice: ");
		info.setFont(font);

		Object[] message = {info, invoice};
		int option = JOptionPane.showConfirmDialog(null, message, "Delete Invoice", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String accountString = invoice.getText();

			boolean success = true;
			try (Connection conn = DriverManager.getConnection(DB_URL)) {
				PreparedStatement statement = conn.prepareStatement(
						"DELETE FROM Invoice WHERE invoiceNum = ?"
						);
				statement.setString(1, accountString);
				statement.executeUpdate();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Database error!", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
				success = false;
			}
			if (success == true) {
				JOptionPane.showMessageDialog(null, "Update Successful!", "Invoice Deleted", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	/**
	 * Deletes an account.
	 */
	private void deleteAcc() {
		JTextField account = new JTextField();
		JLabel info = new JLabel("Delete the following account: ");
		info.setFont(font);

		Object[] message = {info, account};
		int option = JOptionPane.showConfirmDialog(null, message, "Delete Account", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String accountString = account.getText();

			boolean success = true;
			try (Connection conn = DriverManager.getConnection(DB_URL)) {
				PreparedStatement statement = conn.prepareStatement(
						"DELETE FROM Account WHERE accountNum = ?"
						);
				statement.setString(1, accountString);
				statement.executeUpdate();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Database error!", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
				success = false;
			}
			if (success == true) {
				JOptionPane.showMessageDialog(null, "Update Successful!", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
			}

		} 
	}

	/**
	 * Show all invoices for an user input account number.
	 */
	private void invoices() {
		JTextField account = new JTextField();
		JLabel info = new JLabel("Show all invoices for the account: ");
		info.setFont(font);

		Object[] message = {info, account};
		int option = JOptionPane.showConfirmDialog(null, message, "Show All Invoices", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			String accountString = account.getText();
			invoicesTable(accountString);
		} 
	}

	/**
	 * Helper method to create the account info table.
	 */
	private void invoicesTable(String accountString) {
		JFrame invoiceQuery = new JFrame();
		setLayout(new BorderLayout(5, 5));
		invoiceTableModel = new InvoiceTableModel();
		List<Invoice> invoices = fetchInvoices(accountString);
		invoiceTableModel.addAll(invoices);
		invoiceTable = new JTable(invoiceTableModel);
		invoiceQuery.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
		invoiceQuery.setSize(1500, 575);
		invoiceQuery.setVisible(true);
	}

	/**
	 * Helper method to create the invoice info table.
	 */
	private List<Invoice> fetchInvoices(String accountString) {
		List<Invoice> invoices = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * FROM Invoice WHERE accountNum = ?"
					);
			statement.setString(1, accountString);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				int accountNum = results.getInt("invoiceNum"),
						invoiceNum = results.getInt("accountNum");
				Double water = results.getDouble("waterCharge"),
						sewer = results.getDouble("sewerCharge"),
						sani = results.getDouble("sanitationCharge"),
						storm = results.getDouble("stormCharge"),
						streetLight = results.getDouble("streetLight"),
						franchFee = results.getDouble("franchFee"),
						adj = results.getDouble("adjustments"),
						totalCharges = results.getDouble("totalCharges"),
						billingUnits = results.getDouble("billingUnits");
				String dueDate = results.getString("dueDate"),
						serviceFrom = results.getString("serviceFrom"),
						serviceTo = results.getString("serviceTo");
				Invoice invoice = new Invoice(accountNum, invoiceNum, water, sewer, sani, storm, streetLight, franchFee, adj, totalCharges, dueDate, serviceFrom, serviceTo, billingUnits);
				invoices.add(invoice);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					"Error communicating with database: " + ex.getMessage(),
					"Database error",
					JOptionPane.ERROR_MESSAGE);
		}
		return invoices;
	}    

	/**
	 * Shows all information about an account.
	 */
	private void accountInfo() {
		accountTable(); //1570793
	}

	/**
	 * Helper method to create the account info table.
	 */
	private void accountTable() {
		JDialog accountQuery = new JDialog();
		setLayout(new BorderLayout(5, 5));
		accountTableModel = new AccountTableModel();
		List<Account> accounts = fetchAccounts();
		accountTableModel.addAll(accounts);
		accountTable = new JTable(accountTableModel);
		accountQuery.add(new JScrollPane(accountTable), BorderLayout.CENTER);
		accountQuery.setSize(600, 575);
		accountQuery.setVisible(true);
		//cancel(accountQuery);
	}

	/**
	 * Helper method to create the account info table.
	 */
	private List<Account> fetchAccounts() {
		List<Account> accounts = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * FROM Account"
					);
			//statement.setString(1, accountString);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				int accountNum = results.getInt("accountNum");
				String accountName = results.getString("accountName"),
						street = results.getString("street"),
						state = results.getString("state"),
						zip = results.getString("zip"),
						current = results.getString("current");
				Account account = new Account(accountNum, accountName, street, state, zip, current);
				accounts.add(account);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					"Error communicating with database: " + ex.getMessage(),
					"Database error",
					JOptionPane.ERROR_MESSAGE);
		}
		return accounts;
	}    

	/**
	 * Method to close a JFrame (new account and new invoice). 
	 */
	private void cancel(JFrame close) {
		close.setVisible(false);
		close.dispose();
	}

	/**
	 * Main method to run the GUI.
	 */
	public static void main(String... args) {
		WaterGUI water = new WaterGUI();

		//displays on the screen
		water.setVisible(true);
	}
}