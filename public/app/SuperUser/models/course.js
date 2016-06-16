'use strict';
angular.module('studApp').factory('Course',function($resource,$q) {
    var Course = {},
        deffered,
        Courses = [],
        course = $resource('/api/superuser/course', null, {
            'get': {
                method: 'GET',
                isArray: true
            },
            'post': {
                method: 'POST'
            }
        }),
        courseProject = $resource('/api/superuser/course/project', null,{
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

    Course.getCourseProjects = function() {
        deffered = $q.defer();
        courseProject.get(function (data) {
            Courses = data;
            deffered.resolve(Courses);
        });

        return deffered.promise;
    };

    Course.save = function(json){
        deffered = $q.defer();
        course.post(json, function(data){
            var sendJson = data;
            deffered.resolve(sendJson);
            debugger;
        });

        return deffered.promise;
    };


    return Course;
});