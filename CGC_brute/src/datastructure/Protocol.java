package datastructure;
public enum Protocol {
	_2G("2G"), _3G("3G"), _LTE("LTE");
	
	String symbol;
	
	Protocol(String str){
		this.symbol = str;
	}
	public String toString(){
		
		return symbol;
	}
}
