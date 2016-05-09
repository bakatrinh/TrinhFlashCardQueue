import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trinh Nguyen
 * <br>Description: Every time a new card is made, the main controller uses this class to obtain a color.
 * The modulus operator is used to allow the colors to be chosen in order based on the
 * current card count of the main deck
 */
public class HelperCardColors {
	/**
	 * Stored colors from the constructor.
	 */
	private ArrayList<Color> _colors;
	
	/**
	 * Constructors that creates a new _colors ArrayList and add
	 * a couple of Color object to the array.
	 * <br>Complexity: O(1)
	 */
	public HelperCardColors() {
		_colors = new ArrayList<Color>();
		
		//Red
		_colors.add(new Color(254, 187, 185));
		
		//Orange
		_colors.add(new Color(254, 222, 182));
		
		//Yellow
		_colors.add(new Color(254, 238, 179));
		
		//Green
		_colors.add(new Color(193, 245, 176));
		
		//Blue
		_colors.add(new Color(179, 216, 253));
		
		//Purple
		_colors.add(new Color(246, 213, 254));
		
		//Pink
		_colors.add(new Color(254, 192, 210));
		
		//Brown
		_colors.add(new Color(237, 222, 203));
		
		//Graphite
		_colors.add(new Color(216, 216, 220));
		
		//Light Purple
		_colors.add(new Color(204, 204, 255));
		
		//Teal
		_colors.add(new Color(153, 255, 204));
		
		//White
		_colors.add(Color.WHITE);
	}
	
	/**
	 * Obtains a color from one of the colors stored in the array, uses modulus operator
	 * to obtain the color in order.
	 * <br>Complexity: O(1)
	 * @param cardCounter the current card count of whichever deck is being worked on
	 * @return returns a color in order based on the int given
	 */
	public Color getColor(int cardCounter) {
		return _colors.get((cardCounter % (_colors.size())));
	}
}
