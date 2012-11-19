package com.charitychamp

class Campaign {
	
	String name
	Date startDate
	Date endDate
	

    static constraints = {
		name blank:false, unique:true
		
    }
	
	@Override
	public String toString(){
		
	
		return "${name} ${startDate.toString().substring(0,10)} - ${endDate.toString()substring(0,10)}"
		
	}
}
