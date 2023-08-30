package com.mechanicapp.dao.impl;

 
import java.sql.Connection;

 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mechanicapp.dao.ClientDao;
import com.mechanicapp.exception.ClientNotFoundException;
import com.mechanicapp.model.Client;
import com.mechanicapp.util.DbUtil;

public class ClientDaoImpl implements ClientDao {
	
		private final static String SELECT_ALL = "SELECT * FROM CUSTOMER";
		
		private final static String SELECT_BY_ID = "SELECT * FROM CUSTOMER WHERE customerid=?";
		
		private final static String INSERT = "insert into customer(firstName,lastName,address, emailid, mobileNo) values(?,?,?,?,?)";
		
		private final static String UPDATE = "UPDATE CUSTOMER SET firstName=?, lastName=?, address=?, emailid=?, mobileNo=? WHERE customerid=?";
		
		private final static String DELETE = "DELETE FROM CUSTOMER WHERE customerid = ? ";

		
		private Connection connection = DbUtil.getConnection();
		
	    @Override
	    public List<Client> displayAllClient() throws SQLException {
	    		        List<Client> clients = new ArrayList<>();
	        Statement stmt = connection.createStatement();  
	        ResultSet rs = stmt.executeQuery(SELECT_ALL);
	        
	        while (rs.next()) {
	            Client client = new Client();
 	            client.setCustomerid(rs.getInt("customerid"));
	            client.setFirstName(rs.getString("firstName"));
	            client.setLastName(rs.getString("lastName"));
	            client.setAddress(rs.getString("address"));
	            client.setEmailid(rs.getString("emailid"));
	            client.setMobileNo(rs.getLong("mobileNo"));  
	            clients.add(client);
	        }
	        rs.close();
	        stmt.close();
	        return clients;
	    }
	    

	    @Override
	    public Client findById(int clientId) throws ClientNotFoundException, SQLException {
	        Client client = null;
	        PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);
	        stmt.setInt(1, clientId);
	        ResultSet rs = stmt.executeQuery();
	        System.out.println("Rs  : "  + rs);
	        if (rs.next()) {
	            client = new Client();
	            client.setCustomerid(rs.getInt("customerid"));
	            client.setFirstName(rs.getString("firstName"));
	            client.setLastName(rs.getString("lastName"));
	            client.setAddress(rs.getString("address"));
	            client.setEmailid(rs.getString("emailid"));
	            client.setMobileNo(rs.getLong("mobileNo")); // Use consistent case
	        } else {
	            throw new ClientNotFoundException("Client Not Found With Id: " + clientId);
	        }
	        rs.close();
	        stmt.close();
	        return client;
	    }
	    
		@Override
		public boolean update(Client client) throws SQLException {
			PreparedStatement stmt = connection.prepareStatement(UPDATE);
			
			stmt.setString(1,client.getFirstName() );
			stmt.setString(2,client.getLastName() );
			stmt.setString(3,client.getAddress() );
			stmt.setString(4, client.getEmailid());
			stmt.setLong(5, client.getMobileNo());
			stmt.setInt(6,client.getCustomerid() );
			
			int updated = stmt.executeUpdate();
			stmt.close();
			
			return updated > 0;	
			
	}
		//@Override
		public boolean delete(Client client) throws SQLException {
			PreparedStatement stmt = connection.prepareStatement(DELETE);
	        stmt.setInt(1, client.getCustomerid());
			
			int deleted = stmt.executeUpdate();
			
			stmt.close();
			return deleted > 0;

	}

		@Override
		public boolean insert (Client client) throws SQLException {
			boolean result=false;
			PreparedStatement stmt = connection.prepareStatement(INSERT);
			stmt.setString(1,client.getFirstName() );
			stmt.setString(2,client.getLastName() );
			stmt.setString(3,client.getAddress() );
			stmt.setString(4, client.getEmailid());
			stmt.setLong(5, client.getMobileNo());
			if(stmt.executeUpdate()>0) {
				result = true;
			}
			stmt.close();
			return result;
		}

	 


		 

}
