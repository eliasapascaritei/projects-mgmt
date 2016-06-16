var studApp= angular.module('studApp').controller('LocationController', ['$scope','$location', function($scope,$location){

    $scope.showStudents = function(){
        $location.path("/students");
    };

    $scope.showContact = function(){
        $location.path("/contact");
    };

    $scope.showHome = function(){
        $location.path("/");
    };

}]);
