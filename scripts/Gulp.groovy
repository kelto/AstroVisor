includeTargets << grailsScript("_GrailsInit")

target(gulp: "The description of the script goes here!") {
    def sout = new StringBuffer(), serr = new StringBuffer()
    def proc = 'cd yo && gulp'.execute()
    proc.waitForProcessOutput(sout, serr)
    proc.waitFor()
    println "out> $sout err> $serr"
    println "Start compile app"
}

setDefaultTarget(gulp)
