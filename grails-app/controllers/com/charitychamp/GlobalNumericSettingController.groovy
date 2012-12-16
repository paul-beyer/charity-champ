package com.charitychamp

import java.text.NumberFormat

import org.springframework.dao.DataIntegrityViolationException

class GlobalNumericSettingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def goalPerEmployee (){
		
		def settings = GlobalNumericSetting.findAllByName("Goal Amount Per Employee")
		def goals = settings.sort{it.effectiveDate}.reverse()
		[globalNumericSettingInstanceList: goals, globalNumericSettingInstanceTotal: goals.size()]		
		
	}
	
	def mofbShiftValue (){
		
		def settings = GlobalNumericSetting.findAllWhere(mofbShift: true)
		def mofbValues = settings.sort{it.effectiveDate}.reverse()
		[globalNumericSettingInstanceList: mofbValues, globalNumericSettingInstanceTotal: mofbValues.size()]
		
	}
	
	def mealsADollarBuys(){
		
		def settings = GlobalNumericSetting.findAllByName("Meals a Dollar Buys")
		def goals = settings.sort{it.effectiveDate}.reverse()
		[globalNumericSettingInstanceList: goals, globalNumericSettingInstanceTotal: goals.size()]
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [globalNumericSettingInstanceList: GlobalNumericSetting.list(params), globalNumericSettingInstanceTotal: GlobalNumericSetting.count()]
    }

    def create() {
        [globalNumericSettingInstance: new GlobalNumericSetting(params)]
    }
	
	def createEmployeeGoal() {
				
		render(view: "createEmployeeGoal", model: [globalNumericSettingInstance: new GlobalNumericSetting(name : "Goal Amount Per Employee")])
	}
	
	def createMofbShiftValue() {
		render(view: "createMofbShiftValue", model: [globalNumericSettingInstance: new GlobalNumericSetting(params)])
	}
	
	def createMealADollarBuys () {
		render(view: "createMealsADollarBuys", model: [globalNumericSettingInstance: new GlobalNumericSetting(name : "Meals a Dollar Buys")])
	}
	
	
    def saveMofbShift() {
				
		def mofbShift = true
		def value = 0
		
		def globalNumericSettingInstance = new GlobalNumericSetting(params)
		
		if(params.value){
		
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
			
				flash.message = message(code: 'mofb.shift.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "createMofbShiftValue", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
				
		}
		       
		globalNumericSettingInstance.value = value
		globalNumericSettingInstance.mofbShift = mofbShift
		
        if (!globalNumericSettingInstance.save(flush: true)) {
			
            render(view: "createMofbShiftValue", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'mofb.shift.created.message', args: [globalNumericSettingInstance.id])
        redirect(action: "mofbShiftValue")
    }
	
	def saveEmployeeGoal() {
		
		def mofbShift = false
		def value = 0
		
		def globalNumericSettingInstance = new GlobalNumericSetting(params)
		
		if(params.value){
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
				flash.message = message(code: 'employee.goal.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "createEmployeeGoal", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
			
		}
			   	
		globalNumericSettingInstance.value = value
		globalNumericSettingInstance.mofbShift = mofbShift
		
		if (!globalNumericSettingInstance.save(flush: true)) {
			
			render(view: "createEmployeeGoal", model: [globalNumericSettingInstance: globalNumericSettingInstance])
			return
		}

		flash.message = message(code: 'employee.goal.created.message', args: [globalNumericSettingInstance.id])
		redirect(action: "goalPerEmployee")
		
	}
	
	
	def saveMealsDollarBuys(){
		
		def mofbShift = false
		def value = 0
		
		def globalNumericSettingInstance = new GlobalNumericSetting(params)
		
		if(params.value){
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
				flash.message = message(code: 'meals.dollar.buys.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "createMealsADollarBuys", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
			
		}
				   
		globalNumericSettingInstance.value = value
		globalNumericSettingInstance.mofbShift = mofbShift
		
		if (!globalNumericSettingInstance.save(flush: true)) {
			
			render(view: "createMealsADollarBuys", model: [globalNumericSettingInstance: globalNumericSettingInstance])
			return
		}

		flash.message = message(code: 'meals.dollar.buys.created.message', args: [globalNumericSettingInstance.id])
		redirect(action: "mealsADollarBuys")
		
	}
	
	def showEmployeeGoal(Long id){
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "goalPerEmployee")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
	}
	
	def showMealsDollarBuys(Long id){
		
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mealsADollarBuys")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
	}
	
	def showMofbShiftValue(Long id){
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mofbShiftValue")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
		
	}
		
	
	
	def editEmployeeGoal(Long id){
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "goalPerEmployee")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
		
	}

	def editMealsDollarBuys(Long id){
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mealsADollarBuys")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
		
	}
	
	def editMofbShiftValue(Long id){
		
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mofbShiftValue")
			return
		}

		[globalNumericSettingInstance: globalNumericSettingInstance]
		
	}
	
    def updateMofbShift(Long id, Long version) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "mofbShiftValue")
            return
        }

        if (version != null) {
            if (globalNumericSettingInstance.version > version) {
                globalNumericSettingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')] as Object[],
                          "Another user has updated this GlobalNumericSetting while you were editing")
                render(view: "editMofbShiftValue", model: [globalNumericSettingInstance: globalNumericSettingInstance])
                return
            }
        }
		
		globalNumericSettingInstance.properties = params
		
		def value = 0
		
		
		if(params.value){
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
				flash.message = message(code: 'mofb.shift.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "editMofbShiftValue", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
			
		}
		
		globalNumericSettingInstance.value = value
		

        if (!globalNumericSettingInstance.save(flush: true)) {
            render(view: "editMofbShiftValue", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), globalNumericSettingInstance.id])
        redirect(action: "showMofbShiftValue", id: globalNumericSettingInstance.id)
    }
	
	def updateEmployeeGoal(Long id, Long version) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "goalPerEmployee")
            return
        }

        if (version != null) {
            if (globalNumericSettingInstance.version > version) {
                globalNumericSettingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')] as Object[],
                          "Another user has updated this GlobalNumericSetting while you were editing")
                render(view: "editEmployeeGoal", model: [globalNumericSettingInstance: globalNumericSettingInstance])
                return
            }
        }
		
		globalNumericSettingInstance.properties = params
		
		def value = 0
		
		
		if(params.value){
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
				flash.message = message(code: 'employee.goal.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "editEmployeeGoal", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
			
		}
		
		globalNumericSettingInstance.value = value
		

        if (!globalNumericSettingInstance.save(flush: true)) {
            render(view: "editEmployeeGoal", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'employee.goal.updated.message', args: [globalNumericSettingInstance.id])
        redirect(action: "showEmployeeGoal", id: globalNumericSettingInstance.id)
    }
	
	def updateMealsDollarBuys(Long id, Long version) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "mealsADollarBuys")
            return
        }

        if (version != null) {
            if (globalNumericSettingInstance.version > version) {
                globalNumericSettingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')] as Object[],
                          "Another user has updated this GlobalNumericSetting while you were editing")
                render(view: "editMealsDollarBuys", model: [globalNumericSettingInstance: globalNumericSettingInstance])
                return
            }
        }
		
		globalNumericSettingInstance.properties = params
		
		def value = 0
		
		
		if(params.value){
			try{
				def inputValue = NumberFormat.getNumberInstance().parse(params.value)
				value = inputValue.toBigDecimal()
				
			}catch(Exception ex){
				flash.message = message(code: 'meals.dollar.buys.value.parse.exception')
				log.error("An error occurred converting input value to bigDecimal", ex)
				render(view: "editMealsDollarBuys", model: [globalNumericSettingInstance: globalNumericSettingInstance])
				return
			}
			
			
		}
		
		globalNumericSettingInstance.value = value
		

        if (!globalNumericSettingInstance.save(flush: true)) {
            render(view: "editMealsDollarBuys", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'meals.dollar.buys.updated.message', args: [globalNumericSettingInstance.id])
        redirect(action: "showMealsDollarBuys", id: globalNumericSettingInstance.id)
    }

    def delete(Long id) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
      //      redirect(action: "list")
            return
        }

        try {
            globalNumericSettingInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
          //  redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def deleteEmployeeGoal(Long id){
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "goalPerEmployee")
			return
		}

		try {
			globalNumericSettingInstance.delete(flush: true)
			flash.message = message(code: 'employee.goal.deleted.message', args: [id])
			redirect(action: "goalPerEmployee")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "showEmployeeGoal", id: id)
		}
		
	}
	
	def deleteMealsDollarBuys(Long id){
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message	', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mealsADollarBuys")
			return
		}

		try {
			globalNumericSettingInstance.delete(flush: true)
			flash.message = message(code: 'meals.dollar.buys.deleted.message', args: [id])
			redirect(action: "mealsADollarBuys")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "showMealsDollarBuys", id: id)
		}
		
	}
	
	def deleteMofbShiftValue(Long id){
		def globalNumericSettingInstance = GlobalNumericSetting.get(id)
		if (!globalNumericSettingInstance) {
			flash.message = message(code: 'default.not.found.message	', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "mofbShiftValue")
			return
		}

		try {
			globalNumericSettingInstance.delete(flush: true)
			flash.message = message(code: 'mofb.shift.value.deleted.message', args: [id])
			redirect(action: "mofbShiftValue")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
			redirect(action: "showMofbShiftValue", id: id)
		}
		
	}
	
}
