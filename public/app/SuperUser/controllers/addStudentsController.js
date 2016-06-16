angular.module('studApp').controller('addStudentsController', ['$scope', '$stateParams','$location','User','Project','Course','Specialization','Student', function($scope,$stateParams,$location,User,Project,Course,Specialization, Student){
    User.getDetails().then(function(data){
        //var projectId = $stateParams.id;

        Specialization.getSpecializations().then(function(data){
	 		$scope.specializations = data;
        });

		Student.getStudents().then(function (data) {
			$scope.students = data;
		});

		$scope.addStudent = function(){
		
			var student = {
				"firstName": $scope.firstName,
				"lastName": $scope.lastName,
				"email": $scope.email,
				"idSpecialization": parseInt($scope.special),
				"year" : $scope.year
			};

			Student.save(student).then(function(data){
				debugger;
        	});
	 	};

		$scope.showStudent = function(data){
			$location.path("/user/" + data);
		};


    });
}]);