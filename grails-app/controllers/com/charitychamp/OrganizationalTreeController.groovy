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
		
		def foundCompanies = Company.findAll()
		def sortedCompanies = foundCompanies.sort{it.name}
		
//		def orgList = new ArrayList()
//		def idCount = 1
//				
//		sortedCompanies.each {
//			def compId = idCount
//			addToList(orgList, it.name, idCount, 0, '')
//			idCount++
//			
//			if(it.businesses?.size() > 0){
//				def sortedBusinesses = it.businesses.sort{it.name}
//				sortedBusinesses.each {
//					def buslink = "/charity-champ/group/overview/1"
//					def businessId = idCount
//					addToList(orgList, it.name, idCount, compId, buslink)
//					idCount++
//					
//					if(it.offices?.size() > 0){
//						def sortedOffices = it.offices.sort{it.name}
//						sortedOffices.each {
//							def officeId = idCount
//							addToList(orgList, it.name, idCount, businessId, '')
//							idCount++
//							
//							if(it.departments?.size() > 0){
//								def sortedDepartments = it.departments.sort{it.name}
//								sortedDepartments.each{
//									def departmentId = idCount
//									addToList(orgList, it.name, idCount, officeId, '')
//									idCount++
//									
//									if(it.groups?.size() > 0){
//										def sortedGroups = it.groups.sort{it.name}
//										sortedGroups.each{
//											def link = "/charity-champ/group/overview/${it.id}"
//											addToList(orgList, it.name, idCount, departmentId, link)
//											idCount++
//										}
//										
//									}
//								}								
//							}
//						}
//					}
//				}
//			}
//		}
		
		//map of whole org
		def orgMap = [:]
//		def businesses = []
//		def offices = []
//		def companies = []
//		
//		def officeMap1 = ["name" : "Enterprise Applications", "url": "/charity-champ/business/overview/1"] 
//		def officeMap2 = ["name" : "Claims", "url": "/charity-champ/business/overview/1"]
//		offices << officeMap1
//		offices << officeMap2
//		
//		def businessMap1 = ["name" : "IT", "url": "/charity-champ/business/overview/1", "offices" : []]
//		def businessMap2 = ["name" : "Marketing", "url": "/charity-champ/business/overview/2" , "offices" : offices]
//		businesses << businessMap1
//		businesses << businessMap2
//		
//		
//		
//		def companyMap1 = ["name":"Nationwide", "url":"/charity-champ/admin/admin", "businesses": businesses]
		def companyList = []
//		companyList << companyMap1
//		orgMap.companies = companyList
	
			
	sortedCompanies?.each {
	
		
		def businesses = []
		
		def sortedBusinesses = it.businesses?.sort{it.name}
		sortedBusinesses.each{
			def offices = []
			def sortedOffices = it.offices?.sort{it.name}
			sortedOffices.each{
				def departments = []
				def sortedDepartments = it.departments?.sort{it.name}
				sortedDepartments.each{
					def groups = []
					def sortedGroups = it.groups?.sort{it.name}
					sortedGroups.each{
						def group = ["name" : it.name, "url" : "/charity-champ/group/overview/${it.id}"]
						groups << group
					
					}
					
					def department = ["name" : it.name, "url" : "/charity-champ/department/overview/${it.id}", "groups" : groups]
					departments << department
				}
				
				def office = ["name" : it.name, "url" : "", "departments" : departments]
				offices << office
			}
			
			def business = ["name" : it.name, "url" : "", "offices" : offices]
			businesses << business
		}
		
		def company = ["name" : it.name, "url" : "", "businesses" : businesses]
		companyList << company
	}	
	
	
	orgMap.companies = companyList
					
		
	def status = 200
	def returnValue = orgMap
	if(companyList.size() == 0){
		status = 500
		returnValue = ['error', 'No organizational structure could be returned']
	}
	
	response.status = status
	render orgMap as JSON
		

		
	}
}
