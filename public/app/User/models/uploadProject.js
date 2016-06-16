'use strict';
angular.module('studApp').factory('fileUpload', function ($http,$stateParams) {
    var Project={};
    var projectId = $stateParams.id;

    Project.uploadFileToUrl = function(file, uploadUrl, self, callback){
        var status = false;
        var fileArray = file.name.split('.');
        var extension = fileArray[fileArray.length -1];

        $http({
            method: 'POST',
            url: uploadUrl+ "/" + projectId + "/" + extension,
            data: new Blob([file], {type: "octet/stream"}),
            headers: {
                'Content-Type': 'application/octet-stream'
            }
        })
            .success(function(data){
                callback(data)
            })
            .error(function(data){
                callback(data)
            });
    };

    return Project;
});