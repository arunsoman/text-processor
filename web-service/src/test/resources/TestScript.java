import java.io.IOException;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.marker.MarkerFactory;
import com.flytxt.tp.processor.LineProcessor;
import com.flytxt.tp.store.ConsoleStore;
import com.flytxt.tp.store.Store;
import com.flytxt.tp.translator.TpConstant;
import com.flytxt.tp.translator.TpDate;
import com.flytxt.tp.translator.TpLogic;
import com.flytxt.tp.translator.TpMath;
import com.flytxt.tp.translator.TpString;

import liquibase.structure.core.DataType;

public class TestScript implements LineProcessor {
	public final String outputFolder = "TestScript1478773614567";
	public final String regex = "TestScript1478773614567";
	public final String inputFolder = "TestScript1478773614567";
	private String str_inter = new String(TpConstant.INTERDATATYPE.getData());

	public final MarkerFactory mf = new MarkerFactory();

	private TpMath tpMath = new TpMath();
	private TpDate tpDate = new TpDate();
	private TpString tpString = new TpString();
	private TpLogic tpLogic = new TpLogic();

	private Marker m_inter = TpConstant.INTERDATATYPE;
	private Marker m_intra = TpConstant.INTRADATATYPE;
	private Marker line;
	
	private Marker fileName;
	private Marker mlastModifiedTime;
	private Marker mpickedTime;

	private Marker mmsisdn;

	private Marker mMOU;

	private Marker mdate;

	private Marker mREV;

	private Marker mservicetype;

	private byte[] token_124 = "|".getBytes();

	private Marker mMSISDN_CHECK;

	private Marker mMSISDN_CHECK2;

	private Marker mONNET;

	private Marker mOFFNET;

	private Marker mMSISDN_FINAL;

	private Marker mFINAL_DATE;

	private Marker mOFFNET_FINAL_MOU;

	private Marker mOFFNET_REV_FINAL;

	private Marker mONNET_FINAL_MOU;

	private Marker mONNET_FINAL_REV;

	private Marker mRecharge;

	private Marker mRecharge10;

	private Marker mRechargemnull;

	private Marker mRecharge_at_;

	private Marker mRecharge_dt_;

	private Store streamStore = new ConsoleStore(outputFolder, "undefined");

	private Store localfileStore = new ConsoleStore(outputFolder, "MSISDN_FINAL", "OFFNET_FINAL_MOU",
			"OFFNET_REV_FINAL", "FINAL_DATE", "ONNET_FINAL_REV", "ONNET_FINAL_REV");

	private Marker mOFFNET_REV_FINAL_Metric_name;

	private Marker mOFFNET_REV_FINAL_Metric_day;

	private Marker mOFFNET_REV_FINAL_Metric_month;

	private Marker mOFFNET_REV_FINAL_Metric_week;

	private Marker mOFFNET_REV_FINAL_Metric_opType;

	private Marker mOFFNET_FINAL_MOU_Metric_name;

	private Marker mOFFNET_FINAL_MOU_Metric_day;

	private Marker mOFFNET_FINAL_MOU_Metric_month;

	private Marker mOFFNET_FINAL_MOU_Metric_week;

	private Marker mOFFNET_FINAL_MOU_Metric_opType;

	private Store neonStore = new ConsoleStore(outputFolder, "__temp1");

