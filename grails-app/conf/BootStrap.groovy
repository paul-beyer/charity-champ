import org.joda.time.DateTime

import com.charitychamp.Business
import com.charitychamp.Campaign
import com.charitychamp.Company
import com.charitychamp.Department
import com.charitychamp.Group
import com.charitychamp.Office
import com.charitychamp.Person

class BootStrap {
	
    def init = { servletContext ->
		
		environments  {
			
			
			development {
			
				loadBootStrapData()			
			}
			test {
			
			}
			production {
				
			}
		}
	
	}	
  
	def loadBootStrapData = {
		
		DateTime startDateOne = new DateTime(2011, 1 , 1, 0, 0)
		DateTime endDateOne = new DateTime(2011, 12 , 31, 0, 0)
		
		DateTime startDateTwo = new DateTime(2012, 1 , 1, 0, 0)
		DateTime endDateTwo = new DateTime(2012, 12 , 31, 0, 0)
		
		DateTime startDateThree = new DateTime(2013, 1 , 1, 0, 0)
		DateTime endDateThree = new DateTime(2013, 12 , 31, 0, 0)
		
		def campaign = new Campaign(name : "First Campaign", startDate : startDateOne.toDate(), endDate : endDateOne.toDate()).save(flush:true)
		def campaign1 = new Campaign(name: 'Second Campaign', startDate : startDateTwo.toDate(), endDate : endDateTwo.toDate()).save()
		def campaign2 = new Campaign(name: 'Third Campaign', startDate : startDateThree.toDate(), endDate : endDateThree.toDate()).save()
		
		def ceo = new Person(userId : "breesd", firstName : "Drew", lastName : "Brees", personTitle : "CEO", email : "breesd@gmail.com").save(flush:true)
		def executive = new Person(userId : "whiter", firstName : "Roddy", lastName : "White", personTitle : "VP", email : "whitr@gmail.com").save(flush:true)
		def officer = new Person(userId : "turnerm", firstName : "Michael", lastName : "Turner", personTitle : "VP", email : "turnerm@gmail.com").save(flush:true)
		def deptHead = new Person(userId : "johnsons", firstName : "Stevie", lastName : "Johnson", personTitle : "AVP", email : "johnsons@gmail.com").save(flush:true)
		def charityLeader = new Person(userId : "kensethm", firstName : "Matt", lastName : "Kenseth", personTitle : "Consultant", email : "kensethm@gmail.com").save(flush:true)
		def charityCaptain = new Person(userId : "biffleg", firstName : "Greg", lastName : "Biffle", personTitle : "Consultant", email : "biffleg@gmail.com").save(flush:true)
		def charityLieutenant = new Person(userId : "kaynek", firstName : "Kasey", lastName : "Kayne", personTitle : "Consultant", email : "kaynek@gmail.com").save(flush:true)
		def person8 = new Person(userId : "nicksh", firstName : "Hakeem", lastName : "Nicks", personTitle : "Consultant", email : "nicks@gmail.com").save(flush:true)
		def person9 = new Person(userId : "ricer", firstName : "Ray", lastName : "Rice", personTitle : "Consultant", email : "ricer@gmail.com").save(flush:true)
		def person10 = new Person(userId : "smitht", firstName : "Torrey", lastName : "Smith", personTitle : "Consultant", email : "smitht@gmail.com").save(flush:true)
		def person11 = new Person(userId : "richardt", firstName : "Trent", lastName : "Richardson", personTitle : "Consultant", email : "richartk@gmail.com").save(flush:true)
		def person12 = new Person(userId : "morrisa", firstName : "Alfred", lastName : "Morris", personTitle : "Consultant", email : "morrisa@gmail.com").save(flush:true)
		
		
		def company = new Company(name: "ACME", leader : ceo).save(flush:true)
		def business = new Business(name : "Marketing", leader : executive, charityLeader : charityLeader, teamNumber : "1411", company : company).save(flush:true)
		def business2 = new Business(name : "IT", leader : executive, charityLeader : charityLeader, teamNumber: "1562", company : company).save(flush:true)
		def office = new Office(name : "Enterprise IT", leader : officer, charityCaptain : charityCaptain, business : business).save(flush:true)
		def office2 = new Office(name : "Sales", leader : officer, charityCaptain : charityCaptain, business : business).save(flush:true)
		def office3 = new Office(name : "Annuities", leader : officer, charityCaptain : charityCaptain, business : business2).save(flush:true)
		def office4 = new Office(name : "Service", leader : officer, charityCaptain : charityCaptain, business : business2).save(flush:true)
		def office5 = new Office(name : "Warranty", leader : officer, charityCaptain : charityCaptain, business : business2).save(flush:true)
		
		def department = new Department(name : "Billing", leader : deptHead, charityLieutenant : charityLieutenant, numberOfEmployees : 145, dateOfEmployeeCount : new Date(), office : office).save(flush:true)
		def department2 = new Department(name : "Accounts Payable", leader : deptHead, charityLieutenant : charityLieutenant, numberOfEmployees : 156, dateOfEmployeeCount : new Date(), office : office2).save(flush:true)
		def department3 = new Department(name : "Accounts Receivable", leader : deptHead, charityLieutenant : charityLieutenant, numberOfEmployees : 321, dateOfEmployeeCount : new Date(), office : office3).save(flush:true)
		def department4 = new Department(name : "Accounting", leader : deptHead, charityLieutenant : charityLieutenant, numberOfEmployees : 415, dateOfEmployeeCount : new Date(), office : office4).save(flush:true)
		
		def group = new Group(name : "X-Men", leader : ceo, department : department).save(flush:true)
		def group2 = new Group(name : "DejaVu", leader : ceo, department : department).save(flush:true)
		def group3 = new Group(name : "RadioHead", leader : ceo, department : department2).save(flush:true)
		def group4 = new Group(name : "VelocityRaptors", leader : ceo, department : department2).save(flush:true)
		def group5 = new Group(name : "Red Fish, Blue Fish", leader : ceo, department : department3).save(flush:true)
		def group6 = new Group(name : "Lab Rats", leader : ceo, department : department3).save(flush:true)
		def group7 = new Group(name : "Mad Hatters", leader : ceo, department : department4).save(flush:true)
		def group8 = new Group(name : "Testers", leader : ceo, department : department4).save(flush:true)
		
	}
    def destroy = {
    }
}
