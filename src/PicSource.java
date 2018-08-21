import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 *Creates a table to display the info about the pictures.
 */
public class PicSource extends JPanel
{

	/**
	 * Default constructor
	 */
	public PicSource()
	{

		String columnNames[] = { "Name", "Original Size", "Source" };
		
		JTable table = new JTable(getPicData(), columnNames);
		table.setFont(new Font("Serif", Font.BOLD, 15));
		table.setEnabled(false);
		table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 15));

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(200);		
		columnModel.getColumn(1).setPreferredWidth(40);		
		columnModel.getColumn(2).setPreferredWidth(150);	
		table.setRowHeight(20);
	
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(690, 500));
		scrollPane.setBorder(
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * Gets the Original Picture Size
	 * @param imageNum the image number from the image file
	 * @return the width and height concatenated to a string
	 */
	public static String getOriginalPicSize(Integer imageNum)
	{
		String imagePath="src/images/"+imageNum.toString()+".jpg";
	
		File imgPath = new File(imagePath);
		
		BufferedImage bufferedImage = null;
		try
		{
			bufferedImage = ImageIO.read(imgPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return bufferedImage.getRaster().getWidth() + " X "
				+ bufferedImage.getHeight();
	}

	/**
	 * 
	 * @return a String array with the info of the pictures 
	 */
	public String [][] getPicData()
	{
		String picData[][] = new String[15][3];
		Integer fileNum=1;
		for (int i = 0; i < picData.length; i++)
		{

			for (int j = 0; j < picData[i].length; j++)
			{
				picData[i][j] = getOriginalPicSize(fileNum);
			
				if (j % 2 == 0)
				{
					picData[i][j] = "https://hubble25th.org/images/";
				}
				if (j % 3 == 0)
				{
					picData[i][j] = PicChanger.title[i];
				}
			}
			fileNum++;
			System.out.println();
		}
		return picData;
	}

}