	public TestScript() {
		mmsisdn = mf.createMarker(null, 0, 0);

		mMOU = mf.createMarker(null, 0, 0);

		mdate = mf.createMarker(null, 0, 0);

		mREV = mf.createMarker(null, 0, 0);

		mservicetype = mf.createMarker(null, 0, 0);

		mMSISDN_CHECK = mf.createMarker("84".getBytes(), 0, "84".getBytes().length);

		mMSISDN_CHECK2 = mf.createMarker("084".getBytes(), 0, "084".getBytes().length);

		mONNET = mf.createMarker("1".getBytes(), 0, "1".getBytes().length);

		mOFFNET = mf.createMarker("2".getBytes(), 0, "2".getBytes().length);

		byte[] mRecharge_at_b = ("1").getBytes();

		byte[] mRecharge_dt_b = ("1").getBytes();

		byte[] mRecharge10_dt_b = ("10").getBytes();

		mRecharge10 = mf.createMarker(mRecharge10_dt_b, 0, mRecharge10_dt_b.length);

		mRecharge_at_ = mf.createMarker(mRecharge_at_b, 0, mRecharge_at_b.length);

		mRecharge_dt_ = mf.createMarker(mRecharge_dt_b, 0, mRecharge_dt_b.length);

		localfileStore.set("TestScript");

		byte[] mOFFNET_REV_FINAL_Metric_name_b = ("642").getBytes();

		mOFFNET_REV_FINAL_Metric_name = mf.createMarker(mOFFNET_REV_FINAL_Metric_name_b, 0,
				mOFFNET_REV_FINAL_Metric_name_b.length);

		byte[] mOFFNET_REV_FINAL_Metric_day_b = ("1").getBytes();

		mOFFNET_REV_FINAL_Metric_day = mf.createMarker(mOFFNET_REV_FINAL_Metric_day_b, 0,
				mOFFNET_REV_FINAL_Metric_day_b.length);

		byte[] mOFFNET_REV_FINAL_Metric_month_b = ("0").getBytes();

		mOFFNET_REV_FINAL_Metric_month = mf.createMarker(mOFFNET_REV_FINAL_Metric_month_b, 0,
				mOFFNET_REV_FINAL_Metric_month_b.length);

		byte[] mOFFNET_REV_FINAL_Metric_week_b = ("0").getBytes();

		mOFFNET_REV_FINAL_Metric_week = mf.createMarker(mOFFNET_REV_FINAL_Metric_week_b, 0,
				mOFFNET_REV_FINAL_Metric_week_b.length);

		byte[] mOFFNET_REV_FINAL_Metric_opType_b = ("1").getBytes();

		mOFFNET_REV_FINAL_Metric_opType = mf.createMarker(mOFFNET_REV_FINAL_Metric_opType_b, 0,
				mOFFNET_REV_FINAL_Metric_opType_b.length);

		byte[] mOFFNET_FINAL_MOU_Metric_name_b = ("641").getBytes();

		mOFFNET_FINAL_MOU_Metric_name = mf.createMarker(mOFFNET_FINAL_MOU_Metric_name_b, 0,
				mOFFNET_FINAL_MOU_Metric_name_b.length);

		byte[] mOFFNET_FINAL_MOU_Metric_day_b = ("0").getBytes();

		mOFFNET_FINAL_MOU_Metric_day = mf.createMarker(mOFFNET_FINAL_MOU_Metric_day_b, 0,
				mOFFNET_FINAL_MOU_Metric_day_b.length);

		byte[] mOFFNET_FINAL_MOU_Metric_month_b = ("0").getBytes();

		mOFFNET_FINAL_MOU_Metric_month = mf.createMarker(mOFFNET_FINAL_MOU_Metric_month_b, 0,
				mOFFNET_FINAL_MOU_Metric_month_b.length);

		byte[] mOFFNET_FINAL_MOU_Metric_week_b = ("0").getBytes();

		mOFFNET_FINAL_MOU_Metric_week = mf.createMarker(mOFFNET_FINAL_MOU_Metric_week_b, 0,
				mOFFNET_FINAL_MOU_Metric_week_b.length);

		byte[] mOFFNET_FINAL_MOU_Metric_opType_b = ("1").getBytes();

		mOFFNET_FINAL_MOU_Metric_opType = mf.createMarker(mOFFNET_FINAL_MOU_Metric_opType_b, 0,
				mOFFNET_FINAL_MOU_Metric_opType_b.length);

		line = mf.getLineMarker();
		mnull = mf.createMarker(" ".getBytes(), 0, 0);
	}

