angular.module('detail.service',[])
.factory('detailService', function($q, $http){

    return {
        getDetails : function(){
            var deferred = $q.defer();
            var path = 'http://14831r54q5.iask.in/KindlePocket/getDetails';
            var promise = $http.get(path).then(function(response){
                return response;
            }, function(response){
                return response;
            });
            return promise;
        }
    }
    /* var details = {};
     console.log('entered the service')
     var getDetails = function(action){
         return $http({
                 method: 'POST',
                 url: 'http://localhost:9090/KindlePocket/'+action+'',
                 headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
             }).success(function(data,status){
                 console.log("success! status:" + status + " id:" + data.id);
                 alert("get details successfully! id:" + data.id);
                 details = data;
             }).error(function(data,status){
                 alert("get details error!")
                 console.log("error! status:"+status);
             })
     }

     return {
        getDetails: function(action){
         console.log('entered the function')
            return getDetails(action);
        }
     }*/

});