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
                templateUrl: "assets/app/User/views/home.html",
                controller: "HomeController"
            })
            .state('profile', {
                url: "/profile",
                templateUrl: "assets/app/User/views/profile/profile.html",
                controller: 'ProfileController'
            })
            .state('upload', {
                url: "/upload/:id",
                templateUrl: "assets/app/User/views/uploadProject.html",
                controller: 'UploadProjectController'
            })
            .state('profile.not-complete', {
                url: "/complete-profile",
                templateUrl: "assets/app/User/views/profile/complete-profile.html"
            })
           .state('allProjects',   {
                url:"/allProjects/:id",
                controller : "addProjects",
                templateUrl : "assets/app/User/views/addProjects.html"
            })
           .state('projects/', {
                url: "/projects/:id",
                templateUrl: "assets/app/User/views/projectDetails.html",
                controller: 'ProjectDetails'
            })
            .state('userProjects/', {
                url: "/userProjects",
                templateUrl: "assets/app/User/views/userProjects.html",
                controller: 'UserProjectsController'
            })
            .state('contact/', {
                url: "/contact",
                templateUrl: "assets/app/User/views/contact.html",
                controller: 'ContactController'
            })
    });
