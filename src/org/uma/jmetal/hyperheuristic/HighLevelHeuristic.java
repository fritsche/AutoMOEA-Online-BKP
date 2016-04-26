//  HighLevelHeuristic.java
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

public interface HighLevelHeuristic <T> {
	// init the high level heuristic attributes
	public void init();
	// add a new low-level heuristic
	public void add (T t);
	// return the next low-level heuristic
	public T get();
	// evaluate the last given low-level heuristic
	// positive fitness value means improvement
	// negative fitness value means worsens
	// zero fitness value means no improvement or worsens
	public void evaluate(double fitness);

	public void printStatistics(String file);
}
