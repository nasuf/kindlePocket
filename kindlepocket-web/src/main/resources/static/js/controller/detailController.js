angular.module('details',['detail.service'])
.controller('detailController', function($scope, detailService){
    $scope.details = {};
    $scope.getDetails = function(){
        detailService.getDetails().then(
            function (response){
                $scope.details = response.data;
            }
        );
    }
});