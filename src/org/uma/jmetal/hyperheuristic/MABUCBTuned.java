//  MABUCBTuned.java
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

package org.uma.jmetal.hyperheuristic;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import jmetal.util.PseudoRandom;
import org.uma.jmetal.util.FileUtils;
import org.uma.jmetal.hyperheuristic.utils.SlidingWindow;

// This algorithm is based on:
// @incollection{
// 		year={2015},
// 		isbn={978-3-319-15933-1},
// 		booktitle={Evolutionary Multi-Criterion Optimization},
// 		volume={9018},
// 		series={Lecture Notes in Computer Science},
// 		editor={Gaspar-Cunha, António and Henggeler Antunes, Carlos and Coello, Carlos Coello},
// 		doi={10.1007/978-3-319-15934-8_28},
// 		title={Upper Confidence Bound (UCB) Algorithms for Adaptive Operator Selection in MOEA/D},
// 		url={http://dx.doi.org/10.1007/978-3-319-15934-8_28},
// 		publisher={Springer International Publishing},
// 		keywords={Adaptive Operator Selection (AOS); MOEA/D; Upper Confidence Bound (UCB) Algorithms; UCB1; UCB-Tuned; UCB-V},
// 		author={Gonçalves, Richard A. and Almeida, Carolina P. and Pozo, Aurora},
// 		pages={411-425},
// 		language={English}
// }

public class MABUCBTuned<T> extends MABUCB<T> {
	
	public MABUCBTuned() {
		super();
	}

	@Override
	protected void FFRMAB () {
		double [] variance = new double[lowlevelheuristcs.size()];
		int total_usage = 0;
		for (int i=0; i<slidingWindow.size(); ++i) {
			int h = slidingWindow.getHeuristic(i); 
			variance[h] += Math.pow( (slidingWindow.getCredit(i) - (reward[h] / (double) usage[h]) ), 2);
			total_usage++;
		}
		for (int i=0; i<lowlevelheuristcs.size() ; ++i) {
			variance[i]/=slidingWindow.size();
			variance[i] = Math.pow(variance[i],2);
			double vop = variance[i] + Math.sqrt( (2.0 * Math.log(total_usage)) / (usage[i]));
			UCB[i] = reward[i] + this.c * Math.sqrt((Math.log(total_usage) / (usage[i])) * Math.min((0.25d),vop));
			//System.out.println("UCB["+i+"]: "+UCB[i]);
		}
		//System.out.println();
	}

	@Override
	public void printStatistics(String path) {

	}
}
