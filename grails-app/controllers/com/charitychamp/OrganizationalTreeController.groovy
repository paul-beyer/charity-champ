package com.charitychamp

import grails.converters.JSON

class OrganizationalTreeController {

    def index() { 
		

	}
	
	def addToList = {orgList, name, id, pid ->
		
		def map = new HashMap()
		
		map.put('name', name)
		map.put('id', id)
		map.put('pid', pid)
		
		orgList << map
		
	}
	
	def tree(){
			
		def companies = Company.findAll()
		def orgList = new ArrayList()
		def idCount = 1
				
		companies.each {
			def compId = idCount
			addToList(orgList, it.name, idCount, 0)
			idCount++
			
			if(it.businesses.size() > 0){
				it.businesses.each {
					def businessId = idCount
					addToList(orgList, it.name, idCount, compId)
					idCount++
					
					if(it.offices.size() > 0){
						it.offices.each {
							def officeId = idCount
							addToList(orgList, it.name, idCount, businessId)
							idCount++
							
							if(it.departments.size() > 0){
								it.departments.each{
									def departmentId = idCount
									addToList(orgList, it.name, idCount, officeId)
									idCount++
									
									if(it.groups.size() > 0){
										it.groups.each{
											addToList(orgList, it.name, idCount, departmentId)
											idCount++
										}
										
									}
								}								
							}
						}
					}
				}
			}
		}
		
		def status = 200
		def returnValue = orgList
		if(orgList.size() == 0){
			status = 500
			returnValue = ['error', 'No organizational structure could be returned']
		}
		
		response.status = status
		render returnValue as JSON
		

		
	}
}
