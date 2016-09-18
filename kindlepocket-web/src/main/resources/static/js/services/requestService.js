angular.module('request.services',[])
.factory('requestService', function($http){

     console.log('entered the service')
     var req = function(action, params){
         return $http({
                 method: 'POST',
                 url: 'http://kindlepocket.nasuf.cn/KindlePocket/'+action+'',
                 //url: 'http://localhost:9090/KindlePocket/'+action+'',
                 // pass in data as strings
                 data: $.param(params),
                 headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
             }).success(function(data,status){
                 console.log("success! status:"+status);
                 alert("binding successfully!")
             }).error(function(data,status){
                 alert("binding error!")
                 console.log("error! status:"+status);
             })
     }

     return {
        req: function(action,params){
         console.log('entered the function')
            return req(action,params);
        }
     }

});