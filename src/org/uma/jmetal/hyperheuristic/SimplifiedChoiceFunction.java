//  SimplifiedChoiceFunction.java
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jmetal.util.PseudoRandom;
import org.uma.jmetal.util.FileUtils;

/** 
* This algorithm is based on:
* @author Giovani Guizzo, Gian Mauricio Fritsche, Silvia Regina Vergilio, and Aurora Trinidad Ramirez Pozo. 2015. 
* @title A Hyper-Heuristic for the Multi-Objective Integration and Test Order Problem. 
* In Proceedings of the 2015 on Genetic and EvolutionaryComputation Conference (GECCO '15), Sara Silva (Ed.). ACM, New York, NY, USA, 1343-1350. DOI=10.1145/2739480.2754725 http://doi.acm.org/10.1145/2739480.2754725
*/

public class SimplifiedChoiceFunction<T> implements HighLevelHeuristic<T> {
	
	private List<T> lowlevelheuristcs;
	private double [] CF;
	private double [] f1;
	private double [] f3;
	private int [] timesUsed;
	private int s; // the last index sent low level heuristic
	private double alpha;
	private double beta;
	private boolean append;
	private int [] count;
	private int start; 

	public SimplifiedChoiceFunction() {
		
		lowlevelheuristcs = new ArrayList<T>();
		Properties configuration = new Properties();
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream("configurationFiles/SimplifiedChoiceFunction.conf"));
			configuration.load(isr);
		} catch (IOException e){
			e.printStackTrace();
		}

		String aux = configuration.getProperty("alpha");
		if (aux != null)
			alpha = Double.parseDouble(aux);
		else
			alpha = 1.0;

		System.out.println("SimplifiedChoiceFunction[alpha]: "+alpha);

		aux = configuration.getProperty("beta");
		if (aux != null)
			beta = Double.parseDouble(aux);
		else
			beta = 0.00005;

		System.out.println("SimplifiedChoiceFunction[beta]: "+beta);
	}

	// init the high level heuristic attributes
	public void init() {
		int size = lowlevelheuristcs.size();
		CF = new double[size];
		f1 = new double[size];
		f3 = new double[size];
		timesUsed = new int[size];
		count = new int[size];
		for (int i = 0; i<lowlevelheuristcs.size(); ++i){
			CF[i] = 0.0;
			f1[i] = 0.0;
			timesUsed[i] = 1;
			f3[i] = 0.0;
			count[i] = 0;
		}
		s = 0;
		append = false;
		start = 0;
	}

	// add a new low-level heuristic
	public void add (T t) {
		lowlevelheuristcs.add(t);
	}

	// return the next low-level heuristic
	public T get() {
		if (lowlevelheuristcs.size() > 0) { 
			// before start choosing
			// run each low-level heuristic once
			// to evaluate it
			if (start < lowlevelheuristcs.size()) {
				s = start++;
			} else {
				double maxCF = Double.NEGATIVE_INFINITY;
				for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
					if (CF[i] >= maxCF){
						maxCF = CF[i];
						s = i;
					}
				}
			}
			return lowlevelheuristcs.get(s);
		}
		return null;
	}

	// evaluate the last given low-level heuristic
	public void evaluate(double reward) {
		f1[s] = reward;

		for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
			f3[i] = f3[i] + getTimeSpent();
		}
		f3[s] = 0.0;

		for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
			CF[i] = (alpha  * f1[i]) + (beta * f3[i]);
		}
	}

	public void printStatistics(String path) {

	}

	private double getTimeSpent() {
		// instead of use seconds we use executions count
		return 1.0;
	}

}
