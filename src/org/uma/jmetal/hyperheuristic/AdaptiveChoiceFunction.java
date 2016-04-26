//  AdaptiveChoiceFunction.java
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


public class AdaptiveChoiceFunction<T> implements HighLevelHeuristic<T> {
	
	private List<T> lowlevelheuristcs;
	private double [] CF;
	private double [] exploitation;
	private double [] exploration;
	private double [] f1;
	private double [][] f2;
	private double [] f3;
	private int [] timesUsed;
	private int [][] timesUsedPair;
	private int s; // the last index sent low level heuristic
	private int lastLowLevel; // the last index evaluated low level heuristic
	private double lastReward;
	private double phi;
	private double delta;
	private boolean mean;
	private boolean pair;
	private double SF;
	private boolean append;
	private int [] count;
	private boolean norm; // use normalization instead of a Scale Factor $SF$ parameter
	private double maxF1;
	private double maxF2;
	private double minF1;
	private double minF2;
	private double maxF3;
	private double minF3;
	private int start; 

	public AdaptiveChoiceFunction() {
		
		lowlevelheuristcs = new ArrayList<T>();
		Properties configuration = new Properties();
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream("configurationFiles/AdaptiveChoiceFunction.conf"));
			configuration.load(isr);
		} catch (IOException e){
			e.printStackTrace();
		}

		String aux = configuration.getProperty("SF");
		if (aux != null)
			SF = Double.parseDouble(aux);
		else
			SF = 0.5;
		System.out.println("AdaptiveChoiceFunction[SF]: "+SF);

		aux = configuration.getProperty("mean");
		if (aux != null)
			mean = Boolean.parseBoolean(aux);
		else
			mean = true;
		System.out.println("AdaptiveChoiceFunction[mean]: "+mean);

		aux = configuration.getProperty("norm");
		if (aux != null)
			norm = Boolean.parseBoolean(aux);
		else
			norm = false;
		System.out.println("AdaptiveChoiceFunction[norm]: "+norm);

		aux = configuration.getProperty("pair");
		if (aux != null)
			pair = Boolean.parseBoolean(aux);
		else
			pair = true;
		System.out.println("AdaptiveChoiceFunction[pair]: "+pair);

	}

	// init the high level heuristic attributes
	public void init() {
		int size = lowlevelheuristcs.size();
		CF = new double[size];
		exploitation = new double[size];
		exploration = new double[size];
		f1 = new double[size];
		f2 = new double[size][size];
		f3 = new double[size];
		timesUsed = new int[size];
		timesUsedPair = new int[size][size];
		count = new int[size];
		for (int i = 0; i<lowlevelheuristcs.size(); ++i){
			CF[i] = 0.0;
			exploration[i] = 0.0;
			exploitation[i] = 0.0;
			f1[i] = 0.0;
			timesUsed[i] = 1;
			for (int j = 0; j<lowlevelheuristcs.size(); ++j){
				f2[i][j] = 0.0;
				timesUsedPair[i][j] = 1;
			}
			f3[i] = 0.0;
			count[i] = 0;
		}
		phi = 0.99;
		delta = 0.01;
		s = lastLowLevel = 0;
		lastReward = 0.0;
		append = false;
		maxF3 = maxF1 = maxF2 = minF3 = minF1 = minF2 = 0.0;
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
		f1[s] = f1[s] + reward;
		timesUsed[s] = timesUsed[s] + 1;
		
		if (f1[s] > maxF1){
			maxF1 = f1[s];
		}
		if (f1[s] < minF1){
			minF1 = f1[s];
		} 

		f2[lastLowLevel][s] = f2[lastLowLevel][s] + lastReward + reward;
		timesUsedPair[lastLowLevel][s] = timesUsedPair[lastLowLevel][s] + 1;
		
		if (f2[lastLowLevel][s] + f1[s] > maxF2) {
			maxF2 = f2[lastLowLevel][s];
		}
		if (f2[lastLowLevel][s] < minF2) {
			minF2 = f2[lastLowLevel][s];
		}

		for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
			f3[i] = f3[i] + getTimeSpent();
			if (f3[i] > maxF3 && i != s){
				maxF3 = f3[i];
			}
		}
		f3[s] = 0.0;

		if (reward > 0) {
			phi = 0.99;
			delta = 0.01;
		} else {
			phi = phi - 0.01;
			delta = 1.0 - phi;
		}

		lastReward = reward;
		lastLowLevel = s;
		for (int i=0; i<lowlevelheuristcs.size(); ++i ) {
			
			if (pair) {
				if (norm) {
					if (mean) {
						exploitation[i] = (phi * ((f1[i] - minF1) / (maxF1 - minF1)) / timesUsed[i] + phi * ((f2[s][i] - minF2) / (maxF2 - minF2)) / timesUsedPair[s][i]);
						exploration[i] =  delta * f3[i] / maxF3;
					} else {
						exploitation[i] = (phi * ((f1[i] - minF1) / (maxF1 - minF1)) + phi * ((f2[s][i] - minF2) / (maxF2 - minF2)));
						exploration[i] =  delta * f3[i] / maxF3;
					}
					CF[i] = exploitation[i] + exploration[i];
				} else {
					if (mean) {
						exploitation[i] = (phi * f1[i] / timesUsed[i] + phi * f2[s][i] / timesUsedPair[s][i]);
						exploration[i] =  delta * f3[i];
					} else {
						exploitation[i] = (phi * f1[i] + phi * f2[s][i]);
						exploration[i] =  delta * f3[i];
					}
					CF[i] = SF * exploitation[i] + exploration[i];
				}
			} else {
				if (norm) {
					if (mean) {
						exploitation[i] = (phi * ((f1[i] - minF1) / (maxF1 - minF1)) / timesUsed[i]);
						exploration[i] =  delta * f3[i] / maxF3;
					} else {
						exploitation[i] = (phi * ((f1[i] - minF1) / (maxF1 - minF1)));
						exploration[i] =  delta * f3[i] / maxF3;
					}
					CF[i] = exploitation[i] + exploration[i];
				} else {
					if (mean) {
						exploitation[i] = (phi * f1[i] / timesUsed[i]);
						exploration[i] =  delta * f3[i];
					} else {
						exploitation[i] = (phi * f1[i]);
						exploration[i] =  delta * f3[i];
					}
					CF[i] = SF * exploitation[i] + exploration[i];
				}
			}
		}
	}

	public void printStatistics(String path) {
		/*
		if (!append)
	    	FileUtils.checkDirectory(path);

		printStatisticsToFile(CF, path+"/cf.stat");
		printStatisticsToFile(exploitation, path+"/exploitation.stat");
		printStatisticsToFile(exploration, path+"/exploration.stat");
		printStatisticsToFile(f1, path+"/f1.stat");
		printStatisticsToFile(f2[s], path+"/f2.stat");
		printStatisticsToFile(f3, path+"/f3.stat");
		count[s]++;
		printStatisticsToFile(count, path+"/count.stat");
	    
	    append = true;
	    */
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

	private double getTimeSpent() {
		// instead of use seconds we use executions count
		return 1.0;
	}

}
