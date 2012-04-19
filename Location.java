import  java.io.Serializable;

public class Location implements Serializable{
	int x;
	int y;
	
	public Location(){
		x=-1;
		y=-1;
	}
	
	public Location(int X, int Y)
	{
		if (x>7 || y>7 || x<-1 || y<-1 )
		{
			System.out.println("Error in constructor of Location");
			System.exit(1);
		}
		x=X;
		y=Y;
	}
	
	public void setLocation(int x, int y)
	{
		if ( x<8 && y<8 && x>=-1 && y>=-1 )
		{
			this.x=x;
			this.y=y;
		}
		else
		{
			System.out.println("Wrong Location Values for setLocation");
			System.exit(1);
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int i)
	{
		x=i;
	}
	
	public Location invert()
	{
		Location fin=new Location();
		fin=this;
		switch(this.getX())
		{
		case 0:
			fin.setX(7);
			break;
		case 1:
			fin.setX(6);
			break;
		case 2:
			fin.setX(5);
			break;
		case 3:
			fin.setX(4);
			break;
		case 4:
			fin.setX(3);
			break;
		case 5:
			fin.setX(2);
			break;
		case 6:
			fin.setX(1);
			break;
		case 7:
			fin.setX(0);
			break;
		}
		return fin;
	}
}