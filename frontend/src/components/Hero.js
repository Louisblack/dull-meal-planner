import React from "react";

import logo from "../assets/logo.svg";
import {useAuth0} from "@auth0/auth0-react";

const Hero = () => {
  const {
    isAuthenticated,
    loginWithRedirect
  } = useAuth0();
return (
  <div className="text-center hero">
    <h1 className="mb-4">Dull Meal Planner</h1>

    <p className="lead">
      <span>A website to take the decisions out of planning the weeks meals.</span>
      < br />
      {!isAuthenticated ?
        <span>To get started, <a className="nav-item" onClick={() => loginWithRedirect()}>login or sign up</a>.</span> :
        <span/>}
    </p>

  </div>);
};

export default Hero;
