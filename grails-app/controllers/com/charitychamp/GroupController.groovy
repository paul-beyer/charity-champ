package com.charitychamp

import org.joda.time.LocalDate
import org.springframework.dao.DataIntegrityViolationException

class GroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [groupInstanceList: Group.list(params), groupInstanceTotal: Group.count()]
    }

    def create() {
        [groupInstance: new Group(params)]
    }

    def save() {
        def groupInstance = new Group(params)
        if (!groupInstance.save(flush: true)) {
            render(view: "create", model: [groupInstance: groupInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'group.label', default: 'Group'), groupInstance.id])
        redirect(action: "show", id: groupInstance.id)
    }

    def show(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        [groupInstance: groupInstance, secondGroupInstance : groupInstance]
    }

	def overview(Long id) {
		log.debug("Entering overview")
		def groupInstance = Group.get(id)
		if (!groupInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
			redirect(action: "list")
			return
		}
		
		def campaignString = "No campaign found for today's date (Setup Campaign in Configuration)" 
		def currentCampaign = CharityChampUtils.currentCampaign(new LocalDate())
		if(currentCampaign){
			campaignString = currentCampaign.toString()
		}
	
		[groupInstance: groupInstance, departmentName : groupInstance.department?.name, departmentId : groupInstance.department?.id
		, officeId : groupInstance.department?.office?.id, officeName : groupInstance.department?.office?.name 
		, businessId : groupInstance.department?.office?.business?.id, businessName : groupInstance.department?.office?.business?.name
		, companyId :  groupInstance.department?.office?.business?.company?.id,  companyName :  groupInstance.department?.office?.business?.company?.name
		, currentCampaign : campaignString]
	}

	
    def edit(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        [groupInstance: groupInstance]
    }

    def update(Long id, Long version) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (groupInstance.version > version) {
                groupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'group.label', default: 'Group')] as Object[],
                          "Another user has updated this Group while you were editing")
                render(view: "edit", model: [groupInstance: groupInstance])
                return
            }
        }

        groupInstance.properties = params

        if (!groupInstance.save(flush: true)) {
            render(view: "edit", model: [groupInstance: groupInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'group.label', default: 'Group'), groupInstance.id])
        redirect(action: "show", id: groupInstance.id)
    }

    def delete(Long id) {
        def groupInstance = Group.get(id)
        if (!groupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
            return
        }

        try {
            groupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'group.label', default: 'Group'), id])
            redirect(action: "show", id: id)
        }
    }
}
