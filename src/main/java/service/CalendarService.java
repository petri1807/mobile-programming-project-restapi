package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.utils.SystemProperty;

import conn.Connections;
import data.CalendarEvent;

@Path("/calendarservice")
public class CalendarService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addcalendarevent")
	public CalendarEvent addCalendarEvent(CalendarEvent ce) {
		String sql = "insert into calendarEvent(userId, date, timeStart, timeEnd, topic, message) values(?,?,?,?,?,?)";

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
			pstmt.setInt(1, ce.getId());
			pstmt.setString(2, ce.getDate());
			pstmt.setString(3, ce.getTimeStart());
			pstmt.setString(4, ce.getTimeEnd());
			pstmt.setString(5, ce.getTopic());
			pstmt.setString(6, ce.getMessage());

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

		return ce;
	}

	@DELETE
//	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deletecalendarevent/{p1}")
	public void deleteCalendarEvent(@PathParam("p1") int id) {
		String sql = "delete from calendarEvent where id=?";

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
			pstmt.setInt(1, id);
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
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallcalendarevents")
	public ArrayList<CalendarEvent> getAllCalendarEvents() {
		String sql = "select * from calendarEvent";
		ResultSet RS = null;
		ArrayList<CalendarEvent> list = new ArrayList<>();

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
				CalendarEvent c = new CalendarEvent();
				c.setId(RS.getInt("id"));
				c.setDate(RS.getString("date"));
				c.setTimeStart(RS.getString("timeStart"));
				c.setTimeEnd(RS.getString("timeEnd"));
				c.setTopic(RS.getString("topic"));
				c.setMessage(RS.getString("message"));
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gettodayscalendarevents/{p1}/{p2}")
	public ArrayList<CalendarEvent> getTodaysCalendarEvents(@PathParam("p1") int id, @PathParam("p2") String date) {
		String sql = "select * from calendarEvent where userId=" + id + " and date=" + date;
		ResultSet RS = null;
		ArrayList<CalendarEvent> list = new ArrayList<>();

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
				CalendarEvent c = new CalendarEvent();
				c.setId(RS.getInt("id"));
				c.setDate(RS.getString("date"));
				c.setTimeStart(RS.getString("timeStart"));
				c.setTimeEnd(RS.getString("timeEnd"));
				c.setTopic(RS.getString("topic"));
				c.setMessage(RS.getString("message"));
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
