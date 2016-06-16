angular.module('studApp').controller('AddProjectController', ['$scope','$stateParams','User','Project','Course', function($scope,$stateParams,User,Project,Course){
User.getDetails().then(function(data){
    var idCourse = parseInt($stateParams.id);

    Project.getProjectsCourse(idCourse).then(function(data) {
        $scope.projects = data;
    });

    //send project to server
    $scope.addProject = function(){
        var project = {
            "idCourse": idCourse,
            "description": $scope.newProject.description,
            "name": $scope.newProject.name,
            "idUser": 0,
            "isTaken": false,
            "isAccepted": false
        };

        Project.save(project).then(function(data){
            $scope.projects.push(data);
        });

        $scope.newProject.description = "";
        $scope.newProject.name = "";
    };

    $scope.deleteProject = function (project) {
        Project.delete(project.idProject).then(function (data) {
            for(var i = 0; i < $scope.projects.length; i++) {
                if($scope.projects[i].idProject === project.idProject){
                    $scope.projects.splice(i, 1);
                    break;
                }
            }
        })
    };

    $scope.takeProject = function (idProject) {
        Project.take(idProject).then(function (data) {
            console.log("result: " + data)
        })
    };

    
    $scope.showStudents = function(){
        debugger;
        $location.path("/students");
    };

    $scope.showContact = function(){
        debugger;
        $location.path("/contact");
    };
});
}]);
