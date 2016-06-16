angular.module('studApp').controller('addProjects', ['$scope','$stateParams','User','Project','Course', function($scope,$stateParams,User,Project,Course){
User.getDetails().then(function(data){
    $scope.user = data;

    var idCourse = parseInt($stateParams.id);

    Project.getProjectsCourse(idCourse).then(function(data) {
        $scope.projects = data;
    });

  // send project to server
    $scope.addProject = function(){
        var project = {
            "idCourse": idCourse,
            "description": $scope.newProject.description,
            "name": $scope.newProject.name,
            "idUser": 0,
            "isTaken": true,
            "isAccepted": false
        };

        Project.save(project).then(function(data){
            $scope.projects.push(data);
        });

        $scope.newProject.description = "";
        $scope.newProject.name = "";
    };

  
    $scope.assignProjects = function (idProject) {
        debugger;
        Project.assignProject(idProject).then(function (data) {
            console.log("result: " + data)
        })
    };


    $scope.showHome = function(data){
        $location.path("/" + data);
    };

    $scope.showList = function(data){
        $location.path("/userProjects/" + data);
    };

    $scope.showContact = function(data){
        $location.path("/contact/" + data);
    };
});
}]);
