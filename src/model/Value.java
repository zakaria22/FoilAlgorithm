package model;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;


public class Value {
	private Attribute attribute;
	private String value;
	
	public Value() {
		attribute = null;
		value = "";
	}
	
	public Value(Attribute attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public String getvalue() {
		return value;
	}
	
	public void setAttribut(Attribute attribut) {
		this.attribute = attribut;
	}
	
	public void setvalue(String value) {
		this.value = value;
	}
	
	public double frequency(Instances pos){
		double p=0;
		for(int i=0 ; i<pos.numInstances() ; i++) {
			Instance instance = pos.instance(i);
			if(instance.stringValue(getAttribute()).equals(getvalue())) p++;
		}
		return p;
	}
	
	
	public double gain(Instances pos, Instances neg) {
		double P = (double) pos.numInstances();
		double N = (double) neg.numInstances();
		double p = Double.MIN_VALUE;
		double n = Double.MIN_VALUE;
		
		p = frequency(pos);
		n = frequency(neg);
				
		if(p == 0) return -Double.MIN_VALUE;
		return (p * (log2(p / (p+n)) - log2(P / (P + N))));
	}
	
	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}
	
	public Instances getSatisfy(Instances instances) {
		Instances data = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			for(int j=0 ; j<instance.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				if(getAttribute().equals(attribute)) {
					Value L = new Value(attribute,instance.stringValue(attribute));
					if(L.equals(this)) data.add(instance);
					break;
				}
			}
		}
		return data;
	}
	
	@Override
	public String toString() {
		return getAttribute().name() + " = " + value;
	}
	
	public double gainValue(double p, double n, double pos, double neg){
		if(p==0)
			return Double.MIN_VALUE;
		else
			return p*(log2(p/(p+n)) - log2(pos/(pos+neg)));
	}

	
	@Override
	public boolean equals(Object obj) {
		try {
			Value L = (Value) obj;
			if(L.attribute.equals(getAttribute()) && L.value.equals(getvalue())) return true;
			else return false;
		} catch (ClassCastException E) {
			return false;
		}
	}
}
