import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import java.awt.Insets;


public class MineSweeper {

	private JFrame frame;
	private int height;
	private int width;
	private int numOfMines;
	private int[][]grid;
	private boolean isFirst;
	private boolean[][] selected;
	private boolean[][] flagged;
	private boolean isGameOver;
	//may need instance variable for difficulty - private int difficulty;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MineSweeper window = new MineSweeper();
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
	public MineSweeper() {
		width=8;
		height=8;
		numOfMines = 10;
		grid = new int [8][8];
		isGameOver = false;
		flagged = new boolean [8][8];
		selected = new boolean [8][8];
		isFirst = true;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 200, 475, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JMenu mnDifficulty = new JMenu("Difficulty");
		mnOptions.add(mnDifficulty);

		JMenuItem beginnerDifficulty = new JMenuItem("Beginner");
		beginnerDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				height = 8;
				width = 8;
				numOfMines = 10;
				grid = new int[height][width];
				isGameOver = false;
				selected = new boolean [8][8];
				flagged = new boolean [8][8];
				frame.getContentPane().removeAll();
				frame.setBounds(500, 200, 475, 580);
				SwingUtilities.updateComponentTreeUI(frame);
				generateMines();
				JToggleButton[][] mineField = new JToggleButton[height][width];
				generateBoard(height,width,mineField);
			}
		});
		mnDifficulty.add(beginnerDifficulty);

		JMenuItem interDifficulty = new JMenuItem("Intermediate");
		interDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				height = 12;
				width = 12;
				numOfMines = 30;
				grid = new int[height][width];
				isGameOver = false;
				selected = new boolean [12][12];
				flagged = new boolean [12][12];
				frame.getContentPane().removeAll();
				frame.setBounds(500, 200, 475, 580);
				SwingUtilities.updateComponentTreeUI(frame);
				generateMines();
				JToggleButton[][] mineField = new JToggleButton[height][width];
				generateBoard(height,width,mineField);
			}
		});
		mnDifficulty.add(interDifficulty);

		JMenuItem advancedDifficulty = new JMenuItem("Advanced");
		advancedDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				height = 16;
				width = 32;
				numOfMines = 99;
				grid = new int[height][width];
				isGameOver = false;
				selected = new boolean [16][32];
				flagged = new boolean [16][32];
				frame.getContentPane().removeAll();
				frame.setBounds(400, 160, 925, 680);
				SwingUtilities.updateComponentTreeUI(frame);
				generateMines();
				JToggleButton[][] mineField = new JToggleButton[height][width];
				generateBoard(height,width,mineField);
			}
		});
		mnDifficulty.add(advancedDifficulty);


		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		grid = new int[8][8];
		generateMines();
		JToggleButton[][] mineField = new JToggleButton[height][width];
		generateBoard(height,width,mineField);	
	}

	public void generateMines()
	{
		isFirst = true;
		for(int i=0; i<height; i++)
			for(int j = 0; j<width; j++)
			{
				selected[i][j] = false;
				flagged[i][j] = false;
			}

		int totalCells = height*width;
		for(int i=0; i<grid.length; i++)
			for(int j = 0; j<grid[0].length; j++)
				grid[i][j] = 0;

		for(int i = 0; i<numOfMines; i++)
		{
			int location = (int)(Math.random()*totalCells);
			if(grid[location/width][location%width] != -1)
				grid[location/width][location%width] = -1;
			else
				i--;
		}


		//4 corners - 4 sides - general cell
		//0,0  height-1,0  0,width-1

		if(grid[0][0]!=-1)
		{
			int touching = 0;
			if(grid[0][1]==-1)
				touching++;
			if(grid[1][1]==-1)
				touching++;
			if(grid[1][0]==-1)
				touching++;
			grid[0][0] = touching;
		}	
		if(grid[height-1][0]!=-1)
		{
			int touching = 0;
			if(grid[height-2][0]==-1)
				touching++;
			if(grid[height-2][1]==-1)
				touching++;
			if(grid[height-1][1]==-1)
				touching++;
			grid[height-1][0]=touching;
		}
		if(grid[0][width-1]!=-1)
		{
			int touching = 0;
			if(grid[0][width-2]==-1)
				touching++;
			if(grid[1][width-1]==-1)
				touching++;
			if(grid[1][width-2]==-1)
				touching++;
			grid[0][width-1] = touching;
		}
		if(grid[height-1][width-1]!=-1)
		{
			int touching = 0;
			if(grid[height-2][width-1]==-1)
				touching++;
			if(grid[height-1][width-2]==-1)
				touching++;
			if(grid[height-2][width-2]==-1)
				touching++;
			grid[height-1][width-1] = touching;
		}

		//left column[x,0]

		for(int j=1; j<height-1; j++)
		{
			if(grid[j][0]!=-1)
			{
				int touching = 0;
				if(grid[j-1][0]==-1)
					touching++;
				if(grid[j-1][1]==-1)
					touching++;
				if(grid[j][1]==-1)
					touching++;
				if(grid[j+1][1]==-1)
					touching++;
				if(grid[j+1][0]==-1)
					touching++;
				grid[j][0]=touching;
			}
		}

		//right column
		for(int j=1; j<height-1; j++)
		{
			if(grid[j][width-1]!=-1)
			{
				int touching = 0;
				if(grid[j-1][width-1]==-1)
					touching++;
				if(grid[j-1][width-2]==-1)
					touching++;
				if(grid[j][width-2]==-1)
					touching++;
				if(grid[j+1][width-2]==-1)
					touching++;
				if(grid[j+1][width-1]==-1)
					touching++;
				grid[j][width-1]=touching;
			}
		}
		//top row

		for(int j=1; j<width-1; j++)
		{
			if(grid[0][j]!=-1)
			{
				int touching = 0;
				if(grid[0][j-1]==-1)
					touching++;
				if(grid[1][j-1]==-1)
					touching++;
				if(grid[1][j]==-1)
					touching++;
				if(grid[1][j+1]==-1)
					touching++;
				if(grid[0][j+1]==-1)
					touching++;
				grid[0][j]=touching;
			}
		}

		//bottom row
		for(int j=1; j<width-1; j++)
		{
			if(grid[height-1][j]!=-1)
			{
				int touching = 0;
				if(grid[height-1][j-1]==-1)
					touching++;
				if(grid[height-2][j-1]==-1)
					touching++;
				if(grid[height-2][j]==-1)
					touching++;
				if(grid[height-2][j+1]==-1)
					touching++;
				if(grid[height-1][j+1]==-1)
					touching++;
				grid[height-1][j]=touching;
			}
		}

		//general cell
		for(int i = 1; i<grid.length-1; i++) {

			for(int j = 1; j<grid[0].length-1; j++) {
				if(grid[i][j] != -1) {
					int touching = 0;
					if(grid[i-1][j-1] == -1)
						touching++;
					if(grid[i-1][j] == -1)
						touching++;
					if(grid[i-1][j+1] == -1)
						touching++;
					if(grid[i][j-1] == -1)
						touching++;
					if(grid[i][j+1] == -1) 
						touching++;
					if(grid[i+1][j-1] == -1)
						touching++;
					if(grid[i+1][j] == -1) 
						touching++;
					if(grid[i+1][j+1] == -1)
						touching++;

					grid[i][j] = touching;
				}
			}
		}
	}

	public void showMines(JToggleButton[][]mineField)
	{
		isGameOver=true;
		//mineField[i][j].set
		for(int i=0; i<height;i++)
		{
			for(int j=0; j<width; j++)
			{
				//mineField.clone()[i][j].setEnabled(false);
				if(grid[i][j]==-1)
					mineField[i][j].setText(Integer.toString(grid[i][j]));
			}
		}
	}

	public boolean checkWin(JToggleButton[][] mineField)
	{
		for(int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				if(grid[i][j]>=0 && !selected[i][j])
					return false;
			}
		}
		isGameOver = true;
		
		for(int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				if(grid[i][j]==-1)
					mineField[i][j].setBackground(Color.GREEN);;
			}
		}
		return true;
	}

	public void expand(JToggleButton[][]mineField, int x, int y)
	{
		//<base case first>, <>
		if(selected[x][y])
			return;
		selected[x][y]=true;
		displayNumber(mineField[x][y], grid[x][y]);
		//mineField[x][y].setText(Integer.toString(grid[x][y]));
		mineField[x][y].setSelected(true);
		//mineField[x][y].setEnabled(false);

		if(grid[x][y]==0)
		{
			if(y>0)
			{
				//check left
				expand(mineField,x,y-1);
				if(x==0)
					expand(mineField,x+1,y-1);
				else if(x==height-1)
					expand(mineField,x-1,y-1);
				else
				{
					expand(mineField,x+1,y-1);
					expand(mineField,x-1,y-1);
				}
			}
			if(x>0)
			{
				//check above
				expand(mineField,x-1,y);
				if(y==0)
					expand(mineField,x-1,y+1);
				else if(y==width-1)
					expand(mineField,x-1,y-1);
				else
				{
					expand(mineField,x-1,y+1);
					expand(mineField,x-1,y-1);
				}
			}
			if(y<width-1)
			{
				//check right
				expand(mineField,x,y+1);
				if(x==0)
					expand(mineField,x+1,y+1);
				else if(x==height-1)
					expand(mineField,x-1,y+1);
				else
				{
					expand(mineField,x+1,y+1);
					expand(mineField,x-1,y+1);
				}
			}
			if(x<height-1)
			{
				//check below
				expand(mineField,x+1,y);
				if(y==0)
					expand(mineField,x+1,y+1);
				else if(y==width-1)
					expand(mineField,x+1,y-1);
				else
				{
					expand(mineField,x+1,y+1);
					expand(mineField, x+1, y-1);
				}
			}
		}
	}
	public void displayNumber(JToggleButton cell,int value)
	{
		switch(value)
		{
		case 1:
			cell.setForeground(Color.BLUE);
			cell.setText("1");
			break;
		case 2:
			cell.setForeground(Color.GREEN);
			cell.setText("2");
			break;
		case 3:
			cell.setForeground(Color.RED);
			cell.setText("3");
			break;
		case 4:
			cell.setForeground(Color.CYAN);
			cell.setText("4");
			break;
		case 5:
			cell.setForeground(Color.MAGENTA);
			cell.setText("5");
			break;
		case 6:
			cell.setForeground(Color.ORANGE);
			cell.setText("6");
			break;
		case 7:
			cell.setForeground(Color.PINK);
			cell.setText("7");
			break;
		case 8:
			cell.setForeground(Color.GRAY);
			cell.setText("8");
			break;
		}
	}

	public void generateBoard(int height, int width, JToggleButton[][] mineField)
	{
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				GridBagConstraints c = new GridBagConstraints();
				mineField[i][j] = new JToggleButton(" ");
				mineField[i][j].setFont(new Font("Tahoma", Font.BOLD, 10));
				mineField[i][j].setMargin(new Insets(0,0,0,0));
				c.weightx=0.25;
				c.weighty=0.25;
				c.gridx=j;
				c.gridy=i+1;
				c.fill=GridBagConstraints.BOTH;
				JToggleButton temp = mineField[i][j];

				int x=i;
				int y=j;

				mineField[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {

						if(isGameOver)
						{
							if(selected[x][y])
								mineField[x][y].setSelected(true);
							else
								mineField[x][y].setSelected(false);
							return;
						}
						if(selected[x][y])
						{
							mineField[x][y].setSelected(true);
						}

						if(arg0.isShiftDown())
						{
							//put or remove red flag (yellow cell)

							flagged[x][y]=!flagged[x][y];
							if(flagged[x][y])
								mineField[x][y].setText("F");
							else
								mineField[x][y].setText("");
						}

						if(flagged[x][y])
						{
							mineField[x][y].setSelected(true);
							return;
						}

						if(isFirst)
						{
							while(grid[x][y]!=0) 
							{	
								generateMines();
							}
							isFirst=false;
						}

						if(grid[x][y]==-1)
						{
							showMines(mineField);
						}
						else if(grid[x][y]==0)
						{
							expand(mineField,x,y);
						}
						else
						{
							selected[x][y]=true;
							displayNumber(temp,grid[x][y]);
							//temp.setText(Integer.toString(grid[x][y]));
							temp.setSelected(true);
						}
						
						checkWin(mineField);
						
					}
				});

				frame.getContentPane().add(mineField[i][j],c);
			}
		}

		JButton refresh = new JButton();
		GridBagConstraints gbc = new  GridBagConstraints();
		gbc.gridy=0;
		gbc.gridx=width/2-1;
		gbc.gridwidth=2;
		gbc.weighty=0.5;
		gbc.weightx=0.25;
		gbc.fill=GridBagConstraints.BOTH;

		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.getContentPane().removeAll();
				SwingUtilities.updateComponentTreeUI(frame);
				isGameOver = false;
				isFirst=true;
				generateMines();
				generateBoard(height,width,mineField);
			}
		});

		frame.getContentPane().add(refresh,gbc);
	}
}