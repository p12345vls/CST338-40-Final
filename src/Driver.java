
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Driver class, creates a cardPanel to handle all three panels: thumbnail
 * Panel,PicChanger Panel, and PicSource Panel -- contains main()
 *
 * @author Pavlos Papadonikolakis 04/18/2018
 */
public class Driver extends JFrame
{

	private JPanel picSourcePanel;
	private JPanel homePanel;
	public static CardLayout cardView;
	public static JPanel cardPanel;
	private JButton okButton;
	public static Icon home;
	public static JLabel homeLabel;

	/**
	 * Default constructor
	 */
	public Driver()
	{
		super("Picture Album");
		initComponents();
	}

	/**
	 * sets the components of the class
	 */
	private void initComponents()
	{
		setSize(700, 605);
		setLocationRelativeTo(null);
		setResizable(false);

		home = new ImageIcon("src/images/home3.png");
		homeLabel = new JLabel(home);

		okButton = new JButton("OK");

		cardView = new CardLayout();
		cardPanel = new JPanel(cardView);

		picSourcePanel = new JPanel();
		JPanel southPanel = new JPanel();
		southPanel.add(okButton);
		picSourcePanel.setLayout(new BorderLayout());
		picSourcePanel.add(southPanel, BorderLayout.SOUTH);
		picSourcePanel.add(new PicSource());

		cardPanel.add("picChangerPanel", new PicChanger());
		cardPanel.add("thumpPanel", new ThumbnailPic());

		okButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				homeLabel.setVisible(false);
				cardView.show(cardPanel, "picChangerPanel");
				new ThumbnailPic().setVisible(false);
			}
		});

		homeLabel.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				homeLabel.setVisible(false);
				cardView.show(cardPanel, "picSourcePanel");
			}
		});

		cardPanel.add("picSourcePanel", picSourcePanel);
		cardView.show(cardPanel, "picSourcePanel");

		homePanel = new JPanel();
		homePanel.setLayout(new BorderLayout());
		homePanel.add(homeLabel, BorderLayout.SOUTH);
		homeLabel.setVisible(false);

		add(homePanel, BorderLayout.SOUTH);
		getContentPane().add(cardPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// main method
	// ==================================================
	public static void main(String[] args)
	{

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				// uncomment if running on macOS

//				try
//				{
//					javax.swing.UIManager.setLookAndFeel(
//							"javax.swing.plaf.nimbus.NimbusLookAndFeel");
//				} catch (ClassNotFoundException | InstantiationException
//						| IllegalAccessException
//						| javax.swing.UnsupportedLookAndFeelException e)
//				{
//
//					e.printStackTrace();
//				}
//
//				javax.swing.UIManager.put("swing.boldMetal", Boolean.TRUE);
				new Driver();
			}
		});
	}

}
