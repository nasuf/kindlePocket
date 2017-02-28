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
			var rs = this.details;
			// jquery
			 $.get(this.getDetailsUrl)
			    .success(function(result) { 
			    	var r = result;
			    	for(var i in r) {
			    		rs.push(r[i]);
			    	}
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
			Materialize.toast('Send Successfully!', 4000);
			
			//jquery
			$.get(this.sendMailUrl + "?bookId=" + id)
			
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
	    }
	},
	
	created: function() {
		this.getDetails();
	}
	
})