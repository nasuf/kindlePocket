/**
 * 
 */
var app = new Vue({
	
	el: "#details",
	
	data: {
		getDetailsUrl: "/KindlePocket/getDetails",
		sendMailUrl: "/KindlePocket/sendMailMessage",
		sendBookCommentUrl: "/KindlePocket/sendBookComment",
		sendSuggestionUrl: "/KindlePocket/sendSuggestion",
		detail: {
			title:'',
			author:'',
			size:'',
			format:'',
			kindleMailTimes:''
		},
		details: [],
		loading: true,
		searchBarDisplay: false,
		searchContent:'',
		subscriberOpenId: '',
		comment: {
			bookId: '',
			subscriberOpenId: '',
			content: ''
		},
		suggestion:{
			subscriberOpenId: '',
			content: ''
		}
	},
	
	methods: {
		
		toggleLoading: function(show) {
        	this.loading = show
        },
		
		getDetails: function() {
			//var toggle =  this.toggleLoading(false);
			//var rs = this.details;
			var thiz = this;
			// jquery
			 $.get(this.getDetailsUrl + "?subscriberOpenId=" + this.subscriberOpenId)
			    .success(function(result) { 
			    	thiz.details = result;
			    	/*var r = result;
			    	for(var i in r) {
			    		rs.push(r[i]);
			    	}*/
			    	thiz.loading = false;
			 });
			// axios
			/*axios.get(this.getDetailsUrl)
		     .then((response) => {
				this.details = response.data;
			})*/
			
			// vue-resource
			/*this.$http.get(this.getDetailsUrl, { emulateJSON: true })
			.then((response) => {
				this.details = response.data;
			})*/
		},
		
		sendMail: function(id) {
			//jquery
			$.get(this.sendMailUrl + "?bookId=" + id + "&subscriberOpenId=" + this.subscriberOpenId)
				.success(function(result){
					if (result && result == true) {
						Materialize.toast('推送请求已发送！请在个人信息页面中查看推送状态', 4000);
					} else {
						Materialize.toast('您尚未绑定邮箱信息，即将跳转到绑定界面', 4000, '', function(){
							window.location.href = "/KindlePocket/toBindingPage?subscriberOpenId=" + this.subscriberOpenId;
						});
					}
				})
			
			// axios
			/*axios.get(this.sendMailUrl,{
			    params: {
			        bookId: id
			      }
			    })
		    .then((response) => {
			})*/
			
			// vue-resource
			/*this.$http.get(this.sendMailUrl + "?bookId=" + id , { emulateJSON: true })
			.then((response) => {
				console.log(response.data);
			})*/
		},
		
		sendProblems: function (){
	        Materialize.toast('Got it !', 4000);
	    },
	    
	    process: function(number) {
	    	if(Math.ceil(number) / 1024 / 1024 > 1) {
	    		return (number/1024/1024).toFixed(1) + "Mb";
	    	} else {
	    		return Math.ceil(number/1024) + "kb";
	    	}
	    	return number;
	    },
	    
	    toggleSearchBar: function() {
	    	this.searchBarDisplay = !this.searchBarDisplay;
	    },
	    
	    search: function(content) {
	    	this.toggleSearchBar();
	    	this.loading = true;
	    	var thiz = this;
	    	thiz.loading = true;
	    	var searchUrl = "/KindlePocket/getDetails?inPageSearch=true&keyWords=" + content;
	    	 $.get(searchUrl)
			    .success(function(result) { 
			    	thiz.details = result;
			    	thiz.loading = false;
			 });
	    },
	    
	    getSubscriberOpenId: function() {
		  var strCookie = document.cookie; 
		  var arrCookie = strCookie.split("; "); 
		  for(var i=0; i<arrCookie.length; i++) { 
		    var arr = arrCookie[i].split("="); 
		    if(arr[0] == "subscriberOpenId"){
		      this.subscriberOpenId = unescape(arr[1]);
		    }
		  } 
		},
	    
	    saveBookId: function(id) {
	    	this.comment.bookId = id;
	    },
	    
	    sendBookComment: function() {
	    	this.comment.subscriberOpenId = this.subscriberOpenId;
	    	$.post(this.sendBookCommentUrl, this.comment)
		    .success(function(result) { 
		    	 if(result && result == true) {
		    		 Materialize.toast('评论发送成功', 4000);
		    	 }
		    })
		    .error(function(result){
		    	Materialize.toast('评论发送失败', 4000);
		    })
	    },
	    
	    sendSuggestion: function() {
	    	this.suggestion.subscriberOpenId = this.subscriberOpenId;
	    	$.post(this.sendSuggestionUrl, this.suggestion)
		    .success(function(result) { 
		    	 if(result && result == true) {
		    		 Materialize.toast('意见发送成功', 4000);
		    	 }
		    })
		    .error(function(result){
		    	Materialize.toast('意见发送失败', 4000);
		    })
	    },
	    
	    getFormat: function(title) {
	    	return title.split('\.')[title.split('\.').length-1];
	    }
	    
	},
	
	created: function() {
		this.getSubscriberOpenId();
		this.getDetails();
	}
	
})