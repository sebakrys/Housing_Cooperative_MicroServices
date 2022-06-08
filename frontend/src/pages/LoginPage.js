import React, { useState } from "react";
import ReactDOM from "react-dom";

import "./loginStyles.css";
import BuildingsPage from "./BuildingsPage";
import axios from "axios";
import data from "bootstrap/js/src/dom/data";

function LoginPage() {
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

        var { uname, pass } = document.forms[0];



        // Find user login info
        const userData = database.find((user) => user.username === uname.value);

        console.log(uname.value+" "+pass.value);


        // POST request using axios with set headers

        var userJSON = {
            client_id: "nsai-frontend",
            username: uname.value,
            password: pass.value,
            grant_type: "password",
            client_secret: "59Um92ixKBwThNUDYSk1yHEjavIoHvvh"
        };

        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            /*'Access-Control-Allow-Origin': '*',*/
            /*'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin'*/
        };
        var postResponse = axios.post('http://localhost:8080/realms/resourceServer/protocol/openid-connect/token', userJSON, {headers})
            .then((response) => {
                console.log("RESP:"+response);
                //return postResponse.data;

                return(['ok', data]);
            }, (error) => {
                console.log("EEEE:"+error);
                return(['error', 'Nie zalogowano']);
            });




        // Compare user info
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
                    <label>Username </label>
                    <input type="text" name="uname" required />
                    {renderErrorMessage("uname")}
                </div>
                <div className="input-container">
                    <label>Password </label>
                    <input type="password" name="pass" required />
                    {renderErrorMessage("pass")}
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
                <div className="title">Sign In</div>
                {isSubmitted ? <div>User is successfully logged in</div> : renderForm}
            </div>
        </div>
    );
}

const rootElement = document.getElementById("root");
export default LoginPage;