package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CacheUnitView extends Observable implements View {

	private static final String STATISTIC_REQUEST = "STATISTIC";
	private static final String CLIENT_REQ = "CLIENT";
	private JTextArea mTextArea;

	public CacheUnitView() {
	}

	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	protected void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("CacheUnitUI");
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window Closing");
				super.windowClosing(e);
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mTextArea = new JTextArea(20, 20);
		mTextArea.setEditable(false);

		BufferedImage myImage;
		try {
			myImage = ImageIO.read(this.getClass().getResource("/images/backgroundpic.jpeg"));
			frame.setContentPane(new ImagePanel(myImage));
		} catch (IOException e2) {
			System.out.println("File Cant Found");
		}

		JButton file = new JButton("Load a Request");
		ImageIcon imageIconRequest = new ImageIcon(this.getClass().getResource("/images/filepic.png"));
		imageIconRequest = resize(imageIconRequest);
		file.setIcon(imageIconRequest);

		JButton statistic = new JButton("Show Statistic");
		ImageIcon imageIconStatistic = new ImageIcon(this.getClass().getResource("/images/statisticpic.png"));
		imageIconStatistic = resize(imageIconStatistic);
		statistic.setIcon(imageIconStatistic);

		JButton client = new JButton("Number of connected clients");
		ImageIcon imageIconClient = new ImageIcon(this.getClass().getResource("/images/clientpic.jpeg"));
		imageIconClient = resize(imageIconClient);
		client.setIcon(imageIconClient);

		frame.setLayout(new FlowLayout());

		file.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				JFileChooser chooser = new JFileChooser();
				FileFilter filefilter = new FileNameExtensionFilter("", "json");
				chooser.setFileFilter(filefilter);
				int returnVal = chooser.showOpenDialog(chooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File jsonFile = chooser.getSelectedFile();
					Scanner input;
					try {
						input = new Scanner(jsonFile);
						while (input.hasNext()) {
							sb.append(input.nextLine());
							sb.append("\n");
						}
						input.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						System.out.println("No Json File was Selected");
					}
				}
				updateUIData(sb.toString());
			}
		});

		statistic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateUIData(STATISTIC_REQUEST);

			}
		});

		client.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateUIData(CLIENT_REQ);
			}
		});
		Box vbox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		hBox.add(file);
		hBox.add(statistic);
		hBox.add(client);
		vbox.add(hBox);

		vbox.add(mTextArea);
		frame.getContentPane().add(vbox, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900, 500);
		// Display the window.
		frame.setVisible(true);
	}

	public void updateGui(String str) {
		if (str != null) {
			mTextArea.setText(str);
			mTextArea.setFont(new Font("Calibri", Font.PLAIN, 24));
			mTextArea.setForeground(Color.DARK_GRAY);
		}
	}

	public <T> void updateUIData(T t) {
		setChanged();
		notifyObservers(t);
	}

	public ImageIcon resize(ImageIcon imageicon) {
		Image image = imageicon.getImage(); // transform it
		Image newimg = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // scale it the smooth way
		imageicon = new ImageIcon(newimg); // transform it back
		return imageicon;

	}

	class ImagePanel extends JComponent {
		private Image image;

		public ImagePanel(Image image) {
			this.image = image;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}
}
