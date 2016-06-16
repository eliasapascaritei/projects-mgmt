'use strict'
angular.module('studApp').factory('University',function($resource,$q) {
	var University={},
		deffered,
		Universities=[],
		res = $resource('/university',null,{
			'list': {
				method:'GET',
				isArray: true,
			},
		}),
		specialization = $resource('signup/college/:id', null,{
			'get': {
				method: 'GET',
				isArray: true
			},
		});

	/**
	 * Used to retrieve Univerities list. If the list was previously 
	 * fetched it will return the local variable. If a previous call
	 * was made and not resolved it returns the promise.
	 * @return {promise}
	 */	
	University.list = function() {
		if(!deffered && Universities.length <= 0){
			
			console.debug('Returning Universities from SERVER')
			deffered = $q.defer();
			res.list(function(data) {
				Universities = data;
				deffered.resolve(Universities);
			});
		} else if(Universities.length > 0) {
			
			console.debug('Returning Universities from MEMORY')
			deffered.resolve(Universities);
		}
		return deffered.promise;
	}
	University.getColleges =function(idx) {
		deffered = $q.defer();
		specialization.get({id: Universities[idx].idUniversity},function (data) {
			Universities[idx].colleges = data;
			deffered.resolve(Universities);
		});

		return deffered.promise;
	}
	return University;
});