angular.module('binding',['request.services'])
.controller('formController',function($scope,requestService){
    $scope.bindingData = {};
    var params = $scope.bindingData;
    console.log('params: '+params);

    $scope.submitForm = function(){
        // action: bindingData
        requestService.binding('bindingData',params);
    }
});