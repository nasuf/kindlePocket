/**
 * 
 */
var app = new Vue({
	
	el: '#deliveryRecords',
	
	data: {
		getDeliveryRecordsUrl: '/KindlePocket/getDeliveryRecords',
		getSubscriberInfoUrl: '/KindlePocket/getSubscriberInfo',
		getCommentsUrl: '/KindlePocket/getCommentsInfo',
		getSuggestionsUrl: '/KindlePocket/getSuggestions',
		records: [],
		userInfo: {
			userName:'',
			email:'',
			kindleEmail:''
		},
		subscriberOpenId: '',
		comments: [],
		suggestions: []
	},
	
	methods: {
		getDeliveryRecords: function() {
			 var thiz = this;
			 $.get(this.getDeliveryRecordsUrl)
			    .success(function(result) { 
			    	//var r = JSON.parse(result.body);
			    	thiz.records = JSON.parse(result.body);
			    	/*for(var i in r) {
			    		rs.push(r[i]);
			    	}*/
			 });
			//vue-resource
		/*	this.$http.get(this.getDeliveryRecordsUrl, { emulateJSON: true })
			.then((response) => {
				this.records = response.data;
			})	*/
			
			// axios
			/*axios.get(this.getDeliveryRecordsUrl)
			     .then((response) => {
					this.records = response.data;
				  }) */
			 
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
		
		getCommentRecords: function() {
			var thiz = this;
			$.get(this.getCommentsUrl + "?subscriberOpenId=" + this.subscriberOpenId)
		    .success(function(result) { 
		    	 thiz.comments = JSON.parse(result.body);
		    })
		},
		
		getSuggestionRecords: function() {
			var thiz = this;
			$.get(this.getSuggestionsUrl + "?subscriberOpenId=" + this.subscriberOpenId)
		    .success(function(result) { 
		    	 thiz.suggestions = JSON.parse(result.body);
		    })
		},
		
		getSubscriberInfo: function() {
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
					 this.subscriberInfo = response.data;
				 })*/
		},
		
		renderStatus: function(originalStatus){
			
            if(originalStatus == '1'){
                return '✅';
            } else {
                return '❌';
            }
        },
		renderDate: function(millisecond) {
			var date = new Date(millisecond);
			return date.getFullYear() 
					+ "-" + (date.getMonth() + 1)
					+ "-" + date.getDate() 
					+ " " + date.getHours() 
					+ ":" + date.getMinutes()
					+ ":" + date.getSeconds();
		}
		
	},
	
	created: function() {
		this.getSubscriberOpenId();
		this.getDeliveryRecords();
		this.getSubscriberInfo();
		this.getCommentRecords();
		this.getSuggestionRecords();
	}
})