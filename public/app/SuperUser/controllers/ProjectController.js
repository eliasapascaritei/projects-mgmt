angular.module('studApp').controller('ProjectController', ['$scope','$stateParams','User','Project','Course', function($scope,$stateParams,User,Project,Course){
    User.getDetails().then(function(data){
        var projectId = $stateParams.id;

        Project.getProjects(projectId).then(function(data){
            debugger;
            $scope.project = data;
        });

        $scope.download = function(data){
            window.open(data);
        };
    });
}]);
