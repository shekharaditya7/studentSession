import React from 'react';
import logo from './logo.svg';
import './App.css';
import {
  BrowserRouter,
  NavLink,
  Link,
  Switch,
  Route
} from "react-router-dom";

import SessionList from './Components/SessionList.js'
import StudentProfile from './Components/StudentProfile.js'
import Admin from './Components/Admin.js'


function App() {
  return (
    <div className="App">
      <header className="App-header">
      <p> Welcome! </p>
        <BrowserRouter>
          <Switch>
          <Route exact path="/admin/user/:id" component={StudentProfile}/>
          <Route exact path="/admin" component={Admin} />
          <Route exact path="/user/:id" component={StudentProfile} />
          </Switch>
</BrowserRouter>
        
      </header>
    </div>
  );
}

export default App;
