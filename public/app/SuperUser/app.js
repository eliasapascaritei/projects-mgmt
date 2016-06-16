/**
 * studApp Module
 *
 * A comprehensive student working platform
 */
angular.module('studApp', [
    'ngResource',
    'ngAnimate',
    'ui.router'
])
    .config(function($stateProvider, $urlRouterProvider) {
        //
        // For any unmatched url, redirect to /state1
        $urlRouterProvider.otherwise("/");
        // Now set up the states
        $stateProvider
            .state('home', {
                url: "/",
                templateUrl: "assets/app/SuperUser/views/home.html",
                controller: "HomeController"
            })
            .state('profile', {
                url: "/profile",
                templateUrl: "assets/app/SuperUser/views/profile/profile.html",
                controller: 'ProfileController'
            })
            .state('project/', {
                url: "/project/:id",
                templateUrl: "assets/app/SuperUser/views/project.html",
                controller: 'ProjectController'
            })
            .state('profile.not-complete', {
                url: "/complete-profile",
                templateUrl: "assets/app/SuperUser/views/profile/complete-profile.html",
            })
            .state('projectsAdd/',   {
                url:"/projectsAdd/:id",
                controller : "AddProjectController",
                templateUrl : "assets/app/SuperUser/views/projectsAdd.html"
            })
            .state('addstudents/',   {
                url:"/students",
                controller : "addStudentsController",
                templateUrl : "assets/app/SuperUser/views/addStudents.html"
            })
            .state('contact/',   {
                url:"/contact",
                controller : "ContactController",
                templateUrl : "assets/app/SuperUser/views/contact.html"
            })
            .state('/user/', {
                url: "/user/:id",
                controller : "UserController",
                templateUrl : "assets/app/SuperUser/views/editUser.html"
            })
    });
