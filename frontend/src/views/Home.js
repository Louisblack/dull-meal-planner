import React, {Fragment, useState, useEffect} from "react";

import Hero from "../components/Hero";
import Greeting from "../components/Greeting";
import GetName from "../components/GetName";
import {useAuth0} from "@auth0/auth0-react";
import {getConfig} from "../config";
import MealsForTheWeek from "../components/MealsForTheWeek";
import AllMeals from "../components/AllMeals";

const { apiOrigin } = getConfig();

const Home = () => {
  const {
    isAuthenticated,
    getAccessTokenSilently,
  } = useAuth0();

  const [state, setState] = useState({
    user: null,
  });

  async function getUser() {
    if (isAuthenticated) {
      const token = await getAccessTokenSilently();

      try {
        const response = await fetch(`${apiOrigin}/user`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        const user = await response.json();

        setState({
          ...state,
          user,
        });
      } catch (error) {
        console.error(error);
      }
    }
  }

  useEffect(() => {
    getUser();
  }, [])

  function updateUserCallback() {
    getUser();
  }

  function User({user}) {
    if (user && user.name) {
      return <Greeting user={user} />;
    }
     else {
       return <GetName updateUserCallback={updateUserCallback} />;
    }
  }

  function Meals({user}) {
    if (user && user.name) {
      return (
        <Fragment>
          <MealsForTheWeek user={user} />
          <hr />
          <AllMeals user={user} />
        </Fragment>
      )
    }
  }

  return (
    <Fragment>
      <Hero />
      { isAuthenticated ?
        (<div className="text-center">
        <User user={state.user} />
        <Meals user={state.user} />
      </div>) : (<div/>)
      }
    </Fragment>
  )
}

export default Home;
