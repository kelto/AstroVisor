class BootStrap {

    def init = { servletContext ->
        environments {
            production {
                "cd yo && gulp".execute();
            }

            development {
                "cd yo && gulp serve".execute();
            }
        }
    }
    def destroy = {
    }
}
