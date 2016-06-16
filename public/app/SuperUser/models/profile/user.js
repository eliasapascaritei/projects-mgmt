'use strict'
angular.module('studApp').factory('User',function($resource,$q) {
	var User={},
		deffered,
		Users=[],

		res = $resource('/user',null,{
			'getDetails': {
				method:'GET',
				isArray: true,
			}
		}),
		user = $resource('/api/superuser/user/:id', {id: "@id" },{
			'get': {
				method: 'GET'
			},
			'put': {
				method: 'PUT'
			}
		});
	/**
	 * Used to retrieve User details. If the details were previously 
	 * fetched it will return the local variable. If a previous call
	 * was made and not resolved it returns the promise.
	 * @return {promise}
	 */	
	User.getDetails = function() {
		if(!deffered && !User.details){
			
			console.debug('Returning user from SERVER')
			deffered = $q.defer();

			deffered.resolve({});
/*			res.list(function(data) {
				Universities = data;
				deffered.resolve(Universities);
			});*/
		} else if(User.details) {
			
			console.debug('Returning user from MEMORY')
			deffered.resolve(User.details);
		}
		return deffered.promise;
	};

	User.updateDetails = function(details){
		User.details = details;
	};

	User.getUser = function(idx) {
		deffered = $q.defer();
		user.get({id: idx}, function(data){
			Users = data;
			deffered.resolve(Users);
		});

		return deffered.promise;
	};

	User.update = function(json){
		deffered = $q.defer();
		user.put(json, function(data){
			var m = data;
			deffered.resolve(m);
		});

		return deffered.promise;
	};

	return User;
});