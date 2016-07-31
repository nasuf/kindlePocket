angular.module('detail.service',[])
.factory('detailService', function($q, $http){

    return {
        getDetails : function(){
            var deferred = $q.defer();
            var path = 'http://localhost:9090/KindlePocket/getDetails';
            var promise = $http.get(path).then(function(response){
                return response;
            }, function(response){
                return response;
            });
            return promise;
        },

        sendMail : function(bookId){
             return $http({
                 method: 'GET',
                 url: 'http://localhost:9090/KindlePocket/sendMailMessage?bookId='+bookId,
                 // pass in data as strings
                 //data: $.param(params),
                 headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
             }).success(function(data,status){
                 console.log("success! status:"+status);
                 alert("sendMail successfully!")
             }).error(function(data,status){
                 alert("sendMail error!")
                 console.log("error! status:"+status);
             })
        }
    }

});