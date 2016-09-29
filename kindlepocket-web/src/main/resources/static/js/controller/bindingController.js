angular.module('binding',['request.service'])
.controller('formController',function($scope,requestService){
    $scope.bindingData = {};
    var params = $scope.bindingData;
    console.log('params: '+params);

    $scope.submitForm = function(){
        // action: bindingData
        requestService.req('bindingData',params);
    }
});