angular.module('details',['detail.service','request.service'])
.controller('detailController', function($scope, detailService, requestService){
    $scope.detail = {};
    $scope.detailsAll = {};
    $scope.initIndex = 0;
    $scope.initPageSize = 8;
    $scope.bookId = {};

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
      //  alert("id=" + id);
        Materialize.toast('Send Successfully!', 4000);
        $scope.bookId = id;
        var param = $scope.bookId;
        detailService.sendMail(param);
    };

    $scope.popUpCommentModal = function(id){
      	$('#'+id+'').openModal();
    };

    $scope.sendProblems = function (){
        Materialize.toast('Got it !', 4000);
    };

    $scope.closeFAB = function() {
        $('#bottomButton').closeFAB();
    };

});