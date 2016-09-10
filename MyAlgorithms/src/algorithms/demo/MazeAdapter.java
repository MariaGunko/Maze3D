package algorithms.demo;

import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

public class MazeAdapter implements Searchable<Position> {

	private Maze3d myMaze;

	public MazeAdapter (Maze3d myMaze){
		this.myMaze=myMaze;
	}


	@Override
	public State<Position> getStartState() {
		Position p = this.myMaze.getStartPosition();
		State<Position> s = new State<Position> (p);
		return s;
	}

	@Override
	public State<Position> getGoalState() {
		Position p = this.myMaze.getGoalPosition();
		State<Position> s = new State<Position> (p);
		return s;
	}

	@Override
	public List<State<Position>> getAllPossibleStates(State<Position> s) {
		List<State<Position>> statesList = new ArrayList<State<Position>>();
		Position p=s.getValue();
		//String [] possibleMoves = this.myMaze.getPossibleMoves(p);
		Position [] possibleMoves = this.myMaze.getPossiblePossitions(p);
		
		int x,y,z,i=0;
		Position neighborPoint;
		State<Position> neighborState;

		while (i<possibleMoves.length){
			if (possibleMoves[i]!=null){
				//z=Integer.parseInt(possibleMoves[i].substring(1, 2));
				//x=Integer.parseInt(possibleMoves[i].substring(3, 4));
				//y=Integer.parseInt(possibleMoves[i].substring(5, 6));
				//neighborPoint = new Position (z,x,y);
				neighborPoint = possibleMoves[i];
				neighborState = new State<Position> (neighborPoint);
				statesList.add(neighborState);	
			}
			i++;
		}
		return statesList;	
	}

	@Override
	public double getMoveCost(State<Position> currState, State<Position> neighbor) {
		return 1;
	}

}
