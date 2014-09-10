package cgcdb;
// TODO: Auto-generated Javadoc

/**
 * The Enum Protocol.
 * 
 * 2G, 3G, LTE
 * 
 */
public enum Protocol {
	
	/** The _2 g. */
	_2G("2G"), 
    /** The _3 g. */
    _3G("3G"), 
    /** The  lte. */
    _LTE("LTE");
	
	/** The symbol. */
	String symbol;
	
	/**
	 * Instantiates a new protocol.
	 *
	 * @param str the str
	 */
	Protocol(String str){
		this.symbol = str;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		
		return symbol;
	}
	
	/**
	 * By symbol.
	 *
	 * @param str the str
	 * @return the protocol
	 */
	public static Protocol BySymbol(final String str) {
        for (Protocol e : Protocol.values()) {
            if (str.contains(e.symbol)) {
                return e;
            }
        }
        return null;
    }
}
