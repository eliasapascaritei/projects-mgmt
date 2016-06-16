angular.module('studApp').controller('UserController', ['$scope','$stateParams','User','Project', function($scope,$stateParams,User,Project){
    User.getDetails().then(function(data){
        var userId = $stateParams.id;

        User.getUser(userId).then(function(data){
            $scope.user = data;
            $scope.firstName = data.firstName;
            $scope.lastName = data.lastName;
        });

        $scope.updateUser = function(){
            var obj = {
                "idUser": $scope.user.idUser,
                "firstName": $scope.firstName,
                "lastName": $scope.lastName
            };

            debugger;

            User.update(obj).then(function (data) {
                debugger;
            })
        };
    });
}]);
