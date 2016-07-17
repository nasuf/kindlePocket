angular.module('binding.services',[])
.factory('bindingDataService', function($http){

     console.log('entered the service')
     var binding = function(action, params){
         return $http({
                 method: 'POST',
                 url: 'http://c474fd41.ngrok.io/KindlePocket/'+action+'',
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
        binding: function(action,params){
         console.log('entered the function')
            return binding(action,params);
        }
     }

});