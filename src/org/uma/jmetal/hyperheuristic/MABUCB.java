//  MABUCB.java
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

public class MABUCB<T> implements HighLevelHeuristic<T> {
	
	protected List<T> lowlevelheuristcs;
	protected SlidingWindow<Integer> slidingWindow;
	protected final double c;
	protected final int w;
	protected double [] UCB;
	protected int [] count;
	protected double [] reward; // accumulated reward over sliding window of each llh
	protected int [] usage; // count of usage over sliding window of each llh
	protected int [] rank;
	protected int unplayed; // amount of unplayed heuristics
	protected int s; // the last index sent low level heuristic

	public MABUCB() {
		
		lowlevelheuristcs = new ArrayList<T>();

		Properties configuration = new Properties();
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream("configurationFiles/MABUCB.conf"));
			configuration.load(isr);
		} catch (IOException e){
			e.printStackTrace();
		}

		String aux = configuration.getProperty("c");
		if (aux != null)
			this.c = Double.parseDouble(aux);
		else
			this.c = 1.0;
		System.out.println("MABUCB[c]: "+c);

		aux = configuration.getProperty("w");
		if (aux != null)
			this.w = Integer.parseInt(aux);
		else
			this.w = 20;
		System.out.println("MABUCB[w]: "+w);
	}

	// init the high level heuristic attributes
	public void init() {
		int size = lowlevelheuristcs.size();
		UCB = new double[size];
		count = new int[size];
		reward = new double[size];
		usage = new int[size];
		rank = new int[size];
		for (int i = 0; i<lowlevelheuristcs.size(); ++i){
			UCB[i] = 0.0;
			count[i] = 0;
		}
		unplayed = size;
		slidingWindow = new SlidingWindow<Integer>(w);
	}

	// add a new low-level heuristic
	public void add (T t) {
		lowlevelheuristcs.add(t);
	}

	protected int getRandomHeuristic(int values[], int max, int ref) {
		int i = PseudoRandom.randInt(0,max); // select the ith randomly
		int j, p;
		j=p=0;
		while (j <= i) { // search for ith
			if (values[p] == ref) // ref is a valid heuristic, not ref is not valid
				j++;
			p++; 
		}
		return p-1;
	}

	// return the next low-level heuristic
	public T get() {
		if (unplayed > 0) { // if has unplayed heuristics
			s = getRandomHeuristic(count, unplayed-1, 0); // get a random heuristic with count equals to zero (0)
			//System.out.println("s: "+s+" unplayed: "+unplayed);
			unplayed--;
		} else { // get best heuristic
			double max = Double.NEGATIVE_INFINITY;
			int [] ties = new int[lowlevelheuristcs.size()];
			int t = 0;
			int countties = 0;
			for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
				//System.out.println("UCB: "+UCB[i]+" max: "+max);
				if (UCB[i] > max){ 	// if ith is better 
					max = UCB[i]; 	// update max
					s = i; 			// select ith
					countties = 0;	// there is no ties
					t++;			// update reference value
					ties[i] = t;	// each value equals t is a tie
				} else if (UCB[i] == max) { // if is a tie
					ties[i] = t; // insert a new tie
					countties++; // update count of ties
				}
			}
			if (countties > 0) { // it there is a tie
				s = getRandomHeuristic(ties, countties, t); // get a random heuristic with tie value equals t
				//System.out.println("s: "+s+" ties: "+Arrays.toString(ties)+" countties: "+countties+" t: "+t);
			}
		}

		count[s]++; // increment count of selected heuristic
		return lowlevelheuristcs.get(s); // return selected heuristic
	}

	// evaluate the last given low-level heuristic
	public void evaluate(double reward) {
		
		refresment();

		slidingWindow.add(s, reward); // Update the sliding window

		updateRewards(); 
			
		rankRewards();

		creaditAssignmentDecay();

		FFRMAB();
	}

	protected void FFRMAB () {
		double [] variance = new double[lowlevelheuristcs.size()];
		int total_usage = slidingWindow.size();
		for (int i=0; i<lowlevelheuristcs.size() ; ++i) {
			UCB[i] = reward[i] + this.c * Math.sqrt(2.0 * Math.log(total_usage) / (usage[i]));
			//System.out.println("UCB["+i+"]: "+UCB[i]);
		}
		//System.out.println();
	}

	protected void refresment(){
		for (int i=0; i<reward.length; ++i) {
			reward[i]=0.0;
			usage[i]=0;
		}
	}

	// Sum all credits (Rewards) of all heuristics in the sliding window
	// count the usage of each heuristic
	protected void updateRewards() {
		for (int i=0; i<slidingWindow.size(); ++i) {
			int j = slidingWindow.getHeuristic(i);
			reward[j]+=(slidingWindow.getCredit(i));
			usage[j]++;
			// //System.out.println("reward["+j+"]: "+reward[j]+" usage: "+usage[j]);
		}
		// //System.out.println();
	}

	protected void rankRewards() {
		int i, j;
		double[][] temp;
		double temp_index;
		double temp_value;

		temp = new double[2][lowlevelheuristcs.size()];
		for (i = 0; i < lowlevelheuristcs.size(); i++) {
			temp[0][i] = reward[i];
			temp[1][i] = i;
		}

		for (i = 0; i < lowlevelheuristcs.size() - 1; i++) {
			for (j = i + 1; j < lowlevelheuristcs.size(); j++) {
				if (temp[0][i] < temp[0][j]) {
					temp_value = temp[0][j];
					temp[0][j] = temp[0][i];
					temp[0][i] = temp_value;

					temp_index = temp[1][j];
					temp[1][j] = temp[1][i];
					temp[1][i] = temp_index;
				}
			}
		}

		for (i = 0; i < lowlevelheuristcs.size(); i++) {
			rank[i] = (int) temp[1][i];
			// //System.out.println("rank["+i+"]: "+rank[i]);
		}
	}

	protected void creaditAssignmentDecay() {
		int i;
		double decayed, decay_sum, decayFactor = 1.0;
		double[] decay_value;

		decay_value = new double[lowlevelheuristcs.size()];

		for (i = 0; i < lowlevelheuristcs.size(); i++) {
			decayed = Math.pow(decayFactor, i);
			decay_value[rank[i]] = reward[rank[i]] * decayed;
		}

		decay_sum = 0.0;
		for (i = 0; i < lowlevelheuristcs.size(); i++) {
			decay_sum += decay_value[i];
		}

		for (i = 0; i < lowlevelheuristcs.size(); i++) {
			// //System.out.println("reward["+i+"]: "+reward[i]);
			if (decay_sum == 0) {
				reward[i] = 0.0;
			} else {
				reward[i] = decay_value[i] / decay_sum;
			}
			// //System.out.println("reward["+i+"]: "+reward[i]);
		}
	}

	public void printStatistics(String path) {

	}
}
