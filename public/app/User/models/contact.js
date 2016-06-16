'use strict';
angular.module('studApp').factory('Contact',function($resource,$q) {
    var Contact={},
        deffered,
        Contacts=[],

        contact = $resource('/api/normaluser/contact', null,{
            'post': {
                method: 'POST'
            }
        });

    Contact.save = function(json){
        deffered = $q.defer();
        contact.post(json, function(data){
            var m = data;
            deffered.resolve(m);
        });

        return deffered.promise;
    };

    return Contact;
});