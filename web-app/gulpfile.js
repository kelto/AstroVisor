'use strict';

var gulp = require('gulp');
var livereload = require('gulp-livereload');

gulp.task('default', function() {
    livereload.listen(5000);
    gulp.watch('./js/*.js', function(event){
        console.log(event);
        livereload.changed(event.path);
    });
});