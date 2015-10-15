class UrlMappings {

	static mappings = {

        //all RestFull Controller

        group "/api", {
            "/planets/$id?"(controller: "planet") {
                action = [GET: "index", PUT: "update", POST: "save", DELETE: "delete"]

            }

            "/descriptions/$id?"(controller: "description") {
                action = [GET: "index", PUT: "update", POST: "save", DELETE: "delete"]
            }

            "/trades/$id?"(controller: "trade") {
                action = [GET: "index", PUT: "update", POST: "save", DELETE: "delete"]
            }
            "/users/$id?"(controller: "user") {
                action = [POST: "save", PUT: "update"]
            }
        }


        "/"(view:"/index")
        "500"(view:'/error')

	}
}
