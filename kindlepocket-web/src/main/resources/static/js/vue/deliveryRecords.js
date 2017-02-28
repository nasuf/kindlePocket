/**
 * 
 */
var app = new Vue({
	
	el: '#deliveryRecords',
	
	data: {
		getDeliveryRecordsUrl: '/KindlePocket/getDeliveryRecords',
		getSubscriberInfoUrl: '/KindlePocket/getSubscriberInfo',
		records: [],
		userInfo: {
			userName:'',
			email:'',
			kindleEmail:''
		}
	},
	
	methods: {
		getDeliveryRecords: function() {
			// jquery
			 var rs = this.records;
			 $.get(this.getDeliveryRecordsUrl)
			    .success(function(result) { 
			    	var r = JSON.parse(result.body);
			    	for(var i in r) {
			    		rs.push(r[i]);
			    	}
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
                return 'success';
            } else {
                return 'failed';
            }
        },
		renderDate: function(millisecond) {
			var date = new Date(millisecond);
			return date.getFullYear() 
					+ "-" + date.getMonth() + 1
					+ "-" + date.getDate() 
					+ " " + date.getHours() 
					+ ":" + date.getMinutes()
					+ ":" + date.getSeconds();
		}
		
	},
	
	created: function() {
		this.getDeliveryRecords();
		this.getSubscriberInfo();
	}
})