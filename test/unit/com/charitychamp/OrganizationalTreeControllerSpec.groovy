package com.charitychamp

import java.util.Date;

import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

class OrganizationalTreeControllerSpec extends UnitSpec{
//TODO fix these tests once the tree with URLs is finalized
	
//   def "Tree action should return JSON response with one level nesting"() {
//	    setup:
//		
//		mockDomain(Person)
//		mockDomain(Group)
//		mockDomain(Department)
//		mockDomain(Office)
//		mockDomain(Business)
//		mockDomain(Company)
//		   
//	   
//        when:
//
//		def company = new Company(name : 'England Pewter Co.', ceo : new Person(userId : 'meyersm', firstName: 'Michael', lastName : 'Meyers').save()).save()
//		def business = new Business(name : 'Logistics', executive : new Person(userId : 'biffleg', firstName: 'Greg', lastName : 'Biffle').save(), charityLeader : new Person(userId : 'johnsonj', firstName: 'Jimmy', lastName : 'Johnson').save(), teamNumber : '1234', company : company).save()
//		company.addToBusinesses(business).save()
//		def office = new Office(name : 'Outgoing', officer :  new Person(userId : 'breesd', firstName: 'Drew', lastName : 'Brees').save(), charityCaptain : new Person(userId : 'whiter', firstName: 'Roddy', lastName : 'white').save(), business : business).save()
//		business.addToOffices(office).save()
//		def department = new Department(name : 'Shipping', departmentHead : new Person(userId : 'simmonsd', firstName: 'Dan', lastName : 'Simmons').save(), charityLieutenant : new Person(userId : 'kings', firstName: 'Stephen', lastName : 'King').save(), office : office).save()
//		office.addToDepartments(department).save()
//		def group = new Group(name : 'Packaging', department : department).save()		
//		department.addToGroups(group).save()
//	
//        controller.tree()
//
//        then:
//      
//		response.status == 200
//		response.contentAsString == '[{"id":1,"name":"England Pewter Co.","pid":0},{"id":2,"name":"Logistics","pid":1},{"id":3,"name":"Outgoing","pid":2},{"id":4,"name":"Shipping","pid":3},{"id":5,"name":"Packaging","pid":4}]'
//									
//	
//    }
   
   def "Tree action should return an error JSON response if there are no Domain objects"() {
	   setup:
	   
	   mockDomain(Person)
	   mockDomain(Group)
	   mockDomain(Department)
	   mockDomain(Office)
	   mockDomain(Business)
	   mockDomain(Company)
		  
	  
	   when:

	   controller.tree()

	   then:
	 
	   response.status == 500
	
								   
   
   }
   
//   def "Tree action should return JSON response with mulit level nesting"() {
//	   setup:
//	   
//	   mockDomain(Person)
//	   mockDomain(Group)
//	   mockDomain(Department)
//	   mockDomain(Office)
//	   mockDomain(Business)
//	   mockDomain(Company)
//		  
//	  
//	   when:
//
//	   def company = new Company(name : 'England Pewter Co.', ceo : new Person(userId : 'meyersm', firstName: 'Michael', lastName : 'Meyers').save()).save()
//	   def business = new Business(name : 'Logistics', executive : new Person(userId : 'biffleg', firstName: 'Greg', lastName : 'Biffle').save(), charityLeader : new Person(userId : 'johnsonj', firstName: 'Jimmy', lastName : 'Johnson').save(), teamNumber : '1234', company : company).save()
//	   company.addToBusinesses(business).save()
//	   def office = new Office(name : 'Outgoing', officer :  new Person(userId : 'breesd', firstName: 'Drew', lastName : 'Brees').save(), charityCaptain : new Person(userId : 'whiter', firstName: 'Roddy', lastName : 'white').save(), business : business).save()
//	   def office2 = new Office(name : 'Claims', officer :  new Person(userId : 'luckb', firstName: 'Brandon', lastName : 'Luck').save(), charityCaptain : new Person(userId : 'fortef', firstName: 'Matt', lastName : 'Forte').save(), business : business).save()
//	   business.addToOffices(office).save()
//	   business.addToOffices(office2).save()
//	   def department = new Department(name : 'Shipping', departmentHead : new Person(userId : 'simmonsd', firstName: 'Dan', lastName : 'Simmons').save(), charityLieutenant : new Person(userId : 'kings', firstName: 'Stephen', lastName : 'King').save(), office : office).save()
//	   office.addToDepartments(department).save()
//	   def group = new Group(name : 'Packaging', department : department).save()
//	   def group2 = new Group(name : 'Invoices', department : department).save()
//	   department.addToGroups(group).save()
//	   department.addToGroups(group2).save()
//   
//	   controller.tree()
//
//	   then:
//	   response.status == 200
//	   response.contentAsString == '[{"id":1,"name":"England Pewter Co.","pid":0},{"id":2,"name":"Logistics","pid":1},{"id":3,"name":"Claims","pid":2},{"id":4,"name":"Outgoing","pid":2},{"id":5,"name":"Shipping","pid":4},{"id":6,"name":"Invoices","pid":5},{"id":7,"name":"Packaging","pid":5}]'
//								   
//   
//   }
}
