var studApp= angular.module('studApp').controller('ContactController', ['$scope','$location','User','Specialization','Course','Contact', function($scope,$location,User,Specialization,Course,Contact){

    $scope.sendContact = function(){
        var contact = {
            "name": $scope.reporterName,
            "email": $scope.reporterEmail,
            "description": $scope.reporterDescription
        };

        Contact.save(contact).then(function(data){
            debugger;
            $scope.reporterName = "";
            $scope.reporterEmail = "";
            $scope.reporterDescription = "";
        });
    };

    $scope.showStudents = function(){
        debugger;
        $location.path("/students");
    };

    $scope.showContact = function(){
        debugger;
        $location.path("/contact");
    };
}]);
