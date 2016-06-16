var studApp= angular.module('studApp').controller('HomeController', ['$scope','$location','User','Specialization','Course', function($scope,$location,User,Specialization,Course){
User.getDetails().then(function(data){
	$scope.userDetails = data;
	console.log($scope.userDetails);

	$scope.addCourse = function(){
		var course = {
			"idSpecialization": 1,
			"name": $scope.courseName,
			"year": parseInt($scope.newCourse.year)
		};

		Course.save(course).then(function(course){
			$scope.courses.push(course)
		});
	};

	Course.getCourseProjects().then(function(data){
		$scope.courses = data;
	});

	Specialization.getSpecializations().then(function(data){
	 	$scope.specializations = data;
	 });

	 
	$scope.showProject = function(data){
		$location.path("/project/" + data);
	};

	$scope.addProject = function(data){
		$location.path("/projectsAdd/" + data);
	};

	$scope.showUser = function(data){
		debugger;
		$location.path("/user/" + data);
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
