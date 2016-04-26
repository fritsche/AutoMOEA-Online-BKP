//  SlidingWindow.java
//
//  Author:
//       Gian M. Fritsche <gmfritsche@inf.ufpr.br>
//  Contributors:
// 		 Andrei Strickler <stricklerandrei@gmail.com>
//		 Jackson Prado Lima <jacksonpradolima@gmail.com>
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

package org.uma.jmetal.hyperheuristic.utils;

import java.util.List;
import java.util.ArrayList;

public class SlidingWindow<T> {

	protected int w;
	protected List<Double> credits;
	protected List<T> heuristics;	

	public SlidingWindow (int w) {
		this.w = w;
		credits = new ArrayList<Double>();
		heuristics = new ArrayList<T>();
	}

	public void add (T heuristic, double credit) {
		if (heuristics.size() >= w) // if sliding window is full 
			remove(0); // remove older entry
		//System.out.println("add: "+heuristic);
		credits.add(credit); // add new entry at end (FIFO)
		heuristics.add(heuristic);
	}

	protected void remove (int index) {
		//System.out.println("remove: "+heuristics.get(index));
		heuristics.remove(index);
		credits.remove(index);
	}

	public int size() {
		return heuristics.size();
	}

	public T getHeuristic(int index) {
		return heuristics.get(index);
	}

	public double getCredit(int index) {
		return credits.get(index);
	}

}
