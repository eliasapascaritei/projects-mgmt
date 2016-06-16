'use strict';
angular.module('studApp').factory('Project',function($resource,$q) {
    var Project={},
        deffered,
        Projects=[],
        projectYear = $resource('/api/normaluser/project/year/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        projectCourse = $resource('/api/normaluser/project/course/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        projectList = $resource('/api/normaluser/project/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        project = $resource('/api/normaluser/project/', null,{
            'post': {
                method: 'POST'
            },
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        assignProject = $resource('/api/normaluser/project/assign/:id ', 
        {id: "@id" },{
            'post': {
                method: 'POST'
            }
        });



    Project.getProjectsYear = function(idx) {
        deffered = $q.defer();
        projectYear.get({id: idx}, function(data){
            Projects = data;
            deffered.resolve(Projects);
        });

        return deffered.promise;
    };

    Project.getProjectsCourse = function(idx){
        deffered = $q.defer();
        projectCourse.get({id: idx}, function(data){
            Projects = data;
            deffered.resolve(Projects);
        });

        return deffered.promise;
    };

    Project.save = function(json){
        deffered = $q.defer();
        project.post(json, function(data){
            var m = data;
            deffered.resolve(m);
            debugger;
        });

        return deffered.promise;
    };

    Project.getProjects = function(idx) {
        deffered = $q.defer();
        projectList.get({id: idx}, function(data){
            Projects = data;
            deffered.resolve(Projects);
        });

        return deffered.promise;
    };

     Project.take = function (idx) {
        deffered = $q.defer();
        takeProject.put({id: idx}, function(data){
            deffered.resolve();
        });

        return deffered.promise;
    };

    Project.assignProject = function(idx){
        deffered = $q.defer();
        assignProject.post({id: idx}, function(data){
        deffered.resolve();
        });

        return deffered.promise;
    };

    Project.getUserProjects = function() {
        deffered = $q.defer();
        project.get(function(data){
            Projects = data;
            deffered.resolve(Projects);
        });

        return deffered.promise;
    };


    return Project;
});
