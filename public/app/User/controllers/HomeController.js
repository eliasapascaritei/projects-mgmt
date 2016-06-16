angular.module('studApp').controller('HomeController', ['$scope','$location','User','Specialization','Course','Project', function($scope,$location,User,Specialization,Course,Project){
	User.getDetails().then(function(data){
		$scope.userDetails = data;

		Course.getCourses().then(function(data){
			$scope.courses = data;
		});

		$scope.showProject = function(data){
			$location.path("/projects/" + data);
		};

		$scope.showAllProjects = function(data){
			debugger;
			$location.path("/allProjects/" + data);	
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