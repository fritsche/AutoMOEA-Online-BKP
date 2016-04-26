//  HighLevelRoulette.java
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


public class HighLevelRoulette<T> implements HighLevelHeuristic<T> {
	
	private List<T> lowlevelheuristcs;
	private int size;
	private double[] probabilities;
	private boolean append;
	private int s; // last lowLeveHeuristic selected
	private double minProbability = 0.005;
	private double increment;
	private int [] count;
	

	public HighLevelRoulette() {
		
		lowlevelheuristcs = new ArrayList<T>();

	}

	// add a new low-level heuristic
	public void add (T t) {
		lowlevelheuristcs.add(t);
	}

	// init the high level heuristic attributes
	public void init() {

		size = lowlevelheuristcs.size();
		probabilities = new double[size];

		double dsize = (double) size;
		count = new int[size];
		for (int i=0; i<size; ++i ) {
			probabilities[i] = (1.0/dsize);
			count[i] = 0;
		}

		s = 0;

		increment = (1.0 / dsize) / 10.0 ;

		append = false;

	}


	// return the next low-level heuristic
	public T get() {
		double rand = PseudoRandom.randDouble();
		double sum = probabilities[0];
		s = 0;
		for (int i=1; i < size && rand > sum; ++i, s++ ) {
			sum+=probabilities[i];
		}
		return lowlevelheuristcs.get(s);
	}

	// evaluate the last given low-level heuristic
	public void evaluate(double reward) {
		if (reward >= 0.0) {
			probabilities[s] += increment;
			double dsize = (double) size;
			for(int i=0;i<size;i++){
				if ( (probabilities[i] - (increment/dsize) ) > minProbability )
					probabilities[i]-=(increment/dsize);
				else
					probabilities[s]-=(increment/dsize);
			}
		} else{
			if(probabilities[s]-increment < minProbability){
				increment=probabilities[s]-minProbability;
				probabilities[s]=minProbability;
			}else
				probabilities[s]-=increment;
			for(int i=0;i<size;i++){
				if ( (probabilities[i]+(increment/size)) < 1-(minProbability*(size-1)) ) {
					probabilities[i]+=(increment/size);
				}else{
					probabilities[s]+=(increment/size);
				}
			}
		}
	}

	public void printStatistics(String path) {
		
		if (!append)
	    	FileUtils.checkDirectory(path);

	    printStatisticsToFile(probabilities, path+"/probabilities.stat");
	    
	    count[s]++;
		printStatisticsToFile(count, path+"/count.stat");

	    append = true;
	}

	private void printStatisticsToFile(double[] values, String file){
		try {
	      FileOutputStream fos = new FileOutputStream(file, append);
	      OutputStreamWriter osw = new OutputStreamWriter(fos)    ;
	      BufferedWriter bw      = new BufferedWriter(osw)        ;
	      for (double value : values ) {
	          bw.write("\t"+value);
	      }
	      bw.newLine();
	      bw.close();
	    }catch (IOException e) {
	      e.printStackTrace();
	    }
	}

	private void printStatisticsToFile(int[] values, String file){
		try {
	      FileOutputStream fos = new FileOutputStream(file, append);
	      OutputStreamWriter osw = new OutputStreamWriter(fos)    ;
	      BufferedWriter bw      = new BufferedWriter(osw)        ;
	      for (int value : values ) {
	          bw.write("\t"+value);
	      }
	      bw.newLine();
	      bw.close();
	    }catch (IOException e) {
	      e.printStackTrace();
	    }
	}

}
