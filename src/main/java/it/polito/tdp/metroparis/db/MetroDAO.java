package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	/*
	 * questo metodo dice dati due archi essi possono essere collegati o no 
	 */
	public boolean EsisteCollegamento(Fermata f1, Fermata f2) { 
		String sql="SELECT COUNT(*) AS cnt "
				+ "FROM  connessione c "
				+ "WHERE (c.id_stazP=? AND  c.id_stazA=?) OR (c.id_stazP=? AND  c.id_stazA=?)";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, f1.getIdFermata());
			st.setInt(2, f2.getIdFermata());
			st.setInt(3, f2.getIdFermata());
			st.setInt(4, f1.getIdFermata());
			
			ResultSet rs = st.executeQuery();
		     if(rs.next()) {
		    	 
		    	 st.close();
				 conn.close();
		    	 return true;
		     }else {
		    	 st.close();
				 conn.close();
		    	return false; 
		     }
			
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("Errore di connessione al Database.");
				}
			}	
	
	/*
	 * in questo metodo genero una collezione di connesssioni che sarebbero degli archi
	 */
	public List<Connessione> getAllConnessioni(List<Fermata> fermate){
		String sql ="SELECT * "
				+ "FROM  connessione "
				+ "WHERE id_stazP > id_stazA";
		List<Connessione> connessioni = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			Fermata partenza =null;
			Fermata arrivo = null;
			
			
		     
			while(rs.next()) {
				for(Fermata f: fermate) {
					if(f.getIdFermata() == rs.getInt("id_stazP"))
						partenza = f;
				  }
				for(Fermata f: fermate) {
					if(f.getIdFermata() == rs.getInt("id_stazA"))
						arrivo = f;
				   }
				
				Connessione c = new Connessione(rs.getInt("id_connessione"),null, partenza, arrivo);
				connessioni.add(c);
			}
			conn.close();
			return connessioni;
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("Errore di connessione al Database.");
				}
	      }
	
/*	public Fermata getFermata(int idfermata) {
		Fermata f = null;
		String sql="SELECT * "
				+ "FROM  fermata "
				+ "WHERE id_fermata=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
		     
			if(rs.next()) {
				f = new Fermata()
			}
			
			
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("Errore di connessione al Database.");
				}
		
		return f;
	  }*/
   } 
