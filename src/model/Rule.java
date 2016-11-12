package model;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Rule {
	private ArrayList<Value> head;
	private Value body;
	
	public Rule() {
		head = new ArrayList<>();
		body = null;
	}
	
	public Rule(ArrayList<Value> head, Value body) {
		this.head = head;
		this.body = body;
	}
	
	public ArrayList<Value> getValues() {
		return head;
	}
	
	public Value getConclusion() {
		return body;
	}
	
	public void setValues(ArrayList<Value> head) {
		this.head = head;
	}
	
	public void setConclusion(Value body) {
		this.body = body;
	}
	
	/**
	 * get the current Attribute 
	 */
	public ArrayList<Attribute> getCurrentAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		for(Value L : head) {
			attributes.add(L.getAttribute());
		}
		return attributes;
	}
	
	/**
	 * add literal 
	 */
	public boolean addValue(Value L) {
		if(head == null) 
			head = new ArrayList<>();
		return head.add(L);
	}
	
	/**
	 * Instances which satisfy the rule
	 */
	public Instances removeSatisfyInstances(Instances instances) {
		Instances data = new Instances(instances, 0);
		ArrayList<Value> head = this.getValues();
		ArrayList<Attribute> attributes = this.getCurrentAttributes();
		for(int i=0 ;i<instances.numInstances() ; i++) {
			ArrayList<Value> instanceValue = new ArrayList<>();
			Instance instance = instances.instance(i);
			for(int j=0 ; j<instance.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				if(attributes.contains(attribute)) {
					Value L = new Value(attribute, instance.stringValue(attribute));
					instanceValue.add(L);
				}
			}
			if(!sameAs(head, instanceValue)) 
				data.add(instance);
		}
		return data;
	}
	
	/**
	 * compare literals
	 */
	private boolean sameAs(ArrayList<Value> L1, ArrayList<Value> L2) {
		for(Value l : L1)
			if(!L2.contains(l)) 
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		String ch = "if ";
		for(int i=0 ; i<head.size() ; i++) {
			Value L = head.get(i);
			if(i == head.size()-1) ch += L;
			else ch += L + " and ";
		}
		ch += " then " + body;
		return ch;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Rule R = (Rule) obj;
			if(R.head.equals(head) && R.body.equals(body)) 
				return true;
			else 
				return false;
		} catch (ClassCastException E) {
			return false;
		}
	}
}
