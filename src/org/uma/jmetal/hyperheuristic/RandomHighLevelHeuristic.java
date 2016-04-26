//  RandomHighLevelHeuristic.java
//
//  Author:
//       Gian M. Fritsche <gmfritsche@inf.ufpr.br>
//
//  Copyright (c) 2015 Gian M. Fritsche
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.hyperheuristic;

import java.util.List;
import java.util.ArrayList;
import jmetal.util.PseudoRandom;

public class RandomHighLevelHeuristic<T> implements HighLevelHeuristic<T> {
	
	private List<T> lowlevelheuristcs;

	public RandomHighLevelHeuristic() {
		lowlevelheuristcs = new ArrayList<T>();
	}

	// init the high level heuristic attributes
	public void init() { }

	// add a new low-level heuristic
	public void add (T t) {
		lowlevelheuristcs.add(t);
	}

	// return the next low-level heuristic
	public T get() {
		if (lowlevelheuristcs.size() > 0) { 
			return lowlevelheuristcs.get(Math.abs(PseudoRandom.randInt())%lowlevelheuristcs.size());
		}
		return null;
	}

	// evaluate the last given low-level heuristic
	public void evaluate(double fitness) {}
	public void printStatistics(String file){}

}
