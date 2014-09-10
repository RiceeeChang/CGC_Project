package cgcdb;

// TODO: Auto-generated Javadoc
/**
 * The Class _3GInfo.
 */
public class _3GInfo {
	
	/** The gcf_version. */
	private String gcf_version;
	
	/** The ptcrb_version. */
	private String ptcrb_version;
	
	/** The category. */
	private String category;
	
	/** The hsdpa_category. */
	private String hsdpa_category;
	
	/** The primary_spec. */
	private String primary_spec;
	
	/** The audio_category. */
	private String audio_category;
	
	/**
	 * Instantiates a new _3 g info.
	 *
	 * @param gcf_version the gcf_version
	 * @param ptcrb_version the ptcrb_version
	 * @param category the category
	 * @param hsdpa_category the hsdpa_category
	 * @param primary_spec the primary_spec
	 * @param audio_category the audio_category
	 */
	public _3GInfo(
			String gcf_version,
			String ptcrb_version,
			String category,
			String hsdpa_category,
			String primary_spec,
			String audio_category){
		this.gcf_version = gcf_version;
		this.ptcrb_version = ptcrb_version;
		this.category = category;
		this.hsdpa_category = hsdpa_category;
		this.primary_spec = primary_spec;
		this.audio_category = audio_category;
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
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * Gets the HSDP a_ category.
	 *
	 * @return the HSDP a_ category
	 */
	public String getHSDPA_Category(){
		return hsdpa_category;
	}
	
	/**
	 * Gets the primary_ spec.
	 *
	 * @return the primary_ spec
	 */
	public String getPrimary_Spec(){
		return primary_spec;
	}
	
	/**
	 * Gets the audio_ category.
	 *
	 * @return the audio_ category
	 */
	public String getAudio_Category(){
		return audio_category;
	}
}
