package algorithms.mazeGenerators;

import java.util.ArrayList;

public class LastNextMove implements CommonGrowingTree {

	@Override
	public Position buildPathBy(ArrayList<Position> posList) {
		Position pos = posList.get(posList.size()-1);
		return pos;		
	}
}
