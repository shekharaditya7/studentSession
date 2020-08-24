import React from 'react';
import axios from 'axios';
import {api_sessions} from '../constants/index.js';
import {api_students} from '../constants/index.js';
import StudentList from './StudentList.js'

export default class SessionList extends React.Component{
state = {
	sessions : []
};


getList = () => {
	axios.get(api_sessions).then(res => {
		console.log(res);
		this.setState({sessions : res.data});
	})
}


componentDidMount(){
	this.getList();
}

render() {
	return (
		<div>
		<h1> List of Sessions </h1>
		<ol>
		
		{ this.state.sessions.map((session, index) => 
					<li>
					<div key={index}>
						{session.title} {session.slots} {session.session_date} 
						<StudentList students={session.student_set} />
					</div>						
					</li>
		 )}
		
		 </ol>
		</div>
	)
}

}