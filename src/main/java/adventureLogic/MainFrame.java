package adventureLogic;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField playerInputTextField;
	private static JPanel inventoryPane;
	private JLabel lblPicks;
	private static JLabel lblKnivesNumber;
	private static JLabel lblPicksNumber;
	private static JTextArea txtArea;
	private static String currentText = "";

	private static final byte MAX_SHOWED_TEXT_LINES = 35;
	private static final String HELP_MESSAGE = "\n\"help\" per mostrare questo messaggio;"
			+ "\n(\"north\", \"south\", ...) per muoverti in quella direzione;"
			+ "\n\"look\" per osservare la stanza in cui ti trovi;" + "\n\"inventory\" per mostrare l'inventario;"
			+ "\n\"quit\" per salvare ed uscire;" + "\n\"reset\" per eliminare i dati salvati e rincominciare da capo;"
			+ "\n\"scoreboard\" per mostrare la tabella dei punteggi;"
			+ "\n\"reset\" per mostrare le tue statistiche attuali;" + "\n\"stop\" per fermare la musica;"
			+ "\n\"resume\" per far rincominciare la musica;" + "\n\"clear\" per pulire lo schermo;"
			+ "\n\"pick\" per raccogliere gli oggetti nella stanza;"
			+ "\n\"Use <nome oggetto>\" per usare l'oggetto nel tuo inventario;"
			+ "\nIMPORTANTE: Se entri in una stanza in cui c'è qualcuno, o torni da\ndove sei venuto oppure verrai scoperto."
			+ "\nNOTA: Sono disponibili versioni corte dei comandi (\"h\" per \"help\" ad esempio).\n";
	private JLabel lblSmokeGrenades;
	private static JLabel lblSmokeGrenadesNumber;

	public MainFrame() {
		setResizable(false);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 667);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		JButton btnShowInventory = new JButton("");
		btnShowInventory.setBounds(718, 11, 49, 49);
		contentPane.add(btnShowInventory);
		btnShowInventory.setIcon(new ImageIcon(".\\images\\InventoryButtonIcon.png"));

		btnShowInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showOrHideInventory();

			}
		});

		playerInputTextField = new JTextField();
		playerInputTextField.setBackground(Color.WHITE);
		playerInputTextField.setBounds(10, 597, 757, 20);
		contentPane.add(playerInputTextField);
		playerInputTextField.setColumns(10);

		playerInputTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!playerInputTextField.getText().isBlank()) {
					updateOutputText(playerInputTextField.getText());
					Parser.parse(playerInputTextField.getText());
					playerInputTextField.setText("");
				}
			}
		});

		inventoryPane = new JPanel();
		inventoryPane.setBackground(Color.ORANGE);
		inventoryPane.setBounds(553, 11, 165, 191);
		contentPane.add(inventoryPane);
		inventoryPane.setLayout(null);
		inventoryPane.setVisible(false);

		lblPicks = new JLabel("Grimaldelli");
		lblPicks.setBounds(10, 11, 64, 51);
		inventoryPane.add(lblPicks);

		lblPicksNumber = new JLabel("1");
		lblPicksNumber.setBounds(85, 22, 30, 29);
		inventoryPane.add(lblPicksNumber);

		JLabel lblKnives = new JLabel("Coltelli");
		lblKnives.setBounds(10, 59, 64, 51);
		inventoryPane.add(lblKnives);

		lblKnivesNumber = new JLabel("1");
		lblKnivesNumber.setBounds(70, 73, 24, 29);
		inventoryPane.add(lblKnivesNumber);

		lblSmokeGrenades = new JLabel("Granate \r\nfumogene");
		lblSmokeGrenades.setBounds(10, 113, 111, 51);
		inventoryPane.add(lblSmokeGrenades);

		lblSmokeGrenadesNumber = new JLabel("1");
		lblSmokeGrenadesNumber.setBounds(117, 124, 24, 29);
		inventoryPane.add(lblSmokeGrenadesNumber);

		txtArea = new JTextArea();
		txtArea.setToolTipText("Inserisci il testo qui");
		txtArea.setBounds(10, 11, 757, 575);
		txtArea.setBackground(Color.WHITE);
		getContentPane().add(txtArea);

		contentPane.updateUI();
	}

	public static void showOrHideInventory() {
		inventoryPane.setVisible(!inventoryPane.isShowing());
	}

	public static void printHelp() {
		updateOutputText(HELP_MESSAGE);
	}

	public static void updateOutputText(String newText) {

		if (!currentText.equals("")) {
			currentText = currentText + "\n" + newText;
		} else {
			currentText = newText;
		}

		boolean flag = true;
		String[] lines = currentText.split("\r\n|\r|\n");

		if (lines.length > MAX_SHOWED_TEXT_LINES) {
			while (flag) {
				currentText = removeFirstLineOfString(currentText);
				lines = currentText.split("\r\n|\r|\n");
				flag = lines.length > MAX_SHOWED_TEXT_LINES;
			}
		}

		txtArea.setText(currentText);
	}

	private static String removeFirstLineOfString(String str) {

		int stringStartingPoint = str.indexOf("\n") + 1;

		if (stringStartingPoint != -1) {

			char[] strCharArray = str.toCharArray();
			str = "";

			for (int i = stringStartingPoint; i < strCharArray.length; i++) {
				System.out.print(System.getProperty("line.separator"));
				str += strCharArray[i];
			}
		}

		return str;
	}

	public static void clear() {
		currentText = "";
		txtArea.setText("");
	}

	public static void updateInventoryUI() {
		inventoryPane.setVisible(true);
		lblKnivesNumber.setText(Integer.toString(Adventure.getInventory().getNumOfKnives()));
		lblPicksNumber.setText(Integer.toString(Adventure.getInventory().getNumOfPicks()));
		lblSmokeGrenadesNumber.setText(Integer.toString(Adventure.getInventory().getNumOfSmokeGrenades()));
		inventoryPane.setVisible(false);
	}
}
