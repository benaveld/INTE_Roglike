package rougelikeLibrary;

public class Map {

	private int width;
	private int height;
	private Player player;
	private Player[][] points; 
	
	
	public Map(int width, int height)
	{
		if (width <= 0 || height <= 0)
		{
			throw new IllegalArgumentException("Height and width needs to both be bigger than 0");
		}
		this.width = width;
		this.height = height;
		points = new Player[width][height];
	}
	
	public void addPlayer(Player p, int x, int y)
	{
		if (x < 0 || x >= width || y < 0 || y >= height)
		{
			throw new IndexOutOfBoundsException("Posistion needs to be between (0,0) and (width,height)");
		}
		player = p;
		player.setPosistion(x, y);
		points[x][y] = player;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Boolean checkSpotEmpty(int x, int y)
	{
		return points[x][y] == null;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
