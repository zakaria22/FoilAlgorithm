package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.*;
import view.View;

public class Controller {
	private View view;
	private DataSet model;
		
	public Controller(View view, DataSet model) {
		this.view = view;
		this.model = model;
		this.view.startListner(new startListner());
	}
	
	class startListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String path;
			String data;

			view.openFile();
			path = view.getFilePath();
			model.reader(path);

			
			view.setData(model.getData().toString());
			String pos = view.askForPositive("please choose the positive value in : " + model.getDataClass());
			model.sorting(pos);
			view.setSummaryValues(model.getNumInstaces(model.getData()), model.getNumAttributes(model.getData()), 
					model.getp(), model.getn());
			data = model.showData();
			view.append(data);
			Foil algo = new Foil();
			ArrayList<Rule> rules = algo.foil(model.getData(), model.indexOfClassValue(model.getData()));
			view.setRules(algo.getAllRules(rules));

			
		}
		
	}

}
