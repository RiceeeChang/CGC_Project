package cgcdb;

// TODO: Auto-generated Javadoc
/**
 * The Class _2GInfo.
 */
public class _2GInfo{
	
	/** The gcf_version. */
	private String gcf_version;
	
	/** The ptcrb_version. */
	private String ptcrb_version;
	
	/** The gsm. */
	private String gsm;
	
	/** The gprs. */
	private String gprs;
	
	/** The egprs. */
	private String egprs;
	
	/** The amr. */
	private String amr;
	
	/** The satk. */
	private String satk;
	
	/** The dtm. */
	private String dtm;
	
	/**
	 * Instantiates a new _2 g info.
	 *
	 * @param gcf_version the gcf_version
	 * @param ptcrb_version the ptcrb_version
	 * @param gsm the gsm
	 * @param gprs the gprs
	 * @param egprs the egprs
	 * @param amr the amr
	 * @param satk the satk
	 * @param dtm the dtm
	 */
	public _2GInfo(
			String gcf_version,
			String ptcrb_version,
			String gsm,
			String gprs,
			String egprs,
			String amr,
			String satk,
			String dtm){
		this.gcf_version = gcf_version;
		this.ptcrb_version = ptcrb_version;
		this.gsm = gsm;
		this.gprs = gprs;
		this.egprs = egprs;
		this.amr = amr;
		this.satk = satk;
		this.dtm = dtm;
	}
	
	/**
	 * Gets the GC f_ version.
	 *
	 * @return the GC f_ version
	 */
	public String getGCF_Version(){
		return gcf_version;
	}
	
	/**
	 * Gets the PTCR b_ version.
	 *
	 * @return the PTCR b_ version
	 */
	public String getPTCRB_Version(){
		return ptcrb_version;
	}
	
	/**
	 * Gets the GS m_ category.
	 *
	 * @return the GS m_ category
	 */
	public String getGSM_Category(){
		return gsm;
	}
	
	/**
	 * Gets the GPR s_ category.
	 *
	 * @return the GPR s_ category
	 */
	public String getGPRS_Category(){
		return gprs;
	}
	
	/**
	 * Gets the EGPR s_ category.
	 *
	 * @return the EGPR s_ category
	 */
	public String getEGPRS_Category(){
		return egprs;
	}
	
	/**
	 * Gets the AM r_ category.
	 *
	 * @return the AM r_ category
	 */
	public String getAMR_Category(){
		return amr;
	}
	
	/**
	 * Gets the SAT k_ category.
	 *
	 * @return the SAT k_ category
	 */
	public String getSATK_Category(){
		return satk;
	}
	
	/**
	 * Gets the DT m_ category.
	 *
	 * @return the DT m_ category
	 */
	public String getDTM_Category(){
		return dtm;
	}
}
