import org.joda.time.DateTime

import com.charitychamp.Activity
import com.charitychamp.Business
import com.charitychamp.Campaign
import com.charitychamp.CharityChampConstants
import com.charitychamp.Company
import com.charitychamp.Department
import com.charitychamp.DonationSource
import com.charitychamp.GlobalNumericSetting
import com.charitychamp.Group
import com.charitychamp.JeansPayment
import com.charitychamp.Office
import com.charitychamp.Person
import com.charitychamp.StringList
import com.charitychamp.VolunteerShift

class BootStrap {
	
    def init = { servletContext ->
		
		environments  {
			
			
			development {
			
				loadBootStrapData()			
			}
			test {
			
			}
			production {
				loadBootStrapData()		
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
		
		def stringListOne = new StringList(listName : CharityChampConstants.ACTIVITY_TYPE , value : "Chili Cookoff").save(flush :true)
		def stringListTwo = new StringList(listName : CharityChampConstants.ACTIVITY_TYPE , value : "BakeOff").save(flush :true)
		def stringListThree = new StringList(listName : CharityChampConstants.ACTIVITY_TYPE , value : "Snack Sales").save(flush :true)
		def stringListFour = new StringList(listName : CharityChampConstants.ACTIVITY_TYPE , value : "Bake Sale").save(flush :true)
		
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
		
		DateTime dateOne = new DateTime(2013, 1 , 6, 0, 0)
		def activity1 = new Activity(name : 'Chili Cookoff', amountCollected : new BigDecimal('78.88'), donationDate : dateOne.toDate()).save(flush:true)
		def activity2 = new Activity(name : 'BakeOff', amountCollected : new BigDecimal('45.67'), donationDate : dateOne.toDate()).save(flush:true)
		def activity3 = new Activity(name : 'Snack Sales', amountCollected : new BigDecimal('136.44'), donationDate : dateOne.toDate()).save(flush:true)
		def activity4 = new Activity(name : 'Bake Sale', amountCollected : new BigDecimal('102.33'), donationDate : dateOne.toDate()).save(flush:true)
		def activity5 = new Activity(name : 'Chili Cookoff', amountCollected : new BigDecimal('78.88'), donationDate : dateOne.toDate()).save(flush:true)
		def activity6 = new Activity(name : 'BakeOff', amountCollected : new BigDecimal('45.67'), donationDate : dateOne.toDate()).save(flush:true)
		def activity7 = new Activity(name : 'Snack Sales', amountCollected : new BigDecimal('136.44'), donationDate : dateOne.toDate()).save(flush:true)
		def activity8 = new Activity(name : 'Bake Sale', amountCollected : new BigDecimal('102.33'), donationDate : dateOne.toDate()).save(flush:true)
		def activity9 = new Activity(name : 'Chili Cookoff', amountCollected : new BigDecimal('78.88'), donationDate : dateOne.toDate()).save(flush:true)
		def activity10 = new Activity(name : 'BakeOff', amountCollected : new BigDecimal('45.67'), donationDate : dateOne.toDate()).save(flush:true)
		def activity11 = new Activity(name : 'Snack Sales', amountCollected : new BigDecimal('136.44'), donationDate : dateOne.toDate()).save(flush:true)
		def activity12 = new Activity(name : 'Bake Sale', amountCollected : new BigDecimal('102.33'), donationDate : dateOne.toDate()).save(flush:true)
		def activity13 = new Activity(name : 'Snack Sales', amountCollected : new BigDecimal('136.44'), donationDate : dateOne.toDate()).save(flush:true)
		def activity14 = new Activity(name : 'Bake Sale', amountCollected : new BigDecimal('102.33'), donationDate : dateOne.toDate()).save(flush:true)
		
		def jeans1 = new JeansPayment(employeeUserId : 'biffleg', payerFirstName : 'Greg', payerLastName : 'Biffle ',amountCollected : new BigDecimal('85.00'),donationDate : dateOne.toDate()).save(flush:true)
		def jeans2 = new JeansPayment(employeeUserId : 'keselowb', payerFirstName : 'Brad', payerLastName : 'Keselowski ',amountCollected : new BigDecimal('85.00'),donationDate : dateOne.toDate()).save(flush:true)
		def jeans3 = new JeansPayment(employeeUserId : 'bowyerc', payerFirstName : 'Clint', payerLastName : 'Bowyer ',amountCollected : new BigDecimal('85.00'),donationDate : dateOne.toDate()).save(flush:true)
		def jeans4 = new JeansPayment(employeeUserId : 'kensethm', payerFirstName : 'Matt', payerLastName : 'Kenseth ',amountCollected : new BigDecimal('85.00'),donationDate : dateOne.toDate()).save(flush:true)
		
		DateTime effDateOne = new DateTime(2012,1,1,0,0)
		
		def mealsDollarBuys = new GlobalNumericSetting(name : 'Meals a Dollar Buys' ,effectiveDate : startDateOne.toDate(),value : new BigDecimal('3'),mofbShift: false).save(flush:true)
		def regular = new GlobalNumericSetting(name : 'Regular' ,effectiveDate : effDateOne.toDate(),value : new BigDecimal('10'),mofbShift: true).save(flush:true)
		def exec = new GlobalNumericSetting(name : 'Executive' ,effectiveDate : effDateOne.toDate(),value : new BigDecimal('33'),mofbShift: true).save(flush:true)
		def special = new GlobalNumericSetting(name : 'Special' ,effectiveDate : effDateOne.toDate(),value : new BigDecimal('25.5'),mofbShift: true).save(flush:true)
		def shift1 = new VolunteerShift(numberOfVolunteers : 10, donationDate : dateOne.toDate(), mealFactor : regular).save(flush:true)
		def shift2 = new VolunteerShift(numberOfVolunteers : 12, donationDate : dateOne.toDate(), mealFactor : regular).save(flush:true)
		def shift3 = new VolunteerShift(numberOfVolunteers : 21, donationDate : dateOne.toDate(), mealFactor : exec).save(flush:true)
		def shift4 = new VolunteerShift(numberOfVolunteers : 32, donationDate : dateOne.toDate(), mealFactor : special).save(flush:true)
		
		
		
		def donation1 = new DonationSource()
		donation1.donation = activity1
		donation1.orgUnit = group4
		donation1.save(flush:true, failOnError:true)
		
		def donation2 = new DonationSource()
		donation2.donation = activity2
		donation2.orgUnit = group4
		donation2.save(flush:true, failOnError:true)
		
		def donation3 = new DonationSource()
		donation3.donation = activity3
		donation3.orgUnit = group4
		donation3.save(flush:true, failOnError:true)
		
		def donation4 = new DonationSource()
		donation4.donation = activity4
		donation4.orgUnit = group4
		donation4.save(flush:true, failOnError:true)
		
		def donation5 = new DonationSource()
		donation5.donation = jeans1
		donation5.orgUnit = group4
		donation5.save(flush:true, failOnError:true)
		
		def donation6 = new DonationSource()
		donation6.donation = jeans2
		donation6.orgUnit = group4
		donation6.save(flush:true, failOnError:true)
		
		def donation7 = new DonationSource()
		donation7.donation = jeans3
		donation7.orgUnit = group6
		donation7.save(flush:true, failOnError:true)
		
		def donation8 = new DonationSource()
		donation8.donation = jeans4
		donation8.orgUnit = group6
		donation8.save(flush:true, failOnError:true)
		
		def donation9 = new DonationSource()
		donation9.donation = shift1
		donation9.orgUnit = group4
		donation9.save(flush:true, failOnError:true)
		
		def donation10 = new DonationSource()
		donation10.donation = shift2
		donation10.orgUnit = group4
		donation10.save(flush:true, failOnError:true)
		
		def donation11 = new DonationSource()
		donation11.donation = shift3
		donation11.orgUnit = group6
		donation11.save(flush:true, failOnError:true)
		
		def donation12 = new DonationSource()
		donation12.donation = shift4
		donation12.orgUnit = group6
		donation12.save(flush:true, failOnError:true)
		
		def donation13 = new DonationSource()
		donation13.donation = activity5
		donation13.orgUnit = group4
		donation13.save(flush:true, failOnError:true)
		
		def donation14 = new DonationSource()
		donation14.donation = activity6
		donation14.orgUnit = group4
		donation14.save(flush:true, failOnError:true)
		
		def donation15 = new DonationSource()
		donation15.donation = activity7
		donation15.orgUnit = group4
		donation15.save(flush:true, failOnError:true)
		
		def donation16 = new DonationSource()
		donation16.donation = activity8
		donation16.orgUnit = group4
		donation16.save(flush:true, failOnError:true)
		
		def donation17 = new DonationSource()
		donation17.donation = activity9
		donation17.orgUnit = group4
		donation17.save(flush:true, failOnError:true)
		
		def donation18 = new DonationSource()
		donation18.donation = activity10
		donation18.orgUnit = group4
		donation18.save(flush:true, failOnError:true)
		
		def donation19 = new DonationSource()
		donation19.donation = activity11
		donation19.orgUnit = group4
		donation19.save(flush:true, failOnError:true)
		
		def donation20 = new DonationSource()
		donation20.donation = activity12
		donation20.orgUnit = group4
		donation20.save(flush:true, failOnError:true)
		
		def donation21 = new DonationSource()
		donation21.donation = activity13
		donation21.orgUnit = group4
		donation21.save(flush:true, failOnError:true)
		
		def donation22 = new DonationSource()
		donation22.donation = activity14
		donation22.orgUnit = group4
		donation22.save(flush:true, failOnError:true)
		
		campaign2.addToDonationSources(donation1)
		campaign2.addToDonationSources(donation2)
		campaign2.addToDonationSources(donation3)
		campaign2.addToDonationSources(donation4)
		campaign2.addToDonationSources(donation5)
		campaign2.addToDonationSources(donation6)
		campaign2.addToDonationSources(donation7)
		campaign2.addToDonationSources(donation8)
		campaign2.addToDonationSources(donation9)
		campaign2.addToDonationSources(donation10)
		campaign2.addToDonationSources(donation11)
		campaign2.addToDonationSources(donation12)
		campaign2.addToDonationSources(donation13)
		campaign2.addToDonationSources(donation14)
		campaign2.addToDonationSources(donation15)
		campaign2.addToDonationSources(donation16)
		campaign2.addToDonationSources(donation17)
		campaign2.addToDonationSources(donation18)
		campaign2.addToDonationSources(donation19)
		campaign2.addToDonationSources(donation20)
		campaign2.save(flush:true)	
		
	}
    def destroy = {
    }
}
