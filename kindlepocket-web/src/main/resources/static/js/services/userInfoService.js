/**
 * Created by nasuf on 16/10/25.
 */

angular.module('userInfo.service',[])
    .factory('userInfoService', function($q, $http){

        return {
            getDeliveryRecords : function(){
                var deferred = $q.defer();
                //var path = 'http://localhost:9090/KindlePocket/getDeliveryRecords';
                var path = 'http://kindlepocket.nasuf.cn/KindlePocket/getDeliveryRecords';
                var promise = $http.get(path).then(function(response){
                    return response;
                }, function(response){
                    return response;
                });
                return promise;
            }
        }

    });