angular.module('details',['detail.service'])
.controller('detailController', function($scope, detailService){
    $scope.detail = {};
    $scope.detailsAll = {};
    $scope.initIndex = 0;
    $scope.initPageSize = 8;

    $scope.getDetails = function(){
        detailService.getDetails().then(
            function (response){
                $scope.detailsAll = response.data;
                $scope.details = $scope.detailsAll.slice($scope.initIndex,$scope.initPageSize);
            }
        );
    };

    $scope.pagination = function(pageIndex, pageSize){
        var totalRecordsCount = $scope.detailsAll.length;
        var lastRecordIndex;
        if(pageSize * pageIndex > totalRecordsCount){
            lastRecordIndex = totalRecordsCount;
        } else {
            lastRecordIndex = pageSize * pageIndex;
        }
        $scope.details = $scope.detailsAll.slice(
            pageSize * (pageIndex - 1),
            lastRecordIndex
        );
    };

    $scope.sendMail = function(id) {
        //alert("id=" + id);
        detailService.sendMail(id);
    }

});