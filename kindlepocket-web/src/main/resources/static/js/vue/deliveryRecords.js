/**
 * 
 */
var app = new Vue({
	
	el: '#deliveryRecords',
	
	data: {
		getDeliveryRecordsUrl: '/KindlePocket/getDeliveryRecords',
		records: []
	},
	
	methods: {
		getDeliveryRecords: function() {
//			this.$http.get(this.getDeliveryRecordsUrl, { emulateJSON: true })
//			.then((response) => {
//				this.records = response.data;
//			})
			axios.get(this.getDeliveryRecordsUrl)
			     .then((response) => {
					this.records = response.data;
				  })
			 
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
	}
})