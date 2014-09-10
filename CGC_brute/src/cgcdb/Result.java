package cgcdb;

// TODO: Auto-generated Javadoc
/**
 * The Enum Result.
 */
public enum Result { 
	
	/** The void. */
	VOID(false, "Void"),
	
	/** The na. */
	NA(false, "N/A"), 
	
	/** The a. */
	A(true, "A"), 
	
	/** The r. */
	R(true, "R"), 
	
	/** The m. */
	M(true, "M"), 
	
	/** The O1. */
	O1(true, "O.1"), 
	
	/** The O2. */
	O2(true, "O.2"), 
	
	/** The O4. */
	O4(true, "O.4"),
	
	/** The o. */
	O(true, "O");
	
	
//-------------------------------------------------
	/** The value. */
boolean value;
	
	/** The symbol. */
	String symbol;

	/**
	 * Instantiates a new result.
	 *
	 * @param value the value
	 * @param symbol the symbol
	 */
	Result(final boolean value, final String symbol){
		this.value = value;
		this.symbol = symbol;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public boolean getValue(){
		return value;
	}
	
	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol(){
		return symbol;
	}
	
	/**
	 * By symbol.
	 *
	 * @param str the str
	 * @return the result
	 */
	public static Result BySymbol(final String str) {
        for (Result e : Result.values()) {
            if (str.contains(e.symbol)) {
                return e;
            }
        }
        return null;
    }
}