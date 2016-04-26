//  HighLevelHeuristicFactory.java
//
//  Author:
//       Gian Mauricio Fritsche <gmfritsche@inf.ufpr.br>
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

public class HighLevelHeuristicFactory<T> {

	public static String RANDOM   = "RandomHighLevelHeuristic";
	public static String FIXED    = "FixedHighLevelHeuristic";
	public static String ACF      = "AdaptiveChoiceFunction";
	public static String SCF      = "SimplifiedChoiceFunction";
	public static String ROULETTE = "HighLevelRoulette";
	public static String MABTUNED = "MABUCBTuned";
	public static String MABUCB   = "MABUCB";
	public static String MABUCBV  = "MABUCBV";

	public HighLevelHeuristicFactory(){}

	public HighLevelHeuristic<T> getHighLevelHeuristic(String option) throws ClassNotFoundException {
		if (option.equals(RANDOM)) 
			return new RandomHighLevelHeuristic<T>();
		else if (option.equals(FIXED))
			return new FixedHighLevelHeuristic<T>();
		else if (option.equals(ACF))
			return new AdaptiveChoiceFunction<T>();
		else if (option.equals(SCF))
			return new SimplifiedChoiceFunction<T>();
		else if (option.equals(ROULETTE))
			return new HighLevelRoulette<T>();
		else if (option.equals(MABTUNED))
			return new MABUCBTuned<T>();
		else if (option.equals(MABUCB))
			return new MABUCB<T>();
		else if (option.equals(MABUCBV))
			return new MABUCBV<T>();
		else
			throw new ClassNotFoundException(option);
	}

}
