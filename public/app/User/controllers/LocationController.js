var studApp= angular.module('studApp').controller('LocationController', ['$scope','$location', function($scope,$location){

    $scope.showProjects = function(){
        $location.path("/userProjects");
    };

    $scope.showContact = function(){
        $location.path("/contact");
    };

    $scope.showHome = function(){
        $location.path("/");
    };

}]);
