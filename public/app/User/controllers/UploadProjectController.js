angular.module('studApp').controller('UploadProjectController', ['$scope','User', 'fileUpload', function($scope, User, fileUpload){

    console.log("merge");
    var self = this;

    $scope.uploaded = false;
    $scope.uploadFile = function(){
        var file = inviteFile.files[0];
        debugger;
        var uploadUrl = "/api/normaluser/upload";
        fileUpload.uploadFileToUrl(file, uploadUrl, self, function(data) {
            $scope.uploaded = true;
            debugger;
        });
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
}]);