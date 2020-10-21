package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.utils.SystemProperty;

import conn.Connections;
import data.Activity;
import data.CalendarEvent;

@Path("/activityservice")
public class ActivityService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addactivity")
	public Activity addActivity(Activity ce) {
		String sql = "insert into activity(userId, date, timeStart, timeEnd, timeSpent, activityType) values(?,?,?,?,?,?)";
		
		Connection conn = null;
		try {
			if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else  {
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
			pstmt.setInt(5, ce.getTimeSpent());
			pstmt.setString(6, ce.getActivityType());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ce;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallactivities")
	public ArrayList<Activity> getAllActivities() {
		String sql = "select * from activity";
		ResultSet RS = null;
		ArrayList<Activity> list = new ArrayList<>();

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
				Activity a = new Activity();
				a.setId(RS.getInt("id"));
				a.setDate(RS.getString("date"));
				a.setTimeStart(RS.getString("timeStart"));
				a.setTimeEnd(RS.getString("timeEnd"));
				a.setTimeSpent(RS.getInt("timeSpent"));
				a.setActivityType(RS.getString("activityType"));
				list.add(a);
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
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteactivity/{p1}")
	public void deleteActivity(@PathParam("p1") int id) {
		String sql = "delete from activity where id=?";
		
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
	@Path("/timeonactivities")
	public ArrayList<Activity> timeOnActivities() {
		String sqlWork = "select sum(timeSpent) from activity where activityType=Work";
		String sqlMeeting = "select sum(timeSpent) from activity where activityType=Meeting";
		String sqlWorkout = "select sum(timeSpent) from activity where activityType=Workout";
		String sqlPersonal = "select sum(timeSpent) from activity where activityType=Personal";
		
		ResultSet RS = null;
		Connection conn = null;
		ArrayList<Activity> list = new ArrayList<>();
		
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
			RS = stmt.executeQuery(sqlWork);
			while (RS.next()) {
				Activity a = new Activity();
				a.setId(RS.getInt("id"));
				a.setDate(RS.getString("date"));
				a.setTimeStart(RS.getString("timeStart"));
				a.setTimeEnd(RS.getString("timeEnd"));
				a.setTimeSpent(RS.getInt("timeSpent"));
				a.setActivityType(RS.getString("activityType"));
				list.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sqlMeeting);
			while (RS.next()) {
				Activity a = new Activity();
				a.setId(RS.getInt("id"));
				a.setDate(RS.getString("date"));
				a.setTimeStart(RS.getString("timeStart"));
				a.setTimeEnd(RS.getString("timeEnd"));
				a.setTimeSpent(RS.getInt("timeSpent"));
				a.setActivityType(RS.getString("activityType"));
				list.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sqlWorkout);
			while (RS.next()) {
				Activity a = new Activity();
				a.setId(RS.getInt("id"));
				a.setDate(RS.getString("date"));
				a.setTimeStart(RS.getString("timeStart"));
				a.setTimeEnd(RS.getString("timeEnd"));
				a.setTimeSpent(RS.getInt("timeSpent"));
				a.setActivityType(RS.getString("activityType"));
				list.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sqlPersonal);
			while (RS.next()) {
				Activity a = new Activity();
				a.setId(RS.getInt("id"));
				a.setDate(RS.getString("date"));
				a.setTimeStart(RS.getString("timeStart"));
				a.setTimeEnd(RS.getString("timeEnd"));
				a.setTimeSpent(RS.getInt("timeSpent"));
				a.setActivityType(RS.getString("activityType"));
				list.add(a);
			}
		} catch (Exception e) {
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