'use strict';
angular.module('studApp').factory('Student',function($resource,$q) {
    var Student={},
        deffered,
        Students=[],

        studentList = $resource('/api/superuser/user', null,{
            'get': {
                method: 'GET',
                isArray: true
            },
            'post': {
                method: 'POST'
            }
        }),
        student = $resource('/api/superuser/user/:id', {id: "@id" },{
            'delete': {
                method: 'DELETE'
            },
            'put': {
                method: 'PUT'
            }
        });

    Student.getStudents = function(idx) {
        deffered = $q.defer();
        studentList.get({id: idx}, function(data){
            Students = data;
            deffered.resolve(Students);
        });

        return deffered.promise;
    };

    Student.save = function(json){
        deffered = $q.defer();
        studentList.post(json, function(data){
            var m = data;
            deffered.resolve(m);
        });

        return deffered.promise;
    };

    return Student;
});