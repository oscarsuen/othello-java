import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionsLife extends JPanel implements ActionListener, ChangeListener {
	DisplayLife myDisplay;
	JTextArea coding;
	JButton runMe;
	JSlider fps;
	JSlider runs;
	JComboBox mode;
	String[] modes = {"LIFE","REPLICATOR","SEEDS","LIFE_WO_DEATH","LIFE_34","DIAMOEBA","TWO_BY_TWO","HIGH_LIFE","DAY_NIGHT","MORLEY"};
	boolean runningLoop;
	JLabel expectedTime;

	boolean[][] grid = new boolean[120][120];

	public OptionsLife(DisplayLife d) {

		myDisplay = d;
		setBackground(Color.yellow);
		this.setLayout(new FlowLayout());
		runningLoop = false;

		JLabel s = new JLabel("fps");
		this.add(s);

		fps = new JSlider(1, 20);
		fps.setVisible(true);
		fps.setMajorTickSpacing(5);
		fps.setMinorTickSpacing(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);
		fps.setValue(1);
		fps.addChangeListener(this);
		this.add(fps);

		JLabel a = new JLabel("cycles");
		this.add(a);

		runs = new JSlider(1, 500);
		runs.setVisible(true);
		runs.setMajorTickSpacing(100);
		runs.setMinorTickSpacing(50);
		runs.setPaintTicks(true);
		runs.setPaintLabels(true);
		runs.setValue(1);
		runs.addChangeListener(this);
		this.add(runs);

		runMe = new JButton("Run!");
		this.add(runMe);
		runMe.addActionListener(this);

		expectedTime = new JLabel("Expected Time: " + 1 + " sec");
		this.add(expectedTime);

		mode = new JComboBox(modes);
		mode.setSelectedIndex(0);
		mode.addActionListener(this);
		this.add(mode);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == runMe) {
			
			runningLoop = true;
			runLoop();
		}
		if(evt.getSource() == mode){
			JComboBox cb = (JComboBox)evt.getSource();
			myDisplay.game.setRule((String)cb.getSelectedItem());
			System.out.println("Setting game");
			
		}

	}

	// this function will keep running until stop is pressed.
	public void runLoop() {
		for (int i = 0; i < runs.getValue(); i++) {
			// process the code, make changes to boolean array
			// and pass it to display
			myDisplay.getGame().step();
			myDisplay.paint();

			if (i != runs.getValue() - 1) {
				try {
					Thread.sleep((int) (1000 / fps.getValue()));
				} catch (InterruptedException e) {
				}
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		expectedTime.setText("Expected Time: "
				+ ((int) (runs.getValue() / fps.getValue())) + " sec ");
	}
}
