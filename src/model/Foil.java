package model;

import java.util.ArrayList;
import weka.core.*;

public class Foil {
	private ArrayList<Rule> setOfRules = new ArrayList<Rule>();
	private ArrayList<Instance> positiveValues = new ArrayList<Instance>();
	private ArrayList<Instance> negativeValues= new ArrayList<Instance>();
	private String bestLitteral = "";
	private String attribute = "";


	public ArrayList<Instance> getPositiveValues() {
		return positiveValues;
	}

	public void setPositiveValues(ArrayList<Instance> positiveValues) {
		this.positiveValues = positiveValues;
	}

	public ArrayList<Instance> getNegativeValues() {
		return negativeValues;
	}

	public void setNegativeValues(ArrayList<Instance> negativeValues) {
		this.negativeValues = negativeValues;
	}

	public String getBestAttribu() {
		return bestLitteral;
	}

	public void setBestAttribu(String bestAttribu) {
		bestLitteral = bestAttribu;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
	
	
	/**
	 * Methods 
	 * */
	public void parcourir(ArrayList<Instance> temp){
		System.out.println("***************");		
		for(int i=0; i<temp.size();++i){
			System.out.println(temp.get(i));
		}
		System.out.println("***************");
	}
	
	public ArrayList<Rule> getSetOfRules() {
		return setOfRules;
	}

	public void setSetOfRules(ArrayList<Rule> setOfRules) {
		this.setOfRules = setOfRules;
	}
	
	
	public String total(ArrayList<Rule> rule){
		StringBuilder str = new StringBuilder();
		str.append(rule.size());
		
		return str.toString();
		
	}

	public ArrayList<Instance> deletePosInstances(ArrayList<Instance> temp,ArrayList<String> a){
			
		ArrayList<Instance> temp2 = new ArrayList<>();
		
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				if(!temp.get(i).toString().equals(a.get(j))){
					temp2.add(temp.get(i));
				}
			}
		}
			
		temp.clear();
		for(int i=0; i<temp2.size();++i){
			temp.add(temp2.get(i));
		}
		return temp;
	}
	
	public void deleteNegInstances(ArrayList<Instance> temp,String attributeName, String attributeValue){
		int test = -1;
		int numAtt = -1;
		
		
		for(int j=0; j<temp.get(0).numAttributes()-1;++j){
			if(temp.get(0).attribute(j).name().equals(getAttribute())){
				numAtt = temp.get(0).attribute(j).index();
				for(int i=0; i<temp.get(0).attribute(j).numValues();++i){
					if(temp.get(0).attribute(j).value(i).equals(getBestAttribu())){
						//System.out.println(positiveValues.get(0).attribute(j).indexOfValue(getBestAttribu()));
						test = temp.get(0).attribute(j).indexOfValue(getBestAttribu());
					}
				}
			}
		}
		
		ArrayList<Instance> temp2 = new ArrayList<>();
		
		for(int i=0; i<temp.size();++i){
			if((temp.get(i).value(numAtt) == test)){
				temp2.add(temp.get(i));
			}
		}	
		temp.clear();
		for(int i=0; i<temp2.size();++i){
			temp.add(temp2.get(i));
		}	
	}
	
	public void addToSetOfRules(ArrayList<Rule> rule){
		for(Rule R : rule) {
			rule.add(R);
		}		
	}
	public static Value getBestCondidate(Instances Pos, Instances Neg, ArrayList<Attribute> old) {
		assert(Pos.equalHeaders(Neg));
		ArrayList<Value> literals = new ArrayList<>();
		for(int i=0 ; i<Pos.numAttributes()-1 ; i++) {
			Attribute attribute = Pos.attribute(i);
			for(int j=0 ; j<attribute.numValues() ; j++) {
				String label = attribute.value(j);
				literals.add(new Value(attribute, label));
			}
		}

		if(old.size() != 0) {
			for(int i=0 ; i<literals.size() ; ) {
				if(old.contains(literals.get(i).getAttribute())) {
					literals.remove(i);
				}
				else 
					i++;
			}
		}
		
		Value best = literals.get(0);
		for(Value L : literals) {
			if(L.gain(Pos, Neg) > best.gain(Pos, Neg)) best = L;
		}
		return best;
	}
	
	/**
	 * check if the arrays are equal
	 */
	public static boolean compare(ArrayList<Value> L1, ArrayList<Value> L2) {
		for(Value l : L1)
			if(!L2.contains(l)) return false;
		return true;
	}
	
	public static Instances remove(Instances instances) {
		Instances data = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(!instance.hasMissingValue())
				data.add(instance);
		}
		return data;
	}
	
	public static Instances positiveValues(Instances instances, double classVal) {
		Instances pos = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() == classVal)
				pos.add(instance);
		}
		return pos;
	}
	
	public static Instances negativeValues(Instances instances, double classVal) {
		Instances neg = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() != classVal)
				neg.add(instance);
		}
		return neg;
	}
	
	public static ArrayList<Rule> foil(Instances instances, double ClassValue) {
		ArrayList<Rule> rules = new ArrayList<>();
		Attribute att = instances.classAttribute();
		Value conclusion = new Value(att, att.value((int) ClassValue));			
		Instances data = remove(instances);
		
		Instances Pos = positiveValues(data, ClassValue);
		Instances Neg = negativeValues(data, ClassValue);
		
		ArrayList<Attribute> bestAttributesUsed = new ArrayList<>();
		
		while(Pos.numInstances() != 0) {
			Rule rule = new Rule(null, conclusion);	
			Instances negValues = Neg;
			Instances posValues = Pos;
			bestAttributesUsed.clear();
			while(negValues.numInstances() != 0) {
				Value bestCandidate = getBestCondidate(posValues, negValues, bestAttributesUsed);
				bestAttributesUsed.add(bestCandidate.getAttribute());
				rule.addValue(bestCandidate);
				negValues = bestCandidate.getSatisfy(negValues);
				posValues = bestCandidate.getSatisfy(posValues);
			}
			rules.add(rule);
			Pos = rule.removeSatisfyInstances(Pos);
		}
		return rules;
	}
	
	public String getAllRules(ArrayList<Rule> rule){
		StringBuilder str = new StringBuilder();
		str.append("\n");
		for(Rule R : rule) {
			
			str.append(" " + R + "\n");
			
		}
		return str.toString();
		
	}
	
}
