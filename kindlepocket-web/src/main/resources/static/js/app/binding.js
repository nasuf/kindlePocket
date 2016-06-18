angular.module('binding',[])
.controller('formController',function($scope,$http){
    $scope.bindingData = {},
    $scope.submitForm = function(){
        $http({
            method: 'POST',
            url: 'http://00a1f5c8.ngrok.io/Weixin/bindingData',
            // pass in data as strings
            data: $.param($scope.bindingData),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
        }).success(function(data,status){
            console.log("success! status:"+status);
            alert("binding successfully!")
        }).error(function(data,status){
            alert("binding error!")
            console.log("error! status:"+status);
        })
    }
});