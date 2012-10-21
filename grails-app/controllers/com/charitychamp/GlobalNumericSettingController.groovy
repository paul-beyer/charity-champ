package com.charitychamp

import org.springframework.dao.DataIntegrityViolationException

class GlobalNumericSettingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def goalPerEmployee (){
		
		def settings = GlobalNumericSetting.findAllByName("Goal Amount Per Employee")
		settings.sort{it.effectiveDate}
		[globalNumericSettingInstanceList: settings, globalNumericSettingInstanceTotal: settings.size()]		
		
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
	
	
    def save() {
		
		def value = 0
		
		if(params.value){
			value = params.value.toBigDecimal()
		}
	
			
        def globalNumericSettingInstance = new GlobalNumericSetting(params)
		globalNumericSettingInstance.value = value
		
        if (!globalNumericSettingInstance.save(flush: true)) {
            render(view: "create", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), globalNumericSettingInstance.id])
        redirect(action: "show", id: globalNumericSettingInstance.id)
    }

    def show(Long id) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "list")
            return
        }

        [globalNumericSettingInstance: globalNumericSettingInstance]
    }

    def edit(Long id) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "list")
            return
        }

        [globalNumericSettingInstance: globalNumericSettingInstance]
    }

    def update(Long id, Long version) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (globalNumericSettingInstance.version > version) {
                globalNumericSettingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')] as Object[],
                          "Another user has updated this GlobalNumericSetting while you were editing")
                render(view: "edit", model: [globalNumericSettingInstance: globalNumericSettingInstance])
                return
            }
        }
		
		globalNumericSettingInstance.properties = params
		
		def value = 0
		
		if(params.value){
			value = params.value.toBigDecimal()
		}
		
		globalNumericSettingInstance.value = value
		

        if (!globalNumericSettingInstance.save(flush: true)) {
            render(view: "edit", model: [globalNumericSettingInstance: globalNumericSettingInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), globalNumericSettingInstance.id])
        redirect(action: "show", id: globalNumericSettingInstance.id)
    }

    def delete(Long id) {
        def globalNumericSettingInstance = GlobalNumericSetting.get(id)
        if (!globalNumericSettingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "list")
            return
        }

        try {
            globalNumericSettingInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting'), id])
            redirect(action: "show", id: id)
        }
    }
}
