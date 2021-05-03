package com;

import java.sql.*;

public class Fund {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/funds", "root", "raveesha123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	
	//READ
	public String readFunds() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

//Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Funder ID</th>" + "<th>Research ID</th><th>Donated Price</th>"
					+ "<th>Comments</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from fund";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
//iterate through the rows in the result set
			while (rs.next()) {
				String ID = Integer.toString(rs.getInt("ID"));
				String funderID = rs.getString("funderID");
				String researchID = rs.getString("researchID");
				String price = Float.toString(rs.getFloat("price"));
				String comments = rs.getString("comments");

//Add into the html table
				output += "<tr><td>" + funderID + "</td>";
				output += "<td>" + researchID + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + comments + "</td>";
//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + ID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + ID + "'></td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}


    //INSERT
	public String insertFund(String funderID, String researchID, String price, String comments) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into fund (`ID`,`funderID`,`researchID`,`price`,`comments`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, funderID);
			preparedStmt.setString(3, researchID);
			preparedStmt.setFloat(4, Float.parseFloat(price));
			preparedStmt.setString(5, comments);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	//UPDATE
	public String updateFund(String ID, String funderID, String researchID, String price, String comments) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE fund SET funderID=?,researchID=?,price=?,comments=? WHERE ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, funderID);
			preparedStmt.setString(2, researchID);
			preparedStmt.setFloat(3, Float.parseFloat(price));
			preparedStmt.setString(4, comments);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	//DELETE
	public String deleteFund(String ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
// create a prepared statement
			String query = "delete from fund where ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}