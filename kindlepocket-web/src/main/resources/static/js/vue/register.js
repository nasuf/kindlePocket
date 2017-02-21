/**
 * 
 */
var app = new Vue({
	
	el: "#register",
	
	data: {
		registerUrl: "/KindlePocket/bindingData",
		registerFlag: false,
		newUserInfo: {
			userName:'',
			phone:'',
			email:'',
			emailPwd:'',
			kindleEmail:''
		}
	},
	
	methods: {
		register: function() {
			if(this.newUserInfo.userName.trim() == '' 
				|| this.newUserInfo.phone.trim() == '' 
				|| this.newUserInfo.email.trim() == ''
				|| this.newUserInfo.emailPwd.trim() == ''
				|| this.newUserInfo.kindleEmail.trim() == '') {
				alert("信息不完整啊！");
				return ;
			} else {
				this.$http.post(this.registerUrl, this.newUserInfo, { emulateJSON: true })
				.then((response) => {
					console.log(response.data);
					if(response.data.isBinded == 1) {
						this.registerFlag = true;
						alert("注册成功！");
					}
				})
			}
		}
	}
})