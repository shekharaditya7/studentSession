import React from 'react';
import axios from 'axios';
import {api_sessions} from '../constants/index.js';
import {api_students} from '../constants/index.js';
import StudentList from './StudentList.js'
import StudentProfile from './StudentProfile.js'
import { Redirect } from "react-router-dom";
import { Link } from "react-router-dom";

export default class Admin extends React.Component{
state = {
	id:'',
	title : '',
	description: '',
	slots : '',
	session_date : '',
	sessions:[]
};



removeHandler = (id) => {
	console.log(id);

	axios.delete(api_sessions+id+"/").then(res =>{
		alert("Session Removed");
		this.getList();	
	}).catch(error => {
			alert("Couldnt Delete Session")
	})
}


getList = () => {
	axios.get(api_sessions).then(res => {
		console.log(res);
		this.setState({sessions : res.data});
	})
}


componentDidMount(){
	this.getList();
}


handleSubmit = event => {
	event.preventDefault();

	const session = {
		title: this.state.title,
		description : this.state.description,
		slots : this.state.slots,
		session_date : this.state.session_date
}

	axios.post(api_sessions, session).then( res => {
		console.log(res.data);
		alert("Session Created");
		this.getList();	
	}).catch(error => {
			alert("Couldnt Create Session")
	})

}

showSlot(slot){
	if(slot==1)
		return "9 am to 11 am";
	else if(slot==2)
		return "11 am to 1 pm";
	else if(slot==3)
		return "3 pm to 5 pm";
}



handleChange = event => {
    let nam = event.target.name;
    let val = event.target.value;
    
    if(nam==="session_date"){
    	let selected_date = new Date(val);
    	let curr_date = new Date();

    	if(curr_date >= selected_date )
    		{
    			alert("Can't select a Past Date");
    			this.setState({session_date:''});
    		}
    		
    	else{
    	this.setState({[nam]:val});		
    	}

   	}
   	else{
   		this.setState({[nam]:val});
   	}

    

}





render() {

	return (

		<div>
		

		

		<form onSubmit={this.handleSubmit}>
			<label> Title: <input onChange={this.handleChange} type="text" name="title"/> </label>
			<label> Description : <input type="text" onChange={this.handleChange} name="description"/> </label>
			<label> Slot: 
			
			<select
          name="slots"
          onChange={this.handleChange}>
          <option value="1">9 am to 11 am (Slot 1)</option>
          <option value="2">11 am to 1 pm (Slot 2)</option>
          <option value="3">3 pm to 5 pm (Slot 3)</option>
        
        </select>
        </label>
			<label> Title: <input type="date" name="session_date" onChange={this.handleChange} /> </label>
			<button type="submit"> Create Session </button>

		</form>



		<h1> List of Sessions </h1>
		<div>
		<ol>
		
		{ this.state.sessions.map((session, index) => 
					<li>
					<div key={session.id}>
						Title:{session.title} <br/>   

						Slot : {this.showSlot(session.slots)} <br/>
						Date: {session.session_date} <br/>
						<button onClick={() => this.removeHandler(session.id)}> Delete Session </button>
						<StudentList students={session.student_set} />
					</div>						
					</li>
		 )}
		
		 </ol>
		 </div>
	</div>
	)
}

}