package telasi.recutil.beans;

import java.io.Serializable;

/*
 * RECALC_TRASH_SAVE table.
 */
public class TrashVoucher implements Serializable {
	private static final long serialVersionUID = -1687788546642617219L;

	public static final TrashOperation OPER_CHARGE_CORRECTION_AFTER_2011 = new TrashOperation(215, "დარიცხვის შესწორება 2011");
	public static final TrashOperation OPER_SUBSIDY_CORRECTION_AFTER_2011 = new TrashOperation(227, "შეღავათის კორექტირება 2011");

	public static TrashOperation findOperation(int id) {
		if (OPER_CHARGE_CORRECTION_AFTER_2011.getId() == id) {
			return OPER_CHARGE_CORRECTION_AFTER_2011;
		} else if (OPER_SUBSIDY_CORRECTION_AFTER_2011.getId() == id) {
			return OPER_SUBSIDY_CORRECTION_AFTER_2011;
		}
		return null;
	}

	private long id;
	private int trashOperation;
	private double kwh;
	private double gel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTrashOperation() {
		return trashOperation;
	}

	public void setTrashOperation(int trashOperation) {
		this.trashOperation = trashOperation;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public double getGel() {
		return gel;
	}

	public void setGel(double gel) {
		this.gel = gel;
	}

	public static class TrashOperation {
		private int id;
		private String name;

		private TrashOperation(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}

}
