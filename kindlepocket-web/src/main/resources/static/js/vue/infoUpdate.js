/**
 * 
 */
var app = new Vue({
	
	el: "#infoUpdate",
	
	data: {
		getSubscriberInfoUrl: "/KindlePocket/getSubscriberInfo",
		updateUrl: "/KindlePocket/updateSubscriberInfo",
		userInfo: {
			userName:'',
			phone:'',
			email:'',
			emailPwd:'',
			kindleEmail:'',
			subscriberOpenId:''
		},
		updatedFlag: false,
		emailTypes: ['163','126','qq']
	},
	
	methods: {
		loadUserInfo: function() {
			var user = this.userInfo;
			//jquery
			 $.get(this.getSubscriberInfoUrl)
			    .success(function(result) { 
			    	 user.userName = result.userName;
					 user.phone = result.phone;
					 user.email = result.email;
					 user.kindleEmail = result.kindleEmail;
			    })
			    
			
			// axios
			/*axios.get(this.getSubscriberInfoUrl)
			 .then((response) => {
				 this.userInfo.userName = response.data.userName;
				 this.userInfo.phone = response.data.phone;
				 this.userInfo.email = response.data.email;
				 this.userInfo.kindleEmail = response.data.kindleEmail;
			 })  */
			
			// vue-resource
			/*this.$http.post(this.getSubscriberInfoUrl, this.userInfo, { emulateJSON: true })
			.then((response) => {
				this.userInfo.userName = response.data.userName;
				this.userInfo.phone = response.data.phone;
				this.userInfo.email = response.data.email;
				this.userInfo.kindleEmail = response.data.kindleEmail;
			}) */
		
		},
		
		update: function() {
			var emailType = this.userInfo.email.split('@')[1].split('\.')[0];
			var emailTypeValidated = false;
			for (var i in this.emailTypes) {
				if (emailType == this.emailTypes[i]) {
					emailTypeValidated = true;
					break;
				} else 
					continue;
			}
			if(this.userInfo.userName.trim() == '' 
				|| this.userInfo.phone.trim() == '' 
				|| this.userInfo.email.trim() == ''
				|| this.userInfo.emailPwd.trim() == ''
				|| this.userInfo.kindleEmail.trim() == '') {
				alert("信息不完整啊！");
				return ;
			} else if (!emailTypeValidated) {
				alert('个人邮箱类型[ '+ emailType +' ]不支持。暂支持163,126和qq邮箱');
				return ;
			} else {
				//jquery
				 $.post(this.updateUrl, this.userInfo)
				    .success(function(result) { 
				    	 if(result && result == true) {
				    		 alert('更新成功！');
				    	 }
				    })
				    .error(function(result){
				    	alert('更新失败！');
				    })
				    
				    
				// axios
			/*	axios.post(this.updateUrl, this.userInfo,{
					 transformRequest: [function (data) {
					    // Do whatever you want to transform the data
					    let ret = ''
					    for (let it in data) {
					      ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
					    }
					    return ret
					  }],
					  headers: {
					    'Content-Type': 'application/x-www-form-urlencoded'
					  }
				  })
				  .then(function (response) {
					    alert("更新成功");
				  })
				  .catch(function (error) {
					  alert("更新失败");
				  });  	*/
				
				// vue-resource
				/*this.$http.post(this.updateUrl, this.userInfo, {emulateJSON:true})
				.then((response) => {
					this.updatedFlag = true;
					alert("更新成功！");
				}) */
			}
		},
		
		getSubscriberOpenId: function() {
		  var strCookie=document.cookie; 
		  var arrCookie=strCookie.split("; "); 
		  for(var i=0;i<arrCookie.length;i++){ 
		    var arr=arrCookie[i].split("="); 
		    if(arr[0]=="subscriberOpenId"){
		      this.userInfo.subscriberOpenId = unescape(arr[1]);
		    }
		  } 
		}
	},
	created: function() {
		this.getSubscriberOpenId();
		this.loadUserInfo();
	}
})