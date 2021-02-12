import React from 'react';

const StudentList = (props) => {
	return(
		<div>
		<h3> List of Students </h3>
		<ul>
		{ props.students.map((student, index) => 
			<li>
			<div key={index}>
			{student.name}
			</div>
			</li>

		 )}
		 </ul>
		 <br/>
		 <br/>
		</div>
	)
};

export default StudentList;