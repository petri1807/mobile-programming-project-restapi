package service;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.utils.SystemProperty;

import conn.Connections;
import data.Announcement;

@Path("/announcementservice")
public class AnnouncementService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallannouncements")
	public ArrayList<Announcement> getAllAnnouncements() {
		String sql = "select * from announcement";
		ResultSet RS = null;
		ArrayList<Announcement> list = new ArrayList<>();

		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Announcement c = new Announcement();
				c.setId(RS.getInt("id"));
				c.setDate(RS.getString("date"));
				c.setTitle(RS.getString("title"));
				c.setContent(RS.getString("content"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	

}
