import React from 'react';
import logo from './logo.svg';
import './App.css';
import {
  BrowserRouter,
  NavLink,
  Redirect,
  Link,
  Switch,
  Route
} from "react-router-dom";

import SessionList from './Components/SessionList.js'
import StudentProfile from './Components/StudentProfile.js'
import Admin from './Components/Admin.js'






class App extends React.Component{
  
  state = {
    redir : false,
    username : '',
  };

  Login = () =>{
  
  var x = document.getElementById('username').value;
  console.log(x)
  this.setState({redir:true, username:x})
  }


  render(){

   console.log(this.state.username);
   if(this.state.redir==true){
    var user = this.state.username
    return <Redirect to={{
      pathname:'/user/:adi'
    }}/>
   } 

  return (
    <div className="App">
      <header className="App-header">
      <p> Welcome! </p>
   
      <br/>
      
        <BrowserRouter>
          <Switch>
          <Route exact path="/admin" component={Admin} />
          <Route exact path="/user/:id" component={StudentProfile} />
          </Switch>
</BrowserRouter>
        
      </header>
    </div>
  )

  }
}

export default App;
