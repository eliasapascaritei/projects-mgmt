'use strict';
angular.module('studApp').factory('Project',function($resource,$q) {
    var Project={},
        deffered,
        Projects=[],
        projectYear = $resource('/api/superuser/project/year/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        projectCourse = $resource('/api/superuser/project/course/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        }),
        projectList = $resource('/api/superuser/project/:id', null,{
            'get': {
                method: 'GET',
                isArray: true
            },
            'delete': {
                method: 'DELETE'
            }
        }),
        takeProject = $resource('/api/superuser/project/take/:id',
            {id: "@id" },{
            'get':{
                method: 'GET'
            },
            'put': {
                method: 'PUT'
            }
        }),
        project = $resource('/api/superuser/project', null,{
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

    Project.delete = function (idx) {
        deffered = $q.defer();
        projectList.delete({id: idx}, function(data){
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

    return Project;
});