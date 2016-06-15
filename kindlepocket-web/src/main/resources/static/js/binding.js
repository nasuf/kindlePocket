angular.module('binding',[])
.controller('formController',function($scope,$http){
    $scope.bindingData = {},
    $scope.submitForm = function(){
        $http({
            method: 'POST',
            url: 'http://localhost:8080/Weixin/bindingData',
            // pass in data as strings
            data: $.param($scope.bindingData),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
        }).success(function(data,status){
            console.log("success! status:"+status);
        }).error(function(data,status){
            console.log("error! status:"+status);
        })
    }
});