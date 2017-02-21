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
		updatedFlag: false
	},
	
	methods: {
		loadUserInfo: function() {

			this.$http.post(this.getSubscriberInfoUrl, this.userInfo, { emulateJSON: true })
			.then((response) => {
				this.userInfo.userName = response.data.userName;
				this.userInfo.phone = response.data.phone;
				this.userInfo.email = response.data.email;
				this.userInfo.kindleEmail = response.data.kindleEmail;
			})
		
		},
		
		update: function() {
			if(this.userInfo.userName.trim() == '' 
				|| this.userInfo.phone.trim() == '' 
				|| this.userInfo.email.trim() == ''
				|| this.userInfo.emailPwd.trim() == ''
				|| this.userInfo.kindleEmail.trim() == '') {
				alert("信息不完整啊！");
				return ;
			} else {
				this.$http.post(this.updateUrl, this.userInfo, {emulateJSON:true})
				.then((response) => {
					this.updatedFlag = true;
					alert("更新成功！");
				})
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