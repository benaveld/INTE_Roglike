package rougelikeLibrary;

public class Map {

	int width;
	int height;
	
	public Map(int width, int height)
	{
		if (width <= 0 || height <= 0)
		{
			throw new IllegalArgumentException("Height and width needs to both be bigger than 0");
		}
		this.width = width;
		this.height = height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
