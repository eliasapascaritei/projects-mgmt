'use strict'
angular.module('studApp').controller('ProfileController', ['$scope','University','$filter','User', function($scope,University,$filter,User){

	User.getDetails().then(function(data){
		$scope.userDetails = data;
		console.log(data);
	})
	if(typeof x !== 'undefined'){
		$scope.userPic = x;
	}

	University.list().then(function(data){
		$scope.universities = data;
	})

	$scope.getColleges = function(idx) {
		if(!$scope.universities[idx].colleges){
			University.getColleges(idx).then(function(data){
				$scope.userDetails.selectedUniv = data[idx];
				// console.log(data);
			})
		} else {
			$scope.userDetails.selectedUniv = $scope.universities[idx];
		}
	}

	$scope.selectedUniversity = function(univ){
		$scope.$broadcast('angucomplete-alt:clearInput','auto-colleges');
		$scope.$broadcast('angucomplete-alt:clearInput','auto-spec');
		$scope.userDetails.selectedUniv = null;
		$scope.userDetails.selectedColl = null;
		$scope.userDetails.selectedSpec = null;

		if(univ){
			// console.log(univ);
			var idx = findWithAttr($scope.universities,'idUniversity',univ.originalObject.idUniversity);
			if(idx !== undefined){
				$scope.getColleges(idx);
			}
		}
	}
	$scope.selectedCollege = function(coll){
		$scope.userDetails.selectedColl = null;
		$scope.userDetails.selectedSpec = null;
		$scope.$broadcast('angucomplete-alt:clearInput','auto-spec');

		if(coll){
			var idx = findWithAttr($scope.userDetails.selectedUniv.colleges,'idCollege',coll.originalObject.idCollege);
			if(idx !== undefined){
				$scope.userDetails.selectedColl = $scope.userDetails.selectedUniv.colleges[idx];
			}
		}
	}
	$scope.selectedSpecialization = function(spec){
		$scope.userDetails.selectedSpec = spec.originalObject;
	}
	$scope.saveUniversity = function(){
		$scope.savedUniversity = $scope.userDetails.selectedSpec;
		$scope.userDetails.savedUniversity = $scope.savedUniversity;
		User.updateDetails($scope.userDetails);
	}
	function findWithAttr(array, attr, value) {
	    for(var i = 0; i < array.length; i += 1) {
	        if(array[i][attr] === value) {
	            return i;
	        }
	    }
	}
}])