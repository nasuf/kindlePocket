/**
 *
 * Created by nasuf on 16/10/24.
 */

var info = angular.module('info',['userInfo.service'])

    info.controller('infoController',function($scope,userInfoService){
        $scope.records = {};
        $scope.getDeliveryRecords = function(){
            userInfoService.getDeliveryRecords().then(
                function (response){
                    $scope.records = response.data;
                }
            );
        };
    });

    info.filter('renderStatus', function () {
        return function(originalStatus){
            if(originalStatus == '1'){
                return 'success';
            } else {
                return 'failed';
            }
        }
    });