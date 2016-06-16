angular.module('studApp').controller('UserProjectsController', ['$scope','$location','User','Project', function($scope,$location,User,Project){

	Project.getUserProjects().then(function (data) {
		$scope.projects = data;
	});

	$scope.showUpload = function (data) {
		if(data.isAccepted == true && data.s3Name == null)
			$location.path("/upload/" + data);
		else
			console.log("No Upload");
	}

}]);