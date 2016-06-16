'use strict';
angular.module('studApp').factory('Course',function($resource,$q) {
    var Course={},
        deffered,
        Courses=[],
        course = $resource('/api/normaluser/course/special', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        courseUser = $resource('/api/normaluser/course/special/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        });

    Course.getCourses = function() {
        deffered = $q.defer();
        course.get(function (data) {
            Courses = data;
            deffered.resolve(Courses);
        });

        return deffered.promise;
    };

    Course.getCoursesUser = function(idx){
        deffered = $q.defer();
        courseUser.get({id: idx}, function(data){
            Courses = data;
            deffered.resolve(Courses);
        });

        return deffered.promise;
    };

    return Course;
});