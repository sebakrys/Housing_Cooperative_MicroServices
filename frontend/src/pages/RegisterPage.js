import React, { useState } from "react";
import ReactDOM from "react-dom";
import axios from "axios";

import "./loginStyles.css";
import BuildingsPage from "./BuildingsPage";
import BuildingService from "../services/BuildingService";
import registerService from "../services/RegisterService";

function RegisterPage() {
    // React States
    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);

    // User Login info
    const database = [
        {
            username: "user1",
            password: "pass1"
        },
        {
            username: "user2",
            password: "pass2"
        }
    ];

    const errors = {
        uname: "invalid username",
        pass: "invalid password"
    };

    const handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();

        var { firstName, lastName, email, telephone, login, password, passwordRepeat} = document.forms[0];

        if(password.value==passwordRepeat.value){
            var userJSON = {
                firstName: firstName.value,
                lastName: lastName.value,
                email: email.value,
                telephone: telephone.value,
                login: login.value,
                password: password.value,
                enabled: 'true',
                dept: 'true',
                valid: 'true'
            };

            console.log("REGISTER"+firstName.value+" "+lastName.value);

            var a = registerService.registerUser(userJSON);


            console.log("REGISTERED "+a);

        }else{
            setErrorMessages({ name: "passwordRepeat", message: "Passwords don't match" });
        }
        //todo axios.get(BUILDING_REST_URL+"getBuildings");//przyklad z buildign service
        //todo wyslac na serwer do endpointa z register i na podstawie response wysweitlic blad lub sukces

        // Compare user info
        /*
        if (userData) {
            if (userData.password !== pass.value) {
                // Invalid password
                setErrorMessages({ name: "pass", message: errors.pass });
            } else {
                setIsSubmitted(true);
            }
        } else {
            // Username not found
            setErrorMessages({ name: "uname", message: errors.uname });
        }
        */
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
                    <label>First Name </label>
                    <input type="text" name="firstName" required />
                    {renderErrorMessage("firstName")}
                </div>
                <div className="input-container">
                    <label>Last Name </label>
                    <input type="text" name="lastName" required />
                    {renderErrorMessage("lastName")}
                </div>
                <div className="input-container">
                    <label>Email </label>
                    <input type="text" name="email" required />
                    {renderErrorMessage("email")}
                </div>
                <div className="input-container">
                    <label>Telephone </label>
                    <input type="text" name="telephone" required />
                    {renderErrorMessage("telephone")}
                </div>
                <div className="input-container">
                    <label>Login </label>
                    <input type="text" name="login" required />
                    {renderErrorMessage("login")}
                </div>
                <div className="input-container">
                    <label>Password </label>
                    <input type="password" name="password" required />
                    {renderErrorMessage("password")}
                </div>
                <div className="input-container">
                    <label>Repeat Password </label>
                    <input type="password" name="passwordRepeat" required />
                    {renderErrorMessage("passwordRepeat")}
                </div>
                <div className="button-container">
                    <input type="submit" />
                </div>
            </form>
        </div>
    );

    return (
        <div className="app">
            <div className="register-form">
                <div className="title">Sign In</div>
                {isSubmitted ? <div>User is successfully registered</div> : renderForm}
            </div>
        </div>
    );
}

const rootElement = document.getElementById("root");
export default RegisterPage;