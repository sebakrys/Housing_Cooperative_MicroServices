import React, { useState } from "react";
import ReactDOM from "react-dom";

import "./loginStyles.css";
import BuildingsPage from "./BuildingsPage";
import axios from "axios";
import RegisterPage from "./RegisterPage";

function AddBuildingPage() {
    // React States
    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);

    const errors = {
        uname: "invalid username",
        pass: "invalid password"
    };

    const handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();

        var { name, city, street, number, postalcode} = document.forms[0];


            var userJSON = {
                nazwa: name.value,
                city: city.value,
                street: street.value,
                buildingNumber: number.value,
                postalCode: postalcode.value
            };

            console.log("addBuilding"+name.value+" "+city.value);

            const headers = {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Access-Control-Allow-Origin': '*'
            };
            axios.post('http://localhost:8000/building-flat-service/addNewBuilding', userJSON, {headers})
                .then((response) => {
                    console.log("RESP:"+response);
                    //return postResponse.data;
                    setIsSubmitted({ name: "name", message: "Pomyślnie dodano" });
                    //todo wiadomosc o pozytywnym zarejestrowaniu
                    //return(['ok', data]);
                }, (error) => {
                    console.log("EEEE:"+error);
                    //return(['error', 'Nie zarejestrowano']);
                    setErrorMessages({ name: "name", message: "Nie zarejestrowano - Błąd z serwera" });
                });
    };

    // Generate JSX code for error message
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );

    // JSX code for login form
    const renderForm = (
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="input-container">
                    <label>Name </label>
                    <input type="text" name="name" required />
                    {renderErrorMessage("name")}
                </div>
                <div className="input-container">
                    <label>City </label>
                    <input type="text" name="city" required />
                    {renderErrorMessage("city")}
                </div>
                <div className="input-container">
                    <label>Street </label>
                    <input type="text" name="street" required />
                    {renderErrorMessage("street")}
                </div>
                <div className="input-container">
                    <label>Number </label>
                    <input type="text" name="number" required />
                    {renderErrorMessage("number")}
                </div>
                <div className="input-container">
                    <label>Postal Code </label>
                    <input type="text" name="postalcode" required />
                    {renderErrorMessage("postalcode")}
                </div>
                <div className="button-container">
                    <input type="submit" />
                </div>
            </form>
        </div>
    );

    return (
        <div className="app">
            <div className="login-form">
                <div className="title">Add Building</div>
                {isSubmitted ? <div>Building successfully added</div> : renderForm}
            </div>
        </div>
    );
}

const rootElement = document.getElementById("root");
export default AddBuildingPage;