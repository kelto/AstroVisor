class UrlMappings {

	static mappings = {
        /*
        "/api/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
*/
        //*
        //all RestFull Controller
        //group "/api", {
            "/api/planets"(controller = "planet")
        //}
//*/
        "/"(view:"/index")
        "500"(view:'/error')

	}
}
