class UrlMappings {

	static mappings = {

        //all RestFull Controller
        group "/api", {
            "/planets"(controller: "planet")
        }

        "/"(view:"/index")
        "500"(view:'/error')

	}
}
