'use strict';
angular.module('studApp').factory('Specialization',function($resource,$q) {
    var Specialization={},
        deffered,
        Specializations=[],
        specialization = $resource('/api/normaluser/specialization', null,{
            'get': {
                method: 'GET',
                isArray: true
            }
        });

    Specialization.getSpecializations = function() {
        deffered = $q.defer();
        specialization.get(function (data) {
            Specializations = data;
            deffered.resolve(Specializations);
            debugger;
        });

        return deffered.promise;
    };


   return Specialization;
});