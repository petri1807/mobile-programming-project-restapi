package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.utils.SystemProperty;

import conn.Connections;
import data.CalendarEvent;
import data.FloorballPlayer;

@Path("/floorballservice")
public class FloorballService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addplayer")
	public FloorballPlayer addPlayer(FloorballPlayer player) {
		String sql = "insert into players(player) values(?)";

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

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getPlayer());

			pstmt.execute();
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

		return player;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallplayers")
	public ArrayList<FloorballPlayer> getAllPlayers() {
		String sql = "select * from players";
		ResultSet RS = null;
		ArrayList<FloorballPlayer> list = new ArrayList<>();

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
				FloorballPlayer p = new FloorballPlayer();
				p.setId(RS.getInt("id"));
				p.setPlayer(RS.getString("player"));

				list.add(p);
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
