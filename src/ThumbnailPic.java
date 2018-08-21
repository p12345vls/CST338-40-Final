
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;


/**
 * Displays thumbnail pictures
 *
 */
public class ThumbnailPic extends JPanel
{
	private JPanel southPanel;
	private JLabel picDescription;
	private Icon home;
	private JLabel homeLabel;
	private Icon tumbPicture;
	private JLabel tumbLabel;
	protected JPanel picPanel;
	private JLabel photographLabel;
	private JToolBar buttonBar;
	protected JScrollPane scroll;
	private BlankIcon blankImage;

	/**
	 * Default constructor
	 */
	public ThumbnailPic()
	{
		blankImage = new BlankIcon();

		picDescription = new JLabel();
		picDescription.setFont(new Font("Serif", Font.BOLD, 20));
		picDescription.setForeground(Color.BLUE);

		home = new ImageIcon("src/images/home.png");
		homeLabel = new JLabel(home);
		tumbPicture = new ImageIcon("src/images/thambnail.png");
		tumbLabel = new JLabel(tumbPicture);

		picPanel = new JPanel();
		picPanel.setBackground(Color.black);
		picPanel.setLayout(new BorderLayout());

		photographLabel = new JLabel();
		photographLabel.setBorder(new EmptyBorder(0, 100, 0, 0));

		buttonBar = new JToolBar();
		buttonBar.setLayout(new GridLayout(5, 3, 30, 30));

		JPanel southCenter = new JPanel();
		southCenter.setBackground(Color.WHITE);
		southCenter.add(homeLabel);
		southCenter.add(tumbLabel);
		southPanel = new JPanel(new BorderLayout());
		southPanel.add(southCenter, BorderLayout.CENTER);

		scroll = new JScrollPane(buttonBar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());
		upperPanel.add(picDescription);

		picPanel.add(photographLabel, BorderLayout.CENTER);
		picPanel.add(upperPanel, BorderLayout.NORTH);

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		add(picPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);

		tumbLabel.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				picPanel.setVisible(false);
				scroll.setVisible(true);
				homeLabel.setVisible(true);
			}

		});

		homeLabel.addMouseListener(new java.awt.event.MouseAdapter()
		{

			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				homeLabel.setVisible(true);
				Driver.cardView.show(Driver.cardPanel, "picSourcePanel");
			}

		});

		loadimages.execute();
	}

	/**
	 * Loads the images and calls publish when a new one is ready to be
	 * displayed.
	 */
	private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>()
	{
		String dir = "images/";
		String fileExtension = ".jpg";
		Integer fileNum = 1;

		@Override
		protected Void doInBackground() throws Exception
		{
			for (int i = 0; i < PicChanger.title.length; i++)
			{
				ImageIcon icon;
				icon = createImageIcon(dir + fileNum.toString() + fileExtension,
						PicChanger.title[i]);
				fileNum++;
				
				ThumbnailAction action;
				if (icon != null)
				{
					ImageIcon thumbnailIcon = new ImageIcon(
							getScaledImage(icon.getImage(), 150, 150));

					action = new ThumbnailAction(icon, thumbnailIcon,
							PicChanger.title[i]);

				} else
				{
					action = new ThumbnailAction(blankImage, blankImage,
							PicChanger.title[i]);
				}
				publish(action);
			}

			return null;
		}

		@Override
		protected void process(List<ThumbnailAction> chunks)
		{
			for (ThumbnailAction action : chunks)
			{
				JButton thumbButton = new JButton(action);
				buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
			}
		}
	};

	/**
	 * Creates an ImageIcon if the path is valid.
	 *
	 * @param path
	 *           resource path
	 * @param description
	 *           description of the file
	 */
	protected ImageIcon createImageIcon(String path, String description)
	{
		ImageIcon icon;
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null)
		{
			icon = new ImageIcon(imgURL, description);

		} else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
		return new ImageIcon(getScaledImage(icon.getImage(), 500, 500));
	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 *
	 * @param srcImg
	 *           source image to scale
	 * @param w
	 *           width
	 * @param h
	 *           height
	 * @return resized image
	 */
	protected Image getScaledImage(Image srcImg, int w, int h)
	{
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Action class that shows the image specified in it's constructor.
	 */
	private class ThumbnailAction extends AbstractAction
	{

		/**
		 * The icon if the full image we want to display.
		 */
		private Icon displayPhoto;

		/**
		 * @param Icon
		 *           - The full size photo to show in the button.
		 * @param Icon
		 *           - The thumbnail to show in the button.
		 * @param desc
		 *           - The description of the icon.
		 */
		public ThumbnailAction(Icon photo, Icon thumb, String desc)
		{
			displayPhoto = photo;
			putValue(SHORT_DESCRIPTION, desc);
			putValue(LARGE_ICON_KEY, thumb);
		}

		/**
		 * Shows the full image
		 */
		public void actionPerformed(ActionEvent e)
		{
			photographLabel.setIcon(displayPhoto);
			picDescription.setText(getValue(SHORT_DESCRIPTION).toString());
			picPanel.setVisible(true);
			scroll.setVisible(false);

		}

	}
}
