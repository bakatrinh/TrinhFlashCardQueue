import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// Every time a new card is made, FlashCardJPanel uses this class to obtain a color. The modulus operator is used
// to allow the colors to be chosen in order.
public class HelperCardColors {
	private ArrayList<Color> _colors;
	
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
	
	public Color getColor(int cardCounter) {
		return _colors.get((cardCounter % (_colors.size())));
	}
	
	public List<Color> getColorSet() {
		return _colors;
	}
}
