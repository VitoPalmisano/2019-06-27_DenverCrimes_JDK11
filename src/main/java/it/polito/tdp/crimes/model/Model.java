package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDAO;

public class Model {

	private EventsDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> bestCammino;
	private int N;
	private int bestPeso;
	
	public Model() {
		dao = new EventsDAO();
	}
	
	public List<String> listCategorie(){
		return dao.listCategorie();
	}
	
	public List<Integer> listAnni(){
		return dao.listAnni();
	}
	
	public void creaGrafo(String categoria, Integer anno) {
		
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.listReati(categoria, anno));
		
		List<Adiacenza> adiacenze = dao.listAdiacenze(categoria, anno);
		
		for(Adiacenza a : adiacenze) {
			if(grafo.containsVertex(a.getA1()) && grafo.containsVertex(a.getA2())) {
				Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
			}
		}
	}

	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getMax(){
		List<Adiacenza> adiacenze = new ArrayList<Adiacenza>();
		int pesoMax = 0;
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>pesoMax) {
				pesoMax=(int)grafo.getEdgeWeight(e);
			}
		}
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if((int)grafo.getEdgeWeight(e)==pesoMax) {
				adiacenze.add(new Adiacenza(grafo.getEdgeSource(e), grafo.getEdgeTarget(e), pesoMax));
			}
		}
		
		return adiacenze;
	}
	
	public List<String> ricorsione(Adiacenza a) {
		bestCammino = new ArrayList<String>();
		List<String> parziale = new ArrayList<String>();
		
		N = grafo.vertexSet().size();
		bestPeso = 0;
		
		parziale.add(a.getA1());
		
		cerca(parziale, a.getA2(), 0);
		
		return bestCammino;
	}
	
	private void cerca(List<String> parziale, String fine, int pesoTemp) {
		
		if(parziale.get(parziale.size()-1).equals(fine)) {
			if(parziale.size()==N) {
				if(bestPeso == 0) {
					bestPeso = pesoTemp;
					bestCammino = new ArrayList<String>(parziale);
				}
				else if(pesoTemp<bestPeso) {
					bestCammino = new ArrayList<String>(parziale);
				}
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale, fine, pesoTemp+(int)grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-2), s)));
				parziale.remove(s);
			}
		}
	}
}
