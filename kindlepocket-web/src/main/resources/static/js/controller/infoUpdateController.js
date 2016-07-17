angular.module('infoUpdate',[])
.controller('infoUpdateController',function($scope,$http){

    $scope.subscriberData = {},

     $http({
        method: 'GET',
        url: 'http://ed8fc947.ngrok.io/KindlePocket/getSubscriberOpenId'
    }).success(function(data,status){
        $scope.subscriberData.openId = data;
        alert("find open id successfully! id= " + data);

         $http({
                method: 'GET',
                url: 'http://ed8fc947.ngrok.io/KindlePocket/getSubscriberInfo?subscriberOpenId='+$scope.subscriberData.openId
            }).success(function(data,status){
                $scope.subscriberData.userName = data.userName;
                $scope.subscriberData.phone = data.phone;
                $scope.subscriberData.email = data.email;
                $scope.subscriberData.kindleEmail = data.kindleEmail;
                alert("findInfo successfully! id= " + data.id);
            }).error(function(data,status){
                alert("findInfo error! data" + data + " status:" + status);
            })

    }).error(function(data,status){
        alert("find open id error! id: " + data + " status:" + status);
    })



    $scope.updateInfo = function(){
          $http({
              method: 'POST',
              url: 'http://ed8fc947.ngrok.io/KindlePocket/updateSubscriberInfo',
              data: $.param($scope.subscriberData),
              headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
          }).success(function(data,status){
              alert("update successfully!")
          }).error(function(data,status){
              alert("update error!")
          })
    }

});