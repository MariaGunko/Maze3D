package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class RandomNextMove implements CommonGrowingTree {

	private Random rand = new Random();
	
	@Override
	public Position buildPathBy(ArrayList<Position> posList) {
		int r=0;
		if (posList.size()!=1)
			r = rand.nextInt(posList.size()-1);
		Position pos = posList.get(r);
		return pos;
	}

}
