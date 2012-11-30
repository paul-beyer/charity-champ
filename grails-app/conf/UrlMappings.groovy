class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

//		"/"(view:"/home")
		"/"(controller:"home"){
			action = [GET:'home']
		}
		"500"(view:'/error')
		
		"/organizationalTree"(controller:"organizationalTree"){
			action = [GET:'tree']
		}
	}
}
