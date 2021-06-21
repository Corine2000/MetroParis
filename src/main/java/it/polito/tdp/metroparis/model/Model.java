package it.polito.tdp.metroparis.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	Graph<Fermata, DefaultEdge> grafo;
	Map<Fermata, Fermata> predecessori;
	
	public Model() {
		
	}
	
	public void creaGrafo() {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		MetroDAO dao = new MetroDAO();
		List<Fermata> fermate = dao.getAllFermate();
		
		Graphs.addAllVertices(grafo, fermate); //aggiungo i vertici
		
		
		//ora aggiungo gli archi
	/*	for(Fermata f1: fermate) {
			for(Fermata f2: fermate) {
				if(!f1.equals(f2) && dao.EsisteCollegamento(f1,f2)) {
					this.grafo.addEdge(f1, f2);
				}
			}
		}
		questo metodo richiede molto tempo
		*/
		
		List<Connessione> allConnection = dao.getAllConnessioni(fermate);
		for(Connessione c: allConnection) {
			this.grafo.addEdge(c.getStazP(), c.getStazA());
		}
		
		//System.out.println(this.grafo);
		System.out.println("# vertici:" +this.grafo.vertexSet().size());
		System.out.println("# archi: "+ this.grafo.edgeSet().size());
		
		//ora vogliamo esplorare il grafo
	//Fermata f = null;
	/*Set<DefaultEdge> archi = grafo.edgesOf(f);
	for(DefaultEdge e: archi) {
		Fermata f1= grafo.getEdgeSource(e);
		Fermata f2= grafo.getEdgeTarget(e);
		
		if(f.equals(f1)) {
			//f2 è quello che mi serve, visto che il grafo non è orientato allora è necessario queste verifiche
		}else {
			// f1 è quello che mi serve
		}
		//oppure 
		Fermata f3= Graphs.getOppositeVertex(grafo, e, f);
	  }
	
	List<Fermata> adiacenti = Graphs.successorListOf(this.grafo, f) ;
	List<Fermata> adiacentiPrec = Graphs.predecessorListOf(this.grafo, f) ;*/
	}
	
		
		public List<Fermata> fermateRaggiungibili(Fermata partenza) {
			List<Fermata>  result = new ArrayList<>();
			
			//visita in ampiezza
		/*	BreadthFirstIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,partenza );
			
			while(bfv.hasNext()) {
				Fermata f = bfv.next();
				result.add(f);
			}*/
			
			//visita in profondita
			
			DepthFirstIterator<Fermata,DefaultEdge > dfv = new DepthFirstIterator< >(this.grafo, partenza);
			
			while(dfv.hasNext()) {
				Fermata f = dfv.next();
				result.add(f);
			}
			
			//ritroviamo gli stessi vertici ma in un ordine diverso
			return result;
		}
		
		
		public Fermata GetVertexByName(String nome) {
			
			for(Fermata f: this.grafo.vertexSet()) {
				if(f.getNome().equals(nome)) {
					return f;
				}
			}
			return null;
		}
	
		
	

}
