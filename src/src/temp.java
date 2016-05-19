package Agenda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

public class GUI extends JFrame {

	private Schedule schedule;
	private JPanel westBorder;
	private JPanel center;
	private JTable table;
	private JButton addPerformance;
	private JButton addStage;
	private TableModel tableModel = new TableModel();
	private ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
	private ArrayList<Artist> artists = new ArrayList<Artist>();
	private ArrayList<Stage> stages;
	private short state = 0, buttonState = 0;
	private boolean artistGenreMode = true;
	private boolean maxStages = false;
	private String title;
	private JLabel titleLabel;
	private int scheduleStart;
	private int scheduleStop;
	private String scheduleName = "Festival";
	private JLabel nameLabel ;
	private String date;

	public static void main(String s[]) {
		new GUI();
	}

	public GUI() {
		super("Agenda");
		setSize(1000, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		makeFrame();
		getScheduleTime();
		schedule = new Schedule(scheduleStart, scheduleStop);
		timeSlots = new ArrayList<TimeSlot>();
		this.artists = schedule.getArtist();
	}

	public void getScheduleTime() {

		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		int hoursStart;
		int hoursStop;
		String name;

		Object[] message = { "Start Uur", field1, "Eind Uur:", field2, "Festival naam", field3};
		int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			hoursStart = Integer.parseInt(field1.getText());
			hoursStop = Integer.parseInt(field2.getText());
			name = field3.getText();
			if (hoursStart <= hoursStop && hoursStart < 25 && hoursStop < 25) {
				scheduleStart = hoursStart;
				scheduleStop = hoursStop;
				scheduleName = name;
				nameLabel.setText(scheduleName+" - "+date);
				
			} else {
				JOptionPane.showMessageDialog(null, "Geen geldige schedule tijd", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
		repaint();
	}

	private void makeFrame() {

		JPanel content = new JPanel(new BorderLayout());
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Simulator");
		JMenuItem menuItem = new JMenuItem("New Simulator");

		JPanel northBorder = new JPanel(new BorderLayout());
		JPanel northNorth = new JPanel(new FlowLayout());
		nameLabel = new JLabel(scheduleName+" - "+date);
		northNorth.setBorder(blackline);
		northNorth.add(nameLabel);
		northBorder.add(northNorth, BorderLayout.CENTER);
		northBorder.add(menubar, BorderLayout.NORTH);
		content.add(northBorder, BorderLayout.NORTH);
		
		menubar.add(menu);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFrame simulatorFrame = new JFrame("Simulator");
				simulatorFrame.setSize(700,700);
				simulatorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				JPanel content = new Tiled.TiledMap(schedule);
				content.setBackground(Color.black);
				simulatorFrame.setContentPane(content);
				simulatorFrame.setResizable(false);
				simulatorFrame.setVisible(true);
				simulatorFrame.setBackground(Color.black);
			}
			
			
		});

		westBorder = new JPanel();
		westBorder.setBorder(blackline);
		westBorder.setLayout(new BoxLayout(westBorder, BoxLayout.Y_AXIS));
		westBorder.add(new JLabel(" "));
		JButton artistAndGenre = new JButton("Artist & Genre");
		artistAndGenre.setSize(100, 200);
		artistAndGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = 0;
				switchState();
			}
		});

		westBorder.add(artistAndGenre);
		content.add(westBorder, BorderLayout.WEST);

		JPanel eastBorder = new JPanel();
		eastBorder.setBorder(blackline);
		eastBorder.setLayout(new BoxLayout(eastBorder, BoxLayout.Y_AXIS));
		eastBorder.add(new JLabel(" "));
		addStage = new JButton("Add stage");
		addStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStagePopUp();
			}
		});
		eastBorder.add(addStage);

		addPerformance = new JButton("Add show");
		addPerformance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addShow();
			}
		});
		eastBorder.add(addPerformance);
		addPerformance.setEnabled(false);

		JButton saveFile = new JButton("Save");
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schedule.saveSchedule();
			}
		});
		eastBorder.add(saveFile);

		JButton loadFile = new JButton("Load");
		loadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Schedule temp = Schedule.load();
					if (temp != null) {
						schedule = temp;
						artists = temp.getArtist();
						stages = temp.getStages();
						addButtonsLoad();
						switchState();
						tableModel.refresh();
						repaint();

					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		eastBorder.add(loadFile);

		content.add(eastBorder, BorderLayout.EAST);

		
		JPanel southBorder = new JPanel();
		southBorder.setBorder(blackline);
		southBorder.add(new JLabel(" "));
		content.add(southBorder, BorderLayout.SOUTH);

		center = new JPanel(new BorderLayout());
		JPanel centerNorth = new JPanel(new FlowLayout());
		// centerNorth.add(new JLabel("Artist & Genres"));

		table = new JTable(tableModel);
		center.add(new JScrollPane(table), BorderLayout.CENTER);

		switchState();

		centerNorth.add(titleLabel);
		centerNorth.setBorder(blackline);
		center.add(centerNorth, BorderLayout.NORTH);

		content.add(center, BorderLayout.CENTER);

		setContentPane(content);
		setVisible(true);
	}

	public void addStagePopUp() {

		JTextField field1 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field5 = new JTextField();

		int hoursStart;
		int hoursStop;
		int minuteStart;
		int minuteStop;
		int timeSlotLength;
		String stageName;

		Object[] message = { "BeginTijd", field1, "eindtijd", field3, "Naam", field5};
		int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {

			hoursStart = Integer.parseInt(field1.getText());
			minuteStart = 00;
			hoursStop = Integer.parseInt(field3.getText());
			minuteStop = 0;
			stageName = (String) field5.getText();
			timeSlotLength = 30;
			if (minuteStart >= 60) {
				minuteStart -= 60;
				hoursStart += 1;
			}
			if (minuteStop >= 60) {
				minuteStop -= 60;
				hoursStop += 1;
			}
			int timeStart = hoursStart * 100 + minuteStart;
			int timeStop = hoursStop * 100 + minuteStop;
			if (schedule.getScheduleStartTime() <= hoursStart && schedule.getScheduleStopTime() >= hoursStop
					&& hoursStart < hoursStop) {
				schedule.addStage(stageName, timeStart, timeStop, timeSlotLength);
				makeButton(stageName);
			} else {
				JOptionPane.showMessageDialog(null, "Stage valt buiten schema", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void addShow() {

		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();

		String artistName = null;
		String genre = null;
		int timeSlot = 0;
		int popularity = 0;

		Object[] message = { "Artist Name: ", field1, "Genre: ", field2, "Popularity: ", field4, "TimeSlot: ", field3 };
		int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			for (Artist testArtist : artists) {
				// System.out.println(testArtist.getName());
			}

			try {
				artistName = field1.getText();
				genre = field2.getText();
				timeSlot = Integer.parseInt(field3.getText());
			} catch (NumberFormatException e) {
			}
			ArrayList<Stage> stages = new ArrayList<Stage>();
			stages = schedule.getStages();
			try {
				Stage currentStage = stages.get(state - 1);
				Artist artist = null;
				boolean artistSet = false;
				popularity = Integer.parseInt(field4.getText());
				if (popularity > 100)
					popularity = 100;
				

				for (Artist currentArtist : artists) {
					if (currentArtist.getName().equals(artistName)) {
						artist = currentArtist;
						artistSet = true;
					}
				}
				if (!schedule.checkDoubleBooking(artist, timeSlot, currentStage)|| !currentStage.getTimeSlot(timeSlot).getOccupied()) {
					if (artistSet) {

						try {
							currentStage.scheduleArtist(timeSlot, artist,popularity);
							fillTimeslots(currentStage);
						}

						catch (Exception e) {
						}
					} else {
						artist = new Artist(artistName, genre);
						artists.add(artist);

						currentStage.scheduleArtist(timeSlot, artist,popularity);
						fillTimeslots(currentStage);
						schedule.setArtists(artists);
					}
				} else {
					JOptionPane.showMessageDialog(null, "De artiest is dubbel geboekt", "Error",
							JOptionPane.INFORMATION_MESSAGE);

				}
			} catch (IndexOutOfBoundsException e) {

			}
			tableModel.refresh();
			repaint();
		}
	}

	public void makeButton(String name) {

		JButton button = new JButton(name);
		buttonState++;
		button.addActionListener(new ButtonListener(buttonState));
		if (buttonState > 4)
			addStage.setEnabled(false);
		westBorder.add(button);

		revalidate();
	}

	public void switchState() {
		try {
			ArrayList<Stage> stages = schedule.getStages();
			switch (state) {
			case 0:
				titleLabel.setText("Artist & Genres");
				fillTimeslotsAll();
				artistGenreMode = true;
				tableModel.fireTableStructureChanged();
				addPerformance.setEnabled(false);
				break;
			case 1:
				titleLabel.setText(stages.get(0).getName());
				artistGenreMode = false;
				tableModel.fireTableStructureChanged();
				fillTimeslots(stages.get(0));
				addPerformance.setEnabled(true);
				break;
			case 2:
				titleLabel.setText(stages.get(1).getName());
				artistGenreMode = false;
				tableModel.fireTableStructureChanged();
				addPerformance.setEnabled(true);
				fillTimeslots(stages.get(1));
				break;
			case 3:
				titleLabel.setText(stages.get(2).getName());
				artistGenreMode = false;
				tableModel.fireTableStructureChanged();
				addPerformance.setEnabled(true);
				fillTimeslots(stages.get(2));
				break;
			case 4:
				titleLabel.setText(stages.get(3).getName());
				artistGenreMode = false;
				tableModel.fireTableStructureChanged();
				addPerformance.setEnabled(true);
				fillTimeslots(stages.get(3));
				break;
			case 5:
				titleLabel.setText(stages.get(4).getName());
				artistGenreMode = false;
				tableModel.fireTableStructureChanged();
				addPerformance.setEnabled(true);
				fillTimeslots(stages.get(4));
				break;

			}
		} catch (NullPointerException e) {
			titleLabel = new JLabel("Artist & Genres");
		}
		tableModel.refresh();
		repaint();
	}

	private void addButtonsLoad() {
		westBorder.removeAll();
		state = 0;
		buttonState = 0;
		westBorder.add(new JLabel(" "));
		JButton artistAndGenre = new JButton("Artist & Genre");
		artistAndGenre.setSize(100, 200);
		artistAndGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = 0;
				switchState();
			}
		});
		westBorder.add(artistAndGenre);
		ArrayList<Stage> tempList = schedule.getStages();
		Stage tempStage;
		for (int i = 0; i < tempList.size(); i++) {
			tempStage = tempList.get(i);
			makeButton(tempStage.getName());
		}

	}

	private class ButtonListener implements ActionListener {
		private short buttonState;

		public ButtonListener(short buttonState) {
			this.buttonState = buttonState;
		}

		public void actionPerformed(ActionEvent e) {
			state = buttonState;
			switchState();
		}
	}

	public void fillTimeslots(Stage currentStage) {
		timeSlots.clear();
		ArrayList<TimeSlot> tempList = currentStage.getTimeSlots();
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).checkIsOccupied())
				timeSlots.add(tempList.get(i));
		}
	}

	public void fillTimeslotsAll() {
		timeSlots.clear();
		ArrayList<Stage> stagesTemp = schedule.getStages();
		for (int x = 0; x < stagesTemp.size(); x++) {
			Stage currentStage = stagesTemp.get(x);
			ArrayList<TimeSlot> tempList = currentStage.getTimeSlots();
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).checkIsOccupied())
					timeSlots.add(tempList.get(i));
			}
		}
	}

	public class TableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			if (artistGenreMode == true)
				return 6;
			else
				return 5;
		}

		@Override
		public int getRowCount() {
			if (timeSlots.size() != 0)
				return timeSlots.size();
			else
				return 0;
		}

		public Object getValueAt(int row, int column) {
			TimeSlot t = null;
			if (timeSlots != null && timeSlots.size() > row)
				t = timeSlots.get(row);
			else
				return "";

			switch (column) {
			case 0:
				return t.getArtist().getName();
			case 1:
				return t.getArtist().getGenre();
			case 2:
				return t.timeSlotStartToString();
			case 3:
				return t.timeSlotEndToString();
			case 4:
				return t.getPopularity();
			case 5:
				return t.getStageName();
			}
			return "";
		}

		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Artiest";
			case 1:
				return "Genre";
			case 2:
				return "Start";
			case 3:
				return "End";
			case 4:
				return "Popularity";
			case 5:
				return "Stage";
			}
			return "";
		}

		public void refresh() {
			fireTableDataChanged();

		}

	}

}
