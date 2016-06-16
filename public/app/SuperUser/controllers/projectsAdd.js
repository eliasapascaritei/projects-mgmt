angular.module('studApp').controller('projectsAdd', ['$scope','$stateParams','User','Project','Course', function($scope,$stateParams,User,Project,Course){
    User.getDetails().then(function(data){
        var idCourse = $stateParams.id;

       

// send project to server
     $scope.addProject = function(){
		var project = {
		 	"idSpecialization": 1,
			"description": $scope.newProject.description,
			"name":$scope.newProject.name	
  
 	};
        Project.save(project).then(function(data){

            debugger;

        });
	 };


// get project from server

  Project.getProjects(idCourse).then(function(data){
            //debugger;
            $scope.projects = data;
        });
       
  		 $scope.showStudents = function(){
        $location.path("/students");
    };

    $scope.showContact = function(){
        $location.path("/contact");
    };

    $scope.showHome = function(){
        $location.path("/");
    };

    });
}]);
