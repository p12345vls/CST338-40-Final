
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * PicChanger contains the panel for the image slider
 *
 */
public class PicChanger extends JPanel
{
	volatile boolean isPicSlideShow = false;
	private JLabel descriptionlabel;
	private JLabel imagelabel;
	private JPanel upperPanel;
	private int count = 0;
	private JPanel southPanel;
	public final int IMAGE_COUNT = 15;
	private ImageIcon[] image = new ImageIcon[IMAGE_COUNT];
	public static int DEFAULT_WIDTH = 500;
	public static int DEFAULT_HEIGHT = 500;
	private Timer timer;
	private int changer = 0;
	public static String[] title = { "\"Rose made of galaxies\"",
			"\"Identified in the center of NGC\"", "\"Black hole outflows\"",
			"\"Hubble’s 25th anniversary image\"",
			"\"New view of the Pillars" + " of Creation\"",
			"\"The Bubble Nebula\"", "\"The Hubble\"", "\"The Grab\"",
			"\"The Merging\"", "\"The Butterfly\"", "\"The Pillar\"",
			"\"Whirlpool\"", "\"Sagittarius\"", "\"Light\"", "\"Saturn\"" };

	/**
	 * Default constructor
	 */
	public PicChanger()
	{
		setBackground(Color.black);
		JPanel mainPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		upperPanel = new JPanel(new BorderLayout());

		Icon next = new ImageIcon("src/images/next.jpeg");
		JLabel nextLabel = new JLabel(next);
		Icon previous = new ImageIcon("src/images/previous.jpeg");
		JLabel previousLabel = new JLabel(previous);
		Icon last = new ImageIcon("src/images/last.jpeg");
		JLabel lastLabel = new JLabel(last);
		Icon home = new ImageIcon("src/images/home.png");
		JLabel homeLabel = new JLabel(home);
		Icon playOff = new ImageIcon("src/images/offButton.png");
		Icon play = new ImageIcon("src/images/onButton.png");
		JLabel playLabel = new JLabel(playOff);
		Icon tumbPicture = new ImageIcon("src/images/thambnail.png");
		JLabel tumbLabel = new JLabel(tumbPicture);

		imagelabel = new JLabel();
		descriptionlabel = new JLabel();

		loadImages();
		scaleImages(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		imagelabel.setIcon(image[0]);
		descriptionlabel.setFont(new Font("Serif", Font.BOLD, 20));
		descriptionlabel.setForeground(Color.BLUE);
		descriptionlabel.setText(title[count]);

		nextLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{

				if (count < image.length - 1)
				{
					count++;

					imagelabel.setIcon(image[count]);
					descriptionlabel.setText(title[count]);

				}
			}

		});

		previousLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{

				if (count > 0)
				{
					count--;
					imagelabel.setIcon(image[count]);
					descriptionlabel.setText(title[count]);
				}
			}

		});

		playLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{

				if (changer % 2 == 0)
				{

					playLabel.setIcon(play);
					isPicSlideShow = true;
					new Slides().start();
				} else
				{

					playLabel.setIcon(playOff);
					isPicSlideShow = false;
				}
				changer++;
			}

		});

		timer = new Timer(2000, (ActionEvent e) -> {

		});

		tumbLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				Driver.cardView.show(Driver.cardPanel, "thumpPanel");
			}

		});

		lastLabel.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				count = image.length - 1;

				imagelabel.setIcon(image[count]);
				descriptionlabel.setText(title[count]);

			}

		});

		homeLabel.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				Driver.homeLabel.setVisible(false);
				Driver.cardView.show(Driver.cardPanel, "picSourcePanel");// ===========================
			}

		});

		upperPanel.setLayout(new FlowLayout());
		upperPanel.add(descriptionlabel);

		southPanel.setBackground(Color.WHITE);

		JPanel southCenter = new JPanel();
		southCenter.setBackground(Color.WHITE);

		southCenter.add(playLabel);
		southCenter.add(homeLabel);
		southCenter.add(previousLabel);
		southCenter.add(nextLabel);
		southCenter.add(lastLabel);
		southCenter.add(tumbLabel);

		southPanel.add(southCenter, BorderLayout.CENTER);

		mainPanel.add(upperPanel, BorderLayout.NORTH);
		mainPanel.add(imagelabel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		add(mainPanel);

	}

	/**
	 * Loads the images from the file
	 */
	private void loadImages()
	{
		String dir = "src/images/";
		String fileExtension = ".jpg";

		Integer j = 1;
		for (int i = 0; i < IMAGE_COUNT; i++)
		{
			image[i] = new ImageIcon(dir + j.toString() + fileExtension);
			j++;
		}
	}

	/**
	 * Scales the images with specified size
	 */
	private void scaleImages(int width, int height)
	{
		for (int i = 0; i < IMAGE_COUNT; i++)
		{
			ThumbnailPic pic = new ThumbnailPic();
			image[i] = new ImageIcon(
					pic.getScaledImage(image[i].getImage(), width, height));
		}
	}

	// -------------private class Slides --------------------------------


	private class Slides extends Thread
	{
		/**
		 * Run method for the thread
		 */
		public void run()
		{
			while (true)
			{
				if (isPicSlideShow)
				{
					slideShow();
					doNothing(2000); // Do nothing for one full second
				} else
				{
					return;
				}
			}
		}

		/**
		 * Helper method will pause thread for amount of milliseconds given in
		 * argument
		 * 
		 * @param :int milliseconds to pause program for
		 *           
		 */
		public void doNothing(int milliseconds)
		{
			try
			{
				Thread.sleep(milliseconds);
			} catch (InterruptedException e)
			{

				System.out.println("Unexpected interrupt!");
				System.exit(0);
			}
		}

		/**
		 * Sets the Icons of the image array to be displayed through the imageLabel
		 * When the array index reaches the last index, it sets
		 * the array index to zero to continue the slide show 
		 */
		public void slideShow()
		{
			if (count < image.length - 1)
			{
				imagelabel.setIcon(image[count]);
				descriptionlabel.setText(title[count]);
			} else if (count == image.length)
			{
				count = 0;
				imagelabel.setIcon(image[count]);
				descriptionlabel.setText(title[count]);
			}
			count++;
		}

	} // End of Slides class

} // END OF PicChanger class
