package model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class DataSet {


	private Instances data;
	private String value;
	
	private ArrayList<Instance> positiveValues = new ArrayList<Instance>();
	private ArrayList<Instance> negativeValues= new ArrayList<Instance>();

	
	/**
	 * @return the data
	 */
	public Instances getData() {
		return data;
	}
	/**
	 * @param data : the data to set
	 */
	public void setData(Instances data) {
		this.data = data;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value : the positive value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return  index of class
	 */
	public double indexOfClassValue(Instances instances){
		double c=0;
		for (int i = 0; i < instances.classAttribute().numValues(); i++) {
			if(instances.classAttribute().value(i).equals(getValue())){
				c = instances.classAttribute().indexOfValue(instances.classAttribute().value(i));
			}
		}
		return c;
	}
	/**
	 *   read the file and get data from it
	 */
	
	public Attribute getDataClass(){
		return data.classAttribute();
	}
	
	public void reader(String path){
		try {
			BufferedReader bf = new BufferedReader(new FileReader(path));
			data = new Instances(bf);
			data.setClassIndex(data.numAttributes() - 1);
			bf.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumInstaces(Instances instances){
		return instances.numInstances();
		
	}
	
	public int getNumAttributes(Instances instances){
		return instances.numAttributes();
		
	}
	
	public int getp(){
		return positiveValues.size();
		
	}
	
	public int getn(){
		return negativeValues.size();
		
	}
	
	public void sorting(String s){
		positiveValues.clear();
		negativeValues.clear();
		for(int i=0;i<data.numInstances();++i){
			if(data.instance(i).stringValue(data.attribute(data.numAttributes()-1)).equals(s)){
				//data.instance(i).deleteAttributeAt(data.numAttributes()-1);
				positiveValues.add( data.instance(i));
			}else{
				//data.instance(i).deleteAttributeAt(data.numAttributes()-1);
				negativeValues.add( data.instance(i));
			}
		}
	}
	

	public String showData(){
		int i=0,j=0;
		StringBuilder str = new StringBuilder();
		str.append("--------------------- Positive Instences in Data ---------------------------\n\n");
		for (int key =0; key<positiveValues.size();++key) {
			   str.append("Instance " + ++i + " : " + positiveValues.get(key)+"\n");
			}
		
		str.append("\n--------------------- Negative Instences in Data ---------------------------\n\n");
		for (int key =0; key<negativeValues.size(); key++) {
			   str.append("Instance " + ++j + " : " + negativeValues.get(key)+"\n");
			}
		return str.toString();
	}
	
}
