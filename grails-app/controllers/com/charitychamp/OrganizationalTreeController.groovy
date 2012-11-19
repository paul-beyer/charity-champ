package com.charitychamp

import grails.converters.JSON

class OrganizationalTreeController {

    def index() { 
		

	}
	
	def addToList = {orgList, name, id, pid, link ->
		
		def map = new HashMap()
		
		map.put('name', name)
		map.put('id', id)
		map.put('pid', pid)
		map.put('link', link)
		
		orgList << map
		
	}
	
	def tree(){
			
		def companies = Company.findAll()
		def sortedCompanies = companies.sort{it.name}
		def orgList = new ArrayList()
		def idCount = 1
				
		sortedCompanies.each {
			def compId = idCount
			addToList(orgList, it.name, idCount, 0, '')
			idCount++
			
			if(it.businesses?.size() > 0){
				def sortedBusinesses = it.businesses.sort{it.name}
				sortedBusinesses.each {
					def buslink = "/charity-champ/group/overview/1"
					def businessId = idCount
					addToList(orgList, it.name, idCount, compId, buslink)
					idCount++
					
					if(it.offices?.size() > 0){
						def sortedOffices = it.offices.sort{it.name}
						sortedOffices.each {
							def officeId = idCount
							addToList(orgList, it.name, idCount, businessId, '')
							idCount++
							
							if(it.departments?.size() > 0){
								def sortedDepartments = it.departments.sort{it.name}
								sortedDepartments.each{
									def departmentId = idCount
									addToList(orgList, it.name, idCount, officeId, '')
									idCount++
									
									if(it.groups?.size() > 0){
										def sortedGroups = it.groups.sort{it.name}
										sortedGroups.each{
											def link = "/charity-champ/group/overview/${it.id}"
											addToList(orgList, it.name, idCount, departmentId, link)
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
