import {Fragment, useEffect, useState} from "react";
import {useAuth0} from "@auth0/auth0-react";
import {getConfig} from "../config";
import {ListGroup, ListGroupItem} from "reactstrap";
const { apiOrigin } = getConfig();

const MealsForTheWeek = ({user}) => {

  const {
    getAccessTokenSilently,
  } = useAuth0();

  const [state, setState] = useState({
    meals: [],
    error: null,
  });

  async function getMeals() {
    setState({
      ...state,
      error: null,
    });
    const token = await getAccessTokenSilently();
    try {
      const response = await fetch(`${apiOrigin}/meals-for-week`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      if (response.status === 400) {
        setState({
          ...state,
          error: "Add at least 7 meals to get a random meal plan.",
        });
        return;
      }
      const meals = await response.json();

      setState({
        ...state,
        meals,
      });
    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    getMeals();
  }, [])

  function MealList() {
    let entries = Object.entries(state.meals);
    const meals = entries.map(meal => <ListGroupItem key={meal[0]}>{meal[0]} - {meal[1].name}</ListGroupItem>)
    return <ListGroup>{meals}</ListGroup>
  }

  function Meals() {
    if (state.error) {
      return <p>{state.error}</p>;
    } else {
      return (
        <Fragment>
          <h2>Meals for the week</h2>
          <MealList />
        </Fragment>
      );
    }
  }

  return (
    <Fragment>
      <Meals />
    </Fragment>
  );
}

export default MealsForTheWeek;
