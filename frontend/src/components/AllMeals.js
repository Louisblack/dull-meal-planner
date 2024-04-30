import {Fragment, useEffect, useState} from "react";
import {useAuth0} from "@auth0/auth0-react";
import {getConfig} from "../config";
import {Button, Form, FormGroup, Input, Label} from "reactstrap";
const { apiOrigin = "http://localhost:8080", audience } = getConfig();



const AllMeals = ({user}) => {

  const {
    getAccessTokenSilently,
  } = useAuth0();

  const [state, setState] = useState({
    meals: [],
    newMeal: {},
    error: null,
  });

  async function getMeals() {
    setState({
      ...state,
      error: null,
    });
    const token = await getAccessTokenSilently();
    try {
      const response = await fetch(`${apiOrigin}/meals`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

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

  async function addMeal() {
    const token = await getAccessTokenSilently();
    try {
      const response = await fetch(`${apiOrigin}/meals`, {
        method: "POST", // or 'PUT'
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(state.newMeal),
      });
      state.newMeal = {};
      getMeals();
    } catch (error) {
      console.error("Error:", error);
    }
  }

  function setNewMealName(value) {
    state.newMeal.name = value;
  }

  function Meal({meal}) {
    return <li key={meal.name}>{meal.name}</li>
  }

  function Meals() {
    const meals = (state.meals).map(meal => <Meal meal={meal} />);
    return <ul>{meals}</ul>
  }

  function AddMeal() {
    return (
    <Form onSubmit={event => event.preventDefault() || addMeal()}>
      <FormGroup>
        <Label for="name">
          Name of the Meal
        </Label>
        <Input
          id="name"
          name="name"
          type="text"
          value={state.newMeal.name}
          onChange={(e) => {
            setNewMealName(e.target.value);
          }}
        />

      </FormGroup>
      <Button onClick={(e) => e.preventDefault() || addMeal()}>
        Submit
      </Button>
    </Form>
    );
  }

  return (
    <Fragment>
      <h2>All Meals</h2>
      <Meals />
      <AddMeal />
    </Fragment>
  );
}

export default AllMeals;