	@Override
	public void init(String fileNameStr, long lastModifiedTime) {
		byte[] b1 = String.valueOf(lastModifiedTime).getBytes();
		mlastModifiedTime = mf.createMarker(b1, 0, b1.length);
		byte[] b2 = String.valueOf(System.currentTimeMillis()).getBytes();
		mpickedTime = mf.createMarker(b2, 0, b2.length);
		byte[] tt = fileNameStr.getBytes();
		this.fileName = mf.createMarker(tt, 0, tt.length);
		streamStore.set(fileNameStr);
		localfileStore.set(fileNameStr);
		neonStore.set(fileNameStr);

	}

	public String getSourceFolder() {
		return inputFolder;
	}

	public void process() throws IOException {
		process(mf.getLineMarker().getData());
		translate(mf.getLineMarker().getData());
		store(mf.getLineMarker().getData());
		mf.reclaim();
	}

	public String done() throws IOException {
		StringBuilder sb = new StringBuilder("[");
		sb.append(streamStore.done()).append(",");
		sb.append(localfileStore.done()).append(",");
		sb.append(neonStore.done()).append(",");

		sb.deleteCharAt(sb.length() - 1);
		sb.append(']');
		return sb.toString();
	}

	public String getFilter() {
		return this.regex;
	}

	public MarkerFactory getMf() {
		return mf;
	}

	public void process(final byte[] data) {
		line.splitAndGetMarkers(token_124, new int[] { 1, 3, 4, 2, 5 }, mf, mmsisdn, mMOU, mdate, mREV, mservicetype);

	}

	public void translate(final byte[] data) {
		if ((tpString.startsWith(mmsisdn, mMSISDN_CHECK)) || (tpString.startsWith(mmsisdn, mMSISDN_CHECK2))) {
			mMSISDN_FINAL = mmsisdn;
			mFINAL_DATE = mdate;
			if (tpMath.eq(mservicetype, mOFFNET, mf)) {
				mOFFNET_FINAL_MOU = mMOU;
				mOFFNET_REV_FINAL = mREV;
			} else if (tpMath.eq(mservicetype, mONNET, mf)) {
				mONNET_FINAL_MOU = mMOU;
				mONNET_FINAL_REV = mREV;
			}
		}
		try {
			mdate = tpDate.convertDate(mdate, mf, "dd-mm-yyyy");
		} catch (java.text.ParseException e) {
		}
		try {
			mFINAL_DATE = tpDate.convertDate(mFINAL_DATE, mf, "dd-mm-yyyy");
		} catch (java.text.ParseException e) {
		}

	}

	public void store(final byte[] data) throws IOException {
		streamStore.save(data, "", mnull, mnull, mRecharge_at_, mRecharge_dt_, mRecharge10, mOFFNET_FINAL_MOU, mnull,
				mFINAL_DATE, mlastModifiedTime, mpickedTime, mRecharge);

		localfileStore.save(data, fileName.toString(), mMSISDN_FINAL, mOFFNET_FINAL_MOU, mOFFNET_REV_FINAL, mFINAL_DATE,
				mONNET_FINAL_REV, mONNET_FINAL_REV);
		neonStore.save(data, fileName.toString(), mMSISDN_FINAL, mOFFNET_REV_FINAL_Metric_name,
				mOFFNET_REV_FINAL_Metric_day, mOFFNET_REV_FINAL_Metric_month, mOFFNET_REV_FINAL_Metric_week,
				mOFFNET_REV_FINAL_Metric_opType, mdate, mOFFNET_REV_FINAL);
		neonStore.save(data, fileName.toString(), mMSISDN_FINAL, mOFFNET_FINAL_MOU_Metric_name,
				mOFFNET_FINAL_MOU_Metric_day, mOFFNET_FINAL_MOU_Metric_month, mOFFNET_FINAL_MOU_Metric_week,
				mOFFNET_FINAL_MOU_Metric_opType, mdate, mOFFNET_FINAL_MOU);

	}
}
