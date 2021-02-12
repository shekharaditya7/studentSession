import React from 'react';
import axios from 'axios';
import {api_sessions} from '../constants/index.js';
import {api_students} from '../constants/index.js';
import StudentList from './StudentList.js'
import {useParams} from "react-router-dom";
import { Redirect } from "react-router-dom";
import Admin from "./Admin.js"



class StudentProfile extends React.Component<{ student: string }>{

state = {
	username:this.props.student,
	isadmin:'',
	sessions:[],
	name:'',
	sessionList:[]
};


removeHandler = (id, slot, date) => {
	

	let hr = this.giveHours(slot)
	let temp = new Date(date)
	temp.setHours(hr)
	let cur = new Date()

	console.log(cur.getTime())
	console.log(temp.getTime())

	if(cur.getFullYear()===temp.getFullYear() && cur.getMonth()===temp.getMonth() && cur.getDate()===temp.getDate() && cur.getTime()>=temp.getTime() ){
			alert("Can't Remove Past Session")
			return;
		
	}

	else{

		console.log(id);
	let updated = this.state.sessions;
	const index = updated.indexOf(id);
	updated.splice(index, 1);

	const user = {
		username: this.state.username,
		name : this.state.name,
		isadmin : this.state.isadmin,
		sessions : updated
}

	axios.patch(api_students+this.state.username+"/", user).then(res =>{
		this.setState({
			sessions : updated
		})
	}).catch(error => {
			alert("Couldnt Remove")
	})
	}
}


giveHours = (hr) => {
	if(hr=='1')
		return 8
	else if(hr=='2')
		return 10
	else if(hr=='3')
		return 14
}



addHandler = (id, slot, date, all) => {
	console.log(id);
	let updated = this.state.sessions;

	let hr = this.giveHours(slot)
	let temp = new Date(date)
	temp.setHours(hr)
	let cur = new Date()

	console.log(temp.getHours())
	console.log(cur.getHours())

	if(cur.getFullYear()===temp.getFullYear() && cur.getMonth()===temp.getMonth() && cur.getDate()===temp.getDate()){
		if(cur.getTime()>=temp.getTime()){
			alert("Can't Add Past Session")
			return;
		}
	}
	

	var ok = true;
	for(var i=0; i<all.length; i++){
		if(slot==all[i].slots && date==all[i].session_date){
				ok = false;
				break;
		}
	}
	console.log(ok);

	if(ok){

	updated.push(id);
	const user = {
		username: this.state.username,
		name : this.state.name,
		isadmin : this.state.isadmin,
		sessions : updated
}
	axios.patch(api_students+this.state.username+"/", user).then(res =>{

		this.setState({
			sessions : res.data.sessions
				})
			}).catch(error =>{
		alert("Couldnt Add");
		
		})
	}
	else{
		alert("Can't Add Session with Same Slot and Date");
	}
}


showSlot(slot){
	if(slot==1)
		return "9 am to 11 am";
	else if(slot==2)
		return "11 am to 1 pm";
	else if(slot==3)
		return "3 pm to 5 pm";
}


componentDidMount(){

	axios.get(api_students+this.state.username+"/").then(res => {

		console.log(res.data);
		const user = res.data;
		this.setState({ 
			username : user.username,
			isadmin : user.isadmin,
			name : user.name,
			sessions : user.sessions,
			isadmin : user.isadmin
		});

		axios.get(api_sessions).then(res => {
		this.setState({
			sessionList : res.data
		})
	})


	}).catch(error => {
		alert("Invalid ID")

	})

}


handleChange = event => {
    let nam = event.target.name;
    let val = event.target.value;
   		this.setState({[nam]:val});
   	}




render() {


	if(this.state.isadmin)
			return (<Redirect to="/admin"/>)


	function check(sub, val){
		for(var i=0; i<sub.length; i++){
			if(sub[i]==val) return true;
		}
		return false;
	}

	var notSubscribed = [];
	var allid = [];
	var all = this.state.sessionList;
	var sub = this.state.sessions;
	var subList = [];
	var subdetail = [];
	var k =0;
	



	all.map((sessiont) => 
		allid[k++] = sessiont.id
	)
	
	var k=0;
	var j=0;
	var cur = new Date();
	for(var i = 0; i<allid.length; i++){

		var temp = new Date(all[i].session_date);
		if(temp.getFullYear()>=cur.getFullYear() && temp.getMonth() >= cur.getMonth() && temp.getDate() >= cur.getDate()){
		if(check(sub, allid[i])==false){
			notSubscribed[k] = all[i];
			k++;
		}

		else{
			subList[j] = all[i];
			j++;
		}
		}
	}
	


	var cur = new Date();


	

	return (
		
		<div>


		<h1> Profile of {this.state.name}  </h1>

		<h3> Already Subscribed sessions </h3>
		<ol>
		{ subList.map((session) =>

					<li key={session.id}>
					Title:{session.title} <br/>  
					Slot No : {this.showSlot(session.slots)} <br/> 
					Date : {session.session_date} <br/>
					<button onClick={() => this.removeHandler(session.id, session.slots, session.session_date)}> Remove </button>
					</li>						
		 )}
		 </ol>
		

		 <br/>
		 <br/>
		<h3> You can Subscribe </h3>

		<ol>
		{ notSubscribed.map((session) => 
					<li key={session.id} >
						Title:{session.title} <br/>
						Slot No : {this.showSlot(session.slots)}  <br/>
						Date : {session.session_date} 
						<button onClick={() => this.addHandler(session.id, session.slots, session.session_date, subList)}> Add </button>
					
					</li>						
		 )}
		 </ol>


		</div>
		)
	}

}



export default () => {
	const { id } = useParams();
	return (
		<StudentProfile student={ id }/>
		)
}