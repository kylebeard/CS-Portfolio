import java.awt.Color;
import java.awt.Graphics;
/**
 * class that creates a bullet object
 * @author kylebeard
 *
 */
public class Bullet extends Circle{
	
	public static final int RADIUS = 4;
	private double rotation = 2;
	private Point center;
	public Bullet(Point center, double rotation) {
		super(center, RADIUS);
		this.rotation = rotation;
		this.center = center;
	}

	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int) center.x, (int) center.y, radius, radius);		
	}

	@Override
	public void move() {
		center.x += 5 * Math.cos(Math.toRadians(rotation));
		center.y += 5 * Math.sin(Math.toRadians(rotation));

		
	}
	/**
	 * @return the center point of the bullet
	 */
	public Point getCenter(){
		return center;
	}
	
	// determines when the bullet has moved off screen
	public boolean outOfBounds(){
		if(center.x > Asteroids.SCREEN_WIDTH) {
            return true;
        } else if(center.x < 0) {
           return  true;
        }else if(center.y > Asteroids.SCREEN_HEIGHT) {
        	return true;
        } else if(center.y < 0) {
        	return true;
        }
        return false;	
	}
}
