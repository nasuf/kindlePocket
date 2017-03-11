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
		details: [],
		loading: true,
		searchBarDisplay: false,
		searchContent:'',
		subscriberOpenId:''
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
			 $.get(this.getDetailsUrl)
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
	    }
	},
	
	created: function() {
		this.getDetails();
	}
	
})