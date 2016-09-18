angular.module('detail.service',[])
.factory('detailService', function($q, $http){

    return {
        getDetails : function(){
            var deferred = $q.defer();
            //var path = 'http://localhost:9090/KindlePocket/getDetails';
            var path = 'http://kindlepocket.nasuf.cn/KindlePocket/getDetails';
            var promise = $http.get(path).then(function(response){
                return response;
            }, function(response){
                return response;
            });
            return promise;
        },

        sendMail : function(param){
             return $http({
                 method: 'POST',
                 //url: 'http://localhost:9090/KindlePocket/sendMailMessage',
                 url: 'http://kindlepocket.nasuf.cn/KindlePocket/sendMailMessage',
                 // pass in data as strings
                 data: $.param({"bookId":param}),
                 headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
             }).success(function(data,status){
                 console.log("success! status:"+status);
                 //alert("sendMail successfully!")
             }).error(function(data,status){
                 //alert("sendMail error!")
                 console.log("error! status:"+status);
             })
        }
    }

});