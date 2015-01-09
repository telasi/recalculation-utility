package telasi.recutil.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.service.Session;

public class TrashSubsidy {
	private Date startDate;
	private Date endDate;
	private int operationId;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public static List/* TrashSubsidy */getSubsidies(Session session, int customerID) throws SQLException {
		String sql = "SELECT * FROM bs.trashsubsidies WHERE custkey = ? and quantity > 0 ORDER BY 1";

		List subsidies = new ArrayList();
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setInt(1, customerID);
			res = st.executeQuery();
			while (res.next()) {
				TrashSubsidy subs = new TrashSubsidy();
				subs.setStartDate(Date.create(res.getDate("fromdate")));
				subs.setEndDate(Date.create(res.getDate("todate")));
				subs.setOperationId(res.getInt("operationid"));
				subsidies.add(subs);
			}
		} finally {
			try {
				res.close();
				st.close();
			} catch (Exception ex) {
			}
		}

		return subsidies;
	}
}
