angular.module('binding',['binding.services'])
.controller('formController',function($scope,bindingDataService){
    $scope.bindingData = {};
    var params = $scope.bindingData;
    console.log('params: '+params);

    $scope.submitForm = function(){
        bindingDataService.binding('bindingData',params);
    }
});