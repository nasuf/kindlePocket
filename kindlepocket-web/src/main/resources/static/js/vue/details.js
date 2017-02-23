/**
 * 
 */
var app = new Vue({
	
	el: "#details",
	
	data: {
		getDetailsUrl: "/KindlePocket/getDetails",
		sendMailUrl: "/KindlePocket/sendMailMessage",
		detail: {
			title:'',
			author:'',
			size:'',
			format:'',
			kindleMailTimes:''
		},
		details: []
	},
	
	methods: {
		getDetails: function() {
			axios.get(this.getDetailsUrl)
		     .then((response) => {
				this.details = response.data;
			})
//			this.$http.get(this.getDetailsUrl, { emulateJSON: true })
//			.then((response) => {
//				this.details = response.data;
//			})
		},
		
		sendMail: function(id) {
			Materialize.toast('Send Successfully!', 4000);
			axios.get(this.sendMailUrl,{
			    params: {
			        bookId: id
			      }
			    })
		    .then((response) => {
				//console.log(response.data);
			})
//			this.$http.get(this.sendMailUrl + "?bookId=" + id , { emulateJSON: true })
//			.then((response) => {
//				console.log(response.data);
//			})
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
	    }
	},
	
	created: function() {
		this.getDetails();
	}
	
})