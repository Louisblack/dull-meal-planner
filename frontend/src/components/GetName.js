import {Button, Form, FormGroup, Input, Label} from "reactstrap";
import {useAuth0} from "@auth0/auth0-react";

const GetName = ({updateUserCallback}) => {
  const {
    getAccessTokenSilently,
  } = useAuth0();

  let name;

  function setName(newName) {
    name = newName;
  }

  async function updateName() {
    try {
      const token = await getAccessTokenSilently();
      const response = await fetch("http://localhost:8080/user", {
        method: "POST", // or 'PUT'
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({name}),
      });
      updateUserCallback()
    } catch (error) {
      console.error("Error:", error);
    }
  }

  return <div>
    <p>Let me know you're name so we're properly introduced.</p>
    <p>Then we can start adding the meals you like.</p>
    <Form onSubmit={(e) => e.preventDefault() || updateName()}>
      <FormGroup>
        <Label for="name">
          Your Name
        </Label>
        <Input
          id="name"
          name="name"
          type="text"
          value={name}
          onChange={(e) => {
            setName(e.target.value);
          }}
        />

      </FormGroup>
      <Button onClick={(e) => e.preventDefault() || updateName()}>
        Submit
      </Button>
    </Form>
  </div>;
}

export default GetName;
