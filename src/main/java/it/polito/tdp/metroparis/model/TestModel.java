package it.polito.tdp.metroparis.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafo();
		
		Fermata partenza = model.GetVertexByName("La Fourche");
		if(partenza == null) {
			System.out.println("fermata non trovata");
		}else {
		List<Fermata> raggiungibili = model.fermateRaggiungibili(partenza);
		
		for(Fermata f : raggiungibili) {
			System.out.println(f.toString());
		 }
		}

	}

}
