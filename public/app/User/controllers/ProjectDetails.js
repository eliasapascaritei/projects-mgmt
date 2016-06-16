angular.module('studApp').controller('ProjectDetails', ['$scope','$location','$stateParams','User','Project','Course', function($scope,$location,$stateParams,User,Project,Course){
    User.getDetails().then(function(data){
        var projectId = $stateParams.id;

        Project.getProjects(projectId).then(function(data){
            debugger;
            $scope.project = data;
        });


        $scope.showHome = function(data){
			$location.path("/" + data);
		};

		$scope.showList = function(data){
			$location.path("/userProjects/" + data);
		};

		$scope.showContact = function(data){
			$location.path("/contact/" + data);
		};

		$scope.upload = function(data){
			$location.path("/upload/" + data);
		};


    });
}]